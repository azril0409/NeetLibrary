package library.neetoffice.com.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Deo on 2016/3/10.
 */
public final class ElementValue extends ElementMap implements Parcelable {
    @Attribute
    AttributeMap attribute = new AttributeMap();
    @Element
    String value = "";

    protected ElementValue() {

    }

    protected ElementValue(Parcel in) {
        attribute = (AttributeMap) in.readSerializable();
        value = in.readString();
    }

    public static final Creator<ElementValue> CREATOR = new Creator<ElementValue>() {
        @Override
        public ElementValue createFromParcel(Parcel in) {
            return new ElementValue(in);
        }

        @Override
        public ElementValue[] newArray(int size) {
            return new ElementValue[size];
        }
    };

    public AttributeMap getAttributes() {
        return attribute;
    }

    public void addAttribute(String name, String value) {
        attribute.put(name, value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(attribute);
        dest.writeString(value);
    }
}
