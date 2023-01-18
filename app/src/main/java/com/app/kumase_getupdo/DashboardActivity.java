package com.app.kumase_getupdo;

import static android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM;
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
import static com.app.kumase_getupdo.alarm.ConstantsAndStatics.BUNDLE_KEY_TIME_IN_SECS;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.app.kumase_getupdo.adapter.AllAlarmsAdapter;
import com.app.kumase_getupdo.alarm.AlarmBroadcastReceiver;
import com.app.kumase_getupdo.alarm.ConstantsAndStatics;
import com.app.kumase_getupdo.databinding.ActivityDashboardBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.google.gson.Gson;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.api.RetrofitClient;
import com.jbs.general.model.response.alarms.AlarmsApiData;
import com.jbs.general.model.response.alarms.MainResponseGetAlarms;
import com.jbs.general.model.response.alarms.MainResponseSetAlarms;
import com.jbs.general.model.response.singup.MainResponseSignUp;
import com.jbs.general.model.response.singup.SignUpData;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DashboardActivity extends BaseActivity {

    private ActivityDashboardBinding binding;
    private List<AlarmsApiData> alarmsApiDataList = new ArrayList<>();
    private AllAlarmsAdapter allAlarmsAdapter;
    private ViewModelSetAlarm viewModelSetAlarm;
    private ActivityResultLauncher<Intent> settingsActLauncher;
    private String toastText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);

        //initialize views and variables
        initialization();

        SignUpData signUpData = new Gson().fromJson(preferenceUtils.getString(Constants.PreferenceKeys.USER_DATA), SignUpData.class);
        Log.e("SingupData", new Gson().toJson(signUpData) + " **");
        binding.tvUserName.setText(signUpData.getUser_name());

        settingsActLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> viewModelSetAlarm.setIsSettingsActOver(true));

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
            }
        });
    }

    private void initialization() {
        if (preferenceUtils.getInteger(Constants.PreferenceKeys.SUBSCRIBE) == 0) {
            changeStatusApi();
        }
        getAlarmsFromApi();

        allAlarmsAdapter = new AllAlarmsAdapter(DashboardActivity.this, alarmsApiDataList);
        binding.alarmsRecyclerView.setAdapter(allAlarmsAdapter);

        allAlarmsAdapter.setOnItemClickListener(new AllAlarmsAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onOnOffButtonClick(int position) {
                if (preferenceUtils.getInteger(Constants.PreferenceKeys.SUBSCRIBE) == 1){
                    Log.e("Subscribed", "true...!");
                    if (alarmsApiDataList.get(position).getTime().equals("00:00:00")) {
                        showSnackbarLong("Please set Alarm time first.!");
                    } else {
                        Log.e("onOnOffButtonClick", alarmsApiDataList.get(position).getTime() + " **");
                        if (alarmsApiDataList.get(position).getStatus() == 1) {
                                setStatusChangeApiDeActivate(alarmsApiDataList.get(position));
                        } else {
                            if (!isAfterDate(alarmsApiDataList.get(position).getDate() + " " + alarmsApiDataList.get(position).getTime())) {
                                //preferenceUtils.setInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID, alarmsApiDataList.get(position).getId());
                                setStatusChangeApiActivate(alarmsApiDataList.get(position));
                            }else {
                                showSnackbarLong("Can't set alarm for previous date, please change date!");
                            }
                        }
                    }
                }else {
                    Log.e("ActiveAlarmId", preferenceUtils.getInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID) + " **");
                    if (preferenceUtils.getInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID) == 0){
                        Log.e("Alarm", "Already No Alarm Active");
                        if (alarmsApiDataList.get(position).getTime().equals("00:00:00")) {
                            showSnackbarLong("Please set Alarm time first.!");
                        } else {
                            Log.e("onOnOffButtonClick", alarmsApiDataList.get(position).getTime() + " **");
                            if (alarmsApiDataList.get(position).getStatus() == 1) {
                                setStatusChangeApiDeActivate(alarmsApiDataList.get(position));
                            } else {
                                Log.e("DateAndTime", alarmsApiDataList.get(position).getDate() + " " + alarmsApiDataList.get(position).getTime());
                                if (!isAfterDate(alarmsApiDataList.get(position).getDate() + " " + alarmsApiDataList.get(position).getTime())) {
                                    //preferenceUtils.setInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID, alarmsApiDataList.get(position).getId());
                                    setStatusChangeApiActivate(alarmsApiDataList.get(position));
                                }else {
                                    showSnackbarLong("Can't set alarm for previous date, please change date!");
                                }
                            }
                        }
                    }else {
                        if (alarmsApiDataList.get(position).getStatus() == 1) {
                            setStatusChangeApiDeActivate(alarmsApiDataList.get(position));
                        }
                        showSnackbarLong("Please Subscribe to plan.!");
                        Log.e("Alarm", "Already One Alarm Active");
                    }
                }
            }

            @Override
            public void onItemClicked(int position) {
                startActivity(new Intent(DashboardActivity.this, SetAlarmTimeActivity.class)
                        .setAction(Constants.AppConstant.ACTION_EXISTING_ALARM)
                        .putExtra(Constants.PreferenceKeys.ALARM_ID, alarmsApiDataList.get(position).getId())
                        .putExtra(Constants.PreferenceKeys.ALARM_DETAILS, new Gson().toJson(alarmsApiDataList.get(position))));
            }
        });
    }

    private void changeStatusApi() {
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().cancelSubscription(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), parseDateToyyyyMMddhhmmss()).enqueue(new Callback<MainResponseSignUp>() {
                @Override
                public void onResponse(@NonNull Call<MainResponseSignUp> call, @NonNull Response<MainResponseSignUp> response) {
                    hideLoader();
                    if (response.body() == null) {
                        showSnackbarShort(getString(R.string.error_something_went_wrong));
                        return;
                    }

                    MainResponseSignUp mainResponseSignUp = response.body();
                    if (mainResponseSignUp.isSuccess()){
                        preferenceUtils.setInteger(Constants.PreferenceKeys.SUBSCRIBE, 0);
                    }else {
                        showSnackbarShort(getString(R.string.error_something_went_wrong) + "\n" + mainResponseSignUp.getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MainResponseSignUp> call, @NonNull Throwable t) {
                    hideLoader();
                    Timber.tag("onFailure?cancelSubscri").e(t);
                }
            });
        }else {
            hideLoader();
            showSnackbarShort(getString(R.string.error_please_connect_to_internet));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setStatusChangeApiDeActivate(AlarmsApiData alarmsApiData) {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormats.TIME_FORMAT, Locale.UK);
        Date date = null;
        try {
            if (alarmsApiData.getTime() != null) {
                date = sdf.parse(alarmsApiData.getTime());
            } else {
                date = sdf.parse(String.valueOf(LocalDateTime.now().plusHours(1)));
            }
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage() + " ** " + LocalDateTime.now().plusHours(1));
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().setAlarm(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), alarmsApiData.getId(), c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":00", (viewModelSetAlarm.getSnoozeIntervalInSecs()/1000), alarmsApiData.getUri().toString(), 0, alarmsApiData.getDate(), alarmsApiData.getSound_frequency(), alarmsApiData.getSound_time_interval())
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
                                alarmsApiDataList.clear();
                                alarmsApiDataList.addAll(mainResponseSetAlarms.getData().getAlarmsList());
                                allAlarmsAdapter.notifyDataSetChanged();

                                DeActivateAlarm(alarmsApiData);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setStatusChangeApiActivate(AlarmsApiData alarmsApiData) {
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
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().setAlarm(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), alarmsApiData.getId(), c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":00", (viewModelSetAlarm.getSnoozeIntervalInSecs()/1000), alarmsApiData.getUri().toString(), 1, alarmsApiData.getDate(), alarmsApiData.getSound_frequency(), alarmsApiData.getSound_time_interval())
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
                                alarmsApiDataList.clear();
                                alarmsApiDataList.addAll(mainResponseSetAlarms.getData().getAlarmsList());
                                allAlarmsAdapter.notifyDataSetChanged();

                                addOrActivateAlarm(alarmsApiData);
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
        data.putInt(BUNDLE_KEY_ALARM_ID, alarmsApiData.getId());
        data.putInt(BUNDLE_KEY_ALARM_HOUR, alarmDateTime.getHour());
        data.putInt(BUNDLE_KEY_ALARM_MINUTE, alarmDateTime.getMinute());
        data.putInt(BUNDLE_KEY_ALARM_DAY, alarmDateTime.getDayOfMonth());
        data.putInt(BUNDLE_KEY_ALARM_MONTH, alarmDateTime.getMonthValue());
        data.putInt(BUNDLE_KEY_ALARM_YEAR, alarmDateTime.getYear());
        data.putInt(BUNDLE_KEY_ALARM_TYPE, viewModelSetAlarm.getAlarmType());
        data.putBoolean(BUNDLE_KEY_IS_SNOOZE_ON, viewModelSetAlarm.getIsSnoozeOn());
        data.putBoolean(BUNDLE_KEY_IS_REPEAT_ON, viewModelSetAlarm.getIsRepeatOn());
        data.putInt(BUNDLE_KEY_ALARM_VOLUME, viewModelSetAlarm.getAlarmVolume(DashboardActivity.this));
        data.putInt(BUNDLE_KEY_SNOOZE_TIME_IN_MINS, alarmsApiData.getSound_time_interval());
        data.putInt(BUNDLE_KEY_TIME_IN_SECS, alarmsApiData.getSound());
        data.putInt(BUNDLE_KEY_SNOOZE_FREQUENCY, alarmsApiData.getSound_frequency());
        data.putIntegerArrayList(BUNDLE_KEY_REPEAT_DAYS, repeatDays);
        data.putParcelable(BUNDLE_KEY_ALARM_TONE_URI, Uri.parse(alarmsApiData.getUri()));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void DeActivateAlarm(AlarmsApiData alarmsApiData) {
        ConstantsAndStatics.cancelScheduledPeriodicWork(this);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DateFormats.TIME_FORMAT, Locale.UK);
        Date date = null;
        try {
            if (alarmsApiData.getTime() != null) {
                date = sdf.parse(alarmsApiData.getTime());
            } else {
                date = sdf.parse(String.valueOf(LocalDateTime.now().plusHours(1)));
            }
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage() + " ** " + LocalDateTime.now().plusHours(1));
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class)
                .setAction(ConstantsAndStatics.ACTION_DELIVER_ALARM)
                .setFlags(Intent.FLAG_RECEIVER_FOREGROUND);

        int alarmID = alarmsApiData.getId();

        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ?
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE :
                PendingIntent.FLAG_NO_CREATE;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmID, intent, flags);

        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }

        ConstantsAndStatics.killServices(this, alarmID);
        DateTimeFormatter formatter;
        if (DateFormat.is24HourFormat(this)) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
        } else {
            formatter = DateTimeFormatter.ofPattern("hh:mm a");
        }
        LocalTime alarmTime = LocalTime.of(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
        if (preferenceUtils.getInteger(Constants.PreferenceKeys.SUBSCRIBE) == 0) {
            if (preferenceUtils.getInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID) == alarmsApiData.getId()) {
                preferenceUtils.setInteger(Constants.PreferenceKeys.ACTIVE_ALARM_ID, 0);
            }
        }
        showSnackbarLong(getString(R.string.toast_alarmSwitchedOff, alarmTime.format(formatter)));
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
        Log.e("DurationMsg", toastText + " **");
    }

    /**
     * Shows a toast using the text in {@code toastText} variable.
     */
    private void showToast() {
        if (toastText != null) {
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
            toastText = null;
        }
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

    private void getAlarmsFromApi() {
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().getAlarms(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID)).enqueue(new Callback<MainResponseGetAlarms>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(@NonNull Call<MainResponseGetAlarms> call, @NonNull Response<MainResponseGetAlarms> response) {
                    hideLoader();
                    if (response.body() == null) {
                        showSnackbarShort(getString(R.string.error_something_went_wrong));
                        return;
                    }
                    MainResponseGetAlarms mainResponseGetAlarms = response.body();
                    Log.e("mainResponseGetAlarms", new Gson().toJson(mainResponseGetAlarms) + " **");
                    if (mainResponseGetAlarms.isSuccess()) {
                        alarmsApiDataList.clear();
                        alarmsApiDataList.addAll(mainResponseGetAlarms.getData());
                        if (!preferenceUtils.getBoolean(Constants.PreferenceKeys.IS_FIRST_TIME)) {
                            if (!alarmsApiDataList.isEmpty()) {
                                for (int i = 0; i < alarmsApiDataList.size(); i++) {
                                    if (alarmsApiDataList.get(i).getStatus() == 1) {
                                        addOrActivateAlarm(alarmsApiDataList.get(i));
                                    }
                                }
                            }
                            preferenceUtils.setBoolean(Constants.PreferenceKeys.IS_FIRST_TIME, true);
                        }
                        allAlarmsAdapter.notifyDataSetChanged();
                    } else {
                        showSnackbarShort(getString(R.string.error_something_went_wrong) + "\n" + mainResponseGetAlarms.getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MainResponseGetAlarms> call, @NonNull Throwable t) {
                    hideLoader();
                    Timber.tag("onFailure?callSignup").e(t);
                }
            });
        } else {
            hideLoader();
            showSnackbarShort(getString(R.string.error_please_connect_to_internet));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (viewModelSetAlarm.getPendingStatus() && viewModelSetAlarm.getPendingALarmData() != null
                    && viewModelSetAlarm.getIsSettingsActOver()) {

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                if (alarmManager.canScheduleExactAlarms()) {
                    viewModelSetAlarm.setPendingStatus(false);
                    viewModelSetAlarm.setIsSettingsActOver(false);
                    setAlarm(viewModelSetAlarm.getPendingALarmData());
                    viewModelSetAlarm.savePendingAlarm(null);
                } else {
                    requestExactAlarmPerm();
                }
            }
        }
    }

    public void onAddAlarmClick(View view) {
        if (isClickDisabled()) {
            return;
        }
        hideKeyboard(view);

        startActivity(new Intent(DashboardActivity.this, SetAlarmTimeActivity.class)
                .setAction(Constants.AppConstant.ACTION_NEW_ALARM));

    }
}