package com.app.kumase_getupdo;

import static android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_DETAILS;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_ID;

import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_DAY;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_HOUR;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_ID;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_MINUTE;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_MONTH;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_TONE_URI;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_TYPE;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_VOLUME;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_ALARM_YEAR;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_IS_REPEAT_ON;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_IS_SNOOZE_ON;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_REPEAT_DAYS;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_SNOOZE_FREQUENCY;
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_SNOOZE_TIME_IN_MINS;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.app.kumase_getupdo.adapter.SubscriptionAdapter;
import com.app.kumase_getupdo.alarm.AlarmBroadcastReceiver;
import com.app.kumase_getupdo.alarm.ConstantsAndStatics;
import com.app.kumase_getupdo.databinding.ActivitySetAlarmTimeBinding;
import com.app.kumase_getupdo.databinding.ActivitySubscriptionBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.google.android.material.button.MaterialButton;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.api.RetrofitClient;
import com.jbs.general.model.response.alarms.AlarmsApiData;
import com.jbs.general.model.response.alarms.MainResponseSetAlarms;
import com.jbs.general.utils.Constants;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SubscriptionActivity extends BaseActivity implements RecycleViewInterface{

    private ActivitySubscriptionBinding binding;
    private ViewModelSetAlarm viewModelSetAlarm;
    private BillingClient billingClient;
    private Handler handler;
    private List<ProductDetails> productDetailsList;
    private SubscriptionAdapter adapter;
    private ActivityResultLauncher<Intent> settingsActLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subscription);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);
        productDetailsList = new ArrayList<>();
        handler = new Handler();

        settingsActLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> viewModelSetAlarm.setIsSettingsActOver(true));

        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(
                        (billingResult, list) -> {
                            Log.e("Billing", new Gson().toJson(billingResult) + " **\n" + new Gson().toJson(list));
                            if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK && list !=null) {
                                for (Purchase purchase: list){
                                    verifySubPurchase(purchase);
                                }
                            }
                        }
                ).build();

        //start the connection after initializing the billing client
        establishConnection();

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (Objects.equals(getIntent().getAction(), Constants.AppConstant.ACTION_EXISTING_ALARM)) {
                      ArrayList<Integer> repeatDays = new ArrayList<>();
                    repeatDays.add(viewModelSetAlarm.getRepeatDays());
                    if (repeatDays != null) {
                        Collections.sort(repeatDays);
                    }

                    Bundle data = Objects.requireNonNull(getIntent().getExtras()).getBundle(BUNDLE_KEY_ALARM_DETAILS);

                    if (isNetworkAvailable()) {
                        showLoader();
                        RetrofitClient.getInstance().getApi().setAlarm(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), data.getInt(BUNDLE_KEY_ALARM_ID), data.getInt(BUNDLE_KEY_ALARM_HOUR) + ":" + data.getInt(BUNDLE_KEY_ALARM_MINUTE) + ":00", (data.getInt(BUNDLE_KEY_SNOOZE_TIME_IN_MINS)/1000) + " Sec", data.getParcelable(BUNDLE_KEY_ALARM_TONE_URI).toString(),  1, getIntent().getStringExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DATE))
                                .enqueue(new Callback<MainResponseSetAlarms>() {
                                    @Override
                                    public void onResponse(@NonNull Call<MainResponseSetAlarms> call, @NonNull Response<MainResponseSetAlarms> response) {
                                        hideLoader();
                                        if (response.body() == null) {
                                            showSnackbarShort(getString(R.string.error_something_went_wrong));
                                            return;
                                        }

                                        MainResponseSetAlarms mainResponseSetAlarms = response.body();
                                        Log.e("mainResponseGetAlarms", new Gson().toJson(mainResponseSetAlarms) + " **");
                                        if (mainResponseSetAlarms.isSuccess()) {
                                            addOrActivateAlarm(mainResponseSetAlarms.getData().getAlarm());
                                            showToastShort("Alarm saved successfully!");
                                            final Dialog dialog = new Dialog(SubscriptionActivity.this);
                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.dialog_all_set);

                                            MaterialButton btnOk = dialog.findViewById(R.id.btnOk);

                                            btnOk.setOnClickListener(v -> {
                                                dialog.dismiss();
                                                startActivity(new Intent(SubscriptionActivity.this, DashboardActivity.class));
                                                finish();
                                            });

                                            dialog.show();
                                        } else {
                                            showSnackbarShort(getString(R.string.error_something_went_wrong) + "\n" + mainResponseSetAlarms.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<MainResponseSetAlarms> call, @NonNull Throwable t) {
                                        hideLoader();
                                        Timber.tag("onFailure?callSignup").e(t);
                                    }
                                });
                    } else {
                        hideLoader();
                        showSnackbarShort(getString(R.string.error_please_connect_to_internet));
                    }
                }else if (Objects.equals(getIntent().getAction(), Constants.AppConstant.ACTION_NEW_ALARM)) {
                    Bundle data = Objects.requireNonNull(getIntent().getExtras()).getBundle(BUNDLE_KEY_ALARM_DETAILS);
                    callAddCustomAlarmApi(data);
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addOrActivateAlarm(AlarmsApiData alarmsApiData) {
        ConstantsAndStatics.cancelScheduledPeriodicWork(this);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormats.TIME_FORMAT, Locale.UK);
        SimpleDateFormat sdfDate = new SimpleDateFormat(Constants.DateFormats.ALARM_DATE_FORMAT, Locale.UK);
        Date date = null;
        Date dateD = null;
        try {
            if (alarmsApiData.getTime() != null) {
                date = sdf.parse(alarmsApiData.getTime());
            } else {
                date = sdf.parse(String.valueOf(LocalDateTime.now().plusHours(1)));
            }
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage() + " ** " + LocalDateTime.now().plusHours(1));
        }

        try {
            if (alarmsApiData.getDate() != null || !TextUtils.isEmpty(alarmsApiData.getDate())) {
                dateD = sdfDate.parse(alarmsApiData.getDate());
            } else {
                dateD = sdfDate.parse(String.valueOf(LocalDateTime.now().plusHours(1)));
            }
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage() + " ** " + LocalDateTime.now().plusHours(1));
        }
        Calendar c = Calendar.getInstance();
        Calendar cDate = Calendar.getInstance();
        c.setTime(date);
        cDate.setTime(dateD);

        ArrayList<Integer> repeatDays = new ArrayList<>();
        repeatDays.add(alarmsApiData.getDay());
        if (repeatDays != null) {
            Collections.sort(repeatDays);
        }

        LocalDateTime alarmDateTime = ConstantsAndStatics.getAlarmDateTime(LocalDate.of(cDate.get(Calendar.YEAR),
                (cDate.get(Calendar.MONTH) + 1), cDate.get(Calendar.DAY_OF_MONTH)), LocalTime.of(c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE)), viewModelSetAlarm.getIsRepeatOn(), repeatDays);
        Log.e("Dateeee", alarmDateTime + " ** ");

        Bundle data = new Bundle();
        data.putInt(BUNDLE_KEY_ALARM_HOUR, alarmDateTime.getHour());
        data.putInt(BUNDLE_KEY_ALARM_MINUTE, alarmDateTime.getMinute());
        data.putInt(BUNDLE_KEY_ALARM_DAY, alarmDateTime.getDayOfMonth());
        data.putInt(BUNDLE_KEY_ALARM_MONTH, alarmDateTime.getMonthValue());
        data.putInt(BUNDLE_KEY_ALARM_YEAR, alarmDateTime.getYear());
        data.putInt(BUNDLE_KEY_ALARM_TYPE, viewModelSetAlarm.getAlarmType());
        data.putBoolean(BUNDLE_KEY_IS_SNOOZE_ON, viewModelSetAlarm.getIsSnoozeOn());
        data.putBoolean(BUNDLE_KEY_IS_REPEAT_ON, viewModelSetAlarm.getIsRepeatOn());
        data.putInt(BUNDLE_KEY_ALARM_VOLUME, viewModelSetAlarm.getAlarmVolume());
        data.putInt(BUNDLE_KEY_SNOOZE_TIME_IN_MINS, viewModelSetAlarm.getSnoozeIntervalInSecs());
        data.putInt(BUNDLE_KEY_SNOOZE_FREQUENCY, viewModelSetAlarm.getSnoozeFreq());
        data.putIntegerArrayList(BUNDLE_KEY_REPEAT_DAYS, repeatDays);
        data.putParcelable(BUNDLE_KEY_ALARM_TONE_URI, viewModelSetAlarm.getAlarmToneUri());
        data.putString(ConstantsAndStatics.BUNDLE_KEY_ALARM_MESSAGE, alarmsApiData.getName());
        data.putIntegerArrayList(ConstantsAndStatics.BUNDLE_KEY_REPEAT_DAYS, repeatDays);
        data.putSerializable(ConstantsAndStatics.BUNDLE_KEY_DATE_TIME, alarmDateTime);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                viewModelSetAlarm.setPendingStatus(true);
                viewModelSetAlarm.savePendingAlarm(data);
                requestExactAlarmPerm();
                return;
            }
        }

        setAlarm(data);
        if (preferenceUtils.getInteger(Constants.PreferenceKeys.SUBSCRIBE) == 0) {
            preferenceUtils.setInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID, alarmsApiData.getId());
        }
        ConstantsAndStatics.schedulePeriodicWork(this);
    }

    /**
     * Sets the alarm in the Android system.
     *
     * @param data The details of the alarm to be set.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setAlarm(@NonNull Bundle data) {

        LocalDateTime alarmDateTime = (LocalDateTime) data.getSerializable(ConstantsAndStatics.BUNDLE_KEY_DATE_TIME);
        data.remove(ConstantsAndStatics.BUNDLE_KEY_DATE_TIME);

        /*int index = viewModel.toggleAlarmState(alarmDatabase, Objects.requireNonNull(alarmDateTime).getHour(),
                alarmDateTime.getMinute(), 1);
        alarmAdapter.notifyItemChanged(index);*/

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
        intent.setAction(ConstantsAndStatics.ACTION_DELIVER_ALARM);
        intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DETAILS, data);

        int alarmID = data.getInt(ConstantsAndStatics.BUNDLE_KEY_ALARM_ID);

        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmID, intent, flags);

        ZonedDateTime zonedDateTime = ZonedDateTime.of(alarmDateTime.withSecond(0), ZoneId.systemDefault());

        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(zonedDateTime.toEpochSecond() * 1000, pendingIntent), pendingIntent);

        showSnackbarLong(getString(R.string.toast_alarmSwitchedOn,
                getDuration(Duration.between(ZonedDateTime.now(ZoneId.systemDefault()).withSecond(0), zonedDateTime))));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    private String getDuration(@NonNull Duration duration) {

        NumberFormat numFormat = NumberFormat.getInstance();
        numFormat.setGroupingUsed(false);

        long days = duration.toDays();
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();

        String msg;

        if (days == 0) {
            if (hours == 0) {
                msg = numFormat.format(minutes) + getResources().getQuantityString(R.plurals.mins, (int) minutes);
            } else {
                msg = numFormat.format(hours) + getResources().getQuantityString(R.plurals.hour, (int) hours)
                        + getString(R.string.and)
                        + numFormat.format(minutes) + getResources().getQuantityString(R.plurals.mins, (int) minutes);
            }
        } else {
            msg = numFormat.format(days) + getResources().getQuantityString(R.plurals.day, (int) days) + ", "
                    + numFormat.format(hours) + getResources().getQuantityString(R.plurals.hour, (int) hours)
                    + getString(R.string.and)
                    + numFormat.format(minutes) + " " + getResources().getQuantityString(R.plurals.mins, (int) minutes);
        }
        return msg;
    }

    /**
     * Requests the {@link android.Manifest.permission#SCHEDULE_EXACT_ALARM} permission by directing the user to go to Settings.
     */
    @RequiresApi(api = Build.VERSION_CODES.S)
    private void requestExactAlarmPerm() {

        Intent intent = new Intent();
        intent.setAction(ACTION_REQUEST_SCHEDULE_EXACT_ALARM);

        new AlertDialog.Builder(this)
                .setMessage(R.string.request_exact_alarm_perm)
                .setCancelable(false)
                .setPositiveButton("Go to Settings", (dialog, which) -> {
                    viewModelSetAlarm.setIsSettingsActOver(false);
                    settingsActLauncher.launch(intent);
                })
                .show();
    }


    void establishConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.e("BillingResult", billingResult.getResponseCode() + "\n **");
                    showProducts();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.e("onBillingServiceDisconnected", "hereeee...!");
                establishConnection();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void callAddCustomAlarmApi(Bundle data) {
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().addCustomAlarm(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), getIntent().getStringExtra("Alarm_title"), data.getInt(BUNDLE_KEY_ALARM_HOUR) + ":" + data.getInt(BUNDLE_KEY_ALARM_MINUTE) + ":00", (data.getInt(BUNDLE_KEY_SNOOZE_TIME_IN_MINS)/1000) + " Sec", data.getParcelable(BUNDLE_KEY_ALARM_TONE_URI).toString(),  1, getIntent().getStringExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DATE))
                    .enqueue(new Callback<MainResponseSetAlarms>() {
                        @Override
                        public void onResponse(@NonNull Call<MainResponseSetAlarms> call, @NonNull Response<MainResponseSetAlarms> response) {
                            hideLoader();
                            if (response.body() == null) {
                                showSnackbarShort(getString(R.string.error_something_went_wrong));
                                return;
                            }

                            MainResponseSetAlarms mainResponseSetAlarms = response.body();
                            Log.e("mainResponseGetAlarms", new Gson().toJson(mainResponseSetAlarms) + " **");
                            if (mainResponseSetAlarms.isSuccess()) {
                                showToastShort("Alarm saved successfully!");
                                final Dialog dialog = new Dialog(SubscriptionActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_all_set);

                                MaterialButton btnOk = dialog.findViewById(R.id.btnOk);

                                btnOk.setOnClickListener(v -> {
                                    dialog.dismiss();
                                    startActivity(new Intent(SubscriptionActivity.this, DashboardActivity.class));
                                    finish();
                                });

                                dialog.show();
                            } else {
                                showSnackbarShort(getString(R.string.error_something_went_wrong) + "\n" + mainResponseSetAlarms.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MainResponseSetAlarms> call, @NonNull Throwable t) {
                            hideLoader();
                            Timber.tag("onFailure?callSignup").e(t);
                        }
                    });
        } else {
            hideLoader();
            showSnackbarShort(getString(R.string.error_please_connect_to_internet));
        }
    }

    void launchPurchaseFlow(ProductDetails productDetails) {
        assert productDetails.getSubscriptionOfferDetails() != null;
        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .setOfferToken(productDetails.getSubscriptionOfferDetails().get(0).getOfferToken())
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();

        billingClient.launchBillingFlow(SubscriptionActivity.this, billingFlowParams);
    }

    void verifySubPurchase(Purchase purchases) {

        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchases.getPurchaseToken())
                .build();

        billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                //use prefs to set premium
                //Setting premium to 1
                // 1 - premium
                // 0 - no premium
                preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 1);
                goBack();
            }
        });
    }

    private void goBack() {
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), SubscriptionActivity.class));
            finish();
        },2000);

    }

    protected void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),
                (billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : list) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                verifySubPurchase(purchase);
                            }
                        }
                    }
                }
        );
    }


    @SuppressLint("SetTextI18n")
    void showProducts() {
        ImmutableList<QueryProductDetailsParams.Product> productList = ImmutableList.of(
                //Product 1
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("test_monthly_2")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()

                /*//Product 2
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("test_sub_monthly1")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()*/

        );

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();

        Log.e("BillProductList", new Gson().toJson(productList) + "\n **");

        billingClient.queryProductDetailsAsync(
                params,
                (billingResult, prodDetailsList) -> {
                    // Process the result
                    Log.e("Billing", new Gson().toJson(billingResult) + " **\n" + new Gson().toJson(prodDetailsList));
                    productDetailsList.clear();
                    handler.postDelayed(() -> {
                        binding.loadProducts.setVisibility(View.INVISIBLE);
                        productDetailsList.addAll(prodDetailsList);
                        adapter = new SubscriptionAdapter(getApplicationContext(), productDetailsList, SubscriptionActivity.this);
                        binding.recyclerview.setHasFixedSize(true);
                        binding.recyclerview.setLayoutManager(new LinearLayoutManager(SubscriptionActivity.this, LinearLayoutManager.VERTICAL, false));
                        binding.recyclerview.setAdapter(adapter);

                    },2000);

                }
        );

    }

    void restorePurchases(){
        billingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener((billingResult, list) -> {}).build();
        final BillingClient finalBillingClient = billingClient;
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {

                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    finalBillingClient.queryPurchasesAsync(
                            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), (billingResult1, list) -> {
                                if (billingResult1.getResponseCode() == BillingClient.BillingResponseCode.OK){
                                    if(list.size()>0){
                                        if (list.get(0).isAutoRenewing()) {
                                            preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 1);
                                            // set 1 to activate premium feature
                                            showSnackbarShort("Successfully restored");
                                            goBack();
                                        }else {
                                            showSnackbarShort("Oops, No purchase found.");
                                            preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0); // set 0 to de-activate premium feature
                                        }
                                    }else {
                                        showSnackbarShort("Oops, No purchase found.");
                                        preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0); // set 0 to de-activate premium feature
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClick(int pos) {
        launchPurchaseFlow(productDetailsList.get(pos));
    }
}