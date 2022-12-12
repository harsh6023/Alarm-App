package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LastWicket implements Parcelable {
    @SerializedName("player")
    @Expose
    private String player;
    @SerializedName("run")
    @Expose
    private String run;
    @SerializedName("ball")
    @Expose
    private String ball;

    protected LastWicket(Parcel in) {
        player = in.readString();
        run = in.readString();
        ball = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(player);
        dest.writeString(run);
        dest.writeString(ball);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LastWicket> CREATOR = new Creator<LastWicket>() {
        @Override
        public LastWicket createFromParcel(Parcel in) {
            return new LastWicket(in);
        }

        @Override
        public LastWicket[] newArray(int size) {
            return new LastWicket[size];
        }
    };

    public String getPlayer() {
        return player;
    }

    public String getRun() {
        return run;
    }

    public String getBall() {
        return ball;
    }
}
