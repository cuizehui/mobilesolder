package com.cuizehui.Actitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cuizehui.Myview.SettingView;
import com.cuizehui.Services.BlackService;
import com.cuizehui.Services.RockService;
import com.cuizehui.utils.ServiceUtils;
import com.example.cuizehui.mobilesoder.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;

public class SetActivity extends AppCompatActivity {
public SettingView blacksetting,rocksetting;
    public Toolbar tb;


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
             if(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.RockService")==false){
                 startService(intent);
             }else{
                 stopService(intent);
             }


         }
     });
    }

    private void initview() {
         blacksetting= (SettingView) findViewById(R.id.setting_black);
         blacksetting.checkbox.setChecked(ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.BlackService"));
         rocksetting=(SettingView)findViewById(R.id.setting_rock);
        rocksetting.checkbox.setChecked( ServiceUtils.isServiceRunning(SetActivity.this,"com.cuizehui.Services.RockService"));

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
