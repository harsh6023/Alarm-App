package com.jbs.general.utils;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GsonUtils {

    private final Gson mGson;

    @Inject
    GsonUtils(Gson gson) {
        this.mGson = gson;
    }

    public JSONObject createJsonObjectFromPOJO(Object object) throws JSONException {
        return new JSONObject(mGson.toJson(object));
    }

    public JSONArray createJsonArrayFromList(List listOfObjects) throws JSONException {
        return new JSONArray(mGson.toJson(listOfObjects));
    }


   /* public JSONArray createJsonArrayFromString(String listOfObjects) throws JSONException {
        return new JSONArray(mGson.toJson(listOfObjects));
    }*/
    //You must type cast to your class from object

    /**
     * add json object  as param also add pojo.class as param so will clare object with that class
     * you must type cast object to your object
     */
    public Object createPOJOFromJsonObject(JSONObject jsonObject, Class pojoClass) {
        return mGson.fromJson(jsonObject.toString(), pojoClass);
    }

    /**
     * add json object  as param also add pojo.class as param so will clare object with that class
     * you must type cast object to your object
     */
    public Object createPOJOFromString(String jsonString, Class pojoClass) {
        return mGson.fromJson(jsonString, pojoClass);
    }

    public String toJsonString(Object object) {
        return mGson.toJson(object);
    }

    public String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
