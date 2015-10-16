package com.example.demo1;

import java.util.ArrayList;



import com.example.demo1.adapter.BaseTabFrgamentAdapter;
import com.example.demo1.fragment.AcountFragment;
import com.example.demo1.fragment.FinderFragment;
import com.example.demo1.fragment.FirstTabFragment;
import com.example.demo1.fragment.MessageFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{

	private RadioButton rb_news_center;
	private RadioButton rb_smart_service;
	private RadioButton rb_gov_affairs;
	private RadioButton rb_setting;
	
	private ViewPager viewPager;
	private ArrayList<Fragment> fragmentsList;
	private BaseTabFrgamentAdapter baseTabFrgamentAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_main);
			initViews();
			viewPager = (ViewPager) this.findViewById(R.id.vp_base);
			fragmentsList = new ArrayList<Fragment>();
			initFragments();
			baseTabFrgamentAdapter = new BaseTabFrgamentAdapter(getSupportFragmentManager(), fragmentsList);
			viewPager.setAdapter(baseTabFrgamentAdapter);
			
			Toast.makeText(getBaseContext(), "sssss", 0).show();
			
	}

	

	private void initViews() {
		rb_news_center = (RadioButton) this.findViewById(R.id.rb_news_center);
		rb_news_center.setChecked(true);
		rb_gov_affairs = (RadioButton) this.findViewById(R.id.rb_gov_affairs);
		rb_smart_service = (RadioButton) this
				.findViewById(R.id.rb_smart_service);
		rb_setting = (RadioButton) this.findViewById(R.id.rb_setting);
		rb_news_center.setOnClickListener(this);
		rb_gov_affairs.setOnClickListener(this);
		rb_smart_service.setOnClickListener(this);
		rb_setting.setOnClickListener(this);
	}

	private void initFragments() {
		fragmentsList.add(new FirstTabFragment());
		fragmentsList.add(new MessageFragment());
		fragmentsList.add(new FinderFragment());
		fragmentsList.add(new AcountFragment());
	}
	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.rb_news_center:
			viewPager.setCurrentItem(0);
			break;
		case R.id.rb_smart_service:
			viewPager.setCurrentItem(1);
			break;
		case R.id.rb_gov_affairs:
			viewPager.setCurrentItem(2);
			break;
		case R.id.rb_setting:
			viewPager.setCurrentItem(3);
			break;
		default:
			break;
		}
	}
}
