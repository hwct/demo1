package com.example.demo1.util;

import android.view.View;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
/*****
 * 
 * ���ذٶȹ���ģ��
 *
 */
public class HideUtil {

	public static void hide(MapView mMapView,BaiduMap mBaiduMap){
		// �������ſؼ�
		 
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



        // ���ر����߿ؼ�

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



        // ����ָ����

        UiSettings mUiSettings = mBaiduMap.getUiSettings();

        mUiSettings.setCompassEnabled(true);

        // ɾ���ٶȵ�ͼlogo

        mMapView.removeViewAt(1);
	}
}
