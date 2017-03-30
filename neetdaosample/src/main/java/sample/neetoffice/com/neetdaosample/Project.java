package sample.neetoffice.com.neetdaosample;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Parcelable {
    @JsonProperty("Package-Name")
    String packageName;
    @JsonProperty("projectName")
    String projectName;
    @JsonProperty("Project-Key")
    String projectKey;
    @JsonProperty("app")
    String app;

    public Project() {
    }

    protected Project(Parcel in) {
        packageName = in.readString();
        projectName = in.readString();
        projectKey = in.readString();
        app = in.readString();
    }

    @JsonIgnore
    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
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
        dest.writeString(packageName);
        dest.writeString(projectName);
        dest.writeString(projectKey);
        dest.writeString(app);
    }
}
