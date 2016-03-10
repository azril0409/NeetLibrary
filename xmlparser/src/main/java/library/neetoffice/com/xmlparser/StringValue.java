package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

import library.neetoffice.com.xmlparser.Element;
import library.neetoffice.com.xmlparser.Tag;

/**
 * Created by Deo on 2016/3/9.
 */
@Tag
public class StringValue implements Parcelable {
    @Element
    String value;

    public StringValue() {
    }

    public StringValue(String value) {
        this.value = value;
    }

    protected StringValue(Parcel in) {
        value = in.readString();
    }

    public static final Creator<StringValue> CREATOR = new Creator<StringValue>() {
        @Override
        public StringValue createFromParcel(Parcel in) {
            return new StringValue(in);
        }

        @Override
        public StringValue[] newArray(int size) {
            return new StringValue[size];
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
