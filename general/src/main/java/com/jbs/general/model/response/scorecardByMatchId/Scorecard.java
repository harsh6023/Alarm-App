package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Scorecard implements Parcelable {
    @SerializedName("team")
    @Expose
    private ScorecardTeam team;
    @SerializedName("batsman")
    @Expose
    private List<ScorecardBatsman> scorecardBatsmanList = new ArrayList<>();
    @SerializedName("bolwer")
    @Expose
    private List<ScorecardBowler> scorecardBowlerList = new ArrayList<>();
    @SerializedName("fallwicket")
    @Expose
    private List<ScorecardFallwicket> scorecardFallwicketList = new ArrayList<>();

    protected Scorecard(Parcel in) {
        team = in.readParcelable(ScorecardTeam.class.getClassLoader());
        scorecardBatsmanList = in.createTypedArrayList(ScorecardBatsman.CREATOR);
        scorecardBowlerList = in.createTypedArrayList(ScorecardBowler.CREATOR);
        scorecardFallwicketList = in.createTypedArrayList(ScorecardFallwicket.CREATOR);
    }

    public static final Creator<Scorecard> CREATOR = new Creator<Scorecard>() {
        @Override
        public Scorecard createFromParcel(Parcel in) {
            return new Scorecard(in);
        }

        @Override
        public Scorecard[] newArray(int size) {
            return new Scorecard[size];
        }
    };

    public ScorecardTeam getTeam() {
        return team;
    }

    public List<ScorecardBatsman> getScorecardBatsmanList() {
        return scorecardBatsmanList;
    }

    public List<ScorecardBowler> getScorecardBowlerList() {
        return scorecardBowlerList;
    }

    public List<ScorecardFallwicket> getScorecardFallwicketList() {
        return scorecardFallwicketList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(team, i);
        parcel.writeTypedList(scorecardBatsmanList);
        parcel.writeTypedList(scorecardBowlerList);
        parcel.writeTypedList(scorecardFallwicketList);
    }
}
