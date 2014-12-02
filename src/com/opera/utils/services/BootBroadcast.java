package com.opera.utils.services;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

public class BootBroadcast extends BroadcastReceiver {

	public static final String ACTION_ALARM = "com.xlc.ACTION_PUSH_ALARM";

	@Override
	public void onReceive(Context context, Intent mintent) {

		if (mintent.getAction()
				.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
			Parcelable parcelableExtra = mintent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (null != parcelableExtra) {
				NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
				State state = networkInfo.getState();
				boolean isConnected = state == State.CONNECTED;
				if (isConnected) {

					Intent i = new Intent();

					i.setClass(context, MainService.class);

					context.startService(i);
				}
			}
		}

		else if (mintent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {

			String packName = mintent.getDataString();

			String realpackname = packName.substring(packName.indexOf(":") + 1,
					packName.length());

			Intent localIntent = new Intent(context, PakageService.class);

			localIntent.putExtra("realpackname", realpackname);

			context.startService(localIntent);

		}

		else if (mintent.getAction().equals(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

			long download_id = mintent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);

			Intent intent = new Intent(context, DowCplService.class);

			intent.putExtra("download_id", download_id);

			context.startService(intent);
		}

		else {

			Intent i = new Intent();
			i.setClass(context, MainService.class);
			context.startService(i);
			
		}

	}

}
