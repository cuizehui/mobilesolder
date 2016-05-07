package com.cuizehui.Actitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cuizehui.Adapters.BlackmanlvAdaper;
import com.cuizehui.Databases.Blackmdb;
import com.example.cuizehui.mobilesoder.R;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import domain.BlackmanBean;

public class BlackmanActivity extends Activity {
    ListView bmlistView;//黑名单列表
    ProgressBar probar; //进度条
    TextView nodatatv;
    Button addblackbutton;//添加黑名单按钮
    Blackmdb db;//数据库操做类
    AlertDialog alertDialog;//对话框
    BlackmanlvAdaper blvadapter;//黑名单adapter
    List<BlackmanBean> blacklist; //黑名单list
    // 短信模式是1 手机模式是2 全部模式是3
    int modesms = 0;
    int modephone = 0;
    private final int  DownLoad=1;
    private final int  Finishdate=2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackman);
        bmlistView = (ListView) findViewById(R.id.black_man_lv);
        probar = (ProgressBar) findViewById(R.id.black_bar);
        nodatatv= (TextView) findViewById(R.id.black_tv);
        nodatatv.setText("没有添加数据");
        //初始化数据
        initdata();
        //初始化listview

        //初始化添加按钮
        initbtadd();

        //adapter

       handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case DownLoad:

                         probar.setVisibility(View.VISIBLE);
                         nodatatv.setVisibility(View.GONE);
                         bmlistView.setVisibility(View.GONE);

                        break;
                    case Finishdate:
                        if(blacklist.size()!=0){
                            initlistview();
                            probar.setVisibility(View.GONE);
                            nodatatv.setVisibility(View.GONE);
                            bmlistView.setVisibility(View.VISIBLE);
                            blvadapter.notifyDataSetChanged();

                        }else {
                            probar.setVisibility(View.GONE);
                            nodatatv.setVisibility(View.VISIBLE);
                            bmlistView.setVisibility(View.GONE);
                        }
                      break;
                }
            }
        };
    }

   private void initpopwindow(){
       PopupWindow popupWindow=new PopupWindow();
   }
    /**
     * 初始化数据
     * 获取数据这个方法在子线程中操作
     */
    private void initdata() {
        db = Blackmdb.getInstance(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {

                //显示获取进度条
                Message message=new Message();
                message.what=DownLoad;
                handler.sendMessage(message);
                //休眠两秒显示进度条
                SystemClock.sleep(100);
               //显示数据
                blacklist = db.queueblack();
                Message message1=new Message();
                message1.what=Finishdate;
                handler.sendMessage(message1);
                Log.d("执行","执行");

            }
        }).start();
    }

    /**
     * 初始化添加按钮
     * 1首先初始化的dialog,
     * 2处理数据添加和listview
     */
    private void initbtadd() {
        addblackbutton = (Button) findViewById(R.id.add_blackman_bt);
        addblackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showdialog();

            }
        });

    }

    private void showdialog() {
        //通过AlertDialog.builer创建一个dialog  并给了一个view布局！！
        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(BlackmanActivity.this);
        //一定通过这个布局文件找控件
        final View view = View.inflate(BlackmanActivity.this, R.layout.addblackdailog_layout, null);
        Button add_blackmessage = (Button) view.findViewById(R.id.add_blackmessage);
        Button cancel = (Button) view.findViewById(R.id.cecel_bt);

        //checkBox 的初始化
        final CheckBox smscheck = (CheckBox) view.findViewById(R.id.sms_check);
        final CheckBox phonecheck = (CheckBox) view.findViewById(R.id.phone_check);

        //获取输入的号码
        final EditText add_black_et = (EditText) view.findViewById(R.id.addblack_et);

        //给添加按钮设置点击事件
        add_blackmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.valueOf(add_black_et.getText());


                if (smscheck.isChecked() || phonecheck.isChecked()) {
                    if (smscheck.isChecked()) {
                        modesms = 1;
                    } else {
                        modesms = 0;
                    }

                    if (phonecheck.isChecked()) {
                        modephone = 2;
                    } else {
                        modephone = 0;
                    }
                    int mode = modephone + modesms;
                    db.addblack(text, mode);
                    //执行数据库添加语句

                } else {
                    Toast.makeText(getApplicationContext(), "至少选择一种拦截模式", Toast.LENGTH_LONG).show();

                }

                alertDialog.dismiss();
                //
                blacklist.clear();
                initdata();
                //通知list adatper更新



            }
        });

        //给取消按钮设置点击事件
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogbuilder.setView(view);
        //创建对话框 ，为了做对话框的关闭
        alertDialog = dialogbuilder.create();
        alertDialog.show();

    }

    /**
     * 初始化黑名单listview
     */

    private void initlistview() {

        blvadapter = new BlackmanlvAdaper(this, blacklist);
        bmlistView.setAdapter(blvadapter);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
//解决回退BUG
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        blacklist.clear();
    }
}
