package com.example.demo1.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TabsViewPager extends ViewPager {

	private float downx;
	private float downy;
	public TabsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			downx = ev.getRawX();
			 downy= ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			float movex = ev.getRawX();
			float movey = ev.getRawY();
			if(Math.abs(downx-movex)>Math.abs(downy-movey)){
				//x���ƶ�
				if((downx-movex)<0){
					//ҳ�����󻬶�,��������һ��ҳ��ʱ��Ҫ�����������ˡ������ɸ��ؼ�����
					if(getCurrentItem()==0){
						getParent().requestDisallowInterceptTouchEvent(false);
					}else{
						getParent().requestDisallowInterceptTouchEvent(true);
					}
					
				}else{
					if(getCurrentItem()==getChildCount()-1){
						getParent().requestDisallowInterceptTouchEvent(false);
					}else{
						getParent().requestDisallowInterceptTouchEvent(true);
					}
				}
				
			}else{
				//y���ƶ����������Լ�����
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
