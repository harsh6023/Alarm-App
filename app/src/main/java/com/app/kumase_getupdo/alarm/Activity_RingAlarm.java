
package com.app.kumase_getupdo.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.app.kumase_getupdo.R;
import com.jbs.general.utils.Constants;

import java.time.LocalTime;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Activity_RingAlarm extends AppCompatActivity implements View.OnClickListener {

	private final Launcher mLauncher = new Launcher();
	private Handler mSafeHandler = new Handler();

	private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (Objects.equals(intent.getAction(), ConstantsAndStatics.ACTION_DESTROY_RING_ALARM_ACTIVITY)) {
				finish();
			}
		}
	};

	private class Launcher implements Runnable {
		@Override
		public void run() {
			Intent intent1 = new Intent(ConstantsAndStatics.ACTION_CANCEL_ALARM);
			sendBroadcast(intent1);
			finish();
		}
	}
	//-----------------------------------------------------------------------------------------------------

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
			setTurnScreenOn(true);
			setShowWhenLocked(true);
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ringalarm);

		TextView alarmTimeTextView = findViewById(R.id.alarmTimeTextView2);
		TextView alarmMessageTextView = findViewById(R.id.alarmmessageTextView);
		Button snoozeButton = findViewById(R.id.snoozeButton);
		ImageButton cancelButton = findViewById(R.id.cancelButton);

		LocalTime localTime = LocalTime.now();

		if (DateFormat.is24HourFormat(this)) {
			alarmTimeTextView.setText(getResources().getString(R.string.time_24hour,
					localTime.getHour(), localTime.getMinute()));
		} else {
			String amPm = localTime.getHour() < 12 ? "AM" : "PM";

			if ((localTime.getHour() <= 12) && (localTime.getHour() > 0)) {

				alarmTimeTextView.setText(getResources().getString(R.string.time_12hour,
						localTime.getHour(), localTime.getMinute(), amPm));

			} else if (localTime.getHour() > 12 && localTime.getHour() <= 23) {

				alarmTimeTextView.setText(getResources().getString(R.string.time_12hour,
						localTime.getHour() - 12, localTime.getMinute(), amPm));

			} else {
				alarmTimeTextView.setText(getResources().getString(R.string.time_12hour,
						localTime.getHour() + 12, localTime.getMinute(), amPm));
			}
		}

		// Display the alarm message. Additionally, if the screen size is small, change the text size to 15sp.
		if (getIntent().getExtras() != null) {
			String message = getIntent().getExtras().getString(ConstantsAndStatics.BUNDLE_KEY_ALARM_MESSAGE, null);
			if (message != null) {
				int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
				if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
					alarmMessageTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
				}
			}
			alarmMessageTextView.setText(message != null ? message : getString(R.string.alarmMessage));
		}

		snoozeButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConstantsAndStatics.ACTION_DESTROY_RING_ALARM_ACTIVITY);
		registerReceiver(broadcastReceiver, intentFilter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSafeHandler.postDelayed(mLauncher, getIntent().getExtras().getInt("ALARM_SEC", 3000));
	}

	//--------------------------------------------------------------------------------------------------

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mSafeHandler.removeCallbacks(mLauncher);
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onStop() {
		mSafeHandler.removeCallbacks(mLauncher);
		super.onStop();
	}

	//--------------------------------------------------------------------------------------------------

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.snoozeButton) {
			Intent intent = new Intent(ConstantsAndStatics.ACTION_SNOOZE_ALARM);
			sendBroadcast(intent);
			finish();
		} else if (view.getId() == R.id.cancelButton) {
			mSafeHandler.removeCallbacks(mLauncher);
			Intent intent1 = new Intent(ConstantsAndStatics.ACTION_CANCEL_ALARM);
			sendBroadcast(intent1);
			finish();
		}
	}

}
