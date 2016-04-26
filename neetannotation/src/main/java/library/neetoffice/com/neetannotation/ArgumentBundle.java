package library.neetoffice.com.neetannotation;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Deo on 2016/4/18.
 */
public class ArgumentBundle {
    private Bundle bundle = new Bundle();

    public ArgumentBundle putExtra(String name, boolean value) {
        bundle.putBoolean(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, byte value) {
        bundle.putByte(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, char value) {
        bundle.putChar(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, short value) {
        bundle.putShort(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, int value) {
        bundle.putInt(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, long value) {
        bundle.putLong(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, float value) {
        bundle.putFloat(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, double value) {
        bundle.putDouble(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, String value) {
        bundle.putString(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, CharSequence value) {
        bundle.putCharSequence(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, Parcelable value) {
        bundle.putParcelable(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, Parcelable[] value) {
        bundle.putParcelableArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, Serializable value) {
        bundle.putSerializable(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, boolean[] value) {
        bundle.putBooleanArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, byte[] value) {
        bundle.putByteArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, short[] value) {
        bundle.putShortArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, char[] value) {
        bundle.putCharArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, int[] value) {
        bundle.putIntArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, long[] value) {
        bundle.putLongArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, float[] value) {
        bundle.putFloatArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, double[] value) {
        bundle.putDoubleArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, String[] value) {
        bundle.putStringArray(name, value);
        return this;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public ArgumentBundle putExtra(String name, CharSequence[] value) {
        bundle.putCharSequenceArray(name, value);
        return this;
    }

    public ArgumentBundle putExtra(String name, Bundle value) {
        bundle.putBundle(name, value);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}
