package com.example.demo1;

import com.example.demo1.global.ConstantValue;
import com.example.demo1.global.GloBalkeys;
import com.example.demo1.util.PrefUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

public class SplashActivity extends Activity {

    private boolean isfirst;
	private long startAccessData;
	public int splashTime=2000;
	
    private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(isfirst){
		        Intent intent=new Intent(SplashActivity.this,IndicatorActivity.class);
		        startActivity(intent);
		        SplashActivity.this.finish();
	        }else{
	        	Intent intent=new Intent(SplashActivity.this,MainActivity.class);
	        	startActivity(intent);
	        	SplashActivity.this.finish();
	        }
		}
    	
    };



	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_splash);
        isfirst = PrefUtils.getBoolean(this,"isfirst",true);
        View view=findViewById(R.id.rl_splash);
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(splashTime);
        
        view.startAnimation(alphaAnimation);
        startAccessData = SystemClock.elapsedRealtime();
        getDataFromServer();
        
        
       
        
    }


   
	//请求网络数据
	private void getDataFromServer() {
    	HttpUtils httpUtils=new HttpUtils();
    	httpUtils.configTimeout(3000);
    	httpUtils.send(HttpMethod.GET, ConstantValue.BASE_URL,new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				if(responseInfo!=null){
					String json=responseInfo.result.toString();
				}
				long now = SystemClock.elapsedRealtime();
				if((now-startAccessData)>splashTime){
					handler.sendEmptyMessage(0);
				}else{
					handler.sendEmptyMessageDelayed(0,splashTime-(now-startAccessData));
				}
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(SplashActivity.this, "访问网络数据失败", 0).show();
				long now = SystemClock.elapsedRealtime();
				if((now-startAccessData)>splashTime){
					handler.sendEmptyMessage(0);
				}else{
					handler.sendEmptyMessageDelayed(0,splashTime-(now-startAccessData));
				}
			}
		});
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
