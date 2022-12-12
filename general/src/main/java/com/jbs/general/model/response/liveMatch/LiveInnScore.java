package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveInnScore implements Parcelable {
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("ball")
    @Expose
    private String ball;

    protected LiveInnScore(Parcel in) {
        score = in.readString();
        wicket = in.readString();
        ball = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(score);
        dest.writeString(wicket);
        dest.writeString(ball);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveInnScore> CREATOR = new Creator<LiveInnScore>() {
        @Override
        public LiveInnScore createFromParcel(Parcel in) {
            return new LiveInnScore(in);
        }

        @Override
        public LiveInnScore[] newArray(int size) {
            return new LiveInnScore[size];
        }
    };

    public String getScore() {
        return score;
    }

    public String getWicket() {
        return wicket;
    }

    public String getBall() {
        return ball;
    }
}
