package com.jbs.general.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jbs.general.listeners.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Jaymin Soni
 * Email: jayminbsoni94@gmail.com
 * Github: https://github.com/jayminbsoni
 * Link: https://developer.android.com/guide/topics/permissions/overview
 */
@Singleton
public class PermissionUtils {

    private final Context context;

    private PermissionListener permissionListener;
    private int selectedRequestCode = 0;

    @Inject
    PermissionUtils(Context context) {
        this.context = context;
    }

    /**
     * Check Permissions
     *
     * @param activity           - Activity
     * @param permissions        - Permission List
     * @param requestCode        - Request Code
     * @param permissionListener - Permission Listener
     */
    public void checkPermissions(Activity activity, String[] permissions, int requestCode, @NonNull PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
        this.selectedRequestCode = requestCode;
        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        // start request
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        } else {
            permissionListener.permissionGranted(selectedRequestCode);
        }
    }

    /**
     * Check Permission is Granted or Not
     *
     * @param activity     - Activity
     * @param requestCode  - Request Code
     * @param permissions  - Permission List
     * @param grantResults - Grant Result of Permissions
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasAllGranted = true;

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    permissionListener.permissionDontAskAgain(selectedRequestCode);
                } else {
                    permissionListener.permissionDenied(selectedRequestCode);
                }
                break;
            }
        }

        if (hasAllGranted) {
            permissionListener.permissionGranted(selectedRequestCode);
        }
    }

    /**
     * Check Permissions are granted or not
     *
     * @param activity    - Activity
     * @param permissions - Permission List
     * @return - {@code true}: Permission Granted <br>{@code} Permission Not Granted
     */
    private boolean arePermissionsEnabled(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * Check Camera Permissions is granted or not
     *
     * @param activity - Activity
     * @return - {@code true}: Permission Granted <br>{@code} Permission Not Granted
     */
    public boolean isCameraPermissionEnabled(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        return arePermissionsEnabled(activity, permissions);
    }

    /**
     * Ask for Camera Permission
     *
     * @param activity           - Activity
     * @param requestCode        - Request Code
     * @param permissionListener - Permission Listener
     */
    public void askCameraPermission(Activity activity, int requestCode, @NonNull PermissionListener permissionListener) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        checkPermissions(activity, permissions, requestCode, permissionListener);
    }

    /**
     * Check Storage Permissions is granted or not
     *
     * @param activity - Activity
     * @return - {@code true}: Permission Granted <br>{@code} Permission Not Granted
     */
    public boolean isStoragePermissionEnabled(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        return arePermissionsEnabled(activity, permissions);
    }

    /**
     * Ask for Storage Permission
     *
     * @param activity           - Activity
     * @param requestCode        - Request Code
     * @param permissionListener - Permission Listener
     */
    public void askStoragePermission(Activity activity, int requestCode, @NonNull PermissionListener permissionListener) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        checkPermissions(activity, permissions, requestCode, permissionListener);
    }

    /**
     * Check Location Permissions is granted or not
     *
     * @param activity - Activity
     * @return - {@code true}: Permission Granted <br>{@code} Permission Not Granted
     */
    public boolean isLocationPermissionEnabled(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        return arePermissionsEnabled(activity, permissions);
    }

    /**
     * Ask for Location Permission
     *
     * @param activity           - Activity
     * @param requestCode        - Request Code
     * @param permissionListener - Permission Listener
     */
    public void askLocationPermission(Activity activity, int requestCode, @NonNull PermissionListener permissionListener) {
        String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        checkPermissions(activity, permissions, requestCode, permissionListener);
    }
}