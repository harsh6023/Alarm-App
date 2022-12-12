package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScorecardTeam implements Parcelable {
    @SerializedName("team_id")
    @Expose
    private String team_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String short_name;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("over")
    @Expose
    private String over;
    @SerializedName("extras")
    @Expose
    private String extras;

    protected ScorecardTeam(Parcel in) {
        team_id = in.readString();
        name = in.readString();
        short_name = in.readString();
        flag = in.readString();
        score = in.readString();
        wicket = in.readString();
        over = in.readString();
        extras = in.readString();
    }

    public static final Creator<ScorecardTeam> CREATOR = new Creator<ScorecardTeam>() {
        @Override
        public ScorecardTeam createFromParcel(Parcel in) {
            return new ScorecardTeam(in);
        }

        @Override
        public ScorecardTeam[] newArray(int size) {
            return new ScorecardTeam[size];
        }
    };

    public String getTeam_id() {
        return team_id;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public String getFlag() {
        return flag;
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

    public String getExtras() {
        return extras;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(team_id);
        parcel.writeString(name);
        parcel.writeString(short_name);
        parcel.writeString(flag);
        parcel.writeString(score);
        parcel.writeString(wicket);
        parcel.writeString(over);
        parcel.writeString(extras);
    }
}
