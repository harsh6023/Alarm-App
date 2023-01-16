package com.app.kumase_getupdo;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.app.kumase_getupdo.databinding.ActivitySelectTimerBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.utils.Constants;

public class SelectTimerActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private ActivitySelectTimerBinding binding;
    private ViewModelSetAlarm viewModelSetAlarm;
    private RadioGroup intervalRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_timer);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);

        intervalRadioGroup = binding.snoozeIntervalRadioGroup;

        onSwitchCheckedChanged();

        switch (viewModelSetAlarm.getSnoozeIntervalInSecs()) {
            case 3000:
                intervalRadioGroup.check(R.id.intervalRadioButton_three);
                break;
            case 6000:
                intervalRadioGroup.check(R.id.intervalRadioButton_six);
                break;
            case 9000:
                intervalRadioGroup.check(R.id.intervalRadioButton_nine);
                break;
            default:
                intervalRadioGroup.check(R.id.intervalRadioButton_three);
                break;
        }

        onSwitchCheckedChanged();

        intervalRadioGroup.setOnCheckedChangeListener(this);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("getSnoozeIntervalInSecs", viewModelSetAlarm.getSnoozeIntervalInSecs() + " **");
                    Intent intent = new Intent().putExtra(Constants.Bundles.IS_SNOOZE_ON, viewModelSetAlarm.getIsSnoozeOn())
                            .putExtra(Constants.Bundles.IS_SNOOZE_TIME, viewModelSetAlarm.getSnoozeIntervalInSecs())
                            .putExtra(Constants.Bundles.IS_SNOOZE_FREQ, viewModelSetAlarm.getSnoozeFreq());
                    setResult(RESULT_OK, intent);
                    finish();
            }
        });
    }

    /**
     * Makes necessary changes in UI when the snooze switch is turned on or off.
     */
    private void onSwitchCheckedChanged() {

        binding.freqRadioButtonThree.setEnabled(true);
        binding.intervalRadioButtonSix.setEnabled(true);
        binding.intervalRadioButtonNine.setEnabled(true);

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

         if (radioGroup.getId() == R.id.snoozeIntervalRadioGroup) {

            switch (checkedId) {
                case R.id.intervalRadioButton_three:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(3000);
                    break;
                case R.id.intervalRadioButton_six:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(6000);
                    break;
                case R.id.intervalRadioButton_nine:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(9000);
                    break;
                default:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(3000);
                    break;
            }
        }
    }
}