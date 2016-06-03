package com.cuizehui.Actitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cuizehui.mobilesoder.R;

public class problemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        TextView textView= (TextView) findViewById(R.id.textme);
        textView.setText("qq:454776824\n" +
                "nela,泥蜡\n" +
                "有更好的建议可以联系我,欢迎大家和我交流。\n" +
                "学习积累博客地址：\n" +
                "http://blog.csdn.net/cuizehui123 \n" +"记录了源码和某些功能的实现。\n" );
    }
}
