package com.jbs.general.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.jbs.general.General;
import com.jbs.general.network.NetworkUtils;
import com.jbs.general.utils.DateTimeUtils;
import com.jbs.general.utils.FileUtils;
import com.jbs.general.utils.FirebasePhoneAuthUtils;
import com.jbs.general.utils.GsonUtils;
import com.jbs.general.utils.ImagePickerUtils;
import com.jbs.general.utils.PermissionUtils;
import com.jbs.general.utils.PreferenceUtils;
import com.jbs.general.utils.ScalingUtils;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(General general);

    Bus provideBus();

    Context provideContext();

    DateTimeUtils provideDateTimeUtils();

    FileUtils provideFileUtils();

    FirebasePhoneAuthUtils provideFirebasePhoneAuthUtils();

    Gson provideGson();

    GsonUtils provideMasterGson();

    ImagePickerUtils provideImagePickerUtils();

    NetworkUtils provideNetworkUtils();

    PermissionUtils providePermissionUtils();

    PreferenceUtils providePreferenceUtils();

    ScalingUtils provideScalingUtils();
}