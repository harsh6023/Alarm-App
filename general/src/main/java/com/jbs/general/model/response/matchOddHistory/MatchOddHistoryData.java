package com.jbs.general.model.response.matchOddHistory;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchOddHistoryData implements Parcelable {

    @SerializedName("match_odd_id")
    @Expose
    private String match_odd_id;
    @SerializedName("inning")
    @Expose
    private String inning;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("overs")
    @Expose
    private String overs;
    @SerializedName("runs")
    @Expose
    private String runs;
    @SerializedName("min_rate")
    @Expose
    private String min_rate;
    @SerializedName("max_rate")
    @Expose
    private String max_rate;
    @SerializedName("s_min")
    @Expose
    private String s_min;
    @SerializedName("s_max")
    @Expose
    private String s_max;
    @SerializedName("date_time")
    @Expose
    private String date_time;
    @SerializedName("time")
    @Expose
    private String time;

    protected MatchOddHistoryData(Parcel in) {
        match_odd_id = in.readString();
        inning = in.readString();
        team = in.readString();
        score = in.readString();
        overs = in.readString();
        runs = in.readString();
        min_rate = in.readString();
        max_rate = in.readString();
        s_min = in.readString();
        s_max = in.readString();
        date_time = in.readString();
        time = in.readString();
    }

    public static final Creator<MatchOddHistoryData> CREATOR = new Creator<MatchOddHistoryData>() {
        @Override
        public MatchOddHistoryData createFromParcel(Parcel in) {
            return new MatchOddHistoryData(in);
        }

        @Override
        public MatchOddHistoryData[] newArray(int size) {
            return new MatchOddHistoryData[size];
        }
    };

    public String getMatch_odd_id() {
        return match_odd_id;
    }

    public String getInning() {
        return inning;
    }

    public String getTeam() {
        return team;
    }

    public String getScore() {
        return score;
    }

    public String getOvers() {
        return overs;
    }

    public String getRuns() {
        return runs;
    }

    public String getMin_rate() {
        return min_rate;
    }

    public String getMax_rate() {
        return max_rate;
    }

    public String getS_min() {
        return s_min;
    }

    public String getS_max() {
        return s_max;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(match_odd_id);
        parcel.writeString(inning);
        parcel.writeString(team);
        parcel.writeString(score);
        parcel.writeString(overs);
        parcel.writeString(runs);
        parcel.writeString(min_rate);
        parcel.writeString(max_rate);
        parcel.writeString(s_min);
        parcel.writeString(s_max);
        parcel.writeString(date_time);
        parcel.writeString(time);
    }
}
