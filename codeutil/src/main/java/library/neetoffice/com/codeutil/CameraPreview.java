package library.neetoffice.com.codeutil;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import library.neetoffice.com.codeutil.core.AutoFitTextureView;

/**
 * Created by Deo on 2016/3/22.
 */
public class CameraPreview extends AutoFitTextureView implements CameraManager {
    private CameraManager cameraManager;

    public CameraPreview(Context context) {
        super(context);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = new CameraManagerV21(context, this);
        } else {
            cameraManager = new CameraManagerV9(context, this);
        }
    }

    @Override
    public void openCamera() {
        cameraManager.openCamera();
    }

    @Override
    public void stopCamera() {
        cameraManager.stopCamera();
    }

    @Override
    public void setAutoFit(boolean autoFit) {
        cameraManager.setAutoFit(autoFit);
    }

    @Override
    public void setPreviewCallback(PreviewCallback previewCallback) {
        cameraManager.setPreviewCallback(previewCallback);
    }
}
