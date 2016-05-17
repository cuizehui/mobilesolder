package com.cuizehui.Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import domain.BlackmanBean;

/**黑名单数据库操作类（单例）
 * Created by cuizehui on 2016/4/28.
 */
public class Blackmdb {
    public static final String DB_NAME = "black_db";
    public static final int VERSON = 1;
    private static SQLiteDatabase db;
    //这个操作类只有一个 也就保证了数据库只在操作一个数据库
    private static Blackmdb bmdb;
    private BMdbHelper bMdbHelper;
    private List<BlackmanBean> list=new ArrayList<>();
    private Blackmdb(Context context) {

        bMdbHelper = new BMdbHelper(context, DB_NAME, null, VERSON);
        db = bMdbHelper.getWritableDatabase();
    }

    /**
     * 获取数据操作类实例
     * @param context
     * @return
     */
    public synchronized static Blackmdb getInstance(Context context) {
        if (bmdb == null) {
            bmdb = new Blackmdb(context);
        }
        return bmdb;
    }


    /**
     * 增加黑名单
     * @param number 电话号
     * @param mode 模式
    */
    public void addblack(String number, int mode) {
        db.execSQL("insert into blackman(PhoneNumber,mode) values(" + number+ "," + mode+ ") ");

    }


    /**
     * 按电话号码删除黑名单
     * @param number 电话号
     */
    public void deletblack(String number){
        db.execSQL("delete from blackman where PhoneNumber="+"\""+number+"\"");
    }

    /**
     * 改变黑名单电话号码的拦截方式
     * UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
     * @param number
     * @param mode
     */

    public void changeblack(String number,int mode){
        db.execSQL("update blackman set mode="+"\""+mode+"\""+"where PhoneNumber="+"\""+number+"\"");
    }

    /**
     * 查询数据库并
     * @return 黑名单对象的LIST集合
     */
    public List<BlackmanBean> queueblack(){
        Cursor cursor = db.rawQuery("select * from blackman ", null);
        if(cursor.moveToFirst()){
                        do{
                              BlackmanBean bmbean=new BlackmanBean();
                                bmbean.setPhonenumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));

                                bmbean.setMode(cursor.getInt(cursor.getColumnIndex("mode")));
                            list.add(bmbean);

                            }while(cursor.moveToNext());
                    }
        if(cursor!=null){
                cursor.close();
            }
return  list;
    }
    public int queuenumber(String number){
         int mymode=0;
         Cursor cursor=db.rawQuery("select * from blackman where PhoneNumber="+"\""+number+"\"",null);
        if(cursor.moveToFirst()){
            do{
                mymode=cursor.getInt(cursor.getColumnIndex("mode"));
                return mymode;
            }while(cursor.moveToNext());
        }
        if(cursor!=null){
          cursor.close();
         }
        return mymode;
    }
}

