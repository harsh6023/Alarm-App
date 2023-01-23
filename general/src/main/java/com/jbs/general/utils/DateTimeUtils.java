package com.jbs.general.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DateTimeUtils {

    private final Context context;

    @Inject
    DateTimeUtils(Context context) {
        this.context = context;
    }

    /**
     * Change Date Format
     *
     * @param time          - Date Time
     * @param inputPattern  - Input Pattern
     * @param outputPattern - Output Pattern
     * @return - Formatted Date Time
     */
    public String changeDateFormat(String time, String inputPattern, String outputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
