package com.jbs.general.model.response.homelist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoreOver implements Parcelable {
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("over")
    @Expose
    private String over;

    protected ScoreOver(Parcel in) {
        score = in.readString();
        over = in.readString();
    }

    public static final Creator<ScoreOver> CREATOR = new Creator<ScoreOver>() {
        @Override
        public ScoreOver createFromParcel(Parcel in) {
            return new ScoreOver(in);
        }

        @Override
        public ScoreOver[] newArray(int size) {
            return new ScoreOver[size];
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
