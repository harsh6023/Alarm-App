package com.jbs.general.model.response.teamRanking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamRankingData implements Parcelable {
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("point")
    @Expose
    private String point;

    protected TeamRankingData(Parcel in) {
        rank = in.readString();
        team = in.readString();
        rating = in.readString();
        point = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank);
        dest.writeString(team);
        dest.writeString(rating);
        dest.writeString(point);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TeamRankingData> CREATOR = new Creator<TeamRankingData>() {
        @Override
        public TeamRankingData createFromParcel(Parcel in) {
            return new TeamRankingData(in);
        }

        @Override
        public TeamRankingData[] newArray(int size) {
            return new TeamRankingData[size];
        }
    };

    public String getRank() {
        return rank;
    }

    public String getTeam() {
        return team;
    }

    public String getRating() {
        return rating;
    }

    public String getPoint() {
        return point;
    }
}
