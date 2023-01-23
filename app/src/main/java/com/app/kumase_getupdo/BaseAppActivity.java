package com.app.kumase_getupdo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.jbs.general.activity.BaseActivity;

public class BaseAppActivity<T> extends BaseActivity {
    private T activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //validateSubscription();
    }

    private void validateSubscription() {
        if (isNetworkAvailable()) {
            showLoader();

        } else {
            hideLoader();
            showSnackbarShort(getString(R.string.error_please_connect_to_internet));
        }
    }

}
