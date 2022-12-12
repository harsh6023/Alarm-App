package com.jbs.general.api;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jbs.general.BuildConfig;
import com.jbs.general.General;
import com.jbs.general.utils.Constants;
import com.jbs.general.utils.PreferenceUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        PreferenceUtils preferenceUtils = General.getInstance().getAppComponent().providePreferenceUtils();
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(400, TimeUnit.SECONDS)
                    .readTimeout(500, TimeUnit.SECONDS)
                    .writeTimeout(400, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    /*.addInterceptor(headerChain -> {
                        Request.Builder builder = headerChain.request().newBuilder();
                        long userId = preferenceUtils.getLong(Constants.PreferenceKeys.USER_ID);
                        Timber.e("User ID: " + userId);
                        String userToken = preferenceUtils.getString(Constants.PreferenceKeys.USER_TOKEN);
                        Timber.e("User Token: " + userToken);
                        String firebaseToken = preferenceUtils.getString(Constants.PreferenceKeys.FIREBASE_TOKEN);
                        Timber.e("Firebase Token: " + firebaseToken);

                        builder.header(Constants.APIHeaders.DEVICE_TYPE, "android");
                        builder.header(Constants.APIHeaders.VERSION, "1.0.0");
                        builder.header(Constants.APIHeaders.LANGUAGE, "en");
                        builder.header(Constants.APIHeaders.DEVICE_TOKEN, firebaseToken);
                        if (userId != 0) {
                            builder.header(Constants.APIHeaders.USER_ID, String.valueOf(userId));
                        }
                        if (!TextUtils.isEmpty(userToken)) {
                            builder.header(Constants.APIHeaders.USER_TOKEN, userToken);
                        }
                        return headerChain.proceed(builder.build());
                    })*/
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
