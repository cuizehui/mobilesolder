package com.cuizehui.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.cuizehui.Broadcastreceivers.BlackSmsReceiver;
import com.cuizehui.Broadcastreceivers.SmsReceiver;
import com.cuizehui.Databases.Blackmdb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BlackService extends Service {
    private BlackSmsReceiver blsmsReceiver;

    public BlackService() {

    }

        @Override
        public void onCreate() {
            //短信拦截
            blsmsReceiver=new BlackSmsReceiver();
            IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
            intentFilter.setPriority(Integer.MAX_VALUE);
            registerReceiver(blsmsReceiver,intentFilter);
            Log.d("服务已经开启了","开启了");

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            PhoneStateListener listener=new PhoneStateListener(){
                @Override
                //监测电话状态的变化。
                public void onCallStateChanged(int state, String incomingNumber) {
                   switch(state){
                       //无状态
                       case  TelephonyManager.CALL_STATE_IDLE:

                           break;
                       //通话状态
                       case  TelephonyManager.CALL_STATE_OFFHOOK:
                           break;
                       //响铃状态
                       case  TelephonyManager.CALL_STATE_RINGING:
                          int mode= Blackmdb.getInstance(getApplicationContext()).queuenumber(incomingNumber);
                           if(mode==2||mode==3){
                               Log.d("挂断电话","挂断");
                               endcall();
                           }
                           break;
                       default:
                           break;
                     }
                    super.onCallStateChanged(state,incomingNumber);

                }

            };
            tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
        }

    private void endcall() {
    //利用反射机制挂断电话
        try {
            //获取类对象
            Class clazz= Class.forName("android.os.ServiceManager");
            //获取类中方法，这个方法是非静态的
            Method mothod = clazz.getDeclaredMethod("getService",String.class);
            //执行这个方法
           IBinder binder= (IBinder) mothod.invoke(null, Context.TELEPHONY_SERVICE);
            ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
            iTelephony.endCall();//挂断电话
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(blsmsReceiver);
        Log.d("服务关闭了","关闭了");
        super.onDestroy();
    }
}
