package com.jbs.general.model.response.squadByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player implements Parcelable {
    @SerializedName("player_id")
    @Expose
    private String player_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("play_role")
    @Expose
    private String play_role;
    @SerializedName("image")
    @Expose
    private String image;

    protected Player(Parcel in) {
        player_id = in.readString();
        name = in.readString();
        play_role = in.readString();
        image = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getPlayer_id() {
        return player_id;
    }

    public String getName() {
        return name;
    }

    public String getPlay_role() {
        return play_role;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(player_id);
        parcel.writeString(name);
        parcel.writeString(play_role);
        parcel.writeString(image);
    }
}
