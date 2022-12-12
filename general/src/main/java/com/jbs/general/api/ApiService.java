package com.jbs.general.api;

import com.jbs.general.model.response.base.BaseResponse;
import com.jbs.general.model.response.commentary.MainResponseCommentary;
import com.jbs.general.model.response.homelist.MainResponseHomeList;
import com.jbs.general.model.response.liveMatch.MainResponseLiveMatch;
import com.jbs.general.model.response.liveMatchList.MainResponseLiveMatchList;
import com.jbs.general.model.response.matchFancy.MainResponseMatchFancy;
import com.jbs.general.model.response.matchInfo.MainResponseMatchInfo;
import com.jbs.general.model.response.matchOddHistory.MainResponseMatchOddHistory;
import com.jbs.general.model.response.matchesBySeriesId.MainResponseMatchesBySeriesId;
import com.jbs.general.model.response.matchesBySeriesId.MainResponseMatchesBySeriesId2;
import com.jbs.general.model.response.news.MainResponseNews;
import com.jbs.general.model.response.news.MainResponseNewsDetails;
import com.jbs.general.model.response.playerRanking.MainResponsePlayerRanking;
import com.jbs.general.model.response.playersByMatchId.MainResponsePlayersByMatchId;
import com.jbs.general.model.response.pointsTable.MainResponsePointsTable;
import com.jbs.general.model.response.recentMatches.MainResponseRecentMatches;
import com.jbs.general.model.response.scorecardByMatchId.MainResponseScorecardByMatchId;
import com.jbs.general.model.response.serieslist.MainResponseSeries;
import com.jbs.general.model.response.serieslist.MainResponseSeriesList;
import com.jbs.general.model.response.squadByMatchId.MainResponseSquadByMatchId;
import com.jbs.general.model.response.teamRanking.MainResponseTeamRanking;
import com.jbs.general.model.response.upcomingMatches.MainResponseUpComingMatches;
import com.jbs.general.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    //Home List
    @GET(Constants.APIEndPoints.HOME_LIST)
    Call<MainResponseHomeList> getHomeList();

    //Series Login
    @GET(Constants.APIEndPoints.SERIES_LIST)
    Call<MainResponseSeriesList> getSeriesList();

    //Series Login
    @GET(Constants.APIEndPoints.SERIES_LIST_2)
    Call<MainResponseSeries> getSeries();

    //Matches By SeriesId
    @POST(Constants.APIEndPoints.MATCHES_BY_SERIES_ID)
    @FormUrlEncoded
    Call<MainResponseMatchesBySeriesId> getMatchesBySeriesId(@Field(Constants.APIKeys.SERIES_ID) String seriesId);

    //Matches By SeriesId
    @POST(Constants.APIEndPoints.MATCHES_BY_SERIES_ID)
    @FormUrlEncoded
    Call<MainResponseMatchesBySeriesId2> getMatchesBySeriesId2(@Field(Constants.APIKeys.SERIES_ID) String seriesId);

    //Upcoming Matches
    @GET(Constants.APIEndPoints.UPCOMING_MATCHES)
    Call<MainResponseUpComingMatches> getUpComingMatches();

    //Recent Matches
    @GET(Constants.APIEndPoints.RECENT_MATCHES)
    Call<MainResponseRecentMatches> getRecentMatches();

    //Scorecard By MatchId
    @POST(Constants.APIEndPoints.SCORE_CARD_BY_MATCH_ID)
    @FormUrlEncoded
    Call<MainResponseScorecardByMatchId> getScorecardByMatchId(@Field(Constants.APIKeys.MATCH_ID) String seriesId);

    //Match Info
    @POST(Constants.APIEndPoints.MATCH_INFO)
    @FormUrlEncoded
    Call<MainResponseMatchInfo> getMatchInfo(@Field(Constants.APIKeys.MATCH_ID) String matchId);

    //Squad By MatchId
    @POST(Constants.APIEndPoints.SQUAD_BY_MATCH_ID)
    @FormUrlEncoded
    Call<MainResponseSquadByMatchId> getSquadByMatchId(@Field(Constants.APIKeys.MATCH_ID) String seriesId);

    //Match Fancy
    @POST(Constants.APIEndPoints.MATCH_FANCY)
    @FormUrlEncoded
    Call<MainResponseMatchFancy> getMatchFancy(@Field(Constants.APIKeys.MATCH_ID) String seriesId);

    //Live MatchList
    @GET(Constants.APIEndPoints.LIVE_MATCH_LIST)
    Call<MainResponseLiveMatchList> getLiveMatchList();

    //Live Match
    @POST(Constants.APIEndPoints.LIVE_MATCH)
    @FormUrlEncoded
    Call<MainResponseLiveMatch> getLiveMatch(@Field(Constants.APIKeys.MATCH_ID) String matchId);

    //Live Match
    @POST(Constants.APIEndPoints.COMMENTARY)
    @FormUrlEncoded
    Call<MainResponseCommentary> getLiveCommentary(@Field(Constants.APIKeys.MATCH_ID) String matchId);

    //PointsTable
    @POST(Constants.APIEndPoints.POINTS_TABLE)
    @FormUrlEncoded
    Call<MainResponsePointsTable> getPointsTable(@Field(Constants.APIKeys.SERIES_ID) String seriesId);

    //Players By MatchId
    @POST(Constants.APIEndPoints.PLAYERS_BY_MATCH_ID)
    @FormUrlEncoded
    Call<MainResponsePlayersByMatchId> getPlayersByMatchId(@Field(Constants.APIKeys.MATCH_ID) String seriesId);

    //Match Odd History
    @POST(Constants.APIEndPoints.MATCH_ODD_HISTORY)
    @FormUrlEncoded
    Call<MainResponseMatchOddHistory> getMatchOddHistory(@Field(Constants.APIKeys.MATCH_ID) String seriesId);

    /*//Match Stats
    @POST(Constants.APIEndPoints.MATCH_STATES)
    @FormUrlEncoded
    Call<MainResponseProfile> getMatchStats(@Field(Constants.APIKeys.MATCH_ID) String seriesId);*/

    //News
    @GET(Constants.APIEndPoints.NEWS)
    Call<MainResponseNews> getNews();

    //News Detail
    @POST(Constants.APIEndPoints.NEWS_DETAILS)
    @FormUrlEncoded
    Call<MainResponseNewsDetails> getNewsDetail(@Field(Constants.APIKeys.NEWS_ID) String seriesId);

    /*//Commentary
    @POST(Constants.APIEndPoints.COMMENTARY)
    @FormUrlEncoded
    Call<MainResponseProfile> getCommentary(@Field(Constants.APIKeys.MATCH_ID) String seriesId);*/

    //Player Ranking
    @POST(Constants.APIEndPoints.PLAYER_RANKING)
    @FormUrlEncoded
    Call<MainResponsePlayerRanking> getPlayerRanking(@Field(Constants.APIKeys.TYPE) String type);

    //Team Ranking
    @POST(Constants.APIEndPoints.TEAM_RANKING)
    @FormUrlEncoded
    Call<MainResponseTeamRanking> getTeamRanking(@Field(Constants.APIKeys.TYPE) String type);


}