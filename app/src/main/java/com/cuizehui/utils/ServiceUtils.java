package com.cuizehui.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;

import java.util.List;

/**
 * Created by cuizehui on 2016/5/15.
 * 判断service 是否开启。
 */
public class ServiceUtils {

       public static boolean isServiceRunning(Context context,String serviceName){
           boolean isRunning = false;

           //判断运行中的服务状态，ActivityManager
           ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
           //获取android手机中运行的所有服务
           List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(50);

           for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
               //System.out.println(runningServiceInfo.service.getClassName());
               //判断服务的名字是否包含我们指定的服务名
               if (runningServiceInfo.service.getClassName().equals(serviceName)){
                   //名字相等，该服务在运行中
                   isRunning = true;
                   //已经找到 退出循环
                   break;
               }
           }
           return isRunning;
       }





}
