package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScorecardFallwicket implements Parcelable {
    @SerializedName("player")
    @Expose
    private String player;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("over")
    @Expose
    private String over;

    protected ScorecardFallwicket(Parcel in) {
        player = in.readString();
        score = in.readString();
        wicket = in.readString();
        over = in.readString();
    }

    public static final Creator<ScorecardFallwicket> CREATOR = new Creator<ScorecardFallwicket>() {
        @Override
        public ScorecardFallwicket createFromParcel(Parcel in) {
            return new ScorecardFallwicket(in);
        }

        @Override
        public ScorecardFallwicket[] newArray(int size) {
            return new ScorecardFallwicket[size];
        }
    };

    public String getPlayer() {
        return player;
    }

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
        parcel.writeString(player);
        parcel.writeString(score);
        parcel.writeString(wicket);
        parcel.writeString(over);
    }
}
