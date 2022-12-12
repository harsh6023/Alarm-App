package com.jbs.general.model.response.matchInfo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jbs.general.model.response.squadByMatchId.SquadByMatchIdData;

public class MatchInfoData implements Parcelable {
    @SerializedName("series_id")
    @Expose
    private String series_id;
    @SerializedName("series")
    @Expose
    private String series;
    @SerializedName("matchs")
    @Expose
    private String matchs;
    @SerializedName("match_date")
    @Expose
    private String match_date;
    @SerializedName("match_time")
    @Expose
    private String match_time;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("toss")
    @Expose
    private String toss;
    @SerializedName("umpire")
    @Expose
    private String umpire;
    @SerializedName("third_umpire")
    @Expose
    private String third_umpire;
    @SerializedName("referee")
    @Expose
    private String referee;
    @SerializedName("man_of_match")
    @Expose
    private String man_of_match;
    @SerializedName("match_type")
    @Expose
    private String match_type;
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
    @SerializedName("squad")
    @Expose
    private SquadByMatchIdData squad;

    protected MatchInfoData(Parcel in) {
        series_id = in.readString();
        series = in.readString();
        matchs = in.readString();
        match_date = in.readString();
        match_time = in.readString();
        venue = in.readString();
        toss = in.readString();
        umpire = in.readString();
        third_umpire = in.readString();
        referee = in.readString();
        man_of_match = in.readString();
        match_type = in.readString();
        team_a_id = in.readString();
        team_a = in.readString();
        team_a_short = in.readString();
        team_a_img = in.readString();
        team_b_id = in.readString();
        team_b = in.readString();
        team_b_short = in.readString();
        team_b_img = in.readString();
        squad = in.readParcelable(SquadByMatchIdData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(series_id);
        dest.writeString(series);
        dest.writeString(matchs);
        dest.writeString(match_date);
        dest.writeString(match_time);
        dest.writeString(venue);
        dest.writeString(toss);
        dest.writeString(umpire);
        dest.writeString(third_umpire);
        dest.writeString(referee);
        dest.writeString(man_of_match);
        dest.writeString(match_type);
        dest.writeString(team_a_id);
        dest.writeString(team_a);
        dest.writeString(team_a_short);
        dest.writeString(team_a_img);
        dest.writeString(team_b_id);
        dest.writeString(team_b);
        dest.writeString(team_b_short);
        dest.writeString(team_b_img);
        dest.writeParcelable(squad, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MatchInfoData> CREATOR = new Creator<MatchInfoData>() {
        @Override
        public MatchInfoData createFromParcel(Parcel in) {
            return new MatchInfoData(in);
        }

        @Override
        public MatchInfoData[] newArray(int size) {
            return new MatchInfoData[size];
        }
    };

    public String getSeries_id() {
        return series_id;
    }

    public String getSeries() {
        return series;
    }

    public String getMatchs() {
        return matchs;
    }

    public String getMatch_date() {
        return match_date;
    }

    public String getMatch_time() {
        return match_time;
    }

    public String getVenue() {
        return venue;
    }

    public String getToss() {
        return toss;
    }

    public String getUmpire() {
        return umpire;
    }

    public String getThird_umpire() {
        return third_umpire;
    }

    public String getReferee() {
        return referee;
    }

    public String getMan_of_match() {
        return man_of_match;
    }

    public String getMatch_type() {
        return match_type;
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

    public SquadByMatchIdData getSquad() {
        return squad;
    }
}
