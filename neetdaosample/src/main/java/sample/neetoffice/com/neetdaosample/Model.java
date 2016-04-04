package sample.neetoffice.com.neetdaosample;

import android.os.Parcel;
import android.os.Parcelable;

import library.neetoffice.com.neetdao.DatabaseField;
import library.neetoffice.com.neetdao.DatabaseTable;
import library.neetoffice.com.neetdao.Id;

/**
 * Created by Deo on 2016/3/7.
 */
@DatabaseTable
public class Model implements Parcelable {
    public static final String TITLE = "_TEXT";
    public static final String MESSAGE = "_MESSAGE";

    @Id
    Long id;

    @DatabaseField(columnName = TITLE)
    private String title;

    @DatabaseField(columnName = MESSAGE)
    private String message;

    public Model() {
    }

    public Model(String title, String message) {
        this.message = message;
        this.title = title;
    }

    protected Model(Parcel in) {
        final long id = in.readLong();
        this.id = id > 0 ? id : null;
        title = in.readString();
        message = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id != null ? id : -1);
        dest.writeString(title != null ? title : "");
        dest.writeString(message != null ? message : "");
    }
}
