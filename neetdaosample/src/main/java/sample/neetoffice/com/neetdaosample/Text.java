package sample.neetoffice.com.neetdaosample;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Deo-chainmeans on 2017/2/22.
 */

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Text implements Parcelable {
    @JsonProperty("value")
    String value;

    public Text() {
    }

    protected Text(Parcel in) {
        value = in.readString();
    }

    @JsonIgnore
    public static final Creator<Text> CREATOR = new Creator<Text>() {
        @Override
        public Text createFromParcel(Parcel in) {
            return new Text(in);
        }

        @Override
        public Text[] newArray(int size) {
            return new Text[size];
        }
    };

    @Override
    @JsonIgnore
    public int describeContents() {
        return 0;
    }

    @Override
    @JsonIgnore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }
}
