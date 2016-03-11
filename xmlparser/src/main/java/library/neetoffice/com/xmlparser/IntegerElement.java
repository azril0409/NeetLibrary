package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/10.
 */
public final class IntegerElement implements Parcelable {
    @Element
    int value;

    public IntegerElement() {
    }

    public IntegerElement(int value) {
        this.value = value;
    }

    protected IntegerElement(Parcel in) {
        value = in.readInt();
    }

    public static final Creator<IntegerElement> CREATOR = new Creator<IntegerElement>() {
        @Override
        public IntegerElement createFromParcel(Parcel in) {
            return new IntegerElement(in);
        }

        @Override
        public IntegerElement[] newArray(int size) {
            return new IntegerElement[size];
        }
    };

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }
}
