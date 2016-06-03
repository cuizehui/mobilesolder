package com.cuizehui.Myview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.cuizehui.mobilesoder.R;

/**
 * Created by cuizehui on 2016/6/1.
 */
public class SpeedView extends RelativeLayout{
    private final ImageView imageView;
    //属性动画
    private final RotateAnimation ra;

    public SpeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater=LayoutInflater.from(context);
        final View view=  inflater.inflate(R.layout.speed_view,null);
        imageView= (ImageView) view.findViewById(R.id.speed_image);
        ra=new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5F);
        ra.setDuration(1000);

         //
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击执行动画
                Log.d("执行动画了","执行了啊");
                view.startAnimation(ra);
                //执行耗时操作

                //完成执行结束动画

            }
        });
        addView(view);

    }

}
