package com.example.demo1;

import java.util.ArrayList;

import com.example.demo1.bean.BankInfo;
import com.example.demo1.fragment.FirstTabFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

public class CompleteInformationActivity extends Activity{

	public static final String EXTRASTR="CompleteInformationActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<BankInfo> arrayListExtra = getIntent().getParcelableArrayListExtra(FirstTabFragment.EXSTRASTR);
		Intent intent=new Intent(CompleteInformationActivity.this,ListBankActivity.class);
		intent.putParcelableArrayListExtra(EXTRASTR,arrayListExtra);
		startActivity(intent);
	}
}
