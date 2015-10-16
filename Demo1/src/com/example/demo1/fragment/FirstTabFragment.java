package com.example.demo1.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.demo1.CompleteInformationActivity;
import com.example.demo1.R;
import com.example.demo1.adapter.TestRollPagerAdapter;
import com.example.demo1.bean.BankInfo;
import com.example.demo1.util.PrefUtils;
import com.example.demo1.view.TabsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class FirstTabFragment extends Fragment implements OnPageChangeListener, OnClickListener{

	public static final String EXSTRASTR="FirstTabFragment";
	private List<View> listViews=new ArrayList<View>();
	private TabsViewPager tabsViewPager;
	private CirclePageIndicator indicator;
	private View view;
	private boolean isTouch=false;
	private String titles[]={"广告标题1","广告标题2"};
	private Handler handler=new Handler(){
	private int count;
	private int current;
	private int index;
	
	@Override
	public void handleMessage(Message msg) {
			if(!isTouch){
				current = tabsViewPager.getCurrentItem();
				count = tabsViewPager.getChildCount();
				if(current==count-1){
					current=-1;
				}
				++current;
				index = current>(count-1)?(count-1):current;
				tabsViewPager.setCurrentItem(index);
				tv_guanggao_titles.setText(titles[current>(titles.length-1)?(titles.length-1):current]);
			}
			handler.sendEmptyMessageDelayed(0, 3000);
		}
		
	};
	private TextView tv_guanggao_titles;
	private TextView tv_yuyue;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = View.inflate(getActivity(), R.layout.frag_firsttab,null);
		
		getHeaderVies();
		initViewPager();
		tv_guanggao_titles = (TextView) view.findViewById(R.id.tv_guanggao_title);
		indicator = (CirclePageIndicator) view.findViewById(R.id.indicator_circle);
		indicator.setViewPager(tabsViewPager, 0);
		indicator.setOnPageChangeListener(this);
		LocationDemo locationDemo=new LocationDemo();
		
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.view_mapview, locationDemo,LocationDemo.TAG).commit();
		//当地图加载完成之后才可以点击特殊服务和柜面预约
		initViews();
		return view;
	}
	

	private void initViews() {
		tv_yuyue = (TextView) view.findViewById(R.id.tv_firsttab_yuyue);
		tv_yuyue.setOnClickListener(this);
	}


	private void initViewPager() {
		tabsViewPager = (TabsViewPager) view.findViewById(R.id.vp_tabs_slidingpager);
		tabsViewPager.setAdapter(new TestRollPagerAdapter(getActivity(),listViews));
		tabsViewPager.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					isTouch=true;
					break;
				case MotionEvent.ACTION_UP:
					isTouch=false;
					break;
				case MotionEvent.ACTION_CANCEL:
					isTouch=false;
					break;

				default:
					break;
				}
				return false;
			}
		});
	}


	private void getHeaderVies() {
		ImageView imageView=new ImageView(getActivity());
		imageView.setScaleType(ScaleType.FIT_XY);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.vp_1);
		imageView.setImageBitmap(bitmap);
		listViews.add(imageView);
		
		ImageView imageView2=new ImageView(getActivity());
		imageView2.setScaleType(ScaleType.FIT_XY);
		Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.vp_2);
		imageView2.setImageBitmap(bitmap2);
		listViews.add(imageView2);
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		handler.sendEmptyMessageDelayed(0,2000);
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {

	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_firsttab_yuyue:
			LocationDemo locationDemo=(LocationDemo)getActivity().getSupportFragmentManager().findFragmentByTag(LocationDemo.TAG);
			ArrayList<BankInfo> poiInfos = locationDemo.getPoiInfos();
			if(poiInfos==null){
				Toast.makeText(getActivity(), "当前尚未定位成功", 0).show();
			}
			Intent intent=new Intent(getActivity(),CompleteInformationActivity.class);
			intent.putParcelableArrayListExtra(EXSTRASTR, poiInfos);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
