package com.jbs.general.api;

import com.jbs.general.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient myClient;
    private final Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(180, TimeUnit.SECONDS)
                        .readTimeout(180, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (myClient == null){
            myClient = new RetrofitClient();
        }
        return  myClient;
    }

    public ApiService getApi(){
        return retrofit.create(ApiService.class);
    }
}
