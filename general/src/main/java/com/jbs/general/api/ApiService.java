package com.jbs.general.api;

import com.jbs.general.model.response.singup.MainResponseSignUp;
import com.jbs.general.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}