package com.example.cuizehui.mobilesoder;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.cuizehui.Databases.BMdbHelper;
import com.cuizehui.Databases.Blackmdb;
import com.cuizehui.utils.ApkEngine;
import com.cuizehui.utils.ProgressEngine;

import junit.framework.TestCase;

import java.util.List;

import domain.ApkBean;
import domain.BlackmanBean;

/**单元测试数据库
 * Created by cuizehui on 2016/4/28.
 */
public class Mytest extends AndroidTestCase {
   /* public void testcreatdb(){
        Blackmdb blackmdb = Blackmdb.getInstance(getContext());
     // blackmdb.addblack("18679934560",1);
    //  blackmdb.deletblack("18679934560");
      // blackmdb.changeblack("18679934560",2);

       //List<BlackmanBean> blacklist = blackmdb.queueblack();
        //Log.d("数据库信息",""+ blacklist.get(0).getPhonenumber()+":::"+blacklist.get(0).getMode()+"");
      Log.d("数据库信息",""+ blackmdb.queuenumber("1867"));

    }*/
    public void testPackagemothed(){
       List<ApkBean> apkBeens= ApkEngine.getApkMassage(getContext());
        Log.d("应用信息",apkBeens.size()+"");
        for(ApkBean apkBean: apkBeens){
                    Log.d("应用信息name",apkBean.getApkname()+"");
            Log.d("应用信息size",apkBean.getApKsize()+"");
            Log.d("应用信息flag",apkBean.getApkflag()+"");
            Log.d("应用信息pic",apkBean.getApkpicture()+"");
            Log.d("uid",apkBean.getApkuid()+"");
        }
    }
    public  void testTask(){
        ProgressEngine.getProgressBeans(getContext());
    }
}
