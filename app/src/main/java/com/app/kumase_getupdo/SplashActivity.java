package com.app.kumase_getupdo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.QueryPurchasesParams;
import com.app.kumase_getupdo.databinding.ActivitySplashBinding;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.utils.Constants;

import timber.log.Timber;

public class SplashActivity extends BaseActivity {

    private final Launcher mLauncher = new Launcher();
    //region #Variables
    private ActivitySplashBinding binding;
    private BillingClient billingClient;

    //endregion

    //region #InBuilt Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        checkSubscription();

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

    /**
     * Perform Navigation
     */
    private void performNavigation() {
        boolean isFirstTimeLaunch = preferenceUtils.getBoolean(Constants.PreferenceKeys.FIRST_TIME_LAUNCH_INTRO);

        if (!isFirstTimeLaunch) {
            startActivity(new Intent(SplashActivity.this, IntroductionActivity.class));
        } else {
            if (preferenceUtils.getAutoLogin()) {
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }
        finish();
    }

    void checkSubscription() {
        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener((billingResult, list) -> {
        }).build();
        final BillingClient finalBillingClient = billingClient;
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    finalBillingClient.queryPurchasesAsync(
                            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), (billingResult1, list) -> {

                                Log.e("BillingResult", new Gson().toJson(billingResult1) + " \n\n " + new Gson().toJson(list));
                                if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                    if (list.size() > 0) {
                                        if (list.get(0).isAutoRenewing()) {
                                            preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 1); // set 1 to activate premium feature
                                        } else {
                                            preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0); // set 0 to de-activate premium feature
                                        }
                                    } else {
                                        preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0); // set 0 to de-activate premium feature
                                    }
                                }
                            });
                }
            }
        });
    }

    //region #Launcher class
    private class Launcher implements Runnable {
        @Override
        public void run() {
            performNavigation();
        }
    }

}