package com.cuizehui.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import domain.ContactBean;

/**
 * Created by cuizehui on 2016/5/12.
 */
public class ReadContantsEngine {
    /**
     * @return
     *     电话日志的记录
     */
    public static List<ContactBean> readSmslog(Context context){
        //1，电话日志的数据库
        //2,通过分析，db不能直接访问，需要内容提供者访问该数据库
        //3,看上层源码 找到uri content://sms
        Uri uri = Uri.parse("content://sms");
        //获取电话记录的联系人游标
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"address"}, null, null, " _id desc");
        List<ContactBean> datas = new ArrayList<ContactBean>();

        while (cursor.moveToNext()) {
            ContactBean bean = new ContactBean();

            String phone = cursor.getString(0);//获取号码
          //  String name = cursor.getString(1);//获取名字

         //   bean.setContactname(name);
            bean.setPhonenumber(phone);

            //添加数据
            datas.add(bean);

        }
        return datas;

    }

    /**
     * @return
     *     电话日志的记录
     */
    public static List<ContactBean> readCalllog(Context context){
        //1，电话日志的数据库
        //2,通过分析，db不能直接访问，需要内容提供者访问该数据库
        //3,看上层源码 找到uri content://calls
        Uri uri = Uri.parse("content://call_log/calls");
        //获取电话记录的联系人游标
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"number","name"}, null, null, " _id desc");
        List<ContactBean> datas = new ArrayList<ContactBean>();

        while (cursor.moveToNext()) {
            ContactBean bean = new ContactBean();

            String phone = cursor.getString(0);//获取号码
            String name = cursor.getString(1);//获取名字

            bean.setContactname(name);
            bean.setPhonenumber(phone);

            //添加数据
            datas.add(bean);

        }
        return datas;

    }

    /**
     * 读取手机联系人
     */
    public static List<ContactBean> readContants(Context context){
        List<ContactBean> datas = new ArrayList<ContactBean>();
        Uri uriContants = Uri.parse("content://com.android.contacts/contacts");
        Uri uriDatas = Uri.parse("content://com.android.contacts/data");

        Cursor cursor = context.getContentResolver().query(uriContants, new String[]{"_id"}, null, null, null);
        //循环取数据 
        while(cursor.moveToNext()){
            //好友信息的封装bean
            ContactBean bean = new ContactBean();
            //直接打印id
            String id = cursor.getString(0);//获取到联系人的id

            Cursor cursor2 = context.getContentResolver().query(uriDatas,new String[]{"data1","mimetype"}, " raw_contact_id = ? ", new String[]{id}, null);

            //循环每条数据信息都是一个好友的一部分信息
            while(cursor2.moveToNext()) {
                String data  = cursor2.getString(0);
                String mimeType = cursor2.getString(1);


                if (mimeType.equals("vnd.android.cursor.item/name")) {
                    System.out.println("第" +id + "个用户：名字：" + data);
                    bean.setContactname(data);
                } else if (mimeType.equals("vnd.android.cursor.item/phone_v2")) {
                    System.out.println("第" +id + "个用户：电话：" + data);
                    bean.setPhonenumber(data);
                }
            }
            cursor2.close();//关闭游标释放资源
            datas.add(bean);//加一条好友信息
        }
        cursor.close();
        return datas;
    }
}

