package com.jbs.general.model.response.serieslist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbs.general.model.response.matchesBySeriesId.MatchesData;
import com.jbs.general.model.response.pointsTable.PointsTableData;

import java.util.ArrayList;
import java.util.List;

public class SeriesData implements Parcelable {
    @SerializedName("basic")
    @Expose
    private SeriesListData seriesListData;
    @SerializedName("upcoming_matches")
    @Expose
    private List<MatchesData> upComingMatchesList = new ArrayList<>();
    @SerializedName("recent_matches")
    @Expose
    private List<MatchesData> recentMatchesList = new ArrayList<>();
    @SerializedName("points_table")
    @Expose
    private List<PointsTableData> pointsTableDataList = new ArrayList<>();

    protected SeriesData(Parcel in) {
        seriesListData = in.readParcelable(SeriesListData.class.getClassLoader());
        upComingMatchesList = in.createTypedArrayList(MatchesData.CREATOR);
        recentMatchesList = in.createTypedArrayList(MatchesData.CREATOR);
        pointsTableDataList = in.createTypedArrayList(PointsTableData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(seriesListData, flags);
        dest.writeTypedList(upComingMatchesList);
        dest.writeTypedList(recentMatchesList);
        dest.writeTypedList(pointsTableDataList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SeriesData> CREATOR = new Creator<SeriesData>() {
        @Override
        public SeriesData createFromParcel(Parcel in) {
            return new SeriesData(in);
        }

        @Override
        public SeriesData[] newArray(int size) {
            return new SeriesData[size];
        }
    };

    public SeriesListData getSeriesListData() {
        return seriesListData;
    }

    public List<MatchesData> getUpComingMatchesList() {
        return upComingMatchesList;
    }

    public List<MatchesData> getRecentMatchesList() {
        return recentMatchesList;
    }

    public List<PointsTableData> getPointsTableDataList() {
        return pointsTableDataList;
    }
}
