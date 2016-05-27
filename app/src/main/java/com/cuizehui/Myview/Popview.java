package com.cuizehui.Myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cuizehui.mobilesoder.R;

/**
 * Created by cuizehui on 2016/5/24.
 */
public class Popview extends LinearLayout {
    private final View view;
    Drawable drawable;
    public Popview(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater=LayoutInflater.from(context);
           view =inflater.inflate(R.layout.pop_item,null);
        TextView tvname= (TextView) view.findViewById(R.id.popitem_name);
        ImageView im=(ImageView)view.findViewById(R.id.popitem_pic);
         //这里没有处理好图片资源是一个引用类型的属性 所以处理掉了！！！
        TypedArray ta= context.obtainStyledAttributes(attrs,R.styleable.Popview);
        String name=  ta.getString(R.styleable.Popview_popitemname);
        if(name==null){
            name="";
        }
        tvname.setText(name);
        Drawable d = ta.getDrawable(R.styleable.Popview_src);
        if (d != null) {
            drawable=d;
        } else {
            throw new RuntimeException("图像资源为空");
        }
        im.setImageDrawable(drawable);
        ta.recycle();
        addView(view);
    }
    public void onPopClickevent(OnClickListener listener){
        Log.d("执行了pop项点击事件","执行了！");
     view.setOnClickListener(listener);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

}
