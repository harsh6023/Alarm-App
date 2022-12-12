package com.jbs.general.model.response.squadByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Team implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("short_name")
    @Expose
    private String short_name;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("player")
    @Expose
    private List<Player> playerList = new ArrayList<>();

    protected Team(Parcel in) {
        name = in.readString();
        short_name = in.readString();
        flag = in.readString();
        playerList = in.createTypedArrayList(Player.CREATOR);
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public String getFlag() {
        return flag;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(short_name);
        parcel.writeString(flag);
        parcel.writeTypedList(playerList);
    }
}
