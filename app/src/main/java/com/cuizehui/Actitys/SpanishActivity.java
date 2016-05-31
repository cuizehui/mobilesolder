package com.cuizehui.Actitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cuizehui.mobilesoder.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import domain.UrlBean;

public class SpanishActivity extends Activity {
    private final int YI_ZHI=1;//版本一致常数
    private final int BU_YIZHI=2;//版本不一致
    private final int WUFUWU=3;//无服务

    private int curryversion;//当前版本
    private String  curryname;  //版本名
    private Handler handler;  //ui线程handler
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanish);
        initview();

        checkversion();
        getcurrentmessage();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case YI_ZHI:
                        //跳转到主界面
                        Intent intent=new Intent(SpanishActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                         break;
                    case BU_YIZHI:
                        //弹出更新
                        toastDialog();
                        Toast.makeText(SpanishActivity.this, "有更新", Toast.LENGTH_LONG).show();
                        break;
                    case WUFUWU:
                       // Toast.makeText(SpanishActivity.this,"无服务",Toast.LENGTH_LONG).show();
                      Intent intent1=new Intent(SpanishActivity.this,HomeActivity.class);
                      startActivity(intent1);
                      finish();
                        break;
                }

            }
        };

    }

    private void toastDialog() {
        AlertDialog.Builder Dialog=new AlertDialog.Builder(this);
        Dialog.setTitle("有更新");
        Dialog.setMessage("有新版本是否更新？");
        Dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(SpanishActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Dialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog.show();

    }

    /**
     * 获取当前本本信息
     */
    private void getcurrentmessage() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            curryversion=packageInfo.versionCode;
               curryname=packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查版本方法，并通知主线程更新ui
     */
    private void checkversion() {

        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url=new URL("http://192.168.0.125/guardversion.josn");

                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();

                    connection.setReadTimeout(3000);
                    connection.setConnectTimeout(3000);
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();

                    if(responseCode==200){
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                        String readLine =null;
                        StringBuffer josncontext=new StringBuffer();
                        while ((readLine=reader.readLine()) != null){

                            josncontext.append(readLine);
                        }
                        UrlBean bean = parsejosn(josncontext);
                        Message message=new Message();

                        if(bean.getVersion().equals(Integer.toString(curryversion))){
                            Log.d("发一了","");
                            message.what=YI_ZHI;
                            handler.sendMessage(message);
                        }else {
                            Log.d("发二了","");
                                  message.what=BU_YIZHI;
                            handler.sendMessage(message);
                        }
                        reader.close();
                        connection.disconnect();

                }
                    else{
                        Log.d("连接失败","!!");
                         Message message=new Message();
                        message.what=WUFUWU;
                        handler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    //处理访问的http 服务地址错误返回无服务提示并跳转。
                    e.printStackTrace();
                    Message message=new Message();
                    message.what=WUFUWU;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    /**
     * 解析服务器返回的版本信息
     * @param josncontext
     */
    private UrlBean parsejosn(StringBuffer josncontext) {
        UrlBean bean=new UrlBean();

        try {
            JSONObject josnobject= new JSONObject(josncontext+"");
            String version= (String) josnobject.get("version");
            String  url= (String) josnobject.get("url");
            String  desc=josnobject.getString("desc");
            bean.setVersion(version);
            bean.setUrl(url);
            bean.setDesc(desc);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     *初始化进入界面
     */
    private void initview() {
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(3000);
        aa.setFillAfter(true);
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.rl);
        relativeLayout.startAnimation(aa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spanish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
