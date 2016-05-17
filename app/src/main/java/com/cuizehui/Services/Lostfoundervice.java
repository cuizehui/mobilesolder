package com.cuizehui.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.cuizehui.Broadcastreceivers.SmsReceiver;

/**
 * 开启服务  设置系统信息广播监听，拦截短信并。。。
 */
public class Lostfoundervice extends Service {
    SmsReceiver smsReceiver;
    public Lostfoundervice() {
        Log.d("开始手机丢失找回服务了","!!");
    }

    @Override
    public void onCreate() {
        //短信拦截
         smsReceiver=new SmsReceiver();
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
       //在filter设置优先级
        intentFilter.setPriority(1000);
        registerReceiver(smsReceiver,intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(smsReceiver);
        Log.d("服务关闭了","!!!!!!!");
        super.onDestroy();
    }
}
