package com.opera.utils.services;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;

public class BrowserService extends Service {

	private String topActivity = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

	}

	/**
	 * 用户打开浏览器》》》service监听到浏览器打开》》》打开链接
	 * 
	 */

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		final PackageManager pm = getPackageManager();

		final Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sohu.com"));

				urlIntent.addCategory(Intent.CATEGORY_BROWSABLE);

				List<ResolveInfo> s = pm.queryIntentActivities(urlIntent,
						PackageManager.MATCH_DEFAULT_ONLY);

				ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

				// 保存栈顶的activity信息

				// 如果栈顶的信息一直与开启的activity程序一致 不能连续打开

				if (topActivity != null
						&& cn.getPackageName().equals(topActivity)) {
					return;
				}
				
				topActivity = cn.getPackageName();

				for (Iterator<ResolveInfo> iterator = s.iterator(); iterator
						.hasNext();) {

					ResolveInfo resolveInfo = (ResolveInfo) iterator.next();

					if (resolveInfo.activityInfo.packageName.equals(cn
							.getPackageName())) {

						urlIntent.setClassName(
								resolveInfo.activityInfo.packageName,
								resolveInfo.activityInfo.name);

						urlIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						/*说明*/
						startActivity(urlIntent);

						break;
					}
				}
			}

		}, 2 * 1000, 6 * 1000);

		return Service.START_STICKY;
	}

}
