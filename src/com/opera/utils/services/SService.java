package com.opera.utils.services;



import com.opera.utils.utils.MainAsyncTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		MainAsyncTask mainAsyncTask = new MainAsyncTask(getApplicationContext());

		mainAsyncTask.execute();

		return super.onStartCommand(intent, flags, startId);
	}

}
