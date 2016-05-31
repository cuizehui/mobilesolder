package com.cuizehui.Actitys;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.cuizehui.utils.ApkEngine;
import com.cuizehui.utils.FlowEngine;
import com.example.cuizehui.mobilesoder.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import domain.ApkBean;
import domain.FlowBean;

public class FlowDataActivity extends AppCompatActivity {

    private List<ApkBean> allapks;
    //获得的流量数据集合
    private List<FlowBean> mflowBeans= new ArrayList<FlowBean>();
    //
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
    private Handler handler;
    private static int FINISHDATA=1;
    private ProgressBar progressbar;
    private float totalflow;
    private RealTodayFragment realTodayFragment;
    private RealTdSendFragment realTdSendFragment;
    private TodayFlowFragment todayFlowFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_data);
        initActionBar();
        initMenubar();
        initdata();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
               switch (msg.what){
                   case 1:
                       //初始化数据后整理数据
                       initDownBundledata();
                       //显示碎片
                       initfragment();
                       initprogressbar();
                       break;
               }
            }
        };

    }

    private void initprogressbar() {
    progressbar= (ProgressBar) findViewById(R.id.flow_pgbar);
        progressbar.setProgress((int)(totalflow/1048576));
        progressbar.setMax(1024);
        TextView textView= (TextView) findViewById(R.id.flowpg_text);
        textView.setText("今日使用"+""+ Formatter.formatFileSize(getApplicationContext(),TrafficStats.getTotalRxBytes())+"/总流量（1G）");
    }
    private Bundle initDownBundledata() {
     Bundle bundle=new Bundle();
        for(int i=0;i<8;i++){
            bundle.putString("第"+i+"组数据名",mflowBeans.get(i).getApkName());
            bundle.putFloat("第"+i+"组数据值",mflowBeans.get(i).getDownflow());
        }
     return  bundle;
    }
    private Bundle initSendBundledata() {
        Bundle bundle=new Bundle();
        for(int i=0;i<8;i++){
            bundle.putString("第"+i+"组数据名",mflowBeans.get(i).getApkName());
            bundle.putFloat("第"+i+"组数据值",mflowBeans.get(i).getSendflow());
        }
        return  bundle;
    }



    private void initfragment() {
        realTodayFragment=new RealTodayFragment();
        realTodayFragment.setArguments(initDownBundledata());
        FragmentTransaction  transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_rl,realTodayFragment);
        transaction.commit();
    }

    private void initdata() {
     new Thread(){
         @Override
         public void run() {
             //排序
              getFlowBeans(mflowBeans);
              orderFlowBeansbySend(mflowBeans);
              for (FlowBean fb:mflowBeans) {
                 Log.d("流量排序后的统计", fb.getApkName() + ":" + fb.getDownflow() + "发送的流量：" + fb.getSendflow());
              }
              Log.d("总流量",""+ Formatter.formatFileSize(getApplicationContext(),TrafficStats.getTotalRxBytes()));
             totalflow= TrafficStats.getTotalRxBytes();
              Message message=new Message();
              message.what=FINISHDATA;
              handler.sendMessage(message);
         }
     }.start();

    }
    //给集合按照发送量排序
    public void orderFlowBeansbySend(List<FlowBean> flowbeans){
         Collections.sort(flowbeans, new Comparator<FlowBean>() {
            @Override
            public int compare(FlowBean lhs, FlowBean rhs) {
                if(lhs.getSendflow()>=rhs.getSendflow()){
                    return  -1;
                }
                else return 1;
            }
        });

    }
    //给集合按照下载量排序
    public void orderFlowBeansbyDown(List<FlowBean> flowbeans){
        Collections.sort(flowbeans, new Comparator<FlowBean>() {
            @Override
            public int compare(FlowBean lhs, FlowBean rhs) {
                if(lhs.getDownflow()>rhs.getDownflow()){
                    return  -1;
                }
                else return 1;
            }
        });

    }
    //
   public void getFlowBeans(List<FlowBean> flowBeans){

       allapks = ApkEngine.getApkMassage(getApplicationContext());
       for (ApkBean apk : allapks) {
           FlowBean flowbean = new FlowBean();
           flowbean.setApkName(apk.getApkname());
           int uid = apk.getApkuid();
           flowbean.setApkuid(uid);
           long sendflow = FlowEngine.getSendflow(uid);
           flowbean.setSendflow(sendflow);
           long downflow = FlowEngine.getDownflow(uid);
           flowbean.setDownflow(downflow);
           flowBeans.add(flowbean);
           Log.d("流量log", flowbean.getApkName() + ":" + flowbean.getDownflow() + "发送的流量：" + flowbean.getSendflow());
       }

   }

    private void initActionBar() {
        Toolbar tb= (Toolbar) findViewById(R.id.tool_bar);
        tb.setTitle("流量统计");
        setSupportActionBar(tb);
        ActionBar ab=  getSupportActionBar();
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.back);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater menuInflater= getMenuInflater();
        //后面那个参数是父布局 也就是说把这个布局放上去了
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case  R.id.more:
                mMenuDialogFragment.show(fragmentManager,ContextMenuDialogFragment.TAG);
                break;
        }
        return true;
    }

    private  List<MenuObject>   getMenulist(){
        List<MenuObject> menuObjects=new ArrayList<>();

        MenuObject close=new     MenuObject("");
        close.setResource(R.drawable.ic_deletex);



        MenuObject todaydownflow=new MenuObject("今日下载流量");
        todaydownflow.setResource(R.drawable.fl_today);

        MenuObject todaysendflow=new MenuObject("今日发送流量");
        todaysendflow.setResource(R.drawable.fl_today);


        MenuObject historyz = new MenuObject("历史折线");
        historyz.setResource(R.drawable.fl_zhexian);

        MenuObject historyl=new MenuObject("历史柱状");
        historyl.setResource(R.drawable.fl_zhu);
        menuObjects.add(close);
        menuObjects.add(todaydownflow);
        menuObjects.add(todaysendflow);
        menuObjects.add(historyz);
        menuObjects.add(historyl);

        return  menuObjects;
    }
    private  void initMenubar(){
        MenuParams menuParams=new MenuParams();
        menuParams.setMenuObjects(getMenulist());
        menuParams.setActionBarSize((int)getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setClosableOutside(true);
        fragmentManager = getSupportFragmentManager();
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);

        mMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View clickedView, int position) {
                switch (position){
                    case 1:

                            FragmentTransaction  transaction1 = fragmentManager.beginTransaction();
                            transaction1.replace(R.id.fragment_rl,realTodayFragment);
                            transaction1.commit();

                        break;
                    case 2:
                        if(realTdSendFragment==null){
                            realTdSendFragment=new RealTdSendFragment();
                            realTdSendFragment.setArguments(initSendBundledata());
                            FragmentTransaction  transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragment_rl,realTdSendFragment);
                            transaction.commit();
                        }
                        else {
                            FragmentTransaction  transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragment_rl,realTdSendFragment);
                            transaction.commit();

                        }
                        break;
                    case 3:
                        if(todayFlowFragment==null){
                            todayFlowFragment=new TodayFlowFragment();
                            FragmentTransaction  transaction2 = fragmentManager.beginTransaction();
                            transaction2.replace(R.id.fragment_rl,todayFlowFragment);
                            transaction2.commit();
                        }else {
                            FragmentTransaction  transaction2 = fragmentManager.beginTransaction();
                            transaction2.replace(R.id.fragment_rl,todayFlowFragment);
                            transaction2.commit();
                        }
                        break;
                }
            }
        });
    }
}
