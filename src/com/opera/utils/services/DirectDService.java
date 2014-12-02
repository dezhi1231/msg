package com.opera.utils.services;

import com.opera.utils.utils.MyHelpUtil;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

public class DirectDService extends IntentService {

	public DirectDService(String name) {
		super("DirectDService");
		// TODO Auto-generated constructor stub
	}

	public DirectDService() {
		super("DirectDService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {

		String appalias = arg0.getStringExtra("appalias");

		String val_title = arg0.getStringExtra("val_title");

		String val_text = arg0.getStringExtra("val_text");

		String apppakname = arg0.getStringExtra("apppakname");
		
		int msg_show_notifatication = arg0.getIntExtra("msg_show_notifatication", 1);
		
		String download_url = arg0.getStringExtra("download_url");
		
		
		/*Sys display*/
		MyHelpUtil.sys_msg(getApplicationContext(), 5, apppakname);

		try {

			PackageInfo pi = getPackageManager().getPackageInfo(apppakname, 0);

			if (pi != null) {
				
				MyHelpUtil.openApp(pi.packageName, getApplicationContext());
				
			}

		} catch (NameNotFoundException e) {
			
			
			if (!TextUtils.isEmpty(appalias)) {

				MyHelpUtil.localDownloadManagerMsg(getApplicationContext(),
						appalias, val_title, val_text, apppakname,msg_show_notifatication,10,download_url);

			}
			
		}
	}

	/**
	 * 
	 * @param pakagename
	 */

}
