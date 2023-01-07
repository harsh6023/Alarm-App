package com.jbs.general.model.response.alarms;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmsApiData implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("sound")
    @Expose
    private String sound;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("day")
    @Expose
    private int day;
    @SerializedName("date")
    @Expose
    private String date;

    protected AlarmsApiData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        time = in.readString();
        sound = in.readString();
        uri = in.readString();
        status = in.readInt();
        day = in.readInt();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(sound);
        dest.writeString(uri);
        dest.writeInt(status);
        dest.writeInt(day);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlarmsApiData> CREATOR = new Creator<AlarmsApiData>() {
        @Override
        public AlarmsApiData createFromParcel(Parcel in) {
            return new AlarmsApiData(in);
        }

        @Override
        public AlarmsApiData[] newArray(int size) {
            return new AlarmsApiData[size];
        }
    };

    public String getDate() {
        return date;
    }

    public int getDay() {
        return day;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getSound() {
        return sound;
    }

    public int getStatus() {
        return status;
    }

    public String getUri() {
        return uri;
    }
}
