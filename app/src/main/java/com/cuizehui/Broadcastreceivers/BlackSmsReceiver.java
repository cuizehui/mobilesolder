package com.cuizehui.Broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cuizehui.Databases.Blackmdb;

public class BlackSmsReceiver extends BroadcastReceiver {
    public BlackSmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle  bundle= intent.getExtras();
              Object[] datas = (Object[]) bundle.get("pdus");//信息在pdus字段里
            for(Object data:datas){
            //从pdu获取消息
                       //SmsMessage.读取信息
                       SmsMessage smsMessage=SmsMessage.createFromPdu((byte[]) data);

                       String address = smsMessage.getDisplayOriginatingAddress();
                       int mode= Blackmdb.getInstance(context).queuenumber(address);
                       if (mode==1||mode==3){
                           Log.d("拦截短信了","拦截");
                           this.abortBroadcast();
                       }
                       Log.d("信息",":"+address);

            }
    }


}
