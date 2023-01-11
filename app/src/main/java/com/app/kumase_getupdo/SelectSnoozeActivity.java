package com.app.kumase_getupdo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.app.kumase_getupdo.databinding.ActivitySelectSnoozeBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.utils.Constants;

public class SelectSnoozeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private ActivitySelectSnoozeBinding binding;
    private ViewModelSetAlarm viewModelSetAlarm;
    private RadioGroup intervalRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_snooze);
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
                intervalRadioGroup.check(R.id.intervalRadioButton_ten);
                break;
            case 9000:
                intervalRadioGroup.check(R.id.intervalRadioButton_fifteen);
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
                case R.id.intervalRadioButton_five:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(3000);
                    break;
                case R.id.intervalRadioButton_ten:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(6000);
                    break;
                case R.id.intervalRadioButton_fifteen:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(9000);
                    break;
                default:
                    viewModelSetAlarm.setSnoozeIntervalInSecs(3000);
                    break;
            }
        }
    }
}