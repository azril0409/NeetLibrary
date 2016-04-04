package library.neetoffice.com.codeutil;

import android.util.Size;

/**
 * Created by Deo on 2016/3/22.
 */
public interface PreviewCallback {

    void onPreviewFrame(byte[] data, Size size);
}
