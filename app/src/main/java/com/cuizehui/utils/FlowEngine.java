package com.cuizehui.utils;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by cuizehui on 2016/5/27.
 */
public class FlowEngine {

    //先读一个
    public static   long   getDownflow(int uid){

            long size = 0;

            String path = "/proc/uid_stat/" + uid + "/tcp_rcv";
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
                String line = reader.readLine();
               size = Long.parseLong(line);


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
       // Log.d("返回的流量值",""+uid+"::"+size);
         return size;
    }
    public  static long getSendflow(int uid){
        long sendsize=0;
        String path = "/proc/uid_stat/" + uid + "/tcp_snd";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = reader.readLine();
            sendsize = Long.parseLong(line);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       // Log.d("发送的流量值",""+uid+"::"+sendsize);
        return sendsize;
    }


}
