package com.app.kumase_getupdo.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.kumase_getupdo.R;
import com.jbs.general.model.response.alarms.AlarmsApiData;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllAlarmsAdapter extends RecyclerView.Adapter<AllAlarmsAdapter.myViewHolder> {

    private Context context;
    private List<AlarmsApiData> alarmsApiDataList;
    private OnItemClickListener mListener;

    public AllAlarmsAdapter(Context context, List<AlarmsApiData> alarmsApiDataList) {
        this.context = context;
        this.alarmsApiDataList = alarmsApiDataList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerviewrow_alarmslist, parent, false);
        return new myViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        AlarmsApiData alarmData = alarmsApiDataList.get(position);

        if (alarmData.getStatus() == 1) {
            holder.alarmOnOffImgBtn.setImageResource(R.drawable.ic_alarm_on);
        } else {
            holder.alarmOnOffImgBtn.setImageResource(R.drawable.ic_alarm_off);
        }

        String[] times = alarmData.getTime().split(":");
        /*if (DateFormat.is24HourFormat(context)) {*/
            holder.alarmTimeTextView.setText(context.getResources().getString(R.string.time_24hour,
                    Integer.parseInt(times[0]),
                    Integer.parseInt(times[1])));
       /* }else {
            String amPm =  Integer.parseInt(times[0]) < 12 ? "AM" : "PM";

            int alarmHour;

            if (( Integer.parseInt(times[0]) > 0) && ( Integer.parseInt(times[0]) <= 12)) {
                alarmHour =  Integer.parseInt(times[0]);
            } else if ( Integer.parseInt(times[0]) > 12 &&  Integer.parseInt(times[0]) <= 23) {
                alarmHour =  Integer.parseInt(times[0]) - 12;
            } else {
                alarmHour =  Integer.parseInt(times[0]) + 12;
            }

            holder.alarmTimeTextView.setText(context.getResources().getString(R.string.time_12hour, alarmHour,
                    Integer.parseInt(times[1]), amPm));
        }*/

        //holder.alarmDateTextView.setText(new DateFormatSymbols().getShortWeekdays()[alarmData.getDay()]);
        /*ArrayList<Integer> repeatDays = new ArrayList<>();
        repeatDays.add(alarmData.getDay());
        if (repeatDays != null) {
            Collections.sort(repeatDays);
        }
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < repeatDays.size(); i++) {
            int day = (repeatDays.get(i) + 1) > 7 ? 1 : (repeatDays.get(i) + 1);
            str.append(new DateFormatSymbols().getShortWeekdays()[day]);
            if (i < repeatDays.size() - 1) {
                str.append(", ");
            }
        }*/
        holder.alarmDateTextView.setText(alarmData.getDate());
        holder.alarmTypeTextView.setText(context.getResources()
                    .getString(R.string.snoozeOptionsTV_snoozeOn,
                            alarmData.getSound_time_interval(), alarmData.getSound_frequency()));

        String[] alarmsName = alarmData.getName() == null ? null : alarmData.getName().split("/");

        holder.alarmMessageTextView.setText(alarmsName[0] == null ? "" : alarmsName[0]);

        if(alarmsName.length >= 2 ) {
            holder.alarmMessageTextView_2.setText(alarmsName[1] == null ? "" : alarmsName[1]);
        }else{
            holder.alarmDateTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return alarmsApiDataList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public ImageButton alarmOnOffImgBtn, alarmDeleteBtn;
        public TextView alarmTimeTextView, alarmDateTextView, alarmTypeTextView, alarmMessageTextView,alarmMessageTextView_2;
        public CardView alarmCardView;
        public myViewHolder(@NonNull View view, final OnItemClickListener listener) {
            super(view);
            alarmOnOffImgBtn = view.findViewById(R.id.alarmOnOffImgBtn);
            alarmTimeTextView = view.findViewById(R.id.alarmTimeTextView);
            alarmDateTextView = view.findViewById(R.id.alarmDateTextView);
            alarmTypeTextView = view.findViewById(R.id.alarmTypeTextView);
            alarmMessageTextView = view.findViewById(R.id.recyclerView_alarmMessageTextView);
            alarmMessageTextView_2 = view.findViewById(R.id.recyclerView_alarmMessageTextView_2);
            alarmDeleteBtn = view.findViewById(R.id.alarmDeleteBtn);
            alarmCardView = view.findViewById(R.id.alarmCardView);

            alarmOnOffImgBtn.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onOnOffButtonClick(position);
                    }
                }
            });
            alarmCardView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClicked(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onOnOffButtonClick(int position);

        void onItemClicked(int position);

    }
}
