package com.cuizehui.Actitys;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.cuizehui.mobilesoder.R;

public class RockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock);
       ImageView smork1= (ImageView) findViewById(R.id.smorkone);
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(2000);
        smork1.setAnimation(aa);
        ImageView smork2= (ImageView) findViewById(R.id.smorktwo);
        smork2.setAnimation(aa);
        /*
        //将这个做成平移动画！
        ImageView cloud= (ImageView) findViewById(R.id.imcloud);
                 cloud.setAnimation(aa);
       */
        //开启线程延时关闭
         new Thread(){
             @Override
             public void run() {
                 SystemClock.sleep(2000);

                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                    finish();

                     }
                 });




             }
         }.start();

    }
}
