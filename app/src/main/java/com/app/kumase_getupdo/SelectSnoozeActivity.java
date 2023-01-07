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

public class SelectSnoozeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener{

    private ActivitySelectSnoozeBinding binding;
    private ViewModelSetAlarm viewModelSetAlarm;
    private RadioGroup freqRadioGroup, intervalRadioGroup;
    private SwitchCompat onOffSwitch;
    private EditText snoozeFreqEditText, snoozeIntervalEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_snooze);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);

        onOffSwitch = binding.snoozeOnOffSwitch;
        freqRadioGroup = binding.snoozeFreqRadioGroup;
        intervalRadioGroup = binding.snoozeIntervalRadioGroup;
        snoozeFreqEditText = binding.snoozeFreqEditText;
        snoozeIntervalEditText = binding.snoozeIntervalEditText;

        onOffSwitch.setChecked(viewModelSetAlarm.getIsSnoozeOn());
        onSwitchCheckedChanged();
        onOffSwitch.setOnCheckedChangeListener(this);

        switch (viewModelSetAlarm.getSnoozeFreq()) {
            case 3:
                freqRadioGroup.check(R.id.freqRadioButton_three);
                snoozeFreqEditText.setEnabled(false);
                break;
            case 5:
                freqRadioGroup.check(R.id.freqRadioButton_five);
                snoozeFreqEditText.setEnabled(false);
                break;
            case 10:
                freqRadioGroup.check(R.id.freqRadioButton_ten);
                snoozeFreqEditText.setEnabled(false);
                break;
            default:
                freqRadioGroup.check(R.id.freqRadioButton_custom);
                snoozeFreqEditText.setEnabled(true);
                snoozeFreqEditText.setText(String.valueOf(viewModelSetAlarm.getSnoozeFreq()));
                break;
        }

        switch (viewModelSetAlarm.getSnoozeIntervalInMins()) {
            case 5:
                intervalRadioGroup.check(R.id.intervalRadioButton_five);
                snoozeIntervalEditText.setEnabled(false);
                break;
            case 10:
                intervalRadioGroup.check(R.id.intervalRadioButton_ten);
                snoozeIntervalEditText.setEnabled(false);
                break;
            case 15:
                intervalRadioGroup.check(R.id.intervalRadioButton_fifteen);
                snoozeIntervalEditText.setEnabled(false);
                break;
            default:
                intervalRadioGroup.check(R.id.intervalRadioButton_custom);
                snoozeIntervalEditText.setEnabled(true);
                snoozeIntervalEditText.setText(String.valueOf(viewModelSetAlarm.getSnoozeIntervalInMins()));
                break;
        }

        onSwitchCheckedChanged();

        snoozeFreqEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() != 0) {
                    viewModelSetAlarm.setSnoozeFreq(Integer.parseInt("" + charSequence));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        snoozeIntervalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() != 0) {
                    viewModelSetAlarm.setSnoozeIntervalInMins(Integer.parseInt("" + charSequence));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        freqRadioGroup.setOnCheckedChangeListener(this);
        intervalRadioGroup.setOnCheckedChangeListener(this);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModelSetAlarm.getIsSnoozeOn()){
                    Intent intent = new Intent().putExtra(Constants.Bundles.IS_SNOOZE_ON, viewModelSetAlarm.getIsSnoozeOn())
                            .putExtra(Constants.Bundles.IS_SNOOZE_TIME, viewModelSetAlarm.getSnoozeIntervalInMins())
                            .putExtra(Constants.Bundles.IS_SNOOZE_FREQ, viewModelSetAlarm.getSnoozeFreq());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.getId() == R.id.snoozeOnOffSwitch) {
            viewModelSetAlarm.setIsSnoozeOn(isChecked);
            onSwitchCheckedChanged();
        }
    }

    /**
     * Makes necessary changes in UI when the snooze switch is turned on or off.
     */
    private void onSwitchCheckedChanged() {
        if (viewModelSetAlarm.getIsSnoozeOn()) {

            onOffSwitch.setText(getResources().getString(R.string.switchOn));

            binding.freqRadioButtonThree.setEnabled(true);
            binding.freqRadioButtonFive.setEnabled(true);
            binding.freqRadioButtonTen.setEnabled(true);
            binding.freqRadioButtonCustom.setEnabled(true);

            binding.intervalRadioButtonFive.setEnabled(true);
            binding.intervalRadioButtonTen.setEnabled(true);
            binding.intervalRadioButtonFifteen.setEnabled(true);
            binding.intervalRadioButtonCustom.setEnabled(true);

            snoozeFreqEditText.setEnabled(freqRadioGroup.getCheckedRadioButtonId()
                    == R.id.freqRadioButton_custom);
            snoozeIntervalEditText.setEnabled(intervalRadioGroup.getCheckedRadioButtonId()
                    == R.id.intervalRadioButton_custom);
        } else {

            onOffSwitch.setText(getResources().getString(R.string.switchOff));

            binding.freqRadioButtonThree.setEnabled(false);
            binding.freqRadioButtonFive.setEnabled(false);
            binding.freqRadioButtonTen.setEnabled(false);
            binding.freqRadioButtonCustom.setEnabled(false);

            binding.intervalRadioButtonFive.setEnabled(false);
            binding.intervalRadioButtonTen.setEnabled(false);
            binding.intervalRadioButtonFifteen.setEnabled(false);
            binding.intervalRadioButtonCustom.setEnabled(false);

            snoozeFreqEditText.setEnabled(false);
            snoozeIntervalEditText.setEnabled(false);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

        if (radioGroup.getId() == R.id.snoozeFreqRadioGroup) {

            switch (checkedId) {
                case R.id.freqRadioButton_three:
                    viewModelSetAlarm.setSnoozeFreq(3);
                    snoozeFreqEditText.setEnabled(false);
                    break;
                case R.id.freqRadioButton_five:
                    viewModelSetAlarm.setSnoozeFreq(5);
                    snoozeFreqEditText.setEnabled(false);
                    break;
                case R.id.freqRadioButton_ten:
                    viewModelSetAlarm.setSnoozeFreq(10);
                    snoozeFreqEditText.setEnabled(false);
                    break;
                default:
                    snoozeFreqEditText.setEnabled(true);
                    break;
            }

        } else if (radioGroup.getId() == R.id.snoozeIntervalRadioGroup) {

            switch (checkedId) {
                case R.id.intervalRadioButton_five:
                    viewModelSetAlarm.setSnoozeIntervalInMins(5);
                    snoozeIntervalEditText.setEnabled(false);
                    break;
                case R.id.intervalRadioButton_ten:
                    viewModelSetAlarm.setSnoozeIntervalInMins(10);
                    snoozeIntervalEditText.setEnabled(false);
                    break;
                case R.id.intervalRadioButton_fifteen:
                    viewModelSetAlarm.setSnoozeIntervalInMins(15);
                    snoozeIntervalEditText.setEnabled(false);
                    break;
                default:
                    snoozeIntervalEditText.setEnabled(true);
                    break;
            }
        }
    }
}