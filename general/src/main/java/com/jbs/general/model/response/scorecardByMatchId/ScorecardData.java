package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbs.general.model.response.homelist.Score;

public class ScorecardData implements Parcelable {

    @SerializedName("1")
    @Expose
    private Scorecard scorecard1;

    @SerializedName("2")
    @Expose
    private Scorecard scorecard2;

    protected ScorecardData(Parcel in) {
        scorecard1 = in.readParcelable(Scorecard.class.getClassLoader());
        scorecard2 = in.readParcelable(Scorecard.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(scorecard1, flags);
        dest.writeParcelable(scorecard2, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScorecardData> CREATOR = new Creator<ScorecardData>() {
        @Override
        public ScorecardData createFromParcel(Parcel in) {
            return new ScorecardData(in);
        }

        @Override
        public ScorecardData[] newArray(int size) {
            return new ScorecardData[size];
        }
    };

    public Scorecard getScorecard1() {
        return scorecard1;
    }

    public Scorecard getScorecard2() {
        return scorecard2;
    }
}
