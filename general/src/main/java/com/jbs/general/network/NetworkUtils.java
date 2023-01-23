package com.jbs.general.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkUtils {

    private final Context mContext;

    @Inject
    NetworkUtils(Context context) {
        //no direct instances allowed. use di instead.
        mContext = context;
    }

    //region public methods

    /**
     * Checks if device is connected on network
     */
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        return false;
    }
    //endregion
}
