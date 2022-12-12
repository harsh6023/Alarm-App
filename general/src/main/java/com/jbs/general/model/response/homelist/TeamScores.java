package com.jbs.general.model.response.homelist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamScores implements Parcelable {

    @SerializedName("1")
    @Expose
    private Score score1 = null;

    @SerializedName("2")
    @Expose
    private Score score2 = null;

    protected TeamScores(Parcel in) {
        score1 = in.readParcelable(Score.class.getClassLoader());
        score2 = in.readParcelable(Score.class.getClassLoader());
    }

    public static final Creator<TeamScores> CREATOR = new Creator<TeamScores>() {
        @Override
        public TeamScores createFromParcel(Parcel in) {
            return new TeamScores(in);
        }

        @Override
        public TeamScores[] newArray(int size) {
            return new TeamScores[size];
        }
    };

    public Score getScore1() {
        return score1;
    }

    public Score getScore2() {
        return score2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(score1, i);
        parcel.writeParcelable(score2, i);
    }
}
