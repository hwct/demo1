package com.example.demo1.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baidu.mapapi.map.Text;
import com.example.demo1.R;
import com.example.demo1.bean.FindBeans;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FinderFragment extends Fragment{

	private ListView lv;
	private List<FindBeans> findBeansList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.frag_finder,null);
		lv = (ListView) view.findViewById(R.id.lv_find_news);
		findBeansList = new ArrayList<FindBeans>();
		for(int i=0;i<4;i++){
			FindBeans beans=new FindBeans();
			
			beans.time=DateFormat.getDateFormat(getActivity()).format(new Date(System.currentTimeMillis()));
			beans.content=i+"²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ"+i+"²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ²âÊÔ";
			findBeansList.add(beans);
		}
		lv.setAdapter(new MyAdapter());
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	class MyAdapter extends BaseAdapter{

		
		@Override
		public int getCount() {
			return findBeansList.size();
		}

		@Override
		public Object getItem(int position) {
			return findBeansList.get(position);
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
				convertView = View.inflate(getActivity(), R.layout.finder_listview_item,null);
				viewHolder.tv_time=(TextView) convertView.findViewById(R.id.tv_show_time);
				viewHolder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(viewHolder);
			}else{
				viewHolder=(ViewHolder) convertView.getTag();
			}
			
			viewHolder.tv_title.setText(findBeansList.get(position).content);
			viewHolder.tv_time.setText(findBeansList.get(position).time);
			return convertView;
		}
		
		
	}
	class ViewHolder{
		TextView tv_time;
		TextView tv_title;
		
	}
}
