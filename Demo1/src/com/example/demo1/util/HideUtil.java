package com.example.demo1.util;

import android.view.View;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
/*****
 * 
 * 隐藏百度工具模板
 *
 */
public class HideUtil {

	public static void hide(MapView mMapView,BaiduMap mBaiduMap){
		// 隐藏缩放控件
		 
        int childCount = mMapView.getChildCount();

        View zoom = null;

        for (int i = 0; i < childCount; i++) {

                View child = mMapView.getChildAt(i);

                if (child instanceof ZoomControls) {

                        zoom = child;

                        break;

                }

        }

        zoom.setVisibility(View.GONE);



        // 隐藏比例尺控件

        int count = mMapView.getChildCount();

        View scale = null;

        for (int i = 0; i < count; i++) {

                View child = mMapView.getChildAt(i);

                if (child instanceof ZoomControls) {

                        scale = child;

                        break;

                }

        }

        scale.setVisibility(View.GONE);



        // 隐藏指南针

        UiSettings mUiSettings = mBaiduMap.getUiSettings();

        mUiSettings.setCompassEnabled(true);

        // 删除百度地图logo

        mMapView.removeViewAt(1);
	}
}
