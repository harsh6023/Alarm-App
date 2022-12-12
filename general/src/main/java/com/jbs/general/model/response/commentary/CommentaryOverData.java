package com.jbs.general.model.response.commentary;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentaryOverData implements Parcelable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("over")
    @Expose
    private String over;
    @SerializedName("runs")
    @Expose
    private String runs;
    @SerializedName("wickets")
    @Expose
    private String wickets;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("team_score")
    @Expose
    private String team_score;
    @SerializedName("team_wicket")
    @Expose
    private String team_wicket;
    @SerializedName("batsman_1_name")
    @Expose
    private String batsman_1_name;
    @SerializedName("batsman_1_runs")
    @Expose
    private String batsman_1_runs;
    @SerializedName("batsman_1_balls")
    @Expose
    private String batsman_1_balls;
    @SerializedName("batsman_2_name")
    @Expose
    private String batsman_2_name;
    @SerializedName("batsman_2_runs")
    @Expose
    private String batsman_2_runs;
    @SerializedName("batsman_2_balls")
    @Expose
    private String batsman_2_balls;
    @SerializedName("bolwer_name")
    @Expose
    private String bolwer_name;
    @SerializedName("bolwer_overs")
    @Expose
    private String bolwer_overs;
    @SerializedName("bolwer_maidens")
    @Expose
    private String bolwer_maidens;
    @SerializedName("bolwer_runs")
    @Expose
    private String bolwer_runs;
    @SerializedName("bolwer_wickets")
    @Expose
    private String bolwer_wickets;

    @SerializedName("overs")
    @Expose
    private String overs;
    @SerializedName("wicket")
    @Expose
    private String wicket;
    @SerializedName("description")
    @Expose
    private String description;

    protected CommentaryOverData(Parcel in) {
        title = in.readString();
        over = in.readString();
        runs = in.readString();
        wickets = in.readString();
        team = in.readString();
        team_score = in.readString();
        team_wicket = in.readString();
        batsman_1_name = in.readString();
        batsman_1_runs = in.readString();
        batsman_1_balls = in.readString();
        batsman_2_name = in.readString();
        batsman_2_runs = in.readString();
        batsman_2_balls = in.readString();
        bolwer_name = in.readString();
        bolwer_overs = in.readString();
        bolwer_maidens = in.readString();
        bolwer_runs = in.readString();
        bolwer_wickets = in.readString();
        overs = in.readString();
        wicket = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(over);
        dest.writeString(runs);
        dest.writeString(wickets);
        dest.writeString(team);
        dest.writeString(team_score);
        dest.writeString(team_wicket);
        dest.writeString(batsman_1_name);
        dest.writeString(batsman_1_runs);
        dest.writeString(batsman_1_balls);
        dest.writeString(batsman_2_name);
        dest.writeString(batsman_2_runs);
        dest.writeString(batsman_2_balls);
        dest.writeString(bolwer_name);
        dest.writeString(bolwer_overs);
        dest.writeString(bolwer_maidens);
        dest.writeString(bolwer_runs);
        dest.writeString(bolwer_wickets);
        dest.writeString(overs);
        dest.writeString(wicket);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentaryOverData> CREATOR = new Creator<CommentaryOverData>() {
        @Override
        public CommentaryOverData createFromParcel(Parcel in) {
            return new CommentaryOverData(in);
        }

        @Override
        public CommentaryOverData[] newArray(int size) {
            return new CommentaryOverData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getOver() {
        return over;
    }

    public String getRuns() {
        return runs;
    }

    public String getWickets() {
        return wickets;
    }

    public String getTeam() {
        return team;
    }

    public String getTeam_score() {
        return team_score;
    }

    public String getTeam_wicket() {
        return team_wicket;
    }

    public String getBatsman_1_name() {
        return batsman_1_name;
    }

    public String getBatsman_1_runs() {
        return batsman_1_runs;
    }

    public String getBatsman_1_balls() {
        return batsman_1_balls;
    }

    public String getBatsman_2_name() {
        return batsman_2_name;
    }

    public String getBatsman_2_runs() {
        return batsman_2_runs;
    }

    public String getBatsman_2_balls() {
        return batsman_2_balls;
    }

    public String getBolwer_name() {
        return bolwer_name;
    }

    public String getBolwer_overs() {
        return bolwer_overs;
    }

    public String getBolwer_maidens() {
        return bolwer_maidens;
    }

    public String getBolwer_runs() {
        return bolwer_runs;
    }

    public String getBolwer_wickets() {
        return bolwer_wickets;
    }

    public String getOvers() {
        return overs;
    }

    public String getWicket() {
        return wicket;
    }

    public String getDescription() {
        return description;
    }
}
