package com.jbs.general.api;

import com.jbs.general.model.response.alarms.MainResponseGetAlarms;
import com.jbs.general.model.response.alarms.MainResponseSetAlarms;
import com.jbs.general.model.response.singup.MainResponseSignUp;
import com.jbs.general.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //Sign up
    @POST(Constants.APIEndPoints.SIGN_UP)
    @FormUrlEncoded
    Call<MainResponseSignUp> callSignup(@Field(Constants.APIKeys.USER_NAME) String userName,
                                        @Field(Constants.APIKeys.FULL_NAME) String fullName,
                                        @Field(Constants.APIKeys.EMAIL) String email,
                                        @Field(Constants.APIKeys.PASSWORD) String password,
                                        @Field(Constants.APIKeys.LOGIN_FORM) String loginForm);

    //Login
    @POST(Constants.APIEndPoints.LOGIN)
    @FormUrlEncoded
    Call<MainResponseSignUp> callLogin(@Field(Constants.APIKeys.EMAIL) String email,
                                       @Field(Constants.APIKeys.PASSWORD) String password,
                                       @Field(Constants.APIKeys.LOGIN_FORM) String loginForm);

    //Login
    @POST(Constants.APIEndPoints.UPDATE_PASSWORD)
    @FormUrlEncoded
    Call<MainResponseSignUp> updatePassword(@Field(Constants.APIKeys.EMAIL) String email,
                                       @Field(Constants.APIKeys.PASSWORD) String password);

    //Login
    @POST(Constants.APIEndPoints.CHECK_EMAIL)
    @FormUrlEncoded
    Call<MainResponseSignUp> checkEmailExist(@Field(Constants.APIKeys.EMAIL) String email);

    //Get Alarms
    @GET(Constants.APIEndPoints.GET_ALARMS)
    Call<MainResponseGetAlarms> getAlarms(@Query(Constants.APIKeys.USER_ID) String userId);

    //Sign up
    @POST(Constants.APIEndPoints.SET_ALARM)
    @FormUrlEncoded
    Call<MainResponseSetAlarms> setAlarm(@Field(Constants.APIKeys.USER_ID) String userId,
                                         @Field(Constants.APIKeys.ALARM_ID) int alarmId,
                                         @Field(Constants.APIKeys.TIME) String time,
                                         @Field(Constants.APIKeys.SOUND) int sound,
                                         @Field(Constants.APIKeys.URI) String uri,
                                         @Field(Constants.APIKeys.STATUS) int status,
                                         @Field(Constants.APIKeys.DATE) String date,
                                         @Field(Constants.APIKeys.SOUND_FREQUENCY) int soundFreq,
                                         @Field(Constants.APIKeys.SOUND_TIME_INTERVAL) int soundTime);

    //Custom Alarm
    @POST(Constants.APIEndPoints.CUSTOM_ALARM)
    @FormUrlEncoded
    Call<MainResponseSetAlarms> addCustomAlarm(@Field(Constants.APIKeys.USER_ID) String userId,
                                               @Field(Constants.APIKeys.ALARM_NAME) String name,
                                               @Field(Constants.APIKeys.TIME) String time,
                                               @Field(Constants.APIKeys.SOUND) int sound,
                                               @Field(Constants.APIKeys.URI) String uri,
                                               @Field(Constants.APIKeys.STATUS) int status,
                                               @Field(Constants.APIKeys.DATE) String date,
                                               @Field(Constants.APIKeys.SOUND_FREQUENCY) int soundFreq,
                                               @Field(Constants.APIKeys.SOUND_TIME_INTERVAL) int soundTime);
}