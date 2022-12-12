package com.jbs.general.api;

import com.jbs.general.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {

    private static RetrofitClient2 myClient;
    private final Retrofit retrofit;

    private RetrofitClient2(){
        retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL_2)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(180, TimeUnit.SECONDS)
                        .readTimeout(180, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .build();
    }

    public static synchronized RetrofitClient2 getInstance(){
        if (myClient == null){
            myClient = new RetrofitClient2();
        }
        return  myClient;
    }

    public ApiService getApi(){
        return retrofit.create(ApiService.class);
    }
}
