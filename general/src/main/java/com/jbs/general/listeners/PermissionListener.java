package com.jbs.general.listeners;

/**
 * @author Jaymin Soni
 * Email: jayminbsoni94@gmail.com
 * Github: https://github.com/jayminbsoni
 */
public interface PermissionListener {
    /**
     * Permission Granted
     *
     * @param requestCode - Request Code from {@link com.jbs.general.utils.Constants.RequestCodes}
     */
    void permissionGranted(int requestCode);

    /**
     * Permission Denied
     *
     * @param requestCode - Request Code from {@link com.jbs.general.utils.Constants.RequestCodes}
     */
    void permissionDenied(int requestCode);

    /**
     * Permission Don't ask again
     *
     * @param requestCode - Request Code from {@link com.jbs.general.utils.Constants.RequestCodes}
     */
    void permissionDontAskAgain(int requestCode);
}