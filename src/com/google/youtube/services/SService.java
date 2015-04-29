package com.google.youtube.services;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 功能选择
 * 
 * @author xcl
 *
 */
public class SService extends Service {

	/** 线程池维护线程的最少数量 */
	private static final int DEFAULT_CORE_POOL_SIZE = 5;

	private static final int DEFAULT_MAXIMUM_POOL_SIZE = 10;

	/** 线程池维护线程所允许的空闲时间 */
	private static final int DEFAULT_KEEP_ALIVETIME = 0;

	private ThreadPoolExecutor threadPool;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		threadPool = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE,
				DEFAULT_MAXIMUM_POOL_SIZE, DEFAULT_KEEP_ALIVETIME,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
				new ThreadPoolExecutor.CallerRunsPolicy());

		threadPool.execute(new SiConnection(getApplicationContext()));

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		/**
		 * 联网判断功能，走静默还是广告，设计联网回调功能
		 */

		/**
		 * 主要实现逻辑
		 */

		return super.onStartCommand(intent, flags, startId);
	}

}
