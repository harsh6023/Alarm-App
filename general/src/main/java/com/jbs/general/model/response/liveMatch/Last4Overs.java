package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Last4Overs implements Parcelable {
    @SerializedName("over")
    @Expose
    private String over;
    @SerializedName("runs")
    @Expose
    private String runs;
    @SerializedName("balls")
    @Expose
    private List<String> ballsList = new ArrayList<>();

    protected Last4Overs(Parcel in) {
        over = in.readString();
        runs = in.readString();
        ballsList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(over);
        dest.writeString(runs);
        dest.writeStringList(ballsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Last4Overs> CREATOR = new Creator<Last4Overs>() {
        @Override
        public Last4Overs createFromParcel(Parcel in) {
            return new Last4Overs(in);
        }

        @Override
        public Last4Overs[] newArray(int size) {
            return new Last4Overs[size];
        }
    };

    public String getOver() {
        return over;
    }

    public String getRuns() {
        return runs;
    }

    public List<String> getBallsList() {
        return ballsList;
    }
}
