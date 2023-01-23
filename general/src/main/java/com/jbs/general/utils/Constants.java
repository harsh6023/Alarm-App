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
        String ACTION_EXISTING_ALARM = "ACTION_EXISTING_ALARM";
        String ACTION_NEW_ALARM = "ACTION_NEW_ALARM";
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
        String IS_SNOOZE_ON = "IS_SNOOZE_ON";
        String IS_SNOOZE_TIME = "IS_SNOOZE_TIME";
        String IS_SNOOZE_FREQ = "IS_SNOOZE_FREQ";
        String REPEAT_DAYS = "REPEAT_DAYS";
        String SELECTED_DAY = "SELECTED_DAY";
        String SELECTED_MONTH = "SELECTED_MONTH";
        String SELECTED_YEAR = "SELECTED_YEAR";
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
        String ALARM_ID = "alarm_id";
        String ALARM_DETAILS = "alarm_details";
        String USER_DATA = "UserData";
        String SUBSCRIBE= "Subscribe";
        String ACTIVE_ALARM_ID= "Active_Alarm_ID";
        String ACTIVE_WITHOUT_SUBSCRIBE = "Active_Without_Subscribe";
        String FIRST_TIME_LAUNCH_INTRO = "FIRST_TIME_LAUNCH_INTRO";
        String NOTIFICATION_COUNT = "NOTIFICATION_COUNT";
        String FIREBASE_TOKEN = "FIREBASE_TOKEN";
        String IS_FIRST_TIME = "IS_FIRST_TIME";
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
        String SIGN_UP = "signup.php";
        String LOGIN = "login.php";
        String GET_ALARMS = "get_alarms.php";
        String SET_ALARM = "set_alarm.php";
        String CUSTOM_ALARM = "custom_alarm.php";
        String CHECK_EMAIL = "check_email.php";
        String UPDATE_PASSWORD = "update_password.php";
        String CANCEL_SUBSCRIPTION = "cancel_subscription.php";
        String SUBSCRIBE = "subscribe.php";
        String DEACTIVATE_ALARM = "deactivate_alarm.php";
    }

    public interface APIKeys {
        String USER_NAME = "user_name";
        String FULL_NAME = "full_name";
        String EMAIL = "email";
        String USER_ID = "user_id";
        String ALARM_NAME = "name";
        String PASSWORD = "password";
        String LOGIN_FORM = "login_from";
        String ALARM_ID = "alarm_id";
        String TIME = "time";
        String SOUND = "sound";
        String URI = "uri";
        String STATUS = "status";
        String DAY = "day";
        String DATE = "date";
        String SOUND_FREQUENCY = "sound_frequency";
        String SOUND_TIME_INTERVAL = "sound_time_interval";
        String SUBSCRIPTION_CANCEL_DATE_TIME = "subscription_cancel_date_time";
        String SUBSCRIBED_DATE_TIME = "subscribed_date_time";

    }

    public interface DateFormats {
        String TIME_FORMAT = "HH:mm"; //01:05 AM/PM
        String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; //2022-04-08T15:34:37.000000Z
        String PROFILE_DATE_FORMAT = "dd MMM yyyy"; //22 Jun 2022
        String ALARM_DATE_FORMAT = "yyyy-MM-dd"; //22 Jun 2022
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