package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/11.
 */
public final class FloatElement {
    @Element
    float value;

    public FloatElement() {
    }

    public FloatElement(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
