package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveBatsman implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("run")
    @Expose
    private String run;
    @SerializedName("ball")
    @Expose
    private String ball;
    @SerializedName("fours")
    @Expose
    private String fours;
    @SerializedName("sixes")
    @Expose
    private String sixes;
    @SerializedName("strike_rate")
    @Expose
    private String strike_rate;

    protected LiveBatsman(Parcel in) {
        name = in.readString();
        run = in.readString();
        ball = in.readString();
        fours = in.readString();
        sixes = in.readString();
        strike_rate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(run);
        dest.writeString(ball);
        dest.writeString(fours);
        dest.writeString(sixes);
        dest.writeString(strike_rate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveBatsman> CREATOR = new Creator<LiveBatsman>() {
        @Override
        public LiveBatsman createFromParcel(Parcel in) {
            return new LiveBatsman(in);
        }

        @Override
        public LiveBatsman[] newArray(int size) {
            return new LiveBatsman[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getRun() {
        return run;
    }

    public String getBall() {
        return ball;
    }

    public String getFours() {
        return fours;
    }

    public String getSixes() {
        return sixes;
    }

    public String getStrike_rate() {
        return strike_rate;
    }
}
