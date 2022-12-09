package com.app.kumase_getupdo;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences userPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    public SessionManager(Context _context, String sessionName) {
        mContext = _context;
        userPreferences = mContext.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        mEditor = userPreferences.edit();
    }

    public static final String PREF_NAME = "USER_PREF";
    public static final int MODE = Context.MODE_PRIVATE;

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static final String Selected_Data = "Selected_Data";
    public static void saveSelectedData(Context context, String key, String value){
        getEditor(context).putString(key, value).commit();
    }

    public static String readSelectedData(Context context, String key, String defValue){
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();

    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();
    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    /*Auto Login Session*/
    public static void saveAutoLogin(Context context, boolean status){
        SharedPreferences preferences = context.getSharedPreferences("AUTOLOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IsAutoLogin", status);
        editor.apply();
    }

    public static boolean getAutoLogin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("AUTOLOGIN", Context.MODE_PRIVATE);
        return preferences.getBoolean("IsAutoLogin", false);
    }

    public static void clearAutoLogin(Context context){
        SharedPreferences preferences = context.getSharedPreferences("AUTOLOGIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
