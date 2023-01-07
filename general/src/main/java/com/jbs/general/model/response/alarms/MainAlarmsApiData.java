package com.jbs.general.model.response.alarms;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainAlarmsApiData implements Parcelable {
    @SerializedName("alarm")
    @Expose
    private AlarmsApiData alarm;
    @SerializedName("alarm_list")
    @Expose
    private List<AlarmsApiData> alarmsList;

    protected MainAlarmsApiData(Parcel in) {
        alarm = in.readParcelable(AlarmsApiData.class.getClassLoader());
        alarmsList = in.createTypedArrayList(AlarmsApiData.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(alarm, flags);
        dest.writeTypedList(alarmsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainAlarmsApiData> CREATOR = new Creator<MainAlarmsApiData>() {
        @Override
        public MainAlarmsApiData createFromParcel(Parcel in) {
            return new MainAlarmsApiData(in);
        }

        @Override
        public MainAlarmsApiData[] newArray(int size) {
            return new MainAlarmsApiData[size];
        }
    };

    public AlarmsApiData getAlarm() {
        return alarm;
    }

    public List<AlarmsApiData> getAlarmsList() {
        return alarmsList;
    }
}
