package com.opera.utils.ui;

import com.opera.utils.utils.DeviceUtils;

import android.content.Context;
import android.graphics.Color;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

public class MiddleView extends HorizontalScrollView {

	public MiddleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setId(0x193);

		this.setHorizontalScrollBarEnabled(true);

		this.setSmoothScrollingEnabled(true);
		
		this.setFillViewport(true);

		this.setBackgroundColor(Color.WHITE);

		RelativeLayout.LayoutParams localHorizontalScrollView_LayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, (int) (((int)DeviceUtils.getScreenHeight(context))*0.5));

		localHorizontalScrollView_LayoutParams.addRule(RelativeLayout.BELOW,
				0x192);

		this.setLayoutParams(localHorizontalScrollView_LayoutParams);
	}

}
