package com.opera.utils.ui;

import com.opera.utils.utils.DeviceUtils;
import com.opera.utils.utils.MyHelpUtil;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DownLoadView extends RelativeLayout {

	private Button downloadButton;

	public Button getDownloadButton() {
		return downloadButton;
	}

	public void setDownloadButton(Button downloadButton) {
		this.downloadButton = downloadButton;
	}

	public DownLoadView(Context context) {
		super(context);

		this.setBackgroundColor(Color.rgb(229, 229, 229));

		RelativeLayout.LayoutParams DownLoadViewLayoutParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				(int) (DeviceUtils.getScreenHeight(context) * 0.07));

		DownLoadViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		DownLoadViewLayoutParams.addRule(RelativeLayout.BELOW,0x202);

		this.setLayoutParams(DownLoadViewLayoutParams);

		downloadButton = new Button(context);

		//--------
		downloadButton.setText("FREE DOWNLOAD");

		downloadButton.setTextColor(Color.WHITE);

		downloadButton.setTextSize(16f);
		
		downloadButton.setBackgroundDrawable(MyHelpUtil.getDrawable("img/105.gif", context));

		//downloadButton.setBackgroundColor(Color.rgb(0, 168, 0));

		RelativeLayout.LayoutParams downloadButtonLayoutParams = new RelativeLayout.LayoutParams(
				(int)(DeviceUtils.getScreenWidth(context)*0.75), (int) ((DeviceUtils.getScreenHeight(context) * 0.07)*0.91));

		downloadButtonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		downloadButtonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

		downloadButton.setLayoutParams(downloadButtonLayoutParams);

		this.addView(downloadButton);

	}

}
