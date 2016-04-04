package library.neetoffice.com.codeutil;

/**
 * Created by Deo on 2016/3/22.
 */
public interface CameraManager {
    int MAX_PREVIEW_WIDTH = 1920;
    int MAX_PREVIEW_HEIGHT = 1080;

    void openCamera();

    void stopCamera();

    void setAutoFit(boolean autoFit);

    void setPreviewCallback(PreviewCallback previewCallback);

}
