package com.example.demo1.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BankInfo implements Parcelable{
	//����ͼ��
	public int icon;
	//�绰����
	public String phoneNum;
	//���е�ַ
	public String address;
	//��������
	public String name;
	//���붨λ�ľ���
	public double distance;
	//γ��
	public double latitude;
	//����
	public double longitude;

	
	
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	//ϵͳ�ӿڣ�Ĭ��
	@Override
	public int describeContents() {
		return 0;
	}
	//д��ӿ�
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(icon);
		dest.writeString(phoneNum);
		dest.writeString(address);
		dest.writeString(name);
		
		dest.writeDouble(distance);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
	}
	
	public static final Parcelable.Creator<BankInfo> CREATOR = new Creator<BankInfo>(){
		//��ȡ�ӿ�
		@Override
		public BankInfo createFromParcel(Parcel source) {
			BankInfo bankInfo=new BankInfo();
			bankInfo.icon=source.readInt();
			bankInfo.phoneNum=source.readString();
			bankInfo.address=source.readString();
			bankInfo.name=source.readString();
			bankInfo.distance=source.readDouble();
			bankInfo.latitude=source.readDouble();
			bankInfo.longitude=source.readDouble();
			return bankInfo;
		}
		
		@Override
		public BankInfo[] newArray(int size) {
			return new BankInfo[size];
		}
		
	};
	
}
