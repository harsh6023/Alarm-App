package com.jbs.general;

import android.app.Application;
import android.content.res.Configuration;

import com.jbs.general.dagger.AppComponent;
import com.jbs.general.dagger.AppModule;
import com.jbs.general.dagger.DaggerAppComponent;
import com.jbs.general.utils.Constants;
import com.jbs.general.utils.CrashReportingTree;

import timber.log.Timber;

public class General extends Application {

    //region #Variables
    private static General generalInstance;
    private AppComponent appComponent;
    //endregion

    //region #Getter Setter Methods
    public static General getInstance() {
        return generalInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
    //endregion

    //region #In Built Methods
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    @Override
    public void onCreate() {
        super.onCreate();
        generalInstance = this;

        //injecting dependencies
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        //region #Timber
        if (Constants.IS_DEBUG_ENABLE) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
        //endregion
    }


    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    //endregion
}
