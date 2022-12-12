package com.jbs.general.utils;

import com.jbs.general.BuildConfig;

public class Constants {

    //True - Show Custom Logs and Messages [Make it true in Development]
    //False - Disable Custom logs and messages [Make it false in Release Build]
    public static final boolean IS_DEBUG_ENABLE = true;

    //API Base URL
    //public static String BASE_URL = "http://15.206.87.117/creduce_html/a2z/public/api/";
    public static String BASE_URL = "https://a2zpay247.com/api/";

    /**
     * Default Values used in Game
     */
    public interface AppConstant {
        //String COUNTRY_LIST_FILE_NAME = "country_list.json";
        String COUNTRY_LIST_FILE_NAME = "country_list_2.json";

        int DEFAULT_PAGE_OFFSET = 1;
        int DEFAULT_RECORD_LIMIT = 10;
        int NOTIFICATION_RECORD_LIMIT = 15;
    }

    /**
     * Delays or Intervals
     */
    public interface Delays {
        int SPLASH_INTERVAL = 2000; //in milliseconds
        int MIN_TIME_BETWEEN_CLICKS = 300; //in milliseconds
        int BACK_PRESS_INTERVAL = 2000; //in milliseconds
        long PHONE_AUTH_TIMEOUT = 60; //in Seconds
    }

    /**
     * Activity/Fragment Intent/Bundle Arguments Keys and Values
     */
    public interface Bundles {
        String COUNTRY_FLAG = "COUNTRY_FLAG";
        String COUNTRY_CODE = "COUNTRY_CODE";
        String PHONE_NUMBER = "PHONE_NUMBER";
        String PASSWORD = "PASSWORD";
        String TRANSACTION_ID = "TRANSACTION_ID";
        String TRANSACTION_TYPE = "TRANSACTION_TYPE";
        String GET_EXCHANGE = "GET_EXCHANGE";
        String SHOW_USERNAME = "SHOW_USERNAME";
        String AMOUNT = "AMOUNT";
        String WITHDRAW_DEPOSIT_TYPE = "WITHDRAW_DEPOSIT_TYPE";
        String ID_DETAILS = "ID_DETAILS";
        String PROFILE_DATA = "PROFILE_DATA";
        String OTP = "OTP";
    }

    /**
     * Fields Validations
     * Note: Update Values in integers.xml too
     */
    public interface FieldValidation {
        int PHONE_NUMBER_MIN_LENGTH = 9;
        int PASSWORD_MIN_LENGTH = 8;
    }

    /**
     * Preference Keys
     */
    public interface PreferenceKeys {
        String USER_ID = "user_id";
        String USER_TOKEN = "user_token";
        String PROFILE_DATA = "PROFILE_DATA";
        String NOTIFICATION_COUNT = "NOTIFICATION_COUNT";
        String FIREBASE_TOKEN = "FIREBASE_TOKEN";
        String MY_ID_TYPE = "MY_ID_TYPE";
        String WALLET_BALANCE = "WALLET_BALANCE";
        String FIRST_TIME_LAUNCH_INTRO = "FIRST_TIME_LAUNCH_INTRO";
        String METADATA = "METADATA";
        String MERCHANT_ID = "MERCHANT_ID";
        String ORDER_ID = "ORDER_ID";
        String SELECTED_PAYMENT_METHOD = "SELECTED_PAYMENT_METHOD";
        String IS_REDIRECTED_TO_WEB = "IS_REDIRECTED_TO_WEB";
    }

    public interface APIHeaders {
        String USER_ID = "Id";
        String DEVICE_TYPE = "device_type";
        String VERSION = "version";
        String LANGUAGE = "lang";
        String DEVICE_TOKEN = "device_token";
        String USER_TOKEN = "user_token";
    }

    public interface APIEndPoints {
        String HOME_LIST = "homeList/"+ BuildConfig.API_TOKEN;
        String SERIES_LIST = "seriesList/"+ BuildConfig.API_TOKEN;
        String SERIES_LIST_2 = "api/seriesList";
        String MATCHES_BY_SERIES_ID = "matchesBySeriesId/"+ BuildConfig.API_TOKEN;
        String UPCOMING_MATCHES = "upcomingMatches/"+ BuildConfig.API_TOKEN;
        String RECENT_MATCHES = "recentMatches/"+ BuildConfig.API_TOKEN;
        String SCORE_CARD_BY_MATCH_ID = "api/scorecardByMatchId";
        String MATCH_INFO = "api/matchInfo";
        String SQUAD_BY_MATCH_ID = "squadByMatchId/"+ BuildConfig.API_TOKEN;
        String MATCH_FANCY = "matchFancy/"+ BuildConfig.API_TOKEN;
        String LIVE_MATCH_LIST = "api/liveMatchList";
        String LIVE_MATCH = "api/liveMatch";
        String POINTS_TABLE = "pointsTable/"+ BuildConfig.API_TOKEN;
        String PLAYERS_BY_MATCH_ID = "playersByMatchId/"+ BuildConfig.API_TOKEN;
        String MATCH_ODD_HISTORY = "matchOddHistory/"+ BuildConfig.API_TOKEN;
        String MATCH_STATES = "matchStats/"+ BuildConfig.API_TOKEN;
        String NEWS = "api/news";
        String NEWS_DETAILS = "newsDetail/"+ BuildConfig.API_TOKEN;
        String COMMENTARY = "commentary/"+ BuildConfig.API_TOKEN;
        String PLAYER_RANKING = "playerRanking/"+ BuildConfig.API_TOKEN;
        String TEAM_RANKING = "teamRanking/"+ BuildConfig.API_TOKEN;
    }

    public interface APIKeys {
        String SERIES_ID = "series_id";
        String MATCH_ID = "match_id";
        String NEWS_ID = "news_id";
        String TYPE = "type";

    }

    public interface DateFormats {
        String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //2022-04-08T15:34:37.000000Z
        String PROFILE_DATE_FORMAT = "dd MMM yyyy"; //22 Jun 2022
        String PASSBOOK_DATE_FORMAT = "dd MMM yyyy HH:mm";//20 Aug 2022 17:34
        String NOTIFICATION_DATE_FORMAT = "dd MMM yyyy HH:mm";//20 Aug 2022 17:34
    }

    public interface RequestCodes {
        int RC_PERMISSION_CAMERA = 1001;
        int RC_PERMISSION_STORAGE = 1002;

        int RC_CAMERA = 1003;
        int RC_GALLERY = 1004;
    }

    public interface fragmentArgumentsKeys {
        String SERIES_ID = "SeriesID";
        String MATCH_ID = "MatchID";
        String UPCOMING_LIST = "Upcoming_List";
        String RECENT_LIST = "Recent_List";
        String SERIES_DATA = "Series_Data";
        String POINTS_TABLE_DATA = "PointsTable_Data";
        String PREVIOUS_SERIES_DATA = "Previous_Series_Data";
        String LAST_4OVERS_DATA = "Last_4_Overs_Data";
        String PLAYERS_TEAM_A_LIST = "Player_team_a_list";
        String PLAYERS_TEAM_B_LIST = "Player_team_b_list";
        String IS_LIVE = "Is_Live";
    }
}