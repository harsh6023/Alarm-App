package com.jbs.general.model.response.matchesBySeriesId;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchesData implements Parcelable {
    @SerializedName("match_id")
    @Expose
    private String match_id;
    @SerializedName("series_id")
    @Expose
    private String series_id;
    @SerializedName("series")
    @Expose
    private String series;
    @SerializedName("date_wise")
    @Expose
    private String date_wise;
    @SerializedName("match_date")
    @Expose
    private String match_date;
    @SerializedName("match_time")
    @Expose
    private String match_time;
    @SerializedName("matchs")
    @Expose
    private String matchs;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("match_type")
    @Expose
    private String match_type;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("min_rate")
    @Expose
    private String min_rate;
    @SerializedName("max_rate")
    @Expose
    private String max_rate;
    @SerializedName("fav_team")
    @Expose
    private String fav_team;
    @SerializedName("team_a_id")
    @Expose
    private String team_a_id;
    @SerializedName("team_a")
    @Expose
    private String team_a;
    @SerializedName("team_a_short")
    @Expose
    private String team_a_short;
    @SerializedName("team_a_img")
    @Expose
    private String team_a_img;
    @SerializedName("team_a_scores")
    @Expose
    private String team_a_scores;
    @SerializedName("team_a_over")
    @Expose
    private String team_a_over;
    @SerializedName("team_b_id")
    @Expose
    private String team_b_id;
    @SerializedName("team_b")
    @Expose
    private String team_b;
    @SerializedName("team_b_short")
    @Expose
    private String team_b_short;
    @SerializedName("team_b_img")
    @Expose
    private String team_b_img;
    @SerializedName("team_b_scores")
    @Expose
    private String team_b_scores;
    @SerializedName("team_b_over")
    @Expose
    private String team_b_over;

    protected MatchesData(Parcel in) {
        match_id = in.readString();
        series_id = in.readString();
        series = in.readString();
        date_wise = in.readString();
        match_date = in.readString();
        match_time = in.readString();
        matchs = in.readString();
        venue = in.readString();
        match_type = in.readString();
        result = in.readString();
        min_rate = in.readString();
        max_rate = in.readString();
        fav_team = in.readString();
        team_a_id = in.readString();
        team_a = in.readString();
        team_a_short = in.readString();
        team_a_img = in.readString();
        team_a_scores = in.readString();
        team_a_over = in.readString();
        team_b_id = in.readString();
        team_b = in.readString();
        team_b_short = in.readString();
        team_b_img = in.readString();
        team_b_scores = in.readString();
        team_b_over = in.readString();
    }

    public static final Creator<MatchesData> CREATOR = new Creator<MatchesData>() {
        @Override
        public MatchesData createFromParcel(Parcel in) {
            return new MatchesData(in);
        }

        @Override
        public MatchesData[] newArray(int size) {
            return new MatchesData[size];
        }
    };

    public String getSeries_id() {
        return series_id;
    }

    public String getSeries() {
        return series;
    }

    public String getMatch_id() {
        return match_id;
    }

    public String getDate_wise() {
        return date_wise;
    }

    public String getMatch_date() {
        return match_date;
    }

    public String getMatch_time() {
        return match_time;
    }

    public String getMatchs() {
        return matchs;
    }

    public String getVenue() {
        return venue;
    }

    public String getMatch_type() {
        return match_type;
    }

    public String getMin_rate() {
        return min_rate;
    }

    public String getMax_rate() {
        return max_rate;
    }

    public String getFav_team() {
        return fav_team;
    }

    public String getTeam_a_id() {
        return team_a_id;
    }

    public String getTeam_a() {
        return team_a;
    }

    public String getTeam_a_short() {
        return team_a_short;
    }

    public String getTeam_a_img() {
        return team_a_img;
    }

    public String getTeam_b_id() {
        return team_b_id;
    }

    public String getTeam_b() {
        return team_b;
    }

    public String getTeam_b_short() {
        return team_b_short;
    }

    public String getTeam_b_img() {
        return team_b_img;
    }

    public String getResult() {
        return result;
    }

    public String getTeam_a_scores() {
        return team_a_scores;
    }

    public String getTeam_a_over() {
        return team_a_over;
    }

    public String getTeam_b_scores() {
        return team_b_scores;
    }

    public String getTeam_b_over() {
        return team_b_over;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(match_id);
        parcel.writeString(series_id);
        parcel.writeString(series);
        parcel.writeString(date_wise);
        parcel.writeString(match_date);
        parcel.writeString(match_time);
        parcel.writeString(matchs);
        parcel.writeString(venue);
        parcel.writeString(match_type);
        parcel.writeString(result);
        parcel.writeString(min_rate);
        parcel.writeString(max_rate);
        parcel.writeString(fav_team);
        parcel.writeString(team_a_id);
        parcel.writeString(team_a);
        parcel.writeString(team_a_short);
        parcel.writeString(team_a_img);
        parcel.writeString(team_a_scores);
        parcel.writeString(team_a_over);
        parcel.writeString(team_b_id);
        parcel.writeString(team_b);
        parcel.writeString(team_b_short);
        parcel.writeString(team_b_img);
        parcel.writeString(team_b_scores);
        parcel.writeString(team_b_over);
    }
}
