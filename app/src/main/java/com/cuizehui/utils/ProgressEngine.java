package com.cuizehui.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.text.format.Formatter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import domain.ProgressBean;

/**
 * Created by cuizehui on 2016/5/25.
 */
public class ProgressEngine  {
    public   static List<ProgressBean> getProgressBeans(Context context){
         List<ProgressBean> progressBeans=new ArrayList<>();
        //获取AM
        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //获取PM
        PackageManager pm=context.getPackageManager();

       //通过内存信息获取正在运行的进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos=new ArrayList<>();
        runningAppProcessInfos= am.getRunningAppProcesses();
        //通过进程信息获取进程名(包名)拿到包名要获取apk信息还是通过PM
        for (ActivityManager.RunningAppProcessInfo runapinfo:runningAppProcessInfos) {
            ProgressBean progressbean=new ProgressBean();
             //设置进程所在包名
             String processname= runapinfo.processName;
             progressbean.setPackName(processname);
            try {

                 ApplicationInfo apinfo=  pm.getApplicationInfo(processname,0);
                //设置进程图标
                 progressbean.setPicon(apinfo.loadIcon(pm));
                //设置进程名
                 progressbean.setPname(apinfo.loadLabel(pm));
                //获取进程占用内存大小
               Debug.MemoryInfo[] pMenory =  am.getProcessMemoryInfo(new int[]{runapinfo.pid});
                long dirtymenory = pMenory[0].getTotalPrivateDirty()*1024;
                progressbean.setPmemSize(dirtymenory);
                //设置进程类型
                int flags = apinfo.flags;
                if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {

                    progressbean.setPisSystem(true);
                } else {
                    progressbean.setPisSystem(false);
                }

            } catch (PackageManager.NameNotFoundException e) {
                //因为有些进程的包名是没有APK文件的所以 没有信息 会空指针异常！！
                continue;
            }
         progressBeans.add(progressbean);
        }
        return progressBeans  ;
    }
   public static long[] getmemeryinfo(Context context){

       ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
       ActivityManager.MemoryInfo outinfo=new ActivityManager.MemoryInfo();
       am.getMemoryInfo(outinfo);

       long[] memy=new long[]{outinfo.totalMem,outinfo.availMem};
       return memy;
   }
}
