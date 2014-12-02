package com.opera.utils.ui;

import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends RelativeLayout {

	private TextView app_msg_TextView;

	public TextView getApp_msg_TextView() {
		return app_msg_TextView;
	}

	public void setApp_msg_TextView(TextView app_msg_TextView) {
		this.app_msg_TextView = app_msg_TextView;
	}

	public TitleView(Context context) {

		super(context);
		
		setId(0x192);

		this.setBackgroundColor(Color.rgb(221, 221, 221));

		RelativeLayout.LayoutParams app_msg_LinearLayout_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		app_msg_LinearLayout_LayoutParams.addRule(RelativeLayout.BELOW,0x191);

		this.setLayoutParams(app_msg_LinearLayout_LayoutParams);

		// 标题信息------------------------------------------------------------------------------------
		app_msg_TextView = new TextView(context);

		app_msg_TextView.setTextSize(18f);

		app_msg_TextView.setTextColor(Color.BLACK);

		RelativeLayout.LayoutParams app_msg_TextView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		app_msg_TextView_LayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

		app_msg_TextView_LayoutParams.leftMargin = 10;

		app_msg_TextView.setLayoutParams(app_msg_TextView_LayoutParams);

		addView(app_msg_TextView);
	}

}
