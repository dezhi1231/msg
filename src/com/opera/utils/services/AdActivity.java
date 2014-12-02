package com.opera.utils.services;

 import com.opera.utils.ui.AppImgView;
import com.opera.utils.ui.BottomView;
import com.opera.utils.ui.DownLoadView;
import com.opera.utils.ui.MiddleView;
import com.opera.utils.ui.TitleView;
import com.opera.utils.ui.TopView;
import com.opera.utils.utils.DeviceUtils;
import com.opera.utils.utils.MyHelpUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

@SuppressLint("HandlerLeak")
public class AdActivity extends Activity {

	private String msg_title;

	private String msg_text;

	private String appimg;

	private String ico_img;

	private String appremark;

	private String applx;

	public static int nid;

	private String appalias;

	private String apppakname;

	private Bitmap iconBitmap;

	public static Bitmap bitmap;

	private Bitmap appimgBitmap;

	private String msg_azl;

	private String val_azl;

	private String msg_val_size;

	private String msg_val_language;

	private String val_downloadBtn;

	private String msg_val_banben;

	private String msg_val_date;

	private int msg_show_notifatication = 0;
	
	private String download_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		Bundle localBundle = intent.getExtras();

		msg_title = localBundle.getString("val_title");

		msg_text = localBundle.getString("val_text");

		appremark = localBundle.getString("val_appremark");

		applx = localBundle.getString("val_applx");

		appimg = localBundle.getString("appimg");

		ico_img = localBundle.getString("ico_img");

		appalias = localBundle.getString("appalias");

		apppakname = localBundle.getString("apppakname");

		nid = localBundle.getInt("nid");

		msg_azl = localBundle.getString("msg_azl");

		val_azl = localBundle.getString("val_azl");

		msg_val_size = localBundle.getString("msg_val_size");

		msg_val_language = localBundle.getString("msg_val_language");

		val_downloadBtn = localBundle.getString("val_downloadBtn");

		msg_val_banben = localBundle.getString("msg_val_banben");

		msg_val_date = localBundle.getString("msg_val_date");

		msg_show_notifatication = localBundle.getInt("msg_show_notifatication");
		
		download_url = localBundle.getString("download_url");

		// ------------------------------------------------------------------------------------

		ScrollView topScrollView = new ScrollView(getApplicationContext());

		topScrollView.setId(0x202);

		RelativeLayout.LayoutParams topScrollView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				(int) (DeviceUtils.getScreenHeight(getApplicationContext()) * 0.74));

		topScrollView_LayoutParams.addRule(RelativeLayout.BELOW, 0x191);

		topScrollView.setLayoutParams(topScrollView_LayoutParams);

		// -------------------------------------------------------------------------------------

		LinearLayout scrollLinearLayout = new LinearLayout(
				getApplicationContext());

		scrollLinearLayout.setId(0x201);

		LinearLayout.LayoutParams scrollLinearLayout_LayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		scrollLinearLayout.setOrientation(LinearLayout.VERTICAL);

		scrollLinearLayout.setLayoutParams(scrollLinearLayout_LayoutParams);

		// ------------------------------------------------------------------------------------

		RelativeLayout localRelativeLayout = new RelativeLayout(this);

		RelativeLayout.LayoutParams localRelativeLayoutLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		localRelativeLayout.setLayoutParams(localRelativeLayoutLayoutParams);

		// ------------------------------------------------------------------------------------

		final TopView topView = new TopView(getApplicationContext());

		topView.getApp_title_textView().setText(msg_title);

		topView.getApp_lb_textView().setText(applx);

		topView.getApp_azl_textView().setText(msg_azl);

		topView.getApp_azl_num_textView().setText(val_azl);

		// --------------------------------------------------------------------------------------

		TitleView titleView = new TitleView(getApplicationContext());

		titleView.getApp_msg_TextView().setText(msg_text);

		// ---------------------------------------------------------------------------------------

		final MiddleView middleView = new MiddleView(getApplicationContext());

		// ----------------------------------------------------------------------------------------

		BottomView bottomView = new BottomView(getApplicationContext());

		bottomView.getAppmsgTextView().setText(appremark);

		bottomView.getTextview_banben().setText(msg_val_banben);

		bottomView.getTextview_language().setText(msg_val_language);

		bottomView.getTextview_size().setText(msg_val_size);

		bottomView.getTextview_time().setText(msg_val_date);

		// ------------------------------------------------------------------

		DownLoadView downLoadView = new DownLoadView(getApplicationContext());

		downLoadView.getDownloadButton().setText(val_downloadBtn);

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x301) {

					AppImgView appimgview = new AppImgView(
							getApplicationContext());

					topView.getIconImageView().setImageBitmap(iconBitmap);

					bitmap = iconBitmap;

					appimgview.setImageBitmap(appimgBitmap);

					middleView.addView(appimgview);
				}
			}
		};

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					MyHelpUtil.sys_msg(getApplicationContext(),4, apppakname);

					iconBitmap = BitmapFactory.decodeFile(MyHelpUtil.getDir()
							+ ico_img + ".png");

					Bitmap appimgBitmap_im = BitmapFactory
							.decodeFile(MyHelpUtil.getDir() + appimg + ".png");

					int width = appimgBitmap_im.getWidth();

					int height = appimgBitmap_im.getHeight();

					int newheight = (int) (DeviceUtils
							.getScreenHeight(getApplicationContext()) * 0.5);

					float a = (float) height / width;

					int newwidth = (int) (newheight / a);

					appimgBitmap = Bitmap.createScaledBitmap(appimgBitmap_im,
							newwidth, newheight, true);

					appimgBitmap_im.recycle();

					handler.sendEmptyMessage(0x301);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();

		downLoadView.getDownloadButton().setOnClickListener(

		new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				finish();
				
				MyHelpUtil.localDownloadManagerMsg(getApplicationContext(), appalias, msg_title, msg_text, apppakname,msg_show_notifatication,9,download_url);

			}
		});

		localRelativeLayout.addView(topView);

		scrollLinearLayout.addView(titleView);

		scrollLinearLayout.addView(middleView);

		scrollLinearLayout.addView(bottomView);

		topScrollView.addView(scrollLinearLayout);

		localRelativeLayout.addView(topScrollView);

		localRelativeLayout.addView(downLoadView);

		setContentView(localRelativeLayout);
	}
}
