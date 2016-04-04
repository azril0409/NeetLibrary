package library.neetoffice.com.codeutil;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.neetoffice.com.codeutil.core.AutoFitTextureView;
import library.neetoffice.com.codeutil.core.CameraFacing;
import library.neetoffice.com.codeutil.core.CompareCameraSizesByArea;
import library.neetoffice.com.codeutil.core.CompareSizesByArea;
import library.neetoffice.com.codeutil.core.DisplayUtils;
import library.neetoffice.com.codeutil.core.SizeUtil;

/**
 * Created by Deo on 2016/3/22.
 */
class CameraManagerV9 implements CameraManager {
    private final Context context;
    private final AutoFitTextureView textureView;
    private boolean autoFit = false;
    private PreviewCallback previewCallback;
    private Handler autoFocusHandler;
    private Camera mCamera;
    private boolean surfaceCreated = false;

    CameraManagerV9(Context context, AutoFitTextureView textureView) {
        this.context = context;
        this.textureView = textureView;
        autoFocusHandler = new Handler();
    }

    @Override
    public void openCamera() {
        if (textureView.isAvailable()) {
            openCamera(0);
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    public void stopCamera() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.cancelAutoFocus();
                mCamera.setOneShotPreviewCallback(null);
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void setAutoFit(boolean autoFit) {
        this.autoFit = autoFit;
    }

    @Override
    public void setPreviewCallback(PreviewCallback previewCallback) {
        this.previewCallback = previewCallback;
    }

    private void openCamera(int cameraId) {
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras == 0) {
            return;
        }
        boolean explicitRequest = cameraId >= 0;
        Camera.CameraInfo selectedCameraInfo = null;
        int index;
        if (explicitRequest) {
            index = cameraId;
            selectedCameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(index, selectedCameraInfo);
        } else {
            index = 0;
            while (index < numCameras) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(index, cameraInfo);
                CameraFacing reportedFacing = CameraFacing.values()[cameraInfo.facing];
                if (reportedFacing == CameraFacing.BACK) {
                    selectedCameraInfo = cameraInfo;
                    break;
                }
                index++;
            }
        }
        if (index < numCameras) {
            mCamera = Camera.open(index);
        } else {
            if (explicitRequest) {
                mCamera = null;
            } else {
                mCamera = Camera.open(0);
                selectedCameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(0, selectedCameraInfo);
            }
        }
        if (mCamera != null) {
            if (autoFit) {
                setAspectRatio();
            }
            showCameraPreview();
        }
    }

    private void setAspectRatio() {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final int displayRotation = windowManager.getDefaultDisplay().getRotation();
        final int sensorOrientation = getDisplayOrientation();
        boolean swappedDimensions = false;
        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    swappedDimensions = true;
                }
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    swappedDimensions = true;
                }
                break;
            default:
        }
        Camera.Size size = mCamera.getParameters().getPreviewSize();
        final Point displaySize = new Point();
        windowManager.getDefaultDisplay().getSize(displaySize);
        int rotatedPreviewWidth = size.width;
        int rotatedPreviewHeight = size.height;
        int maxPreviewWidth = displaySize.x;
        int maxPreviewHeight = displaySize.y;

        if (swappedDimensions) {
            rotatedPreviewWidth = size.height;
            rotatedPreviewHeight = size.width;
            maxPreviewWidth = displaySize.y;
            maxPreviewHeight = displaySize.x;
        }

        if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
            maxPreviewWidth = MAX_PREVIEW_WIDTH;
        }

        if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
            maxPreviewHeight = MAX_PREVIEW_HEIGHT;
        }
        final List<Camera.Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
        final ArrayList<Size> list = new ArrayList<>();
        for (int i = 0; i < sizes.size(); i++) {
            Camera.Size item = sizes.get(i);
            list.add(new Size(item.width, item.height));
        }
        final Size largest = Collections.max(list, new CompareSizesByArea());
        final Size previewSize = SizeUtil.chooseOptimalSize(list.toArray(new Size[list.size()]), rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth, maxPreviewHeight, largest);
        final int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            textureView.setAspectRatio(previewSize.getWidth(), previewSize.getHeight());
        } else {
            textureView.setAspectRatio(previewSize.getHeight(), previewSize.getWidth());
        }
    }

    public void showCameraPreview() {
        if (mCamera != null) {
            try {
                setupCameraParameters();
                final SurfaceTexture texture = textureView.getSurfaceTexture();
                mCamera.setPreviewTexture(texture);
                mCamera.setDisplayOrientation(getDisplayOrientation());
                mCamera.setOneShotPreviewCallback(mPreviewCallback);
                mCamera.startPreview();
                mCamera.autoFocus(autoFocusCB);
            } catch (Exception e) {
            }
        }
    }

    public void setupCameraParameters() {
        final Camera.Size optimalSize = getOptimalPreviewSize();
        final Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(optimalSize.width, optimalSize.height);
        mCamera.setParameters(parameters);
    }

    private Camera.Size getOptimalPreviewSize() {
        if (mCamera == null) {
            return null;
        }
        final List<Camera.Size> sizes = mCamera.getParameters().getSupportedPreviewSizes();
        Collections.sort(sizes, new CompareCameraSizesByArea());
        final Point screenResolution = DisplayUtils.getScreenResolution(context);
        int w = screenResolution.x;
        int h = screenResolution.y;

        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final int displayRotation = windowManager.getDefaultDisplay().getRotation();
        final int sensorOrientation = getDisplayOrientation();
        boolean swappedDimensions = false;
        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    swappedDimensions = true;
                }
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    swappedDimensions = true;
                }
                break;
            default:
        }

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (swappedDimensions && size.width > textureView.getWidth()) {
                optimalSize = size;
                break;
            } else if (size.height > textureView.getHeight()) {
                optimalSize = size;
                break;
            }
        }
        if (optimalSize == null) {
            for (Camera.Size size : sizes) {
                if (swappedDimensions && size.width > textureView.getWidth()) {
                    optimalSize = size;
                    break;
                } else if (size.height > textureView.getHeight()) {
                    optimalSize = size;
                    break;
                }
            }
        }
        return optimalSize;
    }

    private int getDisplayOrientation() {
        final Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final int rotation = display.getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    private final TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            surfaceCreated = true;
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            stopCamera();
            openCamera();

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            surfaceCreated = false;
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private final Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Size previewSize = camera.getParameters().getPreviewSize();
            final Size size = new Size(previewSize.width, previewSize.height);
            if (previewCallback != null) {
                previewCallback.onPreviewFrame(data, size);
            }
            if (mCamera != null) {
                mCamera.setOneShotPreviewCallback(this);
            }
        }
    };

    private final Camera.AutoFocusCallback autoFocusCB = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            autoFocusHandler.postDelayed(doAutoFocus, 1000);
        }
    };

    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (mCamera != null && surfaceCreated) {
                mCamera.autoFocus(autoFocusCB);
            }
        }
    };
}
