package com.example.demo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IndicatorActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator);
		Intent intent=new Intent(this,MainActivity.class);
		startActivity(intent);
		this.finish();
	}
}
