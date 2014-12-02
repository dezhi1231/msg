package com.opera.utils.services;

import com.opera.utils.utils.MyHelpUtil;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;

public class DirectDLService extends IntentService {

	public DirectDLService(String name) {
		super("DirectDLService");
		// TODO Auto-generated constructor stub
	}

	public DirectDLService() {
		super("DirectDLService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		String appalias = arg0.getStringExtra("appalias");

		String val_title = arg0.getStringExtra("val_title");

		String val_text = arg0.getStringExtra("val_text");

		String apppakname = arg0.getStringExtra("apppakname");
		
		String download_url = arg0.getStringExtra("download_url");

		int msg_show_notifatication = arg0.getIntExtra(
				"msg_show_notifatication", 1);
		
		/*Sys display*/
		MyHelpUtil.sys_msg(getApplicationContext(), 6, apppakname);

		if (!TextUtils.isEmpty(appalias)) {

			MyHelpUtil.localDownloadManagerMsg(getApplicationContext(),
					appalias, val_title, val_text, apppakname,
					msg_show_notifatication,11,download_url);

		}

	}

}
