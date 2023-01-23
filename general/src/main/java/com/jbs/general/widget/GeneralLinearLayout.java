package com.jbs.general.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class GeneralLinearLayout extends LinearLayout {

    public GeneralLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init(attrs);
    }

    public GeneralLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(attrs);
    }

    public GeneralLinearLayout(Context context) {
        super(context);
        if (!isInEditMode())
            init(null);
    }

    protected void init(AttributeSet attrs) {
        //Empty
    }
}
