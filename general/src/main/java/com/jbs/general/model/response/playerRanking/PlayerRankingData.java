package com.jbs.general.model.response.playerRanking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlayerRankingData implements Parcelable {
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("rating")
    @Expose
    private String rating;

    protected PlayerRankingData(Parcel in) {
        rank = in.readString();
        name = in.readString();
        country = in.readString();
        rating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank);
        dest.writeString(name);
        dest.writeString(country);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlayerRankingData> CREATOR = new Creator<PlayerRankingData>() {
        @Override
        public PlayerRankingData createFromParcel(Parcel in) {
            return new PlayerRankingData(in);
        }

        @Override
        public PlayerRankingData[] newArray(int size) {
            return new PlayerRankingData[size];
        }
    };

    public String getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getRating() {
        return rating;
    }
}
