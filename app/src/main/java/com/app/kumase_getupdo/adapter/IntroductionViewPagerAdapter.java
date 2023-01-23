package com.app.kumase_getupdo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.app.kumase_getupdo.R;
import com.bumptech.glide.Glide;
import com.jbs.general.model.response.introduction.IntroductionData;
import com.jbs.general.widget.GeneralAppCompatImageView;
import com.jbs.general.widget.GeneralAppCompatTextView;
import com.jbs.general.widget.GeneralLinearLayout;

import java.util.List;

public class IntroductionViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<IntroductionData> introductionData;

    public IntroductionViewPagerAdapter(Context context, List<IntroductionData> introductionData) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.introductionData = introductionData;
    }

    @Override
    public int getCount() {
        return introductionData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((GeneralLinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_introductions, container, false);
        IntroductionData introduction = introductionData.get(position);

        GeneralAppCompatImageView photoView = itemView.findViewById(R.id.iv_intro);
        GeneralAppCompatTextView tvTitle = itemView.findViewById(R.id.tv_title);
        GeneralAppCompatTextView tvDesc = itemView.findViewById(R.id.tv_description);

        Glide.with(this.mContext)
                .load(introduction.getIntroImage())
                .into(photoView);
        tvTitle.setText(introduction.getTitle());
        tvDesc.setText(introduction.getDescription());
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((GeneralLinearLayout) object);
    }

}


