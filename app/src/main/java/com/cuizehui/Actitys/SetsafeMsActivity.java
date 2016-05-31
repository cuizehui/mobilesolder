package com.cuizehui.Actitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cuizehui.Services.Lostfoundervice;
import com.cuizehui.sharedprefences.Sptools;
import com.example.cuizehui.mobilesoder.R;

public class SetsafeMsActivity extends Activity {
    EditText safenumberev,safekey;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setsafe_ms);
       initview();
    }

    private void initview() {
       safenumberev= (EditText) findViewById(R.id.safe_numberev);
       safekey= (EditText) findViewById(R.id.safe_keyev);
        next= (Button) findViewById(R.id.set_safeBT);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(safekey.getText())||TextUtils.isEmpty(safenumberev.getText())){
                    Toast.makeText(getApplicationContext(),"输入为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    Sptools.saveString(getApplicationContext(),"安全号码",safenumberev.getText().toString());
                    Sptools.saveString(getApplicationContext(),"安全口令",safekey.getText().toString());
                    Toast.makeText(getApplicationContext(),"设置成功",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"设置结果"+Sptools.getString(getApplicationContext(),"安全号码")+"安全口令"+Sptools.getString(getApplicationContext(),"安全口令"),Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
}
