package com.cuizehui.Actitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cuizehui.Services.Lostfoundervice;
import com.cuizehui.sharedprefences.Sptools;
import com.example.cuizehui.mobilesoder.R;

/**
 * 功能1的Activity
 */
public class LostfindActivity extends Activity {

 Button startbutton;//开启服务的按钮
    EditText  savenumber_ev;//输入号码
    TextView  currentservice_tv;//当前服务开启状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factionone);
        initdata();

        startbutton= (Button) findViewById(R.id.start_lostfoundservice);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果号码框为空
                if((savenumber_ev.getText().toString()).equals("")){

                    Toast.makeText(LostfindActivity.this,"请输入安全号码",Toast.LENGTH_LONG).show();
                }
                else {
                    //如果服务已经开启了就关闭并清空数据
                    if(Sptools.getsetsp(LostfindActivity.this,"servicebotton").equals("on")){
                        Sptools.savesetsp(LostfindActivity.this,"safenumber",null);
                        Sptools.savesetsp(LostfindActivity.this,"servicebotton","off");
                        Intent intent =new Intent(LostfindActivity.this, Lostfoundervice.class);
                        currentservice_tv.setText(Sptools.getsetsp(LostfindActivity.this,"servicebotton"));
                        savenumber_ev.setText(Sptools.getsetsp(LostfindActivity.this,"safenumber"));
                        savenumber_ev.setEnabled(true);
                        stopService(intent);
                    }
                    //如果服务没开启 就开启并失去焦点
                    else {
                        String text = String.valueOf(savenumber_ev.getText());
                        Sptools.savesetsp(LostfindActivity.this,"safenumber",text);
                        Sptools.savesetsp(LostfindActivity.this,"servicebotton","on");
                        Intent intent =new Intent(LostfindActivity.this, Lostfoundervice.class);
                        currentservice_tv.setText(Sptools.getsetsp(LostfindActivity.this,"servicebotton"));
                        savenumber_ev.setEnabled(false);
                        startService(intent);
                    }
                }


            }
        });
    }

    private void initdata() {
        savenumber_ev= (EditText) findViewById(R.id.number_ev);
         savenumber_ev.setText(Sptools.getsetsp(this,"safenumber"));
        currentservice_tv= (TextView) findViewById(R.id.servicecurrent_tv);
        currentservice_tv.setText(Sptools.getsetsp(this,"servicebotton"));
        //给开关设置初始值
       String s= Sptools.getsetsp(LostfindActivity.this,"servicebotton");
//进行判空处理避免空指针异常后面可以进行优化！
        if(s==null){
            Sptools.savesetsp(LostfindActivity.this,"servicebotton","off");
        }
        if(Sptools.getsetsp(LostfindActivity.this,"servicebotton").equals("on")){
              savenumber_ev.setEnabled(false);
            Log.d("执行焦点判断了","！！！");
        }
        else {
            savenumber_ev.setEnabled(true);
            Log.d("执行焦点判断了","！！！");
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
