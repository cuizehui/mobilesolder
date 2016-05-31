package com.cuizehui.Actitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.cuizehui.Services.Lostfoundervice;
import com.cuizehui.sharedprefences.Sptools;
import com.example.cuizehui.mobilesoder.R;

/**
 * 功能1的Activity负责注册和登陆的界面
 */
public class LostfindActivity extends AppCompatActivity {
    private EditText passwordev,usernameev;
    private String password,loginname;

    //1 输入安全号码
    //2 开启服务

    //设置安全号码 设置安全口令 比对登陆账号和密码！
    //1比对账号密码没有则注册
    //2设置安全号码和安全口令
    //3开启服务
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factionone);
        initEV();
        initButton();
        initActionbar();
    }

    private void initEV() {
        passwordev= (EditText) findViewById(R.id.password);
        usernameev= (EditText) findViewById(R.id.username);

    }

    private void initButton() {
        //注册按钮
    Button register= (Button) findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                     //判空
                    //匹配配置信息
                    Intent intent =new Intent(LostfindActivity.this, RegisteActivity.class);
                    startActivity(intent);
                    /*
                    Sptools.getString(getApplicationContext(),"loginname");
                    Sptools.getString(getApplicationContext(),"loginpassword");*/
                    //提示注册成功后跳转

                }
            });
        //登陆按钮
     Button login= (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(passwordev.getText())||TextUtils.isEmpty(usernameev.getText())){
                        Toast.makeText(getApplicationContext(),"数据为空",Toast.LENGTH_SHORT).show();

                    }
                    else {
                          password=passwordev.getText().toString();
                          loginname=usernameev.getText().toString();
                          if(matchLogemassege(getApplicationContext(),loginname,password)){
                            //跳转到 设置安全指令和安全手机号的界面
                                  Intent intent  =new Intent(LostfindActivity.this,SetsafeMsActivity.class);
                                  startActivity(intent);
                                  finish();
                          }
                          else {
                            Toast.makeText(getApplicationContext(),"输入有误",Toast.LENGTH_SHORT).show();
                          }
                    }

                }
            });
    }

    /**
     * 比对账号名和密码
     */
    private  Boolean matchLogemassege(Context context, String loginname , String password) {
        Boolean flags=false;
        String logi= Sptools.getString(context,"loginname");
        String logn= Sptools.getString(context,"loginpassword");
        if (logi!=null||logn!=null) {

            if(logi.equals(loginname)|| logn.equals(password)){
                flags=true;
            }else {
                flags=false;
            }

        }
        else {
            Toast.makeText(getApplicationContext(),"没有该用户信息",Toast.LENGTH_SHORT).show();
        }

        Log.d("输入的密码和账号",loginname+":"+password+"数据库中的密码和账号："+Sptools.getString(context,"loginname")+":"+Sptools.getString(context,"loginpassword"));
        Log.d("匹配结果",""+flags);

        return flags;
    }
    //开启的服务
    private void startLostfindService() {
        Intent intent =new Intent(LostfindActivity.this, Lostfoundervice.class);
        startService(intent);
    }

    private void initActionbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("防盗追踪");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.back);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_factionone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home)
        {
            finish();
        }
        else {

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
