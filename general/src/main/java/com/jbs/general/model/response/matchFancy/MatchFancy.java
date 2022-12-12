package com.jbs.general.model.response.matchFancy;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbs.general.model.response.scorecardByMatchId.ScorecardBatsman;
import com.jbs.general.model.response.scorecardByMatchId.ScorecardBowler;
import com.jbs.general.model.response.scorecardByMatchId.ScorecardFallwicket;
import com.jbs.general.model.response.scorecardByMatchId.ScorecardTeam;

import java.util.ArrayList;
import java.util.List;

public class MatchFancy implements Parcelable {
    @SerializedName("fancy")
    @Expose
    private String fancy;
    @SerializedName("back_size")
    @Expose
    private String back_size;
    @SerializedName("back_price")
    @Expose
    private String back_price;
    @SerializedName("lay_size")
    @Expose
    private String lay_size;
    @SerializedName("lay_price")
    @Expose
    private String lay_price;
    @SerializedName("created")
    @Expose
    private String created;

    protected MatchFancy(Parcel in) {
        fancy = in.readString();
        back_size = in.readString();
        back_price = in.readString();
        lay_size = in.readString();
        lay_price = in.readString();
        created = in.readString();
    }

    public static final Creator<MatchFancy> CREATOR = new Creator<MatchFancy>() {
        @Override
        public MatchFancy createFromParcel(Parcel in) {
            return new MatchFancy(in);
        }

        @Override
        public MatchFancy[] newArray(int size) {
            return new MatchFancy[size];
        }
    };

    public String getFancy() {
        return fancy;
    }

    public String getBack_size() {
        return back_size;
    }

    public String getBack_price() {
        return back_price;
    }

    public String getLay_size() {
        return lay_size;
    }

    public String getLay_price() {
        return lay_price;
    }

    public String getCreated() {
        return created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fancy);
        parcel.writeString(back_size);
        parcel.writeString(back_price);
        parcel.writeString(lay_size);
        parcel.writeString(lay_price);
        parcel.writeString(created);
    }
}
