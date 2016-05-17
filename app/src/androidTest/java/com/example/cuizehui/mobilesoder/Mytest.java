package com.example.cuizehui.mobilesoder;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.cuizehui.Databases.BMdbHelper;
import com.cuizehui.Databases.Blackmdb;

import junit.framework.TestCase;

import java.util.List;

import domain.BlackmanBean;

/**单元测试数据库
 * Created by cuizehui on 2016/4/28.
 */
public class Mytest extends AndroidTestCase {
    public void testcreatdb(){
        Blackmdb blackmdb = Blackmdb.getInstance(getContext());
     // blackmdb.addblack("18679934560",1);
    //  blackmdb.deletblack("18679934560");
      // blackmdb.changeblack("18679934560",2);

       //List<BlackmanBean> blacklist = blackmdb.queueblack();
        //Log.d("数据库信息",""+ blacklist.get(0).getPhonenumber()+":::"+blacklist.get(0).getMode()+"");
      Log.d("数据库信息",""+ blackmdb.queuenumber("1867"));

    }

}
