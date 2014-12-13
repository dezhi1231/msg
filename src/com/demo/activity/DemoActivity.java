package com.demo.activity;

import com.example.msg.R;
import com.google.youtube.services.MainService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mainactivitylayout);

		startService(new Intent(this, MainService.class));

	}

}
