package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveBolwer implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("run")
    @Expose
    private String run;
    @SerializedName("over")
    @Expose
    private String over;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("economy")
    @Expose
    private String economy;

    protected LiveBolwer(Parcel in) {
        name = in.readString();
        run = in.readString();
        over = in.readString();
        wicket = in.readString();
        economy = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(run);
        dest.writeString(over);
        dest.writeString(wicket);
        dest.writeString(economy);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveBolwer> CREATOR = new Creator<LiveBolwer>() {
        @Override
        public LiveBolwer createFromParcel(Parcel in) {
            return new LiveBolwer(in);
        }

        @Override
        public LiveBolwer[] newArray(int size) {
            return new LiveBolwer[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getRun() {
        return run;
    }

    public String getOver() {
        return over;
    }

    public String getWicket() {
        return wicket;
    }

    public String getEconomy() {
        return economy;
    }
}
