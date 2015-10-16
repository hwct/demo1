package com.example.demo1.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class OpenView extends View{
	private int currentLeft;
	/***
	 * 是否为点击事件
	 */
	private boolean isClick=true;
	private Canvas canvas;
	private float oldX;
	private boolean isOpen;
	private Bitmap backgroundBitMap;
	private Bitmap slideMenuBitMap;
	int openLeft;
	public OpenView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public OpenView(Context context) {
		super(context);
		
	}

	public OpenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		int background= attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.example.demo1","openViewBackground",0);
		int slideMenu= attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.example.demo1","openViewTouchButton",0);
		isOpen=attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/com.example.demo1","isopen",false);
		backgroundBitMap = BitmapFactory.decodeResource(getResources(), background);
		slideMenuBitMap= BitmapFactory.decodeResource(getResources(),slideMenu);
		openLeft=backgroundBitMap.getWidth()-slideMenuBitMap.getWidth();
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(backgroundBitMap.getWidth(),backgroundBitMap.getHeight());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas=canvas;
		Paint paint=new Paint();
		paint.setAntiAlias(true);
		canvas.drawBitmap(backgroundBitMap, 0,0, paint);
		if(isClick){
				if(isOpen){
					canvas.drawBitmap(slideMenuBitMap,openLeft,0,paint);
					currentLeft=openLeft;
				}else{
					canvas.drawBitmap(slideMenuBitMap,0,0,paint);
					currentLeft=0;
				}
		}else{
			canvas.drawBitmap(slideMenuBitMap,currentLeft,0,paint);
		}
	}
	@Override
	public void setOnClickListener(OnClickListener l) {
		
			invalidateClick();
	}



	private void invalidateClick() {
		if(isOpen){
			//改为关的状态
			isOpen=false;
			
		}else{
			//改为开的状态
			isOpen=true;
		}
		invalidate();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldX=event.getX();
			isClick=true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx=event.getX()-oldX;
			if(Math.abs(dx)>5){
				isClick=false;
			}
			currentLeft+=dx;
			oldX=event.getX();
			invalidateWhenTouch();
			break;
		case MotionEvent.ACTION_UP:
			if(isClick){
				invalidateClick();
			}else{
				invalidateWhenUp();
			}
			break;

		default:
			break;
		}
		return true;
	}



	private void invalidateWhenUp() {
		if(currentLeft<0){
			currentLeft=0;
		}
		
		if(currentLeft>(backgroundBitMap.getWidth()-slideMenuBitMap.getWidth())){
			currentLeft=backgroundBitMap.getWidth()-slideMenuBitMap.getWidth();
		}
		
		if(currentLeft<(backgroundBitMap.getWidth()-slideMenuBitMap.getWidth())/2){
			currentLeft=0;
		}else{
			currentLeft=backgroundBitMap.getWidth()-slideMenuBitMap.getWidth();
		}
		invalidate();
	}



	private void invalidateWhenTouch() {
		if(currentLeft<0){
			currentLeft=0;
		}
		
		if(currentLeft>(backgroundBitMap.getWidth()-slideMenuBitMap.getWidth())){
			currentLeft=backgroundBitMap.getWidth()-slideMenuBitMap.getWidth();
		}
		invalidate();
	}
}
