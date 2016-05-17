package com.cuizehui.Actitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cuizehui.utils.ReadContantsEngine;
import com.example.cuizehui.mobilesoder.R;

import java.util.ArrayList;
import java.util.List;

import domain.ContactBean;

public class ContactsActivity extends ListActivity implements AdapterView.OnItemClickListener {


    final int LOADDATE=1;

    final int FINISH=2;
    private Handler mainhandler;
    private ProgressDialog dialog;
    private ListView mlistView;
    public List<ContactBean> contacts;
    private MAdapter adapter,adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            contacts= new ArrayList<>();
            mlistView= getListView();
            adapter=new MAdapter(contacts);
            mlistView.setAdapter(adapter);
            initcontactsdata();
            mlistView.setOnItemClickListener(this);

         mainhandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
           switch (msg.what){
               case LOADDATE:
                  showMDialog();
                   break;
               case FINISH:
                   showdata();
                   Log.d("测试",contacts.get(0).getContactname()+"");
                   //因为不能通知改变所以重新设置adapter!!!
                   adapter1=new MAdapter(contacts);
                   mlistView.setAdapter(adapter1);
                   break;

           }


            }
        };

    }


    /**
     * 取消对话框并显示数据
     */
    private void showdata() {

        dialog.dismiss();
        dialog=null;
        //释放资源 不然异常
    }

    /**
     * 加载联系人
     */
    private void initcontactsdata() {
        new Thread(){
            @Override
            public void run() {
                //发送正在获取数据
                Message message=Message.obtain();
                message.what=LOADDATE;
                mainhandler.sendMessage(message);
                //模拟睡眠出现对话框
                SystemClock.sleep(2000);
                //执行数据下载
                //返回了一个新的集合所以不可以更新
                contacts= ReadContantsEngine.readSmslog(ContactsActivity.this);
                //同一个message 避免内存浪费
                message= Message.obtain();
                message.what=FINISH;
                mainhandler.sendMessage(message);

            }
        }.start();
    }

    /**
     * 显示对话框
     */
    private void showMDialog() {
        dialog=new ProgressDialog(ContactsActivity.this);
        dialog.setTitle("注意");
        dialog.setMessage("正在加载请稍后.....");
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContactBean contactBean = contacts.get(position);
        String name = contactBean.getContactname();
        String phone=contactBean.getPhonenumber();
        Intent datas = new Intent();
        datas.putExtra("NAME", name);
        datas.putExtra("PHONE",phone);
        //设置数据
        setResult(1, datas);
        //关闭自己
        finish();
// 这里要传递返回值

    }

    /**
     * listview的adapter
     */
    private    class  MAdapter  extends BaseAdapter{
     List<ContactBean> list;
     public MAdapter(List<ContactBean> list) {
         this.list=list;

     }

     @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=LayoutInflater.from(ContactsActivity.this);
            ContactHolder contactHolder=null;
            if(convertView==null){
                contactHolder=new ContactHolder();
                convertView = inflater.inflate(R.layout.contacts_item,null);
                contactHolder.nametv= (TextView) convertView.findViewById(R.id.contact_name_tv);
                contactHolder.numbertv= (TextView) convertView.findViewById(R.id.contact_number_tv);
                convertView.setTag(contactHolder);
            }
            else {
                contactHolder= (ContactHolder)convertView.getTag();
            }
               contactHolder.numbertv.setText(list.get(position).getContactname());
               contactHolder.nametv.setText(list.get(position).getPhonenumber());

           return convertView;
        }
    }

    /**
     * 缓冲器
     */
    public class ContactHolder{
        TextView  nametv,numbertv;
    }
}
