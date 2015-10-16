package com.example.demo1.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BaseTabFrgamentAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment> fragmentsList;
	public BaseTabFrgamentAdapter(FragmentManager fm,ArrayList<Fragment> fragmentsList) {
		super(fm);
		this.fragmentsList=fragmentsList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

}
