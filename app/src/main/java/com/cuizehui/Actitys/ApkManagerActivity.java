package com.cuizehui.Actitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.ColorRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cuizehui.Myview.Popview;
import com.cuizehui.utils.ApkEngine;
import com.example.cuizehui.mobilesoder.R;

import java.text.Format;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import domain.ApkBean;

public class ApkManagerActivity extends AppCompatActivity {

    private ListView apklv;
    List<ApkBean> apkInfosList=new ArrayList<>();
    List<ApkBean> sysApkList=new ArrayList<>();
    List<ApkBean> nomalApklist=new ArrayList<>();
    private TextView apkflagstv;
    private android.support.v7.widget.Toolbar tb;
    private PopupWindow popupWindow;
    private ProgressBar sdprogressbar, romprogressbar;
    private PackageManager pm;
    private ApkBean clickapkBean;
    private ScaleAnimation sa;
    //POPwindow的view
    private View popview;
    private Handler handler;
    private static int FINISHDATA=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_manager);
        pm = getPackageManager();
        initprogressbar();
        inittoolbar();

        initPpwindow();
        initdata();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        initapklv();
                        break;
                }
            }
        };
    }
//加载数据
    private void initdata() {
        new Thread(){
            @Override
            public void run() {
                apkInfosList = ApkEngine.getApkMassage(getApplicationContext());

                for (ApkBean apk : apkInfosList) {
                    if (apk.getApkflag()) {
                        sysApkList.add(apk);
                    } else {
                        nomalApklist.add(apk);
                    }
                }
                Message message=new Message();
                message.what=FINISHDATA;
                handler.sendMessage(message);
            }
        }.start();

    }

    private void initprogressbar() {
        TextView maxsd = (TextView) findViewById(R.id.sd_maxnumber);
        TextView avilsd = (TextView) findViewById(R.id.sd_avilnumber);
        TextView maxrom = (TextView) findViewById(R.id.rom_maxnumber);
        TextView avilrom = (TextView) findViewById(R.id.rom_avilnumber);
        sdprogressbar = (ProgressBar) findViewById(R.id.apksd_progress);
        romprogressbar = (ProgressBar) findViewById(R.id.apkRom_progress);
        //设置文件剩余空间:数据已经获取
        //Log.d("可用内存","" + android.text.format.Formatter.formatFileSize(this, ApkEngine.getAvilSDSpace()));
        String sdMsp = android.text.format.Formatter.formatFileSize(this, ApkEngine.getMaxSpace());
        String sdVsp = android.text.format.Formatter.formatFileSize(this, ApkEngine.getAvilSDSpace());
        String romMsp = android.text.format.Formatter.formatFileSize(this, ApkEngine.getMaxRomSpace());
        String romVsp = android.text.format.Formatter.formatFileSize(this, ApkEngine.getAvilRomSpace());

        sdprogressbar.setMax(spacetoint(sdMsp));
        sdprogressbar.setProgress(spacetoint(sdVsp));
        romprogressbar.setMax(spacetoint(romMsp));
        romprogressbar.setProgress(spacetoint(romVsp));
        maxsd.setText(android.text.format.Formatter.formatFileSize(this, ApkEngine.getMaxSpace()));
        avilsd.setText(android.text.format.Formatter.formatFileSize(this, ApkEngine.getAvilSDSpace()));
        maxrom.setText(android.text.format.Formatter.formatFileSize(this, ApkEngine.getMaxRomSpace()));
        avilrom.setText(android.text.format.Formatter.formatFileSize(this, ApkEngine.getAvilRomSpace()));
    }

    public int spacetoint(String s) {
        int i = 0;
        String data;
        String[] splite = s.split("[^0-9]");
        data = splite[0];
        float d = Float.parseFloat(data);
        int i1 = (int) d;

        Log.d("分割后的数据", d + ":" + i1 + "");


        return i1;
    }

    private void inittoolbar() {
        tb = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar);
        tb.setTitle("软件管理");
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.drawable.back);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    //顶部菜单栏
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initPpwindow() {

        sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(300);
        popupWindow = new PopupWindow(this);
        popview = LayoutInflater.from(this).inflate(R.layout.apkmanger_popwidow, null);

        popupWindow.setContentView(popview);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(-2);
        //popupWindow.setOutsideTouchable(true);这个方法可能会覆盖掉其他的点击事件！
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Popview share = (Popview) popview.findViewById(R.id.apk_share);
        Popview set = (Popview) popview.findViewById(R.id.apk_set);
        Popview remove = (Popview) popview.findViewById(R.id.apk_remove);
        Popview start = (Popview) popview.findViewById(R.id.apk_start);
        //给popwindow项设置点击事件

        share.onPopClickevent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        set.onPopClickevent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        remove.onPopClickevent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //开启APK
        start.onPopClickevent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntentForPackage = pm.getLaunchIntentForPackage(clickapkBean.getPackgename());
                if (launchIntentForPackage == null) {
                    Toast.makeText(getApplicationContext(), "该app没有启动界面", 0).show();
                    return;
                }
                popupWindow.dismiss();
                startActivity(launchIntentForPackage);

                finish();
            }
        });

    }

