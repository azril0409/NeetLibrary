package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/11.
 */
public class LongElement implements Parcelable {
    @Element
    long value;

    public LongElement() {
    }

    public LongElement(long value) {
        this.value = value;
    }

    protected LongElement(Parcel in) {
        value = in.readLong();
    }

    public static final Creator<LongElement> CREATOR = new Creator<LongElement>() {
        @Override
        public LongElement createFromParcel(Parcel in) {
            return new LongElement(in);
        }

        @Override
        public LongElement[] newArray(int size) {
            return new LongElement[size];
        }
    };

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(value);
    }
}
