package com.app.kumase_getupdo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.app.kumase_getupdo.databinding.ActivityDatePickerBinding;
import com.app.kumase_getupdo.databinding.ActivitySelectSnoozeBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.utils.Constants;

import java.time.LocalDate;
import java.util.Calendar;

public class DatePickerActivity extends BaseActivity {

    private ActivityDatePickerBinding binding;
    private ViewModelSetAlarm viewModelSetAlarm;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_date_picker);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);

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
                });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModelSetAlarm.getIsSnoozeOn()){
                    Intent intent = new Intent().putExtra(Constants.Bundles.SELECTED_DAY, viewModelSetAlarm.getMinDate().getDayOfMonth())
                            .putExtra(Constants.Bundles.SELECTED_MONTH, viewModelSetAlarm.getMinDate().getMonthValue())
                            .putExtra(Constants.Bundles.SELECTED_YEAR, viewModelSetAlarm.getMinDate().getYear());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}