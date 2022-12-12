package com.jbs.general.model.response.homelist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamScoresOver implements Parcelable {

    @SerializedName("score")
    @Expose
    private String score;

    @SerializedName("over")
    @Expose
    private String over;

    protected TeamScoresOver(Parcel in) {
        score = in.readString();
        over = in.readString();
    }

    public static final Creator<TeamScoresOver> CREATOR = new Creator<TeamScoresOver>() {
        @Override
        public TeamScoresOver createFromParcel(Parcel in) {
            return new TeamScoresOver(in);
        }

        @Override
        public TeamScoresOver[] newArray(int size) {
            return new TeamScoresOver[size];
        }
    };

    public String getScore() {
        return score;
    }

    public String getOver() {
        return over;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(score);
        parcel.writeString(over);
    }
}
