package com.example.demo1.util;

import com.example.demo1.R;


public class LoadIconUtils {

	public static int loadIcon(String name){
		if(name.contains("�й�����")){
			return R.drawable.bank_chinna;
		}else if(name.contains("��������")){
			return R.drawable.bank_gongshang;
		}else if(name.contains("�й�����")){
			return R.drawable.bank;
		}else if(name.contains("�й�ũҵ")){
			return R.drawable.bank_nongye;
		}else if(name.contains("��ͨ����")){
			return R.drawable.bank_jiaotong;
		}else if(name.contains("��ҵ����")){
			return R.drawable.bank_xingye;
		}else if(name.contains("��������")){
			return R.drawable.bank_zhaoshang;
		}else if(name.contains("��չ")){
			return R.drawable.bank_fazhan;
		}else{
			return R.drawable.bank;
		}
	}
}
