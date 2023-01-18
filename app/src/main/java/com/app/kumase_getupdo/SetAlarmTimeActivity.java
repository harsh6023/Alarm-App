package com.app.kumase_getupdo;

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

import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.app.kumase_getupdo.alarm.Activity_RingtonePicker;
import com.app.kumase_getupdo.alarm.ConstantsAndStatics;
import com.app.kumase_getupdo.databinding.ActivitySetAlarmTimeBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.google.gson.Gson;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.api.RetrofitClient;
import com.jbs.general.model.response.alarms.AlarmsApiData;
import com.jbs.general.model.response.alarms.MainResponseSetAlarms;
import com.jbs.general.utils.Constants;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SetAlarmTimeActivity extends BaseActivity {

    public static final int MODE_NEW_ALARM = 0, MODE_EXISTING_ALARM = 1;
    private ActivitySetAlarmTimeBinding binding;
    private ViewModelSetAlarm viewModelSetAlarm;
    private AlarmsApiData alarmsApiData;
    private static final int RINGTONE_REQUEST_CODE = 5280;
    private static final int SNOOZE_REQUEST_CODE = 5281;
    private static final int DAY_REQUEST_CODE = 5282;
    private static final int DATE_REQUEST_CODE = 5283;
    private static final int TIME_REQUEST_CODE = 5284;
    private String selectedDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_alarm_time);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);

        binding.etAlarmTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarmTimeActivity.this, Activity_RingtonePicker.class)
                        .setAction(RingtoneManager.ACTION_RINGTONE_PICKER)
                        .putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
                        .putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select alarm tone:")
                        .putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
                        .putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
                        .putExtra(ConstantsAndStatics.EXTRA_PLAY_RINGTONE, false)
                        .putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, Settings.System.DEFAULT_ALARM_ALERT_URI)
                        .putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, viewModelSetAlarm.getAlarmToneUri());
                startActivityForResult(intent, RINGTONE_REQUEST_CODE);
            }
        });

        binding.etAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarmTimeActivity.this, SelectTimerActivity.class)
                        .setAction("ACTION_TIME");
                startActivityForResult(intent, TIME_REQUEST_CODE);
            }
        });

        binding.etAlarmSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarmTimeActivity.this, SelectSnoozeActivity.class)
                        .putExtra("Snooze_freq", viewModelSetAlarm.getSnoozeFreq())
                        .putExtra("Snooze_time", viewModelSetAlarm.getSnoozeIntervalInMins())
                        .setAction("ACTION_SNOOZE");
                startActivityForResult(intent, SNOOZE_REQUEST_CODE);
            }
        });

        binding.etAlarmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.datePicker.getVisibility() == View.VISIBLE){
                    binding.datePicker.setVisibility(View.GONE);
                }else {
                    binding.datePicker.setVisibility(View.VISIBLE);
                }
                openCalendar();
               /* Intent intent = new Intent(SetAlarmTimeActivity.this, DatePickerActivity.class)
                        .setAction("ACTION_DATE");
                startActivityForResult(intent, DATE_REQUEST_CODE);*/
            }
        });

        binding.etAlarmDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetAlarmTimeActivity.this, SelectDaysActivity.class)
                        .setAction("ACTION_DAY_SELECT");
                startActivityForResult(intent, DAY_REQUEST_CODE);
            }
        });

        if (Objects.equals(getIntent().getAction(), Constants.AppConstant.ACTION_EXISTING_ALARM)) {
            alarmsApiData = (AlarmsApiData) gsonUtils.createPOJOFromString(Objects.requireNonNull(getIntent().getExtras()).getString(Constants.PreferenceKeys.ALARM_DETAILS), AlarmsApiData.class);

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
                    if (!isAfterDate(alarmsApiData.getDate() + " " + alarmsApiData.getTime())) {
                        dateD = sdfDate.parse(alarmsApiData.getDate());
                    }else {
                        dateD = sdfDate.parse(String.valueOf(LocalDateTime.now().plusHours(1)));
                    }
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

            Log.e("DateParse", cDate.get(Calendar.DAY_OF_MONTH) + " ** " + cDate.get(Calendar.MONTH) + 1 + " ** " + cDate.get(Calendar.YEAR));

            if (alarmsApiData.getTime().equals("00:00:00")) {
                viewModelSetAlarm.setAlarmDateTime(LocalDateTime.now().plusHours(0));
            } else {
                viewModelSetAlarm.setAlarmDateTime(LocalDateTime.now().plusHours(0));
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withHour(c.get(Calendar.HOUR_OF_DAY)));
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withMinute(c.get(Calendar.MINUTE)));
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withDayOfMonth(cDate.get(Calendar.DAY_OF_MONTH)));
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withMonth(cDate.get(Calendar.MONTH) + 1));
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withYear(cDate.get(Calendar.YEAR)));
            }

            viewModelSetAlarm.setIsChosenDateToday(viewModelSetAlarm.getAlarmDateTime().toLocalDate().equals(LocalDate.now()));
            if (viewModelSetAlarm.getIsChosenDateToday()) {
                viewModelSetAlarm.setMinDate(viewModelSetAlarm.getAlarmDateTime().toLocalDate());
            } else {
                if (!viewModelSetAlarm.getAlarmDateTime().toLocalTime().isAfter(LocalTime.now())) {
                    viewModelSetAlarm.setMinDate(LocalDate.now().plusDays(1));
                } else {
                    viewModelSetAlarm.setMinDate(LocalDate.now());
                }
            }

            binding.addAlarmTimePicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
            binding.addAlarmTimePicker.setCurrentMinute(c.get(Calendar.MINUTE));

            viewModelSetAlarm.setAlarmToneUri(Uri.parse(alarmsApiData.getUri()));
            Log.e("AlarmTone", alarmsApiData.getUri() + " ** "  + viewModelSetAlarm.getAlarmToneUri());
            viewModelSetAlarm.setRepeatDays(alarmsApiData.getDay());
            viewModelSetAlarm.setAlarmId(alarmsApiData.getId());
            if (alarmsApiData.getSound_frequency() != 0) {
                viewModelSetAlarm.setSnoozeFreq(alarmsApiData.getSound_frequency());
            }

            if (alarmsApiData.getSound_time_interval() != 0) {
                viewModelSetAlarm.setSnoozeIntervalInMins(alarmsApiData.getSound_time_interval());
            }
            viewModelSetAlarm.setAlarmMessage(alarmsApiData.getName());
            binding.etAlarmTitle.setText(alarmsApiData.getName());
            switch (alarmsApiData.getSound()) {
                case 3:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(3000);
                    break;
                case 6:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(6000);
                    break;
                case 9:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(9000);
                    break;
            }
            displayAlarmTone();
            displayRepeatOptions();

        } else if (Objects.equals(getIntent().getAction(), Constants.AppConstant.ACTION_NEW_ALARM)) {
            displayAlarmTone();
            displayRepeatOptions();
            viewModelSetAlarm.setAlarmDateTime(LocalDateTime.now().plusHours(0));
            viewModelSetAlarm.setIsChosenDateToday(viewModelSetAlarm.getAlarmDateTime().toLocalDate().equals(LocalDate.now()));

            if (viewModelSetAlarm.getIsChosenDateToday()) {
                viewModelSetAlarm.setMinDate(viewModelSetAlarm.getAlarmDateTime().toLocalDate());
            } else {
                if (!viewModelSetAlarm.getAlarmDateTime().toLocalTime().isAfter(LocalTime.now())) {
                    viewModelSetAlarm.setMinDate(LocalDate.now().plusDays(1));
                } else {
                    viewModelSetAlarm.setMinDate(LocalDate.now());
                }
            }
            binding.addAlarmTimePicker.setCurrentHour(viewModelSetAlarm.getAlarmDateTime().getHour());
            binding.addAlarmTimePicker.setCurrentMinute(viewModelSetAlarm.getAlarmDateTime().getMinute());
        }

        displayTimeOptions();
        displaySnoozeOptions();

        binding.addAlarmTimePicker.setIs24HourView(true);
        binding.addAlarmTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withHour(hourOfDay));
                viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withMinute(minute));

                if (viewModelSetAlarm.getIsChosenDateToday()) {
                    ///////////////////////////////////////////////////////////////////////////////////
                    // Chosen date is today. We have to check if the alarm is possible today.
                    // If not possible, the date is changed to tomorrow.
                    //////////////////////////////////////////////////////////////////////////////////
                    viewModelSetAlarm.setAlarmDateTime(LocalDateTime.of(LocalDate.now(), viewModelSetAlarm.getAlarmDateTime().toLocalTime()));

                    if (!viewModelSetAlarm.getAlarmDateTime().toLocalTime().isAfter(LocalTime.now())) {
                        //Date today NOT possible.
                        viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().plusDays(1));
                        viewModelSetAlarm.setIsChosenDateToday(false);
                        viewModelSetAlarm.setHasUserChosenDate(false);
                    }

                    // Set the minDate.
                    viewModelSetAlarm.setMinDate(viewModelSetAlarm.getAlarmDateTime().toLocalDate());
                } else {
                    /////////////////////////////////////////////////////////////////////////////////////
                    // Chosen date is NOT today. If the user has not chosen a different date
                    // deliberately, we check whether alarm today is possible. If possible, we
                    // change the date to today, otherwise it will stay as it is.
                    //
                    // If the user has chosen a date deliberately, we do nothing.
                    /////////////////////////////////////////////////////////////////////////////////////
                    if (!viewModelSetAlarm.getHasUserChosenDate()) {
                        if (viewModelSetAlarm.getAlarmDateTime().toLocalTime().isAfter(LocalTime.now())) {
                            // Date today possible.
                            viewModelSetAlarm.setAlarmDateTime(LocalDateTime.of(LocalDate.now(),
                                    viewModelSetAlarm.getAlarmDateTime().toLocalTime()));
                            viewModelSetAlarm.setIsChosenDateToday(true);
                        }
                    }

                    // Set the minDate.
                    if (!viewModelSetAlarm.getAlarmDateTime().toLocalTime().isAfter(LocalTime.now())) {
                        viewModelSetAlarm.setMinDate(LocalDate.now().plusDays(1));
                    } else {
                        viewModelSetAlarm.setMinDate(LocalDate.now());
                    }
                }

                setDate();
            }
        });

        setDate();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(getIntent().getAction(), Constants.AppConstant.ACTION_EXISTING_ALARM)) {

                    ArrayList<Integer> repeatDays = new ArrayList<>();
                    repeatDays.add(viewModelSetAlarm.getRepeatDays());
                    if (repeatDays != null) {
                        Collections.sort(repeatDays);
                    }

                    Bundle data = new Bundle();
                    data.putInt(BUNDLE_KEY_ALARM_ID, viewModelSetAlarm.getAlarmId());
                    data.putInt(BUNDLE_KEY_ALARM_HOUR, viewModelSetAlarm.getAlarmDateTime().getHour());
                    data.putInt(BUNDLE_KEY_ALARM_MINUTE, viewModelSetAlarm.getAlarmDateTime().getMinute());
                    data.putInt(BUNDLE_KEY_ALARM_DAY, viewModelSetAlarm.getAlarmDateTime().getDayOfMonth());
                    data.putInt(BUNDLE_KEY_ALARM_MONTH, viewModelSetAlarm.getAlarmDateTime().getMonthValue());
                    data.putInt(BUNDLE_KEY_ALARM_YEAR, viewModelSetAlarm.getAlarmDateTime().getYear());
                    data.putInt(BUNDLE_KEY_ALARM_TYPE, viewModelSetAlarm.getAlarmType());
                    data.putBoolean(BUNDLE_KEY_IS_SNOOZE_ON, viewModelSetAlarm.getIsSnoozeOn());
                    data.putBoolean(BUNDLE_KEY_IS_REPEAT_ON, viewModelSetAlarm.getIsRepeatOn());
                    data.putInt(BUNDLE_KEY_ALARM_VOLUME, viewModelSetAlarm.getAlarmVolume(SetAlarmTimeActivity.this));
                    data.putInt(BUNDLE_KEY_SNOOZE_TIME_IN_MINS, viewModelSetAlarm.getSnoozeIntervalInMins());
                    data.putInt(BUNDLE_KEY_TIME_IN_SECS, viewModelSetAlarm.getSnoozeIntervalInSecs());
                    data.putInt(BUNDLE_KEY_SNOOZE_FREQUENCY, viewModelSetAlarm.getSnoozeFreq());
                    data.putIntegerArrayList(BUNDLE_KEY_REPEAT_DAYS, repeatDays);
                    data.putParcelable(BUNDLE_KEY_ALARM_TONE_URI, viewModelSetAlarm.getAlarmToneUri());
                    data.putString(ConstantsAndStatics.BUNDLE_KEY_ALARM_MESSAGE, viewModelSetAlarm.getAlarmMessage());

                    Log.e("OnSaveData", viewModelSetAlarm.getAlarmDateTime().getHour() + " ** " + viewModelSetAlarm.getAlarmDateTime().getMinute());


                    startActivity(new Intent(SetAlarmTimeActivity.this, SubscriptionActivity.class)
                            .setAction(Constants.AppConstant.ACTION_EXISTING_ALARM)
                            .putExtra(Constants.PreferenceKeys.ALARM_ID, alarmsApiData.getId())
                            .putExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DETAILS, data)
                            .putExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DATE, selectedDate)
                            .putExtra(Constants.PreferenceKeys.ALARM_DETAILS, new Gson().toJson(alarmsApiData)));
                   /* if (isNetworkAvailable()) {
                        showLoader();
                        RetrofitClient.getInstance().getApi().setAlarm(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), viewModelSetAlarm.getAlarmId(), viewModelSetAlarm.getAlarmDateTime().getHour() + ":" + viewModelSetAlarm.getAlarmDateTime().getMinute() + ":00", viewModelSetAlarm.getSnoozeIntervalInSecs() + " seconds", viewModelSetAlarm.getAlarmToneUri().toString(),  0, viewModelSetAlarm.getRepeatDays())
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
                                            startActivity(new Intent(SetAlarmTimeActivity.this, DashboardActivity.class));
                                            finish();
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
                    }*/
                }
                else if (Objects.equals(getIntent().getAction(), Constants.AppConstant.ACTION_NEW_ALARM)) {
                    if (isFieldsValid()) {
                        //callAddCustomAlarmApi();
                        ArrayList<Integer> repeatDays = new ArrayList<>();
                        repeatDays.add(viewModelSetAlarm.getRepeatDays());
                        if (repeatDays != null) {
                            Collections.sort(repeatDays);
                        }

                        Bundle data = new Bundle();
                        data.putInt(BUNDLE_KEY_ALARM_HOUR, viewModelSetAlarm.getAlarmDateTime().getHour());
                        data.putInt(BUNDLE_KEY_ALARM_MINUTE, viewModelSetAlarm.getAlarmDateTime().getMinute());
                        data.putInt(BUNDLE_KEY_ALARM_DAY, viewModelSetAlarm.getAlarmDateTime().getDayOfMonth());
                        data.putInt(BUNDLE_KEY_ALARM_MONTH, viewModelSetAlarm.getAlarmDateTime().getMonthValue());
                        data.putInt(BUNDLE_KEY_ALARM_YEAR, viewModelSetAlarm.getAlarmDateTime().getYear());
                        data.putInt(BUNDLE_KEY_ALARM_TYPE, viewModelSetAlarm.getAlarmType());
                        data.putBoolean(BUNDLE_KEY_IS_SNOOZE_ON, viewModelSetAlarm.getIsSnoozeOn());
                        data.putBoolean(BUNDLE_KEY_IS_REPEAT_ON, viewModelSetAlarm.getIsRepeatOn());
                        data.putInt(BUNDLE_KEY_ALARM_VOLUME, viewModelSetAlarm.getAlarmVolume(SetAlarmTimeActivity.this));
                        data.putInt(BUNDLE_KEY_SNOOZE_TIME_IN_MINS, viewModelSetAlarm.getSnoozeIntervalInMins());
                        data.putInt(BUNDLE_KEY_SNOOZE_FREQUENCY, viewModelSetAlarm.getSnoozeFreq());
                        data.putInt(BUNDLE_KEY_TIME_IN_SECS, viewModelSetAlarm.getSnoozeIntervalInSecs());
                        data.putIntegerArrayList(BUNDLE_KEY_REPEAT_DAYS, repeatDays);
                        data.putParcelable(BUNDLE_KEY_ALARM_TONE_URI, viewModelSetAlarm.getAlarmToneUri());
                        data.putString(ConstantsAndStatics.BUNDLE_KEY_ALARM_MESSAGE, viewModelSetAlarm.getAlarmMessage());

                        startActivity(new Intent(SetAlarmTimeActivity.this, SubscriptionActivity.class)
                                .putExtra("Alarm_title", binding.etAlarmTitle.getText().toString())
                                .putExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DETAILS, data)
                                .putExtra(ConstantsAndStatics.BUNDLE_KEY_ALARM_DATE, selectedDate)
                                .setAction(Constants.AppConstant.ACTION_NEW_ALARM));
                    }
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void openCalendar() {
        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.DAY_OF_MONTH, viewModelSetAlarm.getMinDate().getDayOfMonth());
        minCalendar.set(Calendar.MONTH, viewModelSetAlarm.getMinDate().getMonthValue() - 1);
        minCalendar.set(Calendar.YEAR, viewModelSetAlarm.getMinDate().getYear());
        minCalendar.set(Calendar.HOUR_OF_DAY, 1);
        minCalendar.set(Calendar.MINUTE, 0);
        minCalendar.set(Calendar.SECOND, 0);

        binding.datePicker.setMinDate(minCalendar.getTimeInMillis());

        binding.datePicker.init(viewModelSetAlarm.getAlarmDateTime().getYear(), viewModelSetAlarm.getAlarmDateTime().getMonthValue() - 1,
                viewModelSetAlarm.getAlarmDateTime().getDayOfMonth(), (datePicker1, newYear, newMonthOfYear, newDayOfMonth) -> {

                    viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withDayOfMonth(newDayOfMonth));
                    viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withMonth(newMonthOfYear + 1));
                    viewModelSetAlarm.setAlarmDateTime(viewModelSetAlarm.getAlarmDateTime().withYear(newYear));

                    viewModelSetAlarm.setIsChosenDateToday(viewModelSetAlarm.getAlarmDateTime().toLocalDate().equals(LocalDate.now()));
                    viewModelSetAlarm.setHasUserChosenDate(true);

                    setDate();
                });
    }


    private void displayTimeOptions() {
            binding.etAlarmTime.setText(getResources()
                    .getString(R.string.timeOptionsTV_snoozeOn,
                            (viewModelSetAlarm.getSnoozeIntervalInSecs() / 1000)));

    }

    private void displaySnoozeOptions() {
        if (viewModelSetAlarm.getIsSnoozeOn()) {
            binding.etAlarmSnooze.setText(getResources()
                    .getString(R.string.snoozeOptionsTV_snoozeOn,
                            viewModelSetAlarm.getSnoozeIntervalInMins(), viewModelSetAlarm.getSnoozeFreq()));
        } else {
            binding.etAlarmSnooze.setText(getResources().getString(R.string.snoozeOffLabel));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDate() {
        binding.etAlarmDate.setText(viewModelSetAlarm.getAlarmDateTime().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy")));
        selectedDate = viewModelSetAlarm.getAlarmDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    private void callAddCustomAlarmApi() {
        if (isNetworkAvailable()) {
            showLoader();
            RetrofitClient.getInstance().getApi().addCustomAlarm(preferenceUtils.getString(Constants.PreferenceKeys.USER_ID), binding.etAlarmTitle.getText().toString(), viewModelSetAlarm.getAlarmDateTime().getHour() + ":" + viewModelSetAlarm.getAlarmDateTime().getMinute() + ":00", viewModelSetAlarm.getSnoozeIntervalInSecs() + " seconds", viewModelSetAlarm.getAlarmToneUri().toString(), 0, selectedDate)
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
                                startActivity(new Intent(SetAlarmTimeActivity.this, DashboardActivity.class));
                                finish();
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
    }*/

    public boolean isFieldsValid() {
        if (TextUtils.isEmpty(binding.etAlarmTitle.getText().toString().trim())) {
            showSnackbarShort("Please Enter valid Title");
            binding.etAlarmTitle.requestFocus();
            binding.etAlarmTitle.setSelection(binding.etAlarmTitle.getText().toString().length());
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RINGTONE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri uri = Objects.requireNonNull(data.getExtras()).getParcelable(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                assert uri != null;
                viewModelSetAlarm.setAlarmToneUri(uri);
            }
        }else if (requestCode == SNOOZE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                assert data != null;
                viewModelSetAlarm.setIsSnoozeOn(Objects.requireNonNull(data.getExtras()).getBoolean(Constants.Bundles.IS_SNOOZE_ON));
                viewModelSetAlarm.setSnoozeIntervalInMins(Objects.requireNonNull(data.getExtras()).getInt(Constants.Bundles.IS_SNOOZE_TIME));
                viewModelSetAlarm.setSnoozeFreq(Objects.requireNonNull(data.getExtras()).getInt(Constants.Bundles.IS_SNOOZE_FREQ));

            }
        }else if (requestCode == TIME_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                assert data != null;
                viewModelSetAlarm.setSnoozeIntervalInSecs(Objects.requireNonNull(data.getExtras()).getInt(Constants.Bundles.IS_SNOOZE_TIME));
            }
        }else if (requestCode == DATE_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                assert data != null;
                viewModelSetAlarm.setIsSnoozeOn(Objects.requireNonNull(data.getExtras()).getBoolean(Constants.Bundles.IS_SNOOZE_ON));
                viewModelSetAlarm.setSnoozeIntervalInMins(Objects.requireNonNull(data.getExtras()).getInt(Constants.Bundles.IS_SNOOZE_TIME));
                viewModelSetAlarm.setSnoozeFreq(Objects.requireNonNull(data.getExtras()).getInt(Constants.Bundles.IS_SNOOZE_FREQ));
            }
        }else if (requestCode == DAY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                assert data != null;
                Log.e("Daysss", new Gson().toJson(Objects.requireNonNull(data.getExtras()).getIntegerArrayList(Constants.Bundles.REPEAT_DAYS)) + " **");
                viewModelSetAlarm.setRepeatDays(Objects.requireNonNull(data.getExtras()).getInt(Constants.Bundles.REPEAT_DAYS));
                //viewModelSetAlarm.setRepeatDays(Objects.requireNonNull(data.getExtras()).getIntegerArrayList(Constants.Bundles.REPEAT_DAYS));
            }
        }
        displayAlarmTone();
        displayTimeOptions();
        displayRepeatOptions();
        displaySnoozeOptions();
    }

    private void displayAlarmTone() {
        if (viewModelSetAlarm.getAlarmToneUri().equals(Settings.System.DEFAULT_ALARM_ALERT_URI)) {
            binding.etAlarmTone.setText(R.string.defaultAlarmToneText);

        } else {
            String fileName = null;

            try {
                try (Cursor cursor = getContentResolver()
                        .query(viewModelSetAlarm.getAlarmToneUri(), null, null, null, null)) {

                    if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {

                        int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (index != - 1) {
                            fileName = cursor.getString(index);
                        } else {
                            fileName = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                        }
                    } else {
                        viewModelSetAlarm.setAlarmToneUri(Settings.System.DEFAULT_ALARM_ALERT_URI);
                        binding.etAlarmTone.setText(R.string.defaultAlarmToneText);
                        return;
                    }
                }
            } catch (SecurityException se){
                viewModelSetAlarm.setAlarmToneUri(Settings.System.DEFAULT_ALARM_ALERT_URI);
                binding.etAlarmTone.setText(R.string.defaultAlarmToneText);
                return;
            } catch (Exception ignored) {
            }

            if (fileName != null) {
                binding.etAlarmTone.setText(fileName);
            } else {
                binding.etAlarmTone.setText(viewModelSetAlarm.getAlarmToneUri().getLastPathSegment());
            }
        }
    }

    private void displayRepeatOptions() {

        if (viewModelSetAlarm.getIsRepeatOn()) {

            viewModelSetAlarm.setIsRepeatOn(true);
            ArrayList<Integer> repeatDays = new ArrayList<>();
            repeatDays.add(viewModelSetAlarm.getRepeatDays());
            if (repeatDays != null) {
                Collections.sort(repeatDays);
            }
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < repeatDays.size(); i++) {
                int day = (repeatDays.get(i) + 1) > 7 ? 1 : (repeatDays.get(i) + 1);
                str.append(new DateFormatSymbols().getShortWeekdays()[day]);
                if (i < repeatDays.size() - 1) {
                    str.append(", ");
                }
            }
            binding.etAlarmDay.setText(str.toString());
        } else {
            viewModelSetAlarm.setIsRepeatOn(false);
        }
    }
}