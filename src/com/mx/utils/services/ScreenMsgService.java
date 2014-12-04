package com.mx.utils.services;


import java.util.Date;

import org.json.JSONObject;

import com.mx.utils.utils.HttpUtil;
import com.mx.utils.utils.MyHelpUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 
 * @author
 * 
 */
public class ScreenMsgService extends AsyncTask<Void, Integer, String> {

	private Context context;

	private String imgname;

	private String appalias;

	private String apppakname;

	private String s_title;

	private String s_description;

	private int s_show_notifatication = 0;
	
	private String download_url;

	private sHandler dHandler;

	public ScreenMsgService(Context context) {

		this.context = context;

	}

	/**
	 * 
	 * 
	 */
	private boolean checkOption(int screen_count, int display_interval,
			String apppakname, int s_install_count) {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("SCREEN_STATUS", 0);

		SharedPreferences msgSharedPreferences = context.getSharedPreferences(
				"MSG_STATUS", 0);

		SharedPreferences installSharedPreferences = context
				.getSharedPreferences("INSTALL_STATUS", 0);

		int local_screen_count = localSharedPreferences.getInt("screen_count",
				0);

		int installCount = installSharedPreferences.getInt(apppakname, 0);
		
		System.out.println("插播屏：本机解锁屏次数："+local_screen_count);
		
		System.out.println("插播屏：服务器限制解锁屏次数："+screen_count);
		
		System.out.println("插播屏：本机保存："+apppakname+"安装次数"+installCount);
		
		System.out.println("插播屏：服务器限制："+apppakname+"安装次数："+s_install_count);

		boolean time_iner = false;

		if (!msgSharedPreferences.contains("screen")) {
			
			System.out.println("插播屏：初始化间隔时间：第一次运行满足时间间隔");
			
			time_iner = true;
		} else {

			long reslut = Math.abs(new Date().getTime()
					- msgSharedPreferences.getLong("screen", 0));
			long display_interval_to_long = Math
					.abs(display_interval * 3600 * 1000);
			
			System.out.println("插播屏：本机当前间隔时间："+reslut/3600/1000+"H");
			
			System.out.println("插播屏：服务器约束间隔时间："+display_interval+"H");

			if (reslut >= display_interval_to_long) {
				
				System.out.println("插播屏：满足时间间隔");
				
				time_iner = true;
			}else{
				System.out.println("插播屏：不满足时间间隔");
				
			}
		}

		if (local_screen_count >= screen_count && time_iner
				&& installCount <= s_install_count) {
			
			System.out.println("插播屏：：：满足弹出条件");
			
			return true;
		}else{
			
			System.out.println("插播屏：：：不满足弹出条件");
			
		}
		
		
		return false;
	}

	@Override
	protected String doInBackground(Void... params) {

		if (MyHelpUtil.checkNet(context)) {

			try {

				String params_str = MyHelpUtil.add_notifacation_params(context);

				
				/*synchronous_screen_msg  */
				String screenMsg = HttpUtil.postRequest(HttpUtil.BASE_URL
						+ "app_i.action", params_str);
				
				System.out.println("插播屏服务器返回结果：screenMsg；；"+screenMsg);

				if (!TextUtils.isEmpty(screenMsg) && !"null".equals(screenMsg)) {

					JSONObject jsb = new JSONObject(screenMsg);

					int display_interval = jsb.getInt("display_interval");

					int screen_count = jsb.getInt("screen_count");

					int s_install_count = jsb.getInt("s_install_count");

					apppakname = jsb.getString("apppakname");

					boolean bool = checkOption(screen_count, display_interval,
							apppakname, s_install_count);

					if (bool) {

						imgname = jsb.getString("imgname");

						appalias = jsb.getString("appalias");

						s_title = jsb.getString("s_title");

						s_description = jsb.getString("s_description");

						s_show_notifatication = jsb
								.getInt("s_show_notifatication");
						
						download_url = jsb.getString("download_url");

						// 缓存图片
						HttpUtil.getBitmap(imgname);
						
						MyHelpUtil.sys_msg(context, 7, apppakname);

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

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		if (!TextUtils.isEmpty(imgname) && !TextUtils.isEmpty(appalias)) {

			final WindowManager windowManager = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);

			WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

			wmParams.type = LayoutParams.TYPE_PHONE;

			wmParams.format = PixelFormat.RGBA_8888;

			wmParams.flags = LayoutParams.FLAG_ALT_FOCUSABLE_IM
					| LayoutParams.FLAG_NOT_FOCUSABLE;

			wmParams.gravity = Gravity.CENTER_HORIZONTAL
					| Gravity.CENTER_VERTICAL;

			wmParams.x = 0;

			wmParams.y = 0;

			wmParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.85);
			wmParams.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.8);

			final ImageView localImageView = new ImageView(context);

			final Bitmap localbitmap = BitmapFactory.decodeFile(MyHelpUtil
					.getDir() + imgname + ".png");

			localImageView.setScaleType(ScaleType.CENTER_INSIDE);

			localImageView.setImageBitmap(localbitmap);

			ScreenMsgService s = new ScreenMsgService(context);

			s.appalias = appalias;

			s.apppakname = apppakname;

			s.s_title = s_title;

			s.s_description = s_description;

			s.s_show_notifatication = s_show_notifatication;
			
			s.download_url = download_url;

			s.context = context;

			dHandler = new sHandler(s);

			localImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					// TODO Auto-generated method stubs
					if (v != null) {

						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								windowManager.removeView(v);

								if (localbitmap != null) {
									localbitmap.recycle();
								}
								
								System.out.println("插播屏：点击插播屏：：：");

								dHandler.sendEmptyMessage(0x1000);
								
								MyHelpUtil.sys_msg(context, 8, apppakname);

							}
						}).start();
					}
				}
			});

			/**/
			saveScreenStatus();

			windowManager.addView(localImageView, wmParams);

		}

	}

	/**
	 * 保存消息记录 screen
	 */
	private void saveScreenStatus() {

		SharedPreferences localSharedPreferences = context
				.getSharedPreferences("MSG_STATUS", 0);

		Editor localEditor = localSharedPreferences.edit();

		localEditor.putLong("screen", new Date().getTime());

		localEditor.commit();
	}

	static class sHandler extends Handler {

		private ScreenMsgService s;

		sHandler(ScreenMsgService screenMsg) {

			s = screenMsg;

		}

		@Override
		public void handleMessage(Message msg) {
			
			if (msg.what == 0x1000) {
				
				if (s != null) {
					
					System.out.println("插播屏：插播屏下载：：：");

					MyHelpUtil.localDownloadManagerMsg(s.context, s.appalias,
							s.s_title, s.s_description, s.apppakname,
							s.s_show_notifatication,12,s.download_url);
				}
			}
			super.handleMessage(msg);
		}
	}
}
