package com.google.youtube.ui;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

public class AppImgView extends ImageView {

	public AppImgView(Context context) {
		
		
		super(context);
		
		this.setScaleType(ImageView.ScaleType.CENTER);

		LinearLayout.LayoutParams imagegrally_LinearLayout_LayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		imagegrally_LinearLayout_LayoutParams.gravity = Gravity.CENTER;

		this.setLayoutParams(imagegrally_LinearLayout_LayoutParams);

	}

}
