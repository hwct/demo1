package com.example.demo1.adapter;

import java.util.List;

import com.example.demo1.R;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class TestRollPagerAdapter extends PagerAdapter{
	private Activity activity;
	
	private List<View> listViews;


	
	public TestRollPagerAdapter(FragmentActivity activity, List<View> listViews) {
		this.activity=activity;
		this.listViews=listViews;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(listViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		container.addView(listViews.get(position));
		return listViews.get(position);
		
	}

	@Override
	public int getCount() {
		return listViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

}
