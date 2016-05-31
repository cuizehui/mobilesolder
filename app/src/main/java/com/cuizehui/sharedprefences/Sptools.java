package com.cuizehui.sharedprefences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by cuizehui on 2016/4/27.
 */
public class Sptools {

    public static void saveString(Context context,String key,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("set",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }
    public static String  getString(Context context,String key){
        SharedPreferences sharedPreferences=context.getSharedPreferences("set",Context.MODE_PRIVATE);

        return   sharedPreferences.getString(key,null);
    }

}
