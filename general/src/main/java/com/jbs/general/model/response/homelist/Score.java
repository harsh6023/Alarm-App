package com.jbs.general.model.response.homelist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score implements Parcelable {
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("over")
    @Expose
    private String over;

    protected Score(Parcel in) {
        score = in.readString();
        wicket = in.readString();
        over = in.readString();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    public String getScore() {
        return score;
    }

    public String getWicket() {
        return wicket;
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
        parcel.writeString(wicket);
        parcel.writeString(over);
    }
}
