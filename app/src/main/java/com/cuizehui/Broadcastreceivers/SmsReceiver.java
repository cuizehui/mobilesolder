package com.cuizehui.Broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cuizehui.Services.LocationService;

/**
 * 拦截手机短信进行判断  获取短信内容。如果是特定字符端则执行操作
 */
public class SmsReceiver extends BroadcastReceiver {
    public SmsReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取系统发送的信息广播intent对象,并通过bundle将短信信息取出。
        Bundle  bundle= intent.getExtras();
         Object[] datas = (Object[]) bundle.get("pdus");
        for(Object data:datas){
            //从pdu获取消息
            //SmsMessage.读取信息
            SmsMessage smsMessage=SmsMessage.createFromPdu((byte[]) data);

            String body = smsMessage.getDisplayMessageBody();
            String address = smsMessage.getDisplayOriginatingAddress();
            Log.d("信息",body+":"+address);
            if(body.equals("lost")){
                Intent locationintent=new Intent(context,LocationService.class);
                context.startService(locationintent);
            }
        }
    }
}
