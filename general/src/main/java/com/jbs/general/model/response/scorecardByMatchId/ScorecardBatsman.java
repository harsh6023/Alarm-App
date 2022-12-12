package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScorecardBatsman implements Parcelable {
    @SerializedName("player_id")
    @Expose
    private String player_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("run")
    @Expose
    private String run;
    @SerializedName("ball")
    @Expose
    private String ball;
    @SerializedName("fours")
    @Expose
    private String fours;
    @SerializedName("sixes")
    @Expose
    private String sixes;
    @SerializedName("strike_rate")
    @Expose
    private String strike_rate;
    @SerializedName("out_by")
    @Expose
    private String out_by;

    protected ScorecardBatsman(Parcel in) {
        player_id = in.readString();
        name = in.readString();
        run = in.readString();
        ball = in.readString();
        fours = in.readString();
        sixes = in.readString();
        strike_rate = in.readString();
        out_by = in.readString();
    }

    public static final Creator<ScorecardBatsman> CREATOR = new Creator<ScorecardBatsman>() {
        @Override
        public ScorecardBatsman createFromParcel(Parcel in) {
            return new ScorecardBatsman(in);
        }

        @Override
        public ScorecardBatsman[] newArray(int size) {
            return new ScorecardBatsman[size];
        }
    };

    public String getPlayer_id() {
        return player_id;
    }

    public String getName() {
        return name;
    }

    public String getRun() {
        return run;
    }

    public String getBall() {
        return ball;
    }

    public String getFours() {
        return fours;
    }

    public String getSixes() {
        return sixes;
    }

    public String getStrike_rate() {
        return strike_rate;
    }

    public String getOut_by() {
        return out_by;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(player_id);
        parcel.writeString(name);
        parcel.writeString(run);
        parcel.writeString(ball);
        parcel.writeString(fours);
        parcel.writeString(sixes);
        parcel.writeString(strike_rate);
        parcel.writeString(out_by);
    }
}
