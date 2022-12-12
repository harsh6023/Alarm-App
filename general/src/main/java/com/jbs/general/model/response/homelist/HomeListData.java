package com.jbs.general.model.response.homelist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeListData implements Parcelable {
    @SerializedName("match_id")
    @Expose
    private String match_id;
    @SerializedName("series_id")
    @Expose
    private String series_id;
    @SerializedName("series")
    @Expose
    private String series;
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
    @SerializedName("match_status")
    @Expose
    private String match_status;
    @SerializedName("fav_team")
    @Expose
    private String fav_team;
    @SerializedName("min_rate")
    @Expose
    private String min_rate;
    @SerializedName("max_rate")
    @Expose
    private String max_rate;
    @SerializedName("s_max")
    @Expose
    private String s_max;
    @SerializedName("s_min")
    @Expose
    private String s_min;
    @SerializedName("s_ovr")
    @Expose
    private String s_ovr;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("result")
    @Expose
    private String result;

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
    @SerializedName("team_a_score")
    @Expose
    private TeamScores teamAScore;
    @SerializedName("team_a_scores")
    @Expose
    private String team_a_scores;
    @SerializedName("team_a_over")
    @Expose
    private String team_a_over;
    @SerializedName("team_a_scores_over")
    @Expose
    private List<TeamScoresOver> teamAScoresOverList;

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
    @SerializedName("team_b_score")
    @Expose
    private TeamScores teamBScore;
    @SerializedName("team_b_scores")
    @Expose
    private String team_b_scores;
    @SerializedName("team_b_over")
    @Expose
    private String team_b_over;
    @SerializedName("team_b_scores_over")
    @Expose
    private List<TeamScoresOver> teamBScoresOverList;

    protected HomeListData(Parcel in) {
        match_id = in.readString();
        series_id = in.readString();
        series = in.readString();
        match_date = in.readString();
        match_time = in.readString();
        matchs = in.readString();
        venue = in.readString();
        match_type = in.readString();
        match_status = in.readString();
        fav_team = in.readString();
        min_rate = in.readString();
        max_rate = in.readString();
        s_max = in.readString();
        s_min = in.readString();
        s_ovr = in.readString();
        session = in.readString();
        result = in.readString();
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

    public static final Creator<HomeListData> CREATOR = new Creator<HomeListData>() {
        @Override
        public HomeListData createFromParcel(Parcel in) {
            return new HomeListData(in);
        }

        @Override
        public HomeListData[] newArray(int size) {
            return new HomeListData[size];
        }
    };

    public String getMatch_id() {
        return match_id;
    }

    public String getSeries_id() {
        return series_id;
    }

    public String getSeries() {
        return series;
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

    public String getMatch_status() {
        return match_status;
    }

    public String getFav_team() {
        return fav_team;
    }

    public String getMin_rate() {
        return min_rate;
    }

    public String getMax_rate() {
        return max_rate;
    }

    public String getS_max() {
        return s_max;
    }

    public String getS_min() {
        return s_min;
    }

    public String getS_ovr() {
        return s_ovr;
    }

    public String getSession() {
        return session;
    }

    public String getResult() {
        return result;
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

    public TeamScores getTeamAScore() {
        return teamAScore;
    }

    public String getTeam_a_scores() {
        return team_a_scores;
    }

    public String getTeam_a_over() {
        return team_a_over;
    }

    public List<TeamScoresOver> getTeamAScoresOverList() {
        return teamAScoresOverList;
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

    public TeamScores getTeamBScore() {
        return teamBScore;
    }

    public String getTeam_b_scores() {
        return team_b_scores;
    }

    public String getTeam_b_over() {
        return team_b_over;
    }

    public List<TeamScoresOver> getTeamBScoresOverList() {
        return teamBScoresOverList;
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
        parcel.writeString(match_date);
        parcel.writeString(match_time);
        parcel.writeString(matchs);
        parcel.writeString(venue);
        parcel.writeString(match_type);
        parcel.writeString(match_status);
        parcel.writeString(fav_team);
        parcel.writeString(min_rate);
        parcel.writeString(max_rate);
        parcel.writeString(s_max);
        parcel.writeString(s_min);
        parcel.writeString(s_ovr);
        parcel.writeString(session);
        parcel.writeString(result);
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
