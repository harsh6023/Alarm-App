package com.app.kumase_getupdo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.kumase_getupdo.adapter.IntroductionViewPagerAdapter;
import com.app.kumase_getupdo.databinding.ActivityIntroductionBinding;
import com.jbs.general.activity.BaseActivity;
import com.jbs.general.model.response.introduction.IntroductionData;
import com.jbs.general.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class IntroductionActivity extends BaseActivity {
    //region #Variables
    private ActivityIntroductionBinding binding;
    private List<IntroductionData> introductionData = new ArrayList<>();
    private IntroductionViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction);
        binding.setClickListener(this);
        binding.setLifecycleOwner(this);

        //initialize views and variables
        initialization();
    }

    private void initialization() {
        preferenceUtils.setBoolean(Constants.PreferenceKeys.FIRST_TIME_LAUNCH_INTRO, true);

        //introduction List
        introductionData.clear();
        introductionData.add(new IntroductionData(getString(R.string.intro_title_1), getString(R.string.intro_desc_1), R.drawable.ic_launcher_background));
        introductionData.add(new IntroductionData(getString(R.string.intro_title_2), getString(R.string.intro_desc_2), R.drawable.ic_launcher_background));
        introductionData.add(new IntroductionData(getString(R.string.intro_title_3), getString(R.string.intro_desc_3), R.drawable.ic_launcher_background));
        introductionData.add(new IntroductionData(getString(R.string.intro_title_4), getString(R.string.intro_desc_4), R.drawable.ic_launcher_background));

        //introductions
        adapter = new IntroductionViewPagerAdapter(this, introductionData);
        binding.vpIntroduction.setAdapter(adapter);
        binding.dotsIndicator.setViewPager(binding.vpIntroduction);

        binding.vpIntroduction.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Empty
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.tvSkip.setVisibility(View.VISIBLE);
                    binding.tvPrevious.setVisibility(View.GONE);
                    binding.tvContinue.setVisibility(View.GONE);
                    binding.tvNext.setVisibility(View.VISIBLE);
                } else if (position == adapter.getCount() - 1) {
                    binding.tvNext.setVisibility(View.GONE);
                    binding.tvContinue.setVisibility(View.VISIBLE);
                    binding.tvSkip.setVisibility(View.GONE);
                    binding.tvPrevious.setVisibility(View.VISIBLE);
                } else {
                    binding.tvNext.setVisibility(View.VISIBLE);
                    binding.tvContinue.setVisibility(View.GONE);
                    binding.tvSkip.setVisibility(View.GONE);
                    binding.tvPrevious.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Empty
            }
        });

    }

    /**
     * Next  Click
     */
    public void onNextClick(View view) {
        if (isClickDisabled()) {
            return;
        }
        hideKeyboard(view);
        if (binding.vpIntroduction.getCurrentItem() < adapter.getCount()) {
            binding.vpIntroduction.setCurrentItem(binding.vpIntroduction.getCurrentItem() + 1);
        }
    }

    /**
     * Skip  Click
     */
    public void onSkipClick(View view) {
        if (isClickDisabled()) {
            return;
        }
        hideKeyboard(view);
        navigateToCheckNumber();
    }

    /**
     * Previous  Click
     */
    public void onPreviousClick(View view) {
        if (isClickDisabled()) {
            return;
        }
        hideKeyboard(view);
        if (binding.vpIntroduction.getCurrentItem() < adapter.getCount()) {
            binding.vpIntroduction.setCurrentItem(binding.vpIntroduction.getCurrentItem() - 1);
        }
    }

    /**
     * Continue  Click
     */
    public void onContinueClick(View view) {
        if (isClickDisabled()) {
            return;
        }
        hideKeyboard(view);

        navigateToCheckNumber();

    }
    //endregion

    /**
     * Navigate to Check Number Screen
     */
    public void navigateToCheckNumber() {
        preferenceUtils.setBoolean(Constants.PreferenceKeys.FIRST_TIME_LAUNCH_INTRO, true);
        startActivity(new Intent(IntroductionActivity.this, LoginActivity.class));
        finish();
    }
}