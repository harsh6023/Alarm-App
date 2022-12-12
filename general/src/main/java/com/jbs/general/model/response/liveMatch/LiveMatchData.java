package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LiveMatchData implements Parcelable {
    @SerializedName("match_id")
    @Expose
    private String match_id;
    @SerializedName("series_id")
    @Expose
    private String series_id;
    @SerializedName("match_over")
    @Expose
    private String match_over;
    @SerializedName("min_rate")
    @Expose
    private String min_rate;
    @SerializedName("max_rate")
    @Expose
    private String max_rate;
    @SerializedName("fav_team")
    @Expose
    private String fav_team;
    @SerializedName("toss")
    @Expose
    private String toss;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("match_type")
    @Expose
    private String match_type;
    @SerializedName("powerplay")
    @Expose
    private String powerplay;
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
    @SerializedName("next_batsman")
    @Expose
    private String next_batsman;
    @SerializedName("s_ball")
    @Expose
    private String s_ball;
    @SerializedName("s_run")
    @Expose
    private String s_run;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("current_inning")
    @Expose
    private String current_inning;
    @SerializedName("batsman")
    @Expose
    private List<LiveBatsman> liveBatsmanList = new ArrayList<>();
    @SerializedName("bolwer")
    @Expose
    private LiveBolwer liveBolwer;
    @SerializedName("first_circle")
    @Expose
    private String first_circle;
    @SerializedName("second_circle")
    @Expose
    private String second_circle;
    @SerializedName("s_ovr")
    @Expose
    private String s_ovr;
    @SerializedName("s_min")
    @Expose
    private String s_min;
    @SerializedName("s_max")
    @Expose
    private String s_max;
    @SerializedName("batting_team")
    @Expose
    private String batting_team;
    @SerializedName("team_a_score")
    @Expose
    private LiveTeamScore team_a_score;
    @SerializedName("team_a_scores")
    @Expose
    private String team_a_scores;
    @SerializedName("team_a_over")
    @Expose
    private String team_a_over;
    @SerializedName("team_a_scores_over")
    @Expose
    private List<LiveTeamScoresOver> teamScoresOverAList = new ArrayList<>();
    @SerializedName("balling_team")
    @Expose
    private String balling_team;
    @SerializedName("partnership")
    @Expose
    private LivePartnership partnership;
    @SerializedName("yet_to_bet")
    @Expose
    private List<String> yetToBatList = new ArrayList<>();
    @SerializedName("curr_rate")
    @Expose
    private String curr_rate;
    @SerializedName("last4overs")
    @Expose
    private List<Last4Overs> last4OversList = new ArrayList<>();
    @SerializedName("last36ball")
    @Expose
    private List<String> last36BallList = new ArrayList<>();
    @SerializedName("lastwicket")
    @Expose
    private LastWicket lastWicket;
    @SerializedName("team_b_score")
    @Expose
    private LiveTeamScore team_b_score;
    @SerializedName("team_b_scores")
    @Expose
    private String team_b_scores;
    @SerializedName("team_b_over")
    @Expose
    private String team_b_over;
    @SerializedName("team_b_scores_over")
    @Expose
    private List<LiveTeamScoresOver> teamScoresOverBList = new ArrayList<>();
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("rr_rate")
    @Expose
    private String rr_rate;
    @SerializedName("run_need")
    @Expose
    private String run_need;
    @SerializedName("ball_rem")
    @Expose
    private String ball_rem;
    @SerializedName("trail_lead")
    @Expose
    private String trail_lead;

    protected LiveMatchData(Parcel in) {
        match_id = in.readString();
        series_id = in.readString();
        match_over = in.readString();
        min_rate = in.readString();
        max_rate = in.readString();
        fav_team = in.readString();
        toss = in.readString();
        result = in.readString();
        match_type = in.readString();
        powerplay = in.readString();
        team_a_id = in.readString();
        team_a = in.readString();
        team_a_short = in.readString();
        team_a_img = in.readString();
        team_b_id = in.readString();
        team_b = in.readString();
        team_b_short = in.readString();
        team_b_img = in.readString();
        next_batsman = in.readString();
        s_ball = in.readString();
        s_run = in.readString();
        session = in.readString();
        current_inning = in.readString();
        liveBatsmanList = in.createTypedArrayList(LiveBatsman.CREATOR);
        liveBolwer = in.readParcelable(LiveBolwer.class.getClassLoader());
        first_circle = in.readString();
        second_circle = in.readString();
        s_ovr = in.readString();
        s_min = in.readString();
        s_max = in.readString();
        batting_team = in.readString();
        team_a_score = in.readParcelable(LiveTeamScore.class.getClassLoader());
        team_a_scores = in.readString();
        team_a_over = in.readString();
        teamScoresOverAList = in.createTypedArrayList(LiveTeamScoresOver.CREATOR);
        balling_team = in.readString();
        partnership = in.readParcelable(LivePartnership.class.getClassLoader());
        yetToBatList = in.createStringArrayList();
        curr_rate = in.readString();
        last4OversList = in.createTypedArrayList(Last4Overs.CREATOR);
        last36BallList = in.createStringArrayList();
        lastWicket = in.readParcelable(LastWicket.class.getClassLoader());
        team_b_score = in.readParcelable(LiveTeamScore.class.getClassLoader());
        team_b_scores = in.readString();
        team_b_over = in.readString();
        teamScoresOverBList = in.createTypedArrayList(LiveTeamScoresOver.CREATOR);
        target = in.readString();
        rr_rate = in.readString();
        run_need = in.readString();
        ball_rem = in.readString();
        trail_lead = in.readString();
    }

    public static final Creator<LiveMatchData> CREATOR = new Creator<LiveMatchData>() {
        @Override
        public LiveMatchData createFromParcel(Parcel in) {
            return new LiveMatchData(in);
        }

        @Override
        public LiveMatchData[] newArray(int size) {
            return new LiveMatchData[size];
        }
    };

    public String getMatch_id() {
        return match_id;
    }

    public String getSeries_id() {
        return series_id;
    }

    public String getMatch_over() {
        return match_over;
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

    public String getToss() {
        return toss;
    }

    public String getResult() {
        return result;
    }

    public String getMatch_type() {
        return match_type;
    }

    public String getPowerplay() {
        return powerplay;
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

    public String getNext_batsman() {
        return next_batsman;
    }

    public String getS_ball() {
        return s_ball;
    }

    public String getS_run() {
        return s_run;
    }

    public String getSession() {
        return session;
    }

    public String getCurrent_inning() {
        return current_inning;
    }

    public List<LiveBatsman> getLiveBatsmanList() {
        return liveBatsmanList;
    }

    public LiveBolwer getLiveBolwer() {
        return liveBolwer;
    }

    public String getFirst_circle() {
        return first_circle;
    }

    public String getSecond_circle() {
        return second_circle;
    }

    public String getS_ovr() {
        return s_ovr;
    }

    public String getS_min() {
        return s_min;
    }

    public String getS_max() {
        return s_max;
    }

    public String getBatting_team() {
        return batting_team;
    }

    public String getTeam_a_scores() {
        return team_a_scores;
    }

    public String getTeam_a_over() {
        return team_a_over;
    }

    public String getBalling_team() {
        return balling_team;
    }

    public LivePartnership getPartnership() {
        return partnership;
    }

    public List<String> getYetToBatList() {
        return yetToBatList;
    }

    public String getCurr_rate() {
        return curr_rate;
    }

    public List<Last4Overs> getLast4OversList() {
        return last4OversList;
    }

    public List<String> getLast36BallList() {
        return last36BallList;
    }

    public LastWicket getLastWicket() {
        return lastWicket;
    }

    public LiveTeamScore getTeam_a_score() {
        return team_a_score;
    }

    public List<LiveTeamScoresOver> getTeamScoresOverAList() {
        return teamScoresOverAList;
    }

    public LiveTeamScore getTeam_b_score() {
        return team_b_score;
    }

    public String getTeam_b_scores() {
        return team_b_scores;
    }

    public String getTeam_b_over() {
        return team_b_over;
    }

    public List<LiveTeamScoresOver> getTeamScoresOverBList() {
        return teamScoresOverBList;
    }

    public String getTarget() {
        return target;
    }

    public String getRr_rate() {
        return rr_rate;
    }

    public String getRun_need() {
        return run_need;
    }

    public String getBall_rem() {
        return ball_rem;
    }

    public String getTrail_lead() {
        return trail_lead;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(match_id);
        parcel.writeString(series_id);
        parcel.writeString(match_over);
        parcel.writeString(min_rate);
        parcel.writeString(max_rate);
        parcel.writeString(fav_team);
        parcel.writeString(toss);
        parcel.writeString(result);
        parcel.writeString(match_type);
        parcel.writeString(powerplay);
        parcel.writeString(team_a_id);
        parcel.writeString(team_a);
        parcel.writeString(team_a_short);
        parcel.writeString(team_a_img);
        parcel.writeString(team_b_id);
        parcel.writeString(team_b);
        parcel.writeString(team_b_short);
        parcel.writeString(team_b_img);
        parcel.writeString(next_batsman);
        parcel.writeString(s_ball);
        parcel.writeString(s_run);
        parcel.writeString(session);
        parcel.writeString(current_inning);
        parcel.writeTypedList(liveBatsmanList);
        parcel.writeParcelable(liveBolwer, i);
        parcel.writeString(first_circle);
        parcel.writeString(second_circle);
        parcel.writeString(s_ovr);
        parcel.writeString(s_min);
        parcel.writeString(s_max);
        parcel.writeString(batting_team);
        parcel.writeParcelable(team_a_score, i);
        parcel.writeString(team_a_scores);
        parcel.writeString(team_a_over);
        parcel.writeTypedList(teamScoresOverAList);
        parcel.writeString(balling_team);
        parcel.writeParcelable(partnership, i);
        parcel.writeStringList(yetToBatList);
        parcel.writeString(curr_rate);
        parcel.writeTypedList(last4OversList);
        parcel.writeStringList(last36BallList);
        parcel.writeParcelable(lastWicket, i);
        parcel.writeParcelable(team_b_score, i);
        parcel.writeString(team_b_scores);
        parcel.writeString(team_b_over);
        parcel.writeTypedList(teamScoresOverBList);
        parcel.writeString(target);
        parcel.writeString(rr_rate);
        parcel.writeString(run_need);
        parcel.writeString(ball_rem);
        parcel.writeString(trail_lead);
    }
}
