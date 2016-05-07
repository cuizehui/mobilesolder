package com.cuizehui.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 黑名单数据库帮助类
 * Created by cuizehui on 2016/4/28.
 */
public class BMdbHelper extends SQLiteOpenHelper {
    //建表语句
    String createtable="create table blackman(PhoneNumber  char(15) primary key,mode int)";
    public BMdbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 建表只建了一张表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createtable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
