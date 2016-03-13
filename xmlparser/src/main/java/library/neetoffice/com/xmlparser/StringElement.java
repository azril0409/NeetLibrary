package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/9.
 */
public final class StringElement implements Parcelable {
    @Element
    String text;

    public StringElement() {
    }

    public StringElement(String text) {
        this.text = text;
    }

    protected StringElement(Parcel in) {
        text = in.readString();
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text != null ? text : "");
    }
}
