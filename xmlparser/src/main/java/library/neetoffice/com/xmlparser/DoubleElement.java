package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/11.
 */
public class DoubleElement implements Parcelable {
    @Element
    double value;

    public DoubleElement() {
    }

    public DoubleElement(double value) {
        this.value = value;
    }

    protected DoubleElement(Parcel in) {
        value = in.readDouble();
    }

    public static final Creator<DoubleElement> CREATOR = new Creator<DoubleElement>() {
        @Override
        public DoubleElement createFromParcel(Parcel in) {
            return new DoubleElement(in);
        }

        @Override
        public DoubleElement[] newArray(int size) {
            return new DoubleElement[size];
        }
    };

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(value);
    }
}
