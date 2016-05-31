package com.cuizehui.Actitys;

import android.app.Activity;
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
import android.widget.Toast;

import com.cuizehui.Myview.SettingView;
import com.cuizehui.Services.BlackService;
import com.cuizehui.Services.Lostfoundervice;
import com.cuizehui.Services.RockService;
import com.cuizehui.sharedprefences.Sptools;
import com.cuizehui.utils.ServiceUtils;
import com.example.cuizehui.mobilesoder.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

public class SetActivity extends AppCompatActivity {
    public SettingView blacksetting,rocksetting;
    public Toolbar tb;
    private SettingView lostfindsetting;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
       initactionbar();
       initview();
       initevent();
    }

    private void initactionbar() {
       tb= (Toolbar) findViewById(R.id.tool_bar);

       setSupportActionBar(tb);
       ActionBar ab=getSupportActionBar();
        ab.setTitle("设置中心");
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.back);
            ab.setDisplayHomeAsUpEnabled(true);

        }

    }

    private void initevent() {
     blacksetting.itemOnlickLisner(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             blacksetting.checkbox.setChecked(!blacksetting.checkbox.isChecked());
             Intent intent =new Intent(SetActivity.this, BlackService.class);
            if(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.BlackService")==false){
                startService(intent);
            }else{
                stopService(intent);
            }


         }
     });
     rocksetting.itemOnlickLisner(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            rocksetting.checkbox.setChecked(!rocksetting.checkbox.isChecked());
             Intent intent =new Intent(SetActivity.this,RockService.class);
             if(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.RockService")){

                 stopService(intent);

             }else{
                 startService(intent);
             }


         }
     });
        lostfindsetting.itemOnlickLisner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetActivity.this,Lostfoundervice.class);
                if(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.RockService")==false){
                    if(TextUtils.isEmpty(Sptools.getString(getApplicationContext(),"安全号码"))||TextUtils.isEmpty(
                            Sptools.getString(getApplicationContext(),"安全口令"))){
                           Toast.makeText(getApplicationContext()," 请先设置安全口令和安全号码",Toast.LENGTH_LONG).show();
                    }else {

                        startService(intent);
                        lostfindsetting.checkbox.setChecked(!lostfindsetting.checkbox.isChecked());

                    }
                }else{
                    stopService(intent);
                    lostfindsetting.checkbox.setChecked(!lostfindsetting.checkbox.isChecked());

                }




            }
        });
    }

    private void initview() {
         blacksetting= (SettingView) findViewById(R.id.setting_black);
         blacksetting.checkbox.setChecked(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.BlackService"));
         blacksetting.checkcurrent.setText(setcurrent( ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.BlackService")));

        rocksetting=(SettingView)findViewById(R.id.setting_rock);
        rocksetting.checkcurrent.setText(setcurrent( ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.RockService")));
        rocksetting.checkbox.setChecked(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.RockService"));

        lostfindsetting=(SettingView)findViewById(R.id.setting_lostfound);
        lostfindsetting.checkbox.setChecked(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.Lostfoundervice"));
        lostfindsetting.checkcurrent.setText(setcurrent( ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.Lostfoundervice")));
    }

public String setcurrent(Boolean flag){
    String s;
    if(flag==true){
        s="已开启";
    }
    else {
        s="已关闭";
    }
   return s;
}

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
