package library.neetoffice.com.codeutil.core;

import android.util.Size;

import java.util.Comparator;

/**
 * Created by Deo on 2016/3/22.
 */
public class CompareSizesByArea implements Comparator<Size> {
    @Override
    public int compare(Size lhs, Size rhs) {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
    }
}
