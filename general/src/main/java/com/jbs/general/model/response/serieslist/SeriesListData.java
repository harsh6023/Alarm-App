package com.jbs.general.model.response.serieslist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeriesListData implements Parcelable {
    @SerializedName("series_id")
    @Expose
    private String series_id;
    @SerializedName("series")
    @Expose
    private String series;
    @SerializedName("series_date")
    @Expose
    private String series_date;
    @SerializedName("total_matches")
    @Expose
    private String total_matches;
    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("end_date")
    @Expose
    private String end_date;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("month_wise")
    @Expose
    private String month_wise;

    protected SeriesListData(Parcel in) {
        series_id = in.readString();
        series = in.readString();
        series_date = in.readString();
        total_matches = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        image = in.readString();
        month_wise = in.readString();
    }

    public static final Creator<SeriesListData> CREATOR = new Creator<SeriesListData>() {
        @Override
        public SeriesListData createFromParcel(Parcel in) {
            return new SeriesListData(in);
        }

        @Override
        public SeriesListData[] newArray(int size) {
            return new SeriesListData[size];
        }
    };

    public String getSeries_id() {
        return series_id;
    }

    public String getSeries() {
        return series;
    }

    public String getSeries_date() {
        return series_date;
    }

    public String getTotal_matches() {
        return total_matches;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getImage() {
        return image;
    }

    public String getMonth_wise() {
        return month_wise;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(series_id);
        parcel.writeString(series);
        parcel.writeString(series_date);
        parcel.writeString(total_matches);
        parcel.writeString(start_date);
        parcel.writeString(end_date);
        parcel.writeString(image);
        parcel.writeString(month_wise);
    }
}
