package com.jbs.general.model.response.liveMatch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveTeamScore implements Parcelable {
    @SerializedName("team_id")
    @Expose
    private String team_id;
    @SerializedName("1")
    @Expose
    private LiveInnScore liveInnScore1;
    @SerializedName("2")
    @Expose
    private LiveInnScore liveInnScore2;
    @SerializedName("3")
    @Expose
    private LiveInnScore liveInnScore3;
    @SerializedName("4")
    @Expose
    private LiveInnScore liveInnScore4;

    protected LiveTeamScore(Parcel in) {
        team_id = in.readString();
        liveInnScore1 = in.readParcelable(LiveInnScore.class.getClassLoader());
        liveInnScore2 = in.readParcelable(LiveInnScore.class.getClassLoader());
        liveInnScore3 = in.readParcelable(LiveInnScore.class.getClassLoader());
        liveInnScore4 = in.readParcelable(LiveInnScore.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(team_id);
        dest.writeParcelable(liveInnScore1, flags);
        dest.writeParcelable(liveInnScore2, flags);
        dest.writeParcelable(liveInnScore3, flags);
        dest.writeParcelable(liveInnScore4, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveTeamScore> CREATOR = new Creator<LiveTeamScore>() {
        @Override
        public LiveTeamScore createFromParcel(Parcel in) {
            return new LiveTeamScore(in);
        }

        @Override
        public LiveTeamScore[] newArray(int size) {
            return new LiveTeamScore[size];
        }
    };

    public String getTeam_id() {
        return team_id;
    }

    public LiveInnScore getLiveInnScore1() {
        return liveInnScore1;
    }

    public LiveInnScore getLiveInnScore2() {
        return liveInnScore2;
    }

    public LiveInnScore getLiveInnScore3() {
        return liveInnScore3;
    }

    public LiveInnScore getLiveInnScore4() {
        return liveInnScore4;
    }
}
