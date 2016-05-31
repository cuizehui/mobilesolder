package com.cuizehui.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import domain.ApkBean;

/**
 * Created by cuizehui on 2016/5/21.
 */
public class ApkEngine {
    public  static List<ApkBean> getApkMassage(Context context) {
        List<ApkBean> list = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> listpackinfos = packageManager.getInstalledPackages(0);
        //list获取单个对象的增强for循环
        for (PackageInfo packinfo : listpackinfos) {

            ApkBean apkBean = new ApkBean();
            //通过这种方式获得应用图标和名称
            apkBean.setApkname(packinfo.applicationInfo.loadLabel(packageManager) + "");
            apkBean.setApkpicture(packinfo.applicationInfo.loadIcon(packageManager));
            apkBean.setApkpicture(packinfo.applicationInfo.loadIcon(packageManager));
            apkBean.setPackgename(packinfo.packageName);
            apkBean.setApkuid(packinfo.applicationInfo.uid);
            String dir = packinfo.applicationInfo.sourceDir;
            File file = new File(dir);
            apkBean.setApKsize(file.length());
            int flags = packinfo.applicationInfo.flags;
            if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {

                apkBean.setApkflag(true);
            } else {
                apkBean.setApkflag(false);
            }
            list.add(apkBean);
        }
        return list;
    }
    //获取文件的剩余空间
    public static Long getAvilSDSpace(){
        Long sdspace;
        File externalStorageDirectory = Environment.getExternalStorageDirectory();

        sdspace = externalStorageDirectory.getFreeSpace();
         externalStorageDirectory.getTotalSpace();
        return sdspace;
    }
    public static Long getMaxSpace(){
        Long sdmaxSpace;
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        sdmaxSpace = externalStorageDirectory.getTotalSpace();
        return sdmaxSpace;
    }
    public static  Long getAvilRomSpace(){
         long romspace ;
        File dataDirectory=Environment.getDataDirectory();
       romspace= dataDirectory.getFreeSpace();
        return romspace;

    }
    public static  Long getMaxRomSpace(){
        long romspace ;
        File dataDirectory=Environment.getDataDirectory();
       romspace= dataDirectory.getTotalSpace();
        return romspace;

    }
}
