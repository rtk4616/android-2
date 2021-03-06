package com.example.androidserviceaidltest;

import com.example.androidserviceaidl.IRemoteService;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	private IRemoteService mService = null;
	private static final String TAG = "MainActivitytest, PID=" + Process.myPid();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	 Log.d(TAG, TAG + " onCreate()");  
     /** 
      * 启动Service，让Service在Activity结束后依然存在 
      */  
     startService(new Intent("com.example.androidserviceaidl.RemoteService"));  
     /** 
      * 绑定service 
      */  
     bindService(new Intent("com.example.androidserviceaidl.RemoteService"), mConn, Context.BIND_AUTO_CREATE);  
 }  

 /** 
  * 定义一个ServiceConnection 
  */  
 private ServiceConnection mConn = new ServiceConnection() {  

     @Override  
     public void onServiceConnected(ComponentName name, IBinder service) {  
         // TODO Auto-generated method stub  
         mService = IRemoteService.Stub.asInterface(service);  
         Log.d(TAG, TAG + " Service Connected.");  
         try {  
             mService.getCount();  
         } catch (RemoteException e) {  
             // TODO Auto-generated catch block  
             e.printStackTrace();  
         }  
     }  

     @Override  
     public void onServiceDisconnected(ComponentName name) {  
         // TODO Auto-generated method stub  
         mService = null;  
         Log.d(TAG, TAG + " Service Disconnected.");  
     }  
       
 };   

 @Override  
 protected void onDestroy() {  
     // TODO Auto-generated method stub  
     unbindService(mConn);  
     Log.d(TAG, TAG + " onDestroy().");  
     super.onDestroy();  
 }  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
