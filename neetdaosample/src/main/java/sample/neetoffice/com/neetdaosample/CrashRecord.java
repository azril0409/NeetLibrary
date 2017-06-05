package sample.neetoffice.com.neetdaosample;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Deo-chainmeans on 2017/2/21.
 */

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrashRecord implements Parcelable {
    @JsonProperty("id")
    long id;
    @JsonProperty("title")
    String title;
    @JsonProperty("exception")
    String exception;
    @JsonProperty("message")
    Text message;
    @JsonProperty("time")
    long time;

    public CrashRecord() {
    }

    protected CrashRecord(Parcel in) {
        id = in.readLong();
        title = in.readString();
        exception = in.readString();
        message = in.readParcelable(Text.class.getClassLoader());
    }

    @JsonIgnore
    public static final Creator<CrashRecord> CREATOR = new Creator<CrashRecord>() {
        @Override
        public CrashRecord createFromParcel(Parcel in) {
            return new CrashRecord(in);
        }

        @Override
        public CrashRecord[] newArray(int size) {
            return new CrashRecord[size];
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
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(exception);
        dest.writeParcelable(message, flags);
    }
}
