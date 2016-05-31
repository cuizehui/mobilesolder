package com.cuizehui.Myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuizehui.mobilesoder.R;

/**
 * Created by cuizehui on 2016/5/16.
 * 自定义组合控件
 * 设置的组合控件
 */
public class SettingView extends LinearLayout {
    public TextView checkname;
    public TextView checkcurrent;
    public CheckBox checkbox;
    public ImageView imageView;
    private View view;

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
         initview();
         TypedArray ta= context.obtainStyledAttributes(attrs,R.styleable.SettingView);
         String name=ta.getString(R.styleable.SettingView_name);
         String current=ta.getString(R.styleable.SettingView_context);
         Drawable drawable = ta.getDrawable(R.styleable.SettingView_image);

         checkname.setText(name);
         checkcurrent.setText(current);
         imageView.setImageDrawable(drawable);
         ta.recycle();
    }
    public  void itemOnlickLisner(OnClickListener listener){
        view.setOnClickListener(listener);
    }


    private void initview() {
        //将布局添加到自定义组合控件上
        LayoutInflater inflater= LayoutInflater.from(getContext());
        view= inflater.inflate(R.layout.setcenter_item,null);
        addView(view);
         imageView= (ImageView) view.findViewById(R.id.service_image);
         checkname= (TextView) view.findViewById(R.id.checkbox_name);
         checkcurrent= (TextView) view.findViewById(R.id.checkbox_current);
         checkbox= (CheckBox) view.findViewById(R.id.box_check);
         checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkcurrent.setText("已开启");
                }else{
                    checkcurrent.setText("已关闭");
                }
            }
        });

    }




}
