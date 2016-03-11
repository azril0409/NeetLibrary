package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/9.
 */
public final class StringElement implements Parcelable {
    @Element
    String value;

    public StringElement() {
    }

    public StringElement(String value) {
        this.value = value;
    }

    protected StringElement(Parcel in) {
        value = in.readString();
    }

    public static final Creator<StringElement> CREATOR = new Creator<StringElement>() {
        @Override
        public StringElement createFromParcel(Parcel in) {
            return new StringElement(in);
        }

        @Override
        public StringElement[] newArray(int size) {
            return new StringElement[size];
        }
    };

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value != null ? value : "");
    }
}
