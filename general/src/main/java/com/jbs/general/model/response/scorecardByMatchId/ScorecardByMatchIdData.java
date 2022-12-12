package com.jbs.general.model.response.scorecardByMatchId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScorecardByMatchIdData implements Parcelable {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("scorecard")
    @Expose
    private ScorecardData scorecardData;

    protected ScorecardByMatchIdData(Parcel in) {
        result = in.readString();
        scorecardData = in.readParcelable(ScorecardData.class.getClassLoader());
    }

    public static final Creator<ScorecardByMatchIdData> CREATOR = new Creator<ScorecardByMatchIdData>() {
        @Override
        public ScorecardByMatchIdData createFromParcel(Parcel in) {
            return new ScorecardByMatchIdData(in);
        }

        @Override
        public ScorecardByMatchIdData[] newArray(int size) {
            return new ScorecardByMatchIdData[size];
        }
    };

    public String getResult() {
        return result;
    }

    public ScorecardData getScorecardData() {
        return scorecardData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(result);
        parcel.writeParcelable(scorecardData, i);
    }
}