//这是一个耗时操作
    private void initapklv() {
        apkflagstv = (TextView) findViewById(R.id.apkflags_tv);
        apklv = (ListView) findViewById(R.id.apkmassage_lv);

        final ApkAdapter apkadapter = new ApkAdapter();
        apklv.setAdapter(apkadapter);
        //设置listview滑动事件
        apklv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //第一个可以看见的项数
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    Log.d("滚动事件执行关闭了", "！！");
                } else {
                }

                if (firstVisibleItem < nomalApklist.size() + 1) {
                    apkflagstv.setText("我的应用" + ":" + nomalApklist.size());
                } else {
                    apkflagstv.setText("系统应用" + ":" + sysApkList.size());
                }
            }
        });

        apklv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * @param parent
             * @param view
             * @param position 标签无点击事件。注意！
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != nomalApklist.size()) {
                    clickapkBean = (ApkBean) apklv.getItemAtPosition(position);
                    int[] Location = new int[2];
                    view.getLocationInWindow(Location);
                    popupWindow.showAtLocation(view.findViewById(R.id.apk_pic), Gravity.RIGHT | Gravity.TOP, Location[0], Location[1]);
                    popview.startAnimation(sa);
                } else {
                    return;
                }

                //需要一个popWindow 处理三个方法结束。。。。

            }
        });
    }


    /**
     * 写成内部类 参数都不用传了
     */
    class ApkAdapter extends BaseAdapter {

        @Override

        public int getCount() {
            return apkInfosList.size() + 1;
        }

        /**
         * 在外部可以掉用listview的getItematposion 从而获取绑定的adapter 从而重复回调这个方法！
         * 这个方法解决项数和list 不匹配的问题
         *
         * @param position
         * @return 返回一个list中的一个匹配项
         */
        @Override
        public ApkBean getItem(int position) {
            ApkBean itemApkBeam = null;
            if (position < nomalApklist.size()) {
                itemApkBeam = nomalApklist.get(position);
            } else {
                itemApkBeam = sysApkList.get(position - 1 - nomalApklist.size());
            }
            return itemApkBeam;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (position == nomalApklist.size()) {
                TextView textview = new TextView(getApplicationContext());
                textview.setText("系统应用");
                textview.setTextColor(Color.argb(255,229,187,129));
                textview.setBackgroundColor(Color.argb(255,161,23,21));
                return textview;
            } else {
                //布局的复用

                //缓存的信息不仅有正常的还有标签所以要去掉标签
                apkviewHolder holder = new apkviewHolder();
                //下面第二个条件是去掉标签获取Tag缓存的关键！
                if (convertView != null && convertView instanceof RelativeLayout) {

                    holder = (apkviewHolder) convertView.getTag();
                } else {
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    convertView = inflater.inflate(R.layout.apklv_item, null);
                    holder.apkname = (TextView) convertView.findViewById(R.id.apk_name);
                    holder.apk_size = (TextView) convertView.findViewById(R.id.apk_size);
                    holder.apkpic = (ImageView) convertView.findViewById(R.id.apk_pic);
                    convertView.setTag(holder);
                }
                holder.apkname.setText(getItem(position).getApkname());
                holder.apk_size.setText(android.text.format.Formatter.formatFileSize(getApplicationContext(), getItem(position).getApKsize()));
                holder.apkpic.setImageDrawable(getItem(position).getApkpicture());
                //第二次重构
                 /*       ApkBean  itemApkBeam=null;
                        if(position<nomalApklist.size()){
                           itemApkBeam =nomalApklist.get(position);
                        } else {
                            itemApkBeam=sysApkList.get(position-1-nomalApklist.size());
                        }
                           holder.apkname.setText(itemApkBeam.getApkname());
                           holder.apkpic.setImageDrawable(itemApkBeam.getApkpicture());

               */
 /*  第一次代码
                        if(position<nomalApklist.size()){
                            holder.apkname.setText(nomalApklist.get(position).getApkname());
                            holder.apkpic.setImageDrawable(nomalApklist.get(position).getApkpicture());
                        }       else {
                            holder.apkname.setText(sysApkList.get(position-1-nomalApklist.size()).getApkname());
                            holder.apkpic.setImageDrawable(sysApkList.get(position-1-nomalApklist.size()).getApkpicture());

                        }*/


                return convertView;
            }

        }

        public final class apkviewHolder {
            public TextView apkname, apk_size;
            public ImageView apkpic;

        }

    }

}
