package com.cuizehui.Services;


import android.app.LoaderManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.cuizehui.sharedprefences.Sptools;

public class LocationService extends Service {
     //位置短信的内容
     StringBuilder locationtext;
    //位置服务
    LocationManager locationManager;
    //位置服务的监听者 负责位置信息的回调
    String  safenumber;
    //安全电话号码的设置
    LocationListener listener;
    public LocationService() {
    }

    /**
     * 结束服务后资源释放，解除监听后，将指针指向空释放资源
     */
    @Override
    public void onDestroy() {
        locationManager.removeUpdates(listener);
        locationManager=null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.d("执行位置服务","！！");


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        listener = new LocationListener() {


            public void onLocationChanged(Location location) {
                location.getAccuracy();
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("位置","经度"+latitude+"纬度"+longitude);
                locationtext=new StringBuilder();
                locationtext.append("经度"+latitude);
                locationtext.append("纬度"+longitude);
                //发送短信
                safenumber= Sptools.getString(getApplicationContext(),"安全号码");
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(safenumber,"",locationtext.toString(),null,null);


                stopSelf();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, listener);
    }
}
