package com.jbs.general.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.jbs.general.General;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.dagger.DaggerViewComponent;
import com.jbs.general.dagger.ViewComponent;
import com.jbs.general.dagger.ViewModule;
import com.jbs.general.listeners.ImeOptionActionListener;
import com.jbs.general.listeners.SnackbarActionListener;
import com.jbs.general.network.NetworkUtils;
import com.jbs.general.utils.Constants;
import com.jbs.general.utils.FileUtils;
import com.jbs.general.utils.GsonUtils;
import com.jbs.general.utils.ImagePickerUtils;
import com.jbs.general.utils.PermissionUtils;
import com.jbs.general.utils.PreferenceUtils;
import com.jbs.general.utils.ScalingUtils;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

public class BaseFragment extends Fragment {

    //region #Singleton Instance
    protected PreferenceUtils preferenceUtils = General.getInstance().getAppComponent().providePreferenceUtils();
    protected GsonUtils gsonUtils = General.getInstance().getAppComponent().provideMasterGson();
    protected NetworkUtils networkUtils = General.getInstance().getAppComponent().provideNetworkUtils();
    protected PermissionUtils permissionUtils = General.getInstance().getAppComponent().providePermissionUtils();
    protected ImagePickerUtils imagePickerUtils = General.getInstance().getAppComponent().provideImagePickerUtils();
    protected FileUtils fileUtils = General.getInstance().getAppComponent().provideFileUtils();
    protected ScalingUtils scalingUtils = General.getInstance().getAppComponent().provideScalingUtils();
    //endregion

    //region #Variables
    @Inject
    protected Bus mBus;
    @Inject
    protected Handler mSafeHandler;
    private ViewComponent mViewComponent;
    //endregion

    //region #InBuilt Methods
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewComponent = DaggerViewComponent
                .builder()
                .appComponent(General.getInstance().getAppComponent())
                .viewModule(new ViewModule())
                .build();
        mViewComponent.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBus.register(this);
    }

    @Override
    public void onStop() {
        mBus.unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mSafeHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    /**
     * Load Json From Asset
     *
     * @param context  - context of the page
     * @param fileName - File Name
     * @return - Json String
     */
    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //endregion

    //region #Custom Methods

    /**
     * Show Short Toast
     *
     * @param message - message
     */
    public void showToastShort(String message) {
        ((BaseActivity) requireActivity()).showToastShort(message);
    }

    /**
     * Show Short Snackbar
     *
     * @param message - message
     */
    public void showSnackbarShort(String message) {
        ((BaseActivity) requireActivity()).showSnackbarShort(message);
    }

    /**
     * Show Snackbar with Action
     *
     * @param message                - message
     * @param action                 - action text
     * @param snackbarActionListener - snackbar action callback
     */
    public void showSnackBarAction(String message, String action, SnackbarActionListener snackbarActionListener) {
        ((BaseActivity) requireActivity()).showSnackBarAction(message, action, snackbarActionListener);
    }

    /**
     * Check Internet Connectivity
     *
     * @return {@code true}Internet Connected<br>{@code false}Internet not connected
     */
    public boolean isNetworkAvailable() {
        return ((BaseActivity) requireActivity()).isNetworkAvailable();
    }

    /**
     * Show Keyboard
     *
     * @param target - EditText View Reference
     */
    public void showKeyboard(EditText target) {
        ((BaseActivity) requireActivity()).showKeyboard(target);
    }

    /**
     * Hide Keyboard
     *
     * @param target - View
     */
    public void hideKeyboard(View target) {
        ((BaseActivity) requireActivity()).hideKeyboard(target);
    }

    /**
     * Manage EditText IME Option Actions
     *
     * @param editText                - EditText reference
     * @param action                  - IME Option Action
     * @param imeOptionActionListener - IME Option Action callback
     */
    public void manageEditTextImeOption(AppCompatEditText editText, int action, ImeOptionActionListener imeOptionActionListener) {
        ((BaseActivity) requireActivity()).manageEditTextImeOption(editText, action, imeOptionActionListener);
    }

    /**
     * it will return true if consecutive click occurs within {@link Constants.Delays##MIN_TIME_BETWEEN_CLICKS}
     *
     * @return true indicating do not allow any click, false otherwise
     */
    public boolean isClickDisabled() {
        return ((BaseActivity) requireActivity()).isClickDisabled();
    }

    /**
     * Enable/Disable View
     *
     * @param isEnable {@code true}Enable<br>{@code false}Disable
     * @param view     - View
     */
    public void enableDisableView(boolean isEnable, View view) {
        ((BaseActivity) requireActivity()).enableDisableView(isEnable, view);
    }

    /**
     * Open Device Settings Page
     */
    protected void openDeviceSettingsPage() {
        ((BaseActivity) requireActivity()).openDeviceSettingsPage();
    }

    /**
     * Set Color Tint to Drawables
     *
     * @param drawable - Drawable
     * @param color    - Color
     */
    protected void setColorFilter(@NonNull Drawable drawable, @ColorInt int color) {
        ((BaseActivity) requireActivity()).setColorFilter(drawable, color);
    }

    /**
     * Copy to Clipboard
     *
     * @param text - text
     */
    protected void copyToClipboard(String text) {
        ((BaseActivity) requireActivity()).copyToClipboard(text);
    }

    /**
     * Share Text
     *
     * @param text - Text
     */
    protected void shareText(String text) {
        ((BaseActivity) requireActivity()).shareText(text);
    }

    /**
     * Show Loader
     */
    protected void showLoader() {
        ((BaseActivity) requireActivity()).showLoader();
    }

    /**
     * Hide Loader
     */
    protected void hideLoader() {
        ((BaseActivity) requireActivity()).hideLoader();
    }

    /**
     * Perform Logout
     *
     * @param packageContext - From
     * @param cls            - To
     */
    public void performLogout(Context packageContext, Class<?> cls) {
        preferenceUtils.clearAll();

        Intent intent = new Intent(packageContext, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    /**
     * Set Notification Count
     *
     * @param tvNotificationCount - Notification Count Badge View
     */
    protected void setNotificationCount(AppCompatTextView tvNotificationCount) {
        ((BaseActivity) requireActivity()).setNotificationCount(tvNotificationCount);
    }

    /**
     * Manage Show/Hide Password
     *
     * @param editText - EditText
     */
    public void managePasswordEditText(AppCompatEditText editText) {
        ((BaseActivity) requireActivity()).managePasswordEditText(editText);
    }
    //endregion

    /**
     * Set View Enable and Disable
     *
     * @param view     - View
     * @param isEnable - {@code true}Enable <br> {@code false}Disable
     */
    public void setEnableDisable(View view, boolean isEnable) {
        ((BaseActivity) requireActivity()).setEnableDisable(view, isEnable);
    }

    //endregion
}