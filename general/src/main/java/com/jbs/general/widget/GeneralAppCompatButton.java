package com.jbs.general.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;


public class GeneralAppCompatButton extends AppCompatButton {

    public GeneralAppCompatButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            init(attrs);
    }

    public GeneralAppCompatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(attrs);
    }

    public GeneralAppCompatButton(Context context) {
        super(context);
        if (!isInEditMode())
            init(null);
    }

    protected void init(AttributeSet attrs) {
        setIncludeFontPadding(false);
    }

}
