package com.cuizehui.Actitys;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

import domain.BlackmanBean;

public class BlackmanActivity extends AppCompatActivity implements OnMenuItemClickListener {
    ListView bmlistView;//黑名单列表
    ProgressBar probar; //进度条
    TextView nodatatv;
    Blackmdb db;//数据库操做类
    AlertDialog alertDialog;//对话框
    BlackmanlvAdaper blvadapter;//黑名单adapter
    List<BlackmanBean> blacklist; //黑名单list
    //不拦截是0 短信模式是1 电话模式是2 全部模式是 3
    int modesms = 0;
    int modephone = 0;
    private final int  DownLoad=1;
    private final int  Finishdate=2;
    public Handler handler;
    private android.support.v4.app.FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private android.support.v7.widget.Toolbar tb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackman);
        tb= (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        tb.setTitle("黑名单");
        setSupportActionBar(tb);
       ActionBar ab=getSupportActionBar();
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.back);
            ab.setDisplayHomeAsUpEnabled(true);
        }
        bmlistView = (ListView) findViewById(R.id.black_man_lv);
        probar = (ProgressBar) findViewById(R.id.black_bar);
        nodatatv= (TextView) findViewById(R.id.black_tv);
        nodatatv.setText("没有添加数据");
         fragmentManager=getSupportFragmentManager();

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
        //初始化数据
        initdata();
        //初始化listview

        initMenubar();
        //adapter
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

    private void initdialog(String number) {
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
           add_black_et.setText(number);
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

   private  List<MenuObject>   getMenulist(){
       List<MenuObject> menuObjects=new ArrayList<>();

       MenuObject close=new  MenuObject(" 关  闭  ");
       close.setResource(R.drawable.ic_deletex);



       MenuObject fromlx=new MenuObject("联系人获取");
       fromlx.setResource(R.drawable.ic_contact);



       MenuObject fromsm = new MenuObject(" 短信获取 ");
       fromsm.setResource(R.drawable.ic_sms);

       MenuObject fromedit=new MenuObject("手动添加");
       fromedit.setResource(R.drawable.ic_edit);
       menuObjects.add(close);
       menuObjects.add(fromlx);
       menuObjects.add(fromsm);
       menuObjects.add(fromedit);

            return  menuObjects;
   }
   private  void initMenubar(){
       MenuParams menuParams=new MenuParams();
       menuParams.setMenuObjects(getMenulist());
       menuParams.setActionBarSize((int)getResources().getDimension(R.dimen.tool_bar_height));
       menuParams.setClosableOutside(true);
       menuParams.setAnimationDuration(60);

       mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
       mMenuDialogFragment.setItemClickListener(this);
   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         switch(item.getItemId()){
            case R.id.more:
                 mMenuDialogFragment.show(fragmentManager,ContextMenuDialogFragment.TAG);

                 break;
             case android.R.id.home:
                 blacklist.clear();
                 finish();

         }
        return super.onOptionsItemSelected(item);
    }

    //解决回退BUG
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    @Override
    public void onMenuItemClick(View clickedView, int position) {
    switch (position){
        case 0:
            break;
        case 1:
            //调用系统电话簿
            startActivityForResult(new Intent(
                    Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
            break;
        case 2:
            Intent dianhuajilu=new Intent(BlackmanActivity.this,ContactsActivity.class);
            startActivityForResult(dianhuajilu,1);
            break;
        case 3:
            initdialog(null);
            break;
    }

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         switch (resultCode){
            case Activity.RESULT_OK:
                ContentResolver reContentResolverol = getContentResolver();
                Uri contactData = data.getData();
                @SuppressWarnings("deprecation")
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();
                String   username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null);
                while (phone.moveToNext()) {
                    String    usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    initdialog(usernumber);
                }
                break;
            case 1:
                if(data!=null){
              String    number=data.getStringExtra("PHONE");
                    initdialog(number);
                }
                break;
        }

    }

}

