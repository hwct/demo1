package com.example.demo1.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.demo1.MarkerOptionsDemo;
import com.example.demo1.R;
import com.example.demo1.bean.BankInfo;
import com.example.demo1.util.HideUtil;
import com.example.demo1.util.PrefUtils;
/**
 * 定位
 * @author hwct
 * 
 */
public class LocationDemo extends Fragment implements OnMapClickListener{
	public static final String TAG="locationFrgament";
	
	public LocationClient mLocationClient;
	public BDLocationListener myListener;
	private BitmapDescriptor geo;
	private PoiSearch poiSearch;
	protected MapView mMapView;
	protected BaiduMap baiduMap;
	private View view;
	
	//public String poistrs[]=new String[1000];
	public ArrayList<BankInfo> bankInfos=new ArrayList<BankInfo>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		view = View.inflate(getActivity(), R.layout.common_location, null);
		initBaiduMap();	
		HideUtil.hide(mMapView, baiduMap);
		lacate();
		mLocationClient.start();
		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	

	private void initBaiduMap() {
		mMapView = (MapView) view.findViewById(R.id.mapview);
		baiduMap = mMapView.getMap();
		baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
		baiduMap.setOnMapClickListener(this);
	}
	
	private void lacate() {
		mLocationClient = new LocationClient(getActivity().getApplicationContext());
		myListener = new MyListener();
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
		geo = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_geo);
		MyLocationConfiguration configuration = new MyLocationConfiguration(
				MyLocationConfiguration.LocationMode.FOLLOWING, true, geo);
		baiduMap.setMyLocationConfigeration(configuration);// 设置定位显示的模式
		baiduMap.setMyLocationEnabled(true);// 打开定位图层
	}
	
	private LatLng myLatlng;

	@Override
	public void onStop() {
		super.onStop();
		mLocationClient.stop();
	}


	class MyListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation result) {
			if (result != null) {
				MyLocationData data = new MyLocationData.Builder()
						.latitude(result.getLatitude())
						.longitude(result.getLongitude()).build();
				if(data!=null&&baiduMap!=null){
					baiduMap.setMyLocationData(data);
					myLatlng=new LatLng(data.latitude,data.longitude);
					//poistrs[0]=myLatlng.latitude+":"+myLatlng.longitude;
					BankInfo bankInfo=new BankInfo();
					bankInfo.setLatitude(myLatlng.latitude);
					bankInfo.setLongitude(myLatlng.longitude);
					bankInfos.add(bankInfo);
					
					
					search(myLatlng);
				}
			}
		}
	}


	private int currentNum=0;
	private void search(LatLng latLng) {

		poiSearch = PoiSearch.newInstance();

		poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

			@Override
			public void onGetPoiResult(PoiResult result) {
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//					if(currentNum==0){
//
//						PoiOverlay poiOverlay = new MyPoiOverlay(baiduMap);
//						baiduMap.setOnMarkerClickListener(poiOverlay);
//						poiOverlay.setData(result); // 设置数据
//						poiOverlay.addToMap(); // 添加到地图
//						poiOverlay.zoomToSpan(); // 缩放到合适视野范围
//					}
					
					List<PoiInfo> allPoi = result.getAllPoi();
					for(int i=0;i<allPoi.size();i++){
						PoiInfo poiInfo = allPoi.get(i);
					//	//poistrs[poistrsSize++]=poiInfo.location.latitude+":"+poiInfo.location.longitude+":"+poiInfo.name+":"+poiInfo.address+":"+poiInfo.phoneNum;
						if(!poiInfo.name.contains("ATM")){
							BankInfo bankInfo=new BankInfo();
							bankInfo.setName(poiInfo.name);
							bankInfo.setAddress(poiInfo.address);
							bankInfo.setLatitude(poiInfo.location.latitude);
							bankInfo.setLongitude(poiInfo.location.longitude);
							bankInfo.setPhoneNum(poiInfo.phoneNum);
							
							
							bankInfos.add(bankInfo);
						}
					}
									
					if(result.getCurrentPageNum()<result.getTotalPoiNum()){
						currentNum++;
						search(myLatlng);
					}
				} else {
					showToast("未搜索到内容");
				}
			}

			@Override
			public void onGetPoiDetailResult(PoiDetailResult result) {
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					showToast(result.getName()
							+"\n电话："+result.getTelephone()
							+"\n地址："+result.getAddress());
				}else{
					showToast("未搜索到详细内容");
				}
			}
		});
		
			PoiNearbySearchOption nearByOption = new PoiNearbySearchOption();
			nearByOption.location(latLng).radius(5000).keyword("银行").pageCapacity(40).pageNum(currentNum);
			poiSearch.searchNearby(nearByOption);
		
	}

	class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap map) {
			super(map);
		}

		@Override
		public boolean onPoiClick(int position) {
			PoiResult poiResult = getPoiResult(); // 获取到setData时的搜索结果
			List<PoiInfo> allPoi = poiResult.getAllPoi();
			PoiInfo poiInfo = allPoi.get(position); // 根据点击的位置获取到poi信息
			PoiDetailSearchOption detailOption = new PoiDetailSearchOption();
			detailOption.poiUid(poiInfo.uid);
			// showToast("name: "+poiInfo.name+", address: "+poiInfo.address);
			poiSearch.searchPoiDetail(detailOption);
			return super.onPoiClick(position);
		}

	}

		//显示toast
		void showToast(String msg){
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onResume() {
			super.onResume();
			mMapView.onResume();
			
		}
		@Override
		public void onDestroy() {
			super.onDestroy();
			mMapView.onDestroy();
			
		}
		@Override
		public void onPause() {
			super.onPause();
			mMapView.onPause();
		}
	
		
	@Override
	public void onMapClick(LatLng arg0) {
		if(myLatlng!=null){
			//打开makerOptionActivity
			Intent intent=new Intent(getActivity(),MarkerOptionsDemo.class);
			String strmy=myLatlng.latitude+":"+myLatlng.longitude;
			intent.putExtra(MarkerOptionsDemo.LOCATION_KEY,strmy);
			startActivity(intent);
		}
	}
	
	
	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}
	
	
	
	public ArrayList<BankInfo> getPoiInfos(){
		
		return bankInfos;
	}

}
