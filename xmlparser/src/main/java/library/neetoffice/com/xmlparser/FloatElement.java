package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/11.
 */
public final class FloatElement implements Parcelable {
    @Element
    float value;

    public FloatElement() {
    }

    public FloatElement(float value) {
        this.value = value;
    }

    protected FloatElement(Parcel in) {
        value = in.readFloat();
    }

    public static final Creator<FloatElement> CREATOR = new Creator<FloatElement>() {
        @Override
        public FloatElement createFromParcel(Parcel in) {
            return new FloatElement(in);
        }

        @Override
        public FloatElement[] newArray(int size) {
            return new FloatElement[size];
        }
    };

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(value);
    }
}
