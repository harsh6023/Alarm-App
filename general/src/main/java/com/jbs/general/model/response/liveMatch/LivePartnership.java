package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LivePartnership implements Parcelable {
    @SerializedName("ball")
    @Expose
    private String ball;
    @SerializedName("run")
    @Expose
    private String run;

    protected LivePartnership(Parcel in) {
        ball = in.readString();
        run = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ball);
        dest.writeString(run);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LivePartnership> CREATOR = new Creator<LivePartnership>() {
        @Override
        public LivePartnership createFromParcel(Parcel in) {
            return new LivePartnership(in);
        }

        @Override
        public LivePartnership[] newArray(int size) {
            return new LivePartnership[size];
        }
    };

    public String getBall() {
        return ball;
    }

    public String getRun() {
        return run;
    }
}
