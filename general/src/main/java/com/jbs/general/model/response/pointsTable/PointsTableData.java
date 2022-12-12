package com.jbs.general.model.response.pointsTable;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PointsTableData implements Parcelable {
    @SerializedName("teams")
    @Expose
    private String teams;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("P")
    @Expose
    private String P;
    @SerializedName("W")
    @Expose
    private String W;
    @SerializedName("L")
    @Expose
    private String L;
    @SerializedName("NR")
    @Expose
    private String NR;
    @SerializedName("Pts")
    @Expose
    private String Pts;
    @SerializedName("NRR")
    @Expose
    private String NRR;

    protected PointsTableData(Parcel in) {
        teams = in.readString();
        flag = in.readString();
        P = in.readString();
        W = in.readString();
        L = in.readString();
        NR = in.readString();
        Pts = in.readString();
        NRR = in.readString();
    }

    public static final Creator<PointsTableData> CREATOR = new Creator<PointsTableData>() {
        @Override
        public PointsTableData createFromParcel(Parcel in) {
            return new PointsTableData(in);
        }

        @Override
        public PointsTableData[] newArray(int size) {
            return new PointsTableData[size];
        }
    };

    public String getTeams() {
        return teams;
    }

    public String getFlag() {
        return flag;
    }

    public String getP() {
        return P;
    }

    public String getW() {
        return W;
    }

    public String getL() {
        return L;
    }

    public String getNR() {
        return NR;
    }

    public String getPts() {
        return Pts;
    }

    public String getNRR() {
        return NRR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(teams);
        parcel.writeString(flag);
        parcel.writeString(P);
        parcel.writeString(W);
        parcel.writeString(L);
        parcel.writeString(NR);
        parcel.writeString(Pts);
        parcel.writeString(NRR);
    }
}
