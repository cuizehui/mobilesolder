package com.cuizehui.Services;

import android.app.ActionBar;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cuizehui.Actitys.RockActivity;
import com.example.cuizehui.mobilesoder.R;

public class RockService extends Service implements View.OnTouchListener {
    private static final int SEND_ROCK =1 ;
    WindowManager wm;
    private WindowManager.LayoutParams mLayoutParams;
    private View view;
    private int lastx=0;
    private int lasty=0;
    private Handler mhandler;
    private ImageView imageView;
    //view动画图片
    private AnimationDrawable drawable;

    public RockService() {
    }

    @Override
    public void onCreate() {
        wm= (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayoutParams =new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,0,0, PixelFormat.TRANSPARENT);
        //这两句是关键哦 ！！
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
		/* | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE */
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mLayoutParams.x=100;
        mLayoutParams.y=300;
        super.onCreate();
        showRock();
         mhandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {

                    wm.updateViewLayout(view,mLayoutParams);


            }
        };
    }

    private void showRock() {
        LayoutInflater inflater= LayoutInflater.from(this);
        view= inflater.inflate(R.layout.rock_view,null);
      //给view设置图片切换动画
        imageView= (ImageView) view.findViewById(R.id.rocket);
        //背景是一个动画的图片
        drawable= (AnimationDrawable) imageView.getBackground();
        view.setOnTouchListener(this);
         wm.addView(view,mLayoutParams);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        wm.removeView(view);
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawx = (int) event.getRawX();
        int rawy = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawable.start();
                lastx = rawx;
                lasty = rawy;
                break;
            case MotionEvent.ACTION_MOVE:

                mLayoutParams.x += (rawx - lastx);
                mLayoutParams.y += (rawy - lasty);
                wm.updateViewLayout(view, mLayoutParams);
               Log.d("当前点x,y",mLayoutParams.x+":"+mLayoutParams.y+"");
               //重置初始位置！！
                lastx = rawx;
                lasty = rawy;
                break;
            case MotionEvent.ACTION_UP:
                //判断抬手时的位置
                if (mLayoutParams.x > 100 && mLayoutParams.x + view.getWidth() < wm.getDefaultDisplay().getWidth() - 100 &&
                        mLayoutParams.y > 980) {
                    Log.d("落入到当前位置了","落入");
                    //中心处理
                    mLayoutParams.x = (wm.getDefaultDisplay().getWidth() - view.getWidth()) / 2;
                     //耗时动画做线程处理

                    new Thread(){
                        @Override
                        public void run() {

                          Message ms= mhandler.obtainMessage();
                            ms.what = SEND_ROCK;
                            //这部分逻辑需要重新测试
                            for (int j = 0;  mLayoutParams.y>=0; ) {
                                SystemClock.sleep(50);
                                mLayoutParams.y -= j;
                                j += 5;

                                mhandler.obtainMessage().sendToTarget();
                            }
                        }
                    }.start();

                          Intent intent =new Intent(getApplicationContext(), RockActivity.class);
                    //在服务中开启活动需要开启一个新的任务栈！！
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          startActivity(intent);
                }
                else {
                    Log.d("没有落入","没落入");
                }
                drawable.stop();
                break;
        }
        return false;
    }
}
