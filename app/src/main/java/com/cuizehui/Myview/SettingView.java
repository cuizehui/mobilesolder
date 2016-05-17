package com.cuizehui.Myview;

import android.content.Context;
import android.location.GpsStatus;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private View view;

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
         initview();
         String name= attrs.getAttributeValue("http://schemas.android.com/apk/com.example.cuizehui.mobilesoder","name");
         String current=attrs.getAttributeValue("http://schemas.android.com/apk/com.example.cuizehui.mobilesoder","context");
        checkname.setText(name);
        checkcurrent.setText(current);

    }
    public  void itemOnlickLisner(OnClickListener listener){
        view.setOnClickListener(listener);
    }


    private void initview() {
        //将布局添加到自定义组合控件上
        LayoutInflater inflater= LayoutInflater.from(getContext());
        view= inflater.inflate(R.layout.setcenter_item,null);
        addView(view);
         checkname= (TextView) view.findViewById(R.id.checkbox_name);
         checkcurrent= (TextView) view.findViewById(R.id.checkbox_current);
         checkbox= (CheckBox) view.findViewById(R.id.box_check);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getContext(),"选中",Toast.LENGTH_SHORT).show();
                    checkcurrent.setText("已关闭");
                }else{
                    checkcurrent.setText("已开启");
                }
            }
        });

    }




}
