package com.jbs.general.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jbs.general.General;
import com.jbs.general.R;
import com.jbs.general.annotation.FileMimeType;
import com.jbs.general.dagger.DaggerViewComponent;
import com.jbs.general.dagger.ViewComponent;
import com.jbs.general.dagger.ViewModule;
import com.jbs.general.listeners.ImeOptionActionListener;
import com.jbs.general.listeners.SnackbarActionListener;
import com.jbs.general.network.NetworkChangeReceiver;
import com.jbs.general.network.NetworkUtils;
import com.jbs.general.utils.Constants;
import com.jbs.general.utils.DateTimeUtils;
import com.jbs.general.utils.FirebasePhoneAuthUtils;
import com.jbs.general.utils.GsonUtils;
import com.jbs.general.utils.ImagePickerUtils;
import com.jbs.general.utils.PermissionUtils;
import com.jbs.general.utils.PreferenceUtils;
import com.jbs.general.widget.GeneralProgressDialog;
import com.squareup.otto.Bus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    //region #Singleton Instance
    protected PreferenceUtils preferenceUtils = General.getInstance().getAppComponent().providePreferenceUtils();
    protected GsonUtils gsonUtils = General.getInstance().getAppComponent().provideMasterGson();
    protected NetworkUtils networkUtils = General.getInstance().getAppComponent().provideNetworkUtils();
    protected DateTimeUtils dateUtils = General.getInstance().getAppComponent().provideDateTimeUtils();
    protected FirebasePhoneAuthUtils firebasePhoneAuthUtils = General.getInstance().getAppComponent().provideFirebasePhoneAuthUtils();
    protected ImagePickerUtils imagePickerUtils = General.getInstance().getAppComponent().provideImagePickerUtils();
    protected PermissionUtils permissionUtils = General.getInstance().getAppComponent().providePermissionUtils();
    //endregion

    //region #Variables
    @Inject
    protected Bus mBus;
    @Inject
    protected NetworkChangeReceiver mNetworkChangeReceiver;
    @Inject
    protected InputMethodManager mInputMethodManager;
    @Inject
    protected Handler mSafeHandler;

    private Dialog mProgressDialog;

    private long lastClickTime;
    //endregion

    //region #InBuilt Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new GeneralProgressDialog(this);

        ViewComponent viewComponent = DaggerViewComponent
                .builder()
                .appComponent(General.getInstance().getAppComponent())
                .viewModule(new ViewModule())
                .build();
        viewComponent.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBus.register(this);
        IntentFilter connectivityChangeFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkChangeReceiver, connectivityChangeFilter);
    }

    @Override
    protected void onStop() {
        mBus.unregister(this);
        unregisterReceiver(mNetworkChangeReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mSafeHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    //endregion

    //region #Custom Methods

    /**
     * Show Short Toast
     *
     * @param message - message
     */
    public void showToastShort(String message) {
        if (TextUtils.isEmpty(message.trim())) return;
        Toast.makeText(this, message.trim(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Show Short Snackbar
     *
     * @param message - message
     */
    public void showSnackbarShort(String message) {
        if (TextUtils.isEmpty(message.trim())) return;
        Snackbar.make(findViewById(android.R.id.content), message.trim(), BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    /**
     * Show Snackbar with Action
     *
     * @param message                - message
     * @param action                 - action text
     * @param snackbarActionListener - snackbar action callback
     */
    public void showSnackBarAction(String message, String action, SnackbarActionListener snackbarActionListener) {
        if (TextUtils.isEmpty(message.trim())) return;
        if (TextUtils.isEmpty(action.trim())) return;
        Snackbar.make(findViewById(android.R.id.content), message.trim(), BaseTransientBottomBar.LENGTH_SHORT)
                .setAction(action.trim(), view -> snackbarActionListener.onSnackbarActionClick())
                .show();
    }

    /**
     * Check Internet Connectivity
     *
     * @return {@code true}Internet Connected<br>{@code false}Internet not connected
     */
    public boolean isNetworkAvailable() {
        return networkUtils.isConnected();
    }

    /**
     * Show Keyboard
     *
     * @param target - EditText View Reference
     */
    public void showKeyboard(EditText target) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide Keyboard
     *
     * @param target - View
     */
    public void hideKeyboard(View target) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(target.getWindowToken(), 0);
    }

    /**
     * Manage EditText IME Option Actions
     *
     * @param editText                - EditText reference
     * @param action                  - IME Option Action
     * @param imeOptionActionListener - IME Option Action callback
     */
    public void manageEditTextImeOption(AppCompatEditText editText, int action, ImeOptionActionListener imeOptionActionListener) {
        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == action) {
                imeOptionActionListener.onActionPerform();
            }
            return false;
        });
    }

    /**
     * it will return true if consecutive click occurs within {@link Constants.Delays##MIN_TIME_BETWEEN_CLICKS}
     *
     * @return true indicating do not allow any click, false otherwise
     */
    public boolean isClickDisabled() {
        if ((SystemClock.elapsedRealtime() - lastClickTime) < Constants.Delays.MIN_TIME_BETWEEN_CLICKS) {
            return true;
        } else {
            lastClickTime = SystemClock.elapsedRealtime();
            return false;
        }
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

    /**
     * Enable/Disable View
     *
     * @param isEnable {@code true}Enable<br>{@code false}Disable
     * @param view     - View
     */
    public void enableDisableView(boolean isEnable, @NonNull View view) {
        view.setEnabled(isEnable);
        view.setAlpha(isEnable ? 1 : 0.5f);
    }

    /**
     * Open Device Settings Page
     */
    public void openDeviceSettingsPage() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    /**
     * Set Color Tint to Drawables
     *
     * @param drawable - Drawable
     * @param color    - Color
     */
    public void setColorFilter(@NonNull Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * Copy to Clipboard
     *
     * @param text - text
     */
    public void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copy text", text);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Share Text
     *
     * @param text - Text
     */
    public void shareText(String text) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType(FileMimeType.TEXT_HTML);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Share Text on whatsapp
     *
     * @param text - Text
     */
    public void shareTextWhatsapp(String text) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType(FileMimeType.TEXT_HTML);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            try {
                startActivity(sendIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Share Text on Telegram
     *
     * @param text - Text
     */
    public void shareTextTelegram(String text) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType(FileMimeType.TEXT_HTML);
            sendIntent.setPackage("org.telegram.messenger");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            try {
                startActivity(sendIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=org.telegram.messenger")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Share Text on Instagram
     *
     * @param text - Text
     */
    public void shareTextInstagram(String text) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType(FileMimeType.TEXT_HTML);
            sendIntent.setPackage("com.instagram.android");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            try {
                startActivity(sendIntent);
            } catch (ActivityNotFoundException ex) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.instagram.android")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show Loader
     */
    public void showLoader() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * Hide Loader
     */
    public void hideLoader() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Perform Logout
     *
     * @param packageContext - From
     * @param cls            - To
     */
    public void performLogout(Context packageContext, Class<?> cls) {
        boolean isFirstTimeLaunch = preferenceUtils.getBoolean(Constants.PreferenceKeys.FIRST_TIME_LAUNCH_INTRO);
        preferenceUtils.clearAll();
        preferenceUtils.setBoolean(Constants.PreferenceKeys.FIRST_TIME_LAUNCH_INTRO, isFirstTimeLaunch);

        Intent intent = new Intent(packageContext, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Set Notification Count
     *
     * @param tvNotificationCount - Notification Count Badge View
     */
    public void setNotificationCount(AppCompatTextView tvNotificationCount) {
        //Set Notification Count
        long notificationCount = preferenceUtils.getLong(Constants.PreferenceKeys.NOTIFICATION_COUNT);
        if (notificationCount == 0) {
            tvNotificationCount.setVisibility(View.GONE);
        } else {
            tvNotificationCount.setVisibility(View.VISIBLE);
            if (notificationCount < 100) {
                tvNotificationCount.setText(String.valueOf(notificationCount));
            } else {
                tvNotificationCount.setText("99+");
            }
        }
    }

    /**
     * Manage Show/Hide Password
     *
     * @param editText - EditText
     */
    public void managePasswordEditText(AppCompatEditText editText) {
        Drawable hidePasswordDrawable = ContextCompat.getDrawable(this, R.drawable.ic_hide_password);
        Drawable showPasswordDrawable = ContextCompat.getDrawable(this, R.drawable.ic_show_password);

        editText.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, showPasswordDrawable, null);
                    } else {
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, hidePasswordDrawable, null);
                    }
                    editText.setSelection(editText.getText().toString().length());
                    return true;
                }
            }
            return false;
        });
    }

    /**
     * Set View Enable and Disable
     *
     * @param view     - View
     * @param isEnable - {@code true}Enable <br> {@code false}Disable
     */
    public void setEnableDisable(View view, boolean isEnable) {
        view.setAlpha(isEnable ? 1 : 0.5f);
        view.setEnabled(isEnable);
    }
    //endregion
}