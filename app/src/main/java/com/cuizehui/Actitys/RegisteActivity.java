package com.cuizehui.Actitys;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cuizehui.sharedprefences.Sptools;
import com.example.cuizehui.mobilesoder.R;

public class RegisteActivity extends Activity {
    String password,loginname;
    private EditText passwordev, nameev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        initEV();
        initButton();
    }

    private void initButton() {
        Button registbotton= (Button) findViewById(R.id.register_loginmassage);
        registbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判空
                //判空
                password=passwordev.getText().toString();
                loginname=nameev.getText().toString();
                if(TextUtils.isEmpty(password)||TextUtils.isEmpty(loginname)){
                    Toast.makeText(getApplicationContext(),"输入的内容为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    //写入配置信息
                    Sptools.saveString(getApplicationContext(),"loginname",loginname);
                    Sptools.saveString(getApplicationContext(),"loginpassword",password);
                    finish();
                }
                //提示注册成功后关闭
            }
        });
    }

    private void initEV() {
         passwordev= (EditText) findViewById(R.id.register_password_ev);
         nameev= (EditText) findViewById(R.id.register_username_ev);

    }
}
