package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/11.
 */
public final class BooleanElement implements Parcelable {
    @Element
    boolean value;

    public BooleanElement() {
    }

    public BooleanElement(boolean value) {
        this.value = value;
    }

    protected BooleanElement(Parcel in) {
        value = in.readByte() != 0;
    }

    public static final Creator<BooleanElement> CREATOR = new Creator<BooleanElement>() {
        @Override
        public BooleanElement createFromParcel(Parcel in) {
            return new BooleanElement(in);
        }

        @Override
        public BooleanElement[] newArray(int size) {
            return new BooleanElement[size];
        }
    };

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (value ? 1 : 0));
    }
}
