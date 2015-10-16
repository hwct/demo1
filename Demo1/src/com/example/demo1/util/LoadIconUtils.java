package com.example.demo1.util;

import com.example.demo1.R;


public class LoadIconUtils {

	public static int loadIcon(String name){
		if(name.contains("中国银行")){
			return R.drawable.bank_chinna;
		}else if(name.contains("工商银行")){
			return R.drawable.bank_gongshang;
		}else if(name.contains("中国邮政")){
			return R.drawable.bank;
		}else if(name.contains("中国农业")){
			return R.drawable.bank_nongye;
		}else if(name.contains("交通银行")){
			return R.drawable.bank_jiaotong;
		}else if(name.contains("兴业银行")){
			return R.drawable.bank_xingye;
		}else if(name.contains("招商银行")){
			return R.drawable.bank_zhaoshang;
		}else if(name.contains("发展")){
			return R.drawable.bank_fazhan;
		}else{
			return R.drawable.bank;
		}
	}
}
