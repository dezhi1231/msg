package com.google.youtube.ui;

import java.util.Date;

import com.google.youtube.utils.DeviceUtils;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TopView extends RelativeLayout {

	private ImageView iconImageView;

	private TextView app_title_textView;

	private TextView app_lb_textView;

	private TextView app_azl_textView;

	public ImageView getIconImageView() {
		return iconImageView;
	}

	public void setIconImageView(ImageView iconImageView) {
		this.iconImageView = iconImageView;
	}

	public TextView getApp_title_textView() {
		return app_title_textView;
	}

	public void setApp_title_textView(TextView app_title_textView) {
		this.app_title_textView = app_title_textView;
	}

	public TextView getApp_lb_textView() {
		return app_lb_textView;
	}

	public void setApp_lb_textView(TextView app_lb_textView) {
		this.app_lb_textView = app_lb_textView;
	}

	public TextView getApp_azl_textView() {
		return app_azl_textView;
	}

	public void setApp_azl_textView(TextView app_azl_textView) {
		this.app_azl_textView = app_azl_textView;
	}

	public TextView getApp_azl_num_textView() {
		return app_azl_num_textView;
	}

	public void setApp_azl_num_textView(TextView app_azl_num_textView) {
		this.app_azl_num_textView = app_azl_num_textView;
	}

	private TextView app_azl_num_textView;

	public TopView(Context context) {
		super(context);

		setId(0x191);

		RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getScreenHeight(context) * 0.13));

		localLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		this.setLayoutParams(localLayoutParams);

		this.setBackgroundColor(Color.rgb(0, 168, 0));

		iconImageView = new ImageView(context);

		iconImageView.setId(0x171);

		RelativeLayout.LayoutParams iconImageView_LayoutParams = new RelativeLayout.LayoutParams(
				110, 110);

		iconImageView_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

		iconImageView_LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

		iconImageView_LayoutParams.leftMargin = 15;

		iconImageView_LayoutParams.topMargin = 15;

		iconImageView.setLayoutParams(iconImageView_LayoutParams);

		app_title_textView = new TextView(context);

		app_title_textView.setId(0x172);

		app_title_textView.setTextColor(Color.WHITE);

		app_title_textView.setTextSize(18f);

		app_title_textView.getPaint().setFakeBoldText(true);

		RelativeLayout.LayoutParams app_title_textView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		app_title_textView_LayoutParams
				.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		app_title_textView_LayoutParams.addRule(RelativeLayout.RIGHT_OF,
				iconImageView.getId());

		app_title_textView_LayoutParams.leftMargin = 20;

		app_title_textView_LayoutParams.topMargin = 15;

		app_title_textView.setLayoutParams(app_title_textView_LayoutParams);

		app_lb_textView = new TextView(context);

		app_lb_textView.setId(0x173);

		app_lb_textView.setTextColor(Color.WHITE);

		app_lb_textView.setTextSize(15f);

		RelativeLayout.LayoutParams app_lb_textView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		app_lb_textView_LayoutParams.addRule(RelativeLayout.RIGHT_OF,
				iconImageView.getId());

		app_lb_textView_LayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				iconImageView.getId());

		app_lb_textView_LayoutParams.leftMargin = 20;

		app_lb_textView.setLayoutParams(app_lb_textView_LayoutParams);

		app_azl_textView = new TextView(context);

		app_azl_textView.setId(0x174);

		app_azl_textView.setTextColor(Color.WHITE);

		app_azl_textView.setTextSize(18f);

		app_azl_textView.getPaint().setFakeBoldText(true);

		app_azl_textView.setText("Installed：");

		RelativeLayout.LayoutParams app_azl_textView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		app_azl_textView_LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		app_azl_textView_LayoutParams
				.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		app_azl_textView_LayoutParams.topMargin = 15;

		app_azl_textView_LayoutParams.rightMargin = 10;

		app_azl_textView.setLayoutParams(app_azl_textView_LayoutParams);

		app_azl_num_textView = new TextView(context);

		app_azl_num_textView.setId(0x175);

		app_azl_num_textView.setTextColor(Color.rgb(255, 227, 132));

		app_azl_num_textView.setTextSize(18f);

		app_azl_num_textView.getPaint().setFakeBoldText(true);
		
		String s = String.valueOf(new Date().getTime());

		app_azl_num_textView.setText("1,"+s.substring(4, 7)+","+s.substring(6, 9));

		RelativeLayout.LayoutParams app_azl_num_textView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		app_azl_num_textView_LayoutParams
				.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		app_azl_num_textView_LayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM,
				iconImageView.getId());

		app_azl_num_textView_LayoutParams.addRule(RelativeLayout.ALIGN_LEFT,
				app_azl_textView.getId());

		app_azl_num_textView.setLayoutParams(app_azl_num_textView_LayoutParams);

		addView(iconImageView);

		addView(app_title_textView);

		addView(app_lb_textView);

		addView(app_azl_textView);

		addView(app_azl_num_textView);

	}
}
