package com.app.kumase_getupdo;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.app.kumase_getupdo.databinding.ActivitySplashBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.utils.Constants;

import timber.log.Timber;

public class SplashActivity extends BaseActivity {

    //region #Variables
    private ActivitySplashBinding binding;
    private final Launcher mLauncher = new Launcher();

    //endregion

    //region #InBuilt Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);

        //initialize views and variables
        initialization();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSafeHandler.postDelayed(mLauncher, Constants.Delays.SPLASH_INTERVAL);
    }

    @Override
    protected void onStop() {
        mSafeHandler.removeCallbacks(mLauncher);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    //endregion

    /**
     * initialize views and variables
     */
    private void initialization() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Timber.e("Fetching FCM registration token failed: " + task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    preferenceUtils.setString(Constants.PreferenceKeys.FIREBASE_TOKEN, token);
                });
    }

    //region #Launcher class
    private class Launcher implements Runnable {
        @Override
        public void run() {
            performNavigation();
        }
    }

    /**
     * Perform Navigation
     */
    private void performNavigation() {
        boolean isFirstTimeLaunch = preferenceUtils.getBoolean(Constants.PreferenceKeys.FIRST_TIME_LAUNCH_INTRO);

        if (!isFirstTimeLaunch){
            startActivity(new Intent(SplashActivity.this, IntroductionActivity.class));
        }else {
            if (preferenceUtils.getAutoLogin()){
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }
        finish();
    }

}