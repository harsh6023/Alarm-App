package com.app.kumase_getupdo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.app.kumase_getupdo.databinding.ActivitySelectDaysBinding;
import com.app.kumase_getupdo.viewmodel.ViewModelSetAlarm;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;

public class SelectDaysActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private ViewModelSetAlarm viewModelSetAlarm;
    private ActivitySelectDaysBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_days);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);
        viewModelSetAlarm = new ViewModelProvider(this).get(ViewModelSetAlarm.class);

        binding.daysRadioGroup.setOnCheckedChangeListener(this);

        switch (viewModelSetAlarm.getRepeatDays()) {
            case 2:
                binding.daysRadioGroup.check(R.id.radioFriday);
                break;
            case 3:
                binding.daysRadioGroup.check(R.id.radioSaturday);
                break;
        }

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModelSetAlarm.getIsSnoozeOn()){
                    Intent intent = new Intent().putExtra(Constants.Bundles.REPEAT_DAYS, viewModelSetAlarm.getRepeatDays());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (radioGroup.getId() == R.id.daysRadioGroup) {

            switch (checkedId) {
                case R.id.radioFriday:
                    viewModelSetAlarm.setRepeatDays(2);
                    break;
                case R.id.radioSaturday:
                    viewModelSetAlarm.setRepeatDays(3);
                    break;
            }

        }
    }
}