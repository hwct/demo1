package com.example.demo1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.demo1.bean.BankInfo;
import com.example.demo1.bean.BanksInfo;
import com.example.demo1.fragment.FirstTabFragment;
import com.example.demo1.util.LoadIconUtils;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListBankActivity extends Activity{

	
	
	private ArrayList<BankInfo> poiInfos;

	private LatLng myLocation;
	private ListView lv_listbanks;

	private ListView lv_recommands;

	private ArrayList<BanksInfo> banksInfosList;
	
	private ArrayList<BanksInfo> recomendList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_listbanks);
		
		banksInfosList=new ArrayList<BanksInfo>();
		poiInfos = getIntent().getParcelableArrayListExtra(CompleteInformationActivity.EXTRASTR);
		if(poiInfos!=null&&poiInfos.size()>1){
			myLocation=new LatLng(poiInfos.get(0).latitude, poiInfos.get(0).longitude);
			String keys[]={"中国邮","工商银"};
			
			recommand();
			for(int i=0;i<keys.length;i++){
				parseData(banksInfosList,keys[i]);
			}
			initViews();
		}
	}
	
	private void initViews() {
		lv_listbanks = (ListView) this.findViewById(R.id.lv_listbanks);
		
		lv_recommands = (ListView) this.findViewById(R.id.lv_recomamndbanks);
		
		MyAdapter banksinfosAdapter = new MyAdapter(banksInfosList);
		lv_listbanks.setAdapter(banksinfosAdapter);
		MyAdapter recommandAdapter = new MyAdapter(recomendList);
		lv_recommands.setAdapter(recommandAdapter);
		
	}

	private void recommand() {
		recomendList=new ArrayList<BanksInfo>();
		
		for(int i=0;i<poiInfos.size();i++){
			poiInfos.get(i).distance= DistanceUtil.getDistance(myLocation,new LatLng(poiInfos.get(i).latitude,poiInfos.get(i).longitude));
			
		}
		poiInfos.remove(0);
		Collections.sort(poiInfos,comparator);
		
		BankInfo bankInfo = poiInfos.get(0);
		if(bankInfo!=null){
			parseData(recomendList,bankInfo.name.substring(0,3));
		}
		
		BankInfo bankInfo2 =findSecondCloset(bankInfo);
		if(bankInfo2!=null){
			parseData(recomendList,bankInfo2.name.substring(0,3));
		}
	}


	private int findCount=1;
	private BankInfo findSecondCloset(BankInfo bankInfo) {
		
		BankInfo bankInfo2 = poiInfos.get(findCount);
		if(bankInfo!=null&&bankInfo2!=null){
			if(bankInfo2.name.substring(0,3)==bankInfo.name.substring(0,3)){
				findCount++;
				findSecondCloset(bankInfo);
				
			}
		}
		return bankInfo2;
	}



	//xlistView排序。。。。。所用comparator
	private Comparator<BankInfo> comparator = new Comparator<BankInfo>() {
			
			@Override
			public int compare(BankInfo b1,BankInfo b2) {
				//从小到大，从大到小
//				根据第一个参数小于、等于或大于第二个参数分别返回负整数、零或正整数。 
				if (b1.distance< b2.distance) {
					return -1;
				} else if (b1.distance> b2.distance) {
					return 1;
				} else {
					return 0;
				}
			}
		};


	
	
	/***
	 * 得到选中的银行的数据
	 * @param key
	 */
	private void parseData(ArrayList<BanksInfo> banksInfosArrayList,String key) {
		BanksInfo banksInfo=new BanksInfo();
		banksInfo.name=key;
		for(int i=0;i<poiInfos.size();i++){
			BankInfo bankInfo = poiInfos.get(i);
			if(bankInfo.name.contains(key)){
				
				if(banksInfo.minDistance==0){
					banksInfo.minDistance=bankInfo.distance;
				}else{
					if(banksInfo.minDistance>bankInfo.distance){
						banksInfo.minDistance=bankInfo.distance;
					}
				}
				
				banksInfo.icon=bankInfo.icon;
				banksInfo.bankInfos.add(bankInfo);
				if(bankInfo.name.contains("自助")){
					banksInfo.auotoCount++;
				}else{
					banksInfo.managerCount++;
				}
			}
		}
		banksInfosArrayList.add(banksInfo);
	}
	
	



	class MyAdapter extends BaseAdapter{

		private ArrayList<BanksInfo> banksInfos;
		
		public MyAdapter(ArrayList<BanksInfo> banksInfos) {
			super();
			this.banksInfos = banksInfos;
		}

		@Override
		public int getCount() {
			return banksInfos.size();
		}

		@Override
		public Object getItem(int position) {
			return banksInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView==null){
				viewHolder=new ViewHolder();
				convertView = View.inflate(ListBankActivity.this,R.layout.listbank_item,null);
				viewHolder.iv_icon=(ImageView) convertView.findViewById(R.id.iv_bank_icon);
				viewHolder.tv_manager=(TextView) convertView.findViewById(R.id.tv_bank_manager_count);
				viewHolder.tv_auto=(TextView) convertView.findViewById(R.id.tv_bank_auto_count);
				viewHolder.tv_mindistance=(TextView) convertView.findViewById(R.id.tv_mindistance);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			
			viewHolder.iv_icon.setImageBitmap(BitmapFactory.decodeResource(getResources(), LoadIconUtils.loadIcon(banksInfos.get(position).name)));
			viewHolder.tv_manager.setText(banksInfos.get(position).managerCount+"");
			viewHolder.tv_auto.setText(banksInfos.get(position).auotoCount+"");
			viewHolder.tv_mindistance.setText(banksInfos.get(position).minDistance+"");
			return convertView;
		}

	}
	class ViewHolder{
		ImageView iv_icon;
		TextView tv_manager;
		TextView tv_auto;
		TextView tv_mindistance;
	}
	
	
}
