package library.neetoffice.com.codeutil.core;

import android.hardware.Camera;

import java.util.Comparator;

/**
 * Created by Deo on 2016/3/23.
 */
public class CompareCameraSizesByArea implements Comparator<Camera.Size> {
    @Override
    public int compare(Camera.Size lhs, Camera.Size rhs) {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.width * lhs.height - (long) rhs.width * rhs.height);
    }
}
