package com.jbs.general.model.response.squadByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SquadByMatchIdData implements Parcelable {
    @SerializedName("team_a")
    @Expose
    private Team teamA;
    @SerializedName("team_b")
    @Expose
    private Team teamB;

    protected SquadByMatchIdData(Parcel in) {
        teamA = in.readParcelable(Team.class.getClassLoader());
        teamB = in.readParcelable(Team.class.getClassLoader());
    }

    public static final Creator<SquadByMatchIdData> CREATOR = new Creator<SquadByMatchIdData>() {
        @Override
        public SquadByMatchIdData createFromParcel(Parcel in) {
            return new SquadByMatchIdData(in);
        }

        @Override
        public SquadByMatchIdData[] newArray(int size) {
            return new SquadByMatchIdData[size];
        }
    };

    public Team getTeamA() {
        return teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(teamA, i);
        parcel.writeParcelable(teamB, i);
    }
}
