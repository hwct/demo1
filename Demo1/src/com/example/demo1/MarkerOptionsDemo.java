package com.example.demo1;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption.DrivingPolicy;
import com.example.demo1.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
/***
 * changed
 * @author hwctme
 *
 */
public class MarkerOptionsDemo extends Activity {

	private View pop;
	private String title;
	private TextView titleText;
	protected MapView mMapView;
	protected BaiduMap baiduMap;
	public static final String LOCATION_KEY="MarkerOptionDemo";
	private LatLng mylocation;
	private PoiSearch poiSearch;
	private List<PoiInfo> allPoi;
	private RoutePlanSearch routeSearch;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.common_location);
		
		String str=getIntent().getStringExtra(LOCATION_KEY);
		String[] strs=str.split(":");
		mylocation=new LatLng(Double.parseDouble(strs[0]),Double.parseDouble(strs[1]));
		initBaiduMap();
		search(mylocation);
		initPop();
	
	}

	private void initBaiduMap() {
		mMapView = (MapView) this.findViewById(R.id.mapview);
		baiduMap = mMapView.getMap();
		baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(16));
		baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(mylocation));
	}
	
	private void initPop() {
		pop = View.inflate(this, R.layout.pop, null);
		LayoutParams params = new MapViewLayoutParams.Builder()  //必须MapViewLayoutParams
		.height(MapViewLayoutParams.WRAP_CONTENT)
		.width(MapViewLayoutParams.WRAP_CONTENT)
		.layoutMode(ELayoutMode.mapMode)      //使用经纬度的方式设置控件的位置
		.position(mylocation)
		.yOffset(-5)
		.build();
		
		pop.setVisibility(View.GONE);
		titleText = (TextView)pop.findViewById(R.id.title);
		mMapView.addView(pop, params);
	}
	
	/***
	 *根据传入的mylocation进行搜索 
	 */
	public void search(LatLng myLocation) {

		poiSearch = PoiSearch.newInstance();
		PoiNearbySearchOption nearByOption = new PoiNearbySearchOption();
		nearByOption.location(myLocation).radius(3000).keyword("银行");
		poiSearch.searchNearby(nearByOption);
		poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
			@Override
			public void onGetPoiResult(PoiResult result) {
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					PoiOverlay poiOverlay = new PoiOverlay(baiduMap);
					allPoi = result.getAllPoi();
					/***
					 * 当所有结果都搜索出来了再去绘制标记
					 */
					draw();
					baiduMap.setOnMarkerClickListener(poiOverlay);
					poiOverlay.setData(result); // 设置数据
					poiOverlay.addToMap(); // 添加到地图
					poiOverlay.zoomToSpan(); //缩放到合适视野范围
				} else {
					showToast("未搜索到内容");
				}
			}

			@Override
			public void onGetPoiDetailResult(PoiDetailResult result) {
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					//showToast(result.getName());
				}else{
					showToast("未搜索到详细内容");
				}
			}
		});
		
	}
	
	/****
	 * 当结果搜索出来了绘制标记
	 */
	private void draw() {
		MarkerOptions markerOptions = new MarkerOptions();
		
		BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.eat_icon);
		ArrayList<BitmapDescriptor> icons = new ArrayList<BitmapDescriptor>();
		icons.add(icon);
		icons.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_geo));
		
		markerOptions.draggable(true).icons(icons ).title("我的位置").position(mylocation).period(10);
		baiduMap.addOverlay(markerOptions);
		
		for(int i=1;i<allPoi.size();i++){
			if(allPoi.get(i)==null){
				continue;
			}
			
			markerOptions = new MarkerOptions().title(":"+allPoi.get(i).name)
					.position(new LatLng(allPoi.get(i).location.latitude,allPoi.get(i).location.longitude))
					.icon(icon);
			baiduMap.addOverlay(markerOptions);
		}
		
		//设置当标记点击后出发监听事件
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				title = marker.getTitle(); //设置title
				if(title==null){
					for(PoiInfo info:allPoi){
						if(info.location==marker.getPosition()){
							title=info.name;
						}
					}
				}
				/****
				 * 搜索具体的线路，是开车，走路或者乘公交
				 */
				searchRounte(marker.getPosition());
				
				return false;
			}

			
		});
	}
	/****
	 * 显示并更新popview
	 * @param marker
	 * @param title
	 */
	private void updateShowPop(LatLng position,String title) {
		titleText.setText(title);
		LayoutParams params = new MapViewLayoutParams.Builder()
		.height(MapViewLayoutParams.WRAP_CONTENT)
		.width(MapViewLayoutParams.WRAP_CONTENT)
		.layoutMode(ELayoutMode.mapMode)
		.position(position)
		.yOffset(-5)
		.build();
		//更新pop的位置并显示出来
		mMapView.updateViewLayout(pop, params);
		pop.setVisibility(View.VISIBLE);
	}

	//显示toast
	void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	//开车需要时间,单位秒
	private int driveduration;
	//驾车路线
	private DrivingRouteOverlay drivingRouteOverlay;
	//搜索乘车路线
	private void searchRounte(final LatLng toLatLng) {
		routeSearch = RoutePlanSearch.newInstance();
//		ArrayList<PlanNode> pass = new ArrayList<PlanNode>();
//		pass.add(PlanNode.withCityNameAndPlaceName("深圳", "塘朗山公园"));
		DrivingRoutePlanOption drivingOption = new DrivingRoutePlanOption();
		drivingOption.from(PlanNode.withLocation(mylocation))   //从凌云大厦出发
		.to(PlanNode.withLocation(toLatLng))//到深圳北d
//		.passBy(pass)
		.policy(DrivingPolicy.ECAR_TIME_FIRST);  //出行策略，费用低或者时间短、距离近等等
		routeSearch.drivingSearch(drivingOption );
		
		routeSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
			}
			
			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {
			}
			
			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult result) {
				if(result.error == SearchResult.ERRORNO.NO_ERROR){
					//清除以前的overlay
					if(drivingRouteOverlay!=null){
						drivingRouteOverlay.removeFromMap();
					}
						drivingRouteOverlay = new DrivingRouteOverlay(baiduMap);
						drivingRouteOverlay.setData(result.getRouteLines().get(0));  //设置第一条线路
						drivingRouteOverlay.addToMap();   
						drivingRouteOverlay.zoomToSpan();
						
						List<DrivingRouteLine> routeLines = result.getRouteLines();
						driveduration = routeLines.get(0).getDuration();
						title=title+"::"+(int)(driveduration/60);
						//得到具体的信息后更新pop
						updateShowPop(toLatLng,title);
					
				}else{
					showToast("未搜索到相关路线");
				}
			}
		});
		
	}

}
