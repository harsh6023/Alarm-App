package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScorecardBowler implements Parcelable {
    @SerializedName("player_id")
    @Expose
    private String player_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("over")
    @Expose
    private String over;
    @SerializedName("maiden")
    @Expose
    private String maiden;
    @SerializedName("run")
    @Expose
    private String run;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("economy")
    @Expose
    private String economy;
    @SerializedName("dot_ball")
    @Expose
    private String dot_ball;

    protected ScorecardBowler(Parcel in) {
        player_id = in.readString();
        name = in.readString();
        over = in.readString();
        maiden = in.readString();
        run = in.readString();
        wicket = in.readString();
        economy = in.readString();
        dot_ball = in.readString();
    }

    public static final Creator<ScorecardBowler> CREATOR = new Creator<ScorecardBowler>() {
        @Override
        public ScorecardBowler createFromParcel(Parcel in) {
            return new ScorecardBowler(in);
        }

        @Override
        public ScorecardBowler[] newArray(int size) {
            return new ScorecardBowler[size];
        }
    };

    public String getPlayer_id() {
        return player_id;
    }

    public String getName() {
        return name;
    }

    public String getOver() {
        return over;
    }

    public String getMaiden() {
        return maiden;
    }

    public String getRun() {
        return run;
    }

    public String getWicket() {
        return wicket;
    }

    public String getEconomy() {
        return economy;
    }

    public String getDot_ball() {
        return dot_ball;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(player_id);
        parcel.writeString(name);
        parcel.writeString(over);
        parcel.writeString(maiden);
        parcel.writeString(run);
        parcel.writeString(wicket);
        parcel.writeString(economy);
        parcel.writeString(dot_ball);
    }
}
