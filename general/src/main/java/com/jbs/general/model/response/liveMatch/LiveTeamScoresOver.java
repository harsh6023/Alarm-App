package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveTeamScoresOver implements Parcelable {
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("over")
    @Expose
    private String over;

    protected LiveTeamScoresOver(Parcel in) {
        score = in.readString();
        over = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(score);
        dest.writeString(over);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveTeamScoresOver> CREATOR = new Creator<LiveTeamScoresOver>() {
        @Override
        public LiveTeamScoresOver createFromParcel(Parcel in) {
            return new LiveTeamScoresOver(in);
        }

        @Override
        public LiveTeamScoresOver[] newArray(int size) {
            return new LiveTeamScoresOver[size];
        }
    };

    public String getScore() {
        return score;
    }

    public String getOver() {
        return over;
    }
}
