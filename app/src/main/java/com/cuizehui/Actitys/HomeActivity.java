package com.cuizehui.Actitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cuizehui.Adapters.GridviewAdapter;
import com.cuizehui.Adapters.ViewPagerAdapter;
import com.example.cuizehui.mobilesoder.R;

import java.util.ArrayList;
import java.util.List;

import domain.Grbean;
import lecho.lib.hellocharts.model.PieChartData;

public class HomeActivity extends AppCompatActivity {
    GridView gridView;//Grid view
    private ViewPager vp;
    private int[] vppic;
    public PieChartData pieview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionbar();
        initgr();
       // initviewpager();
       //  initpoints();


    }

    private void initActionbar() {
      Toolbar toolbar= (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }


  /*  private void initpoints() {
        LinearLayout ll= (LinearLayout) findViewById(R.id.points);
        for(int i=0;i<vppic.length;i++){
            ImageView imageview=new ImageView(this);
            if(i==0){

                imageview.setBackgroundResource(R.drawable.ic_point_select);
            }else {
                imageview.setBackgroundResource(R.drawable.ic_point_normal);
            }
            ll.addView(imageview);
        }
    }*/


    /**
     * 初始化viewpager
     */
   /* private void initviewpager() {
        vp= (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter vpadapter=new ViewPagerAdapter(getImageviews(),this);
        vp.setAdapter(vpadapter);

    }*/
    /*private List<ImageView> getImageviews() {
       List<ImageView> imageviews=new ArrayList<>();
        vppic =new int[]{
            R.drawable.down_diannaojiuyuan,
            R.drawable.down_ruanjianguanjia,
            R.drawable.down_qingli_group,
            R.drawable.down_shamuma_new,
    };
           for(int i=0;i<vppic.length;i++){
               ImageView imageview=new ImageView(this);
               imageview.setImageResource(vppic[i]);

               imageviews.add(imageview);
           }
        return  imageviews;
    }*/

    /**
     * 初始化gridview
     */
    private void initgr() {
        gridView= (GridView) findViewById(R.id.gr);
        //图片id初始化
        List<Grbean> list=new ArrayList();
        int  pic[]=new int[]{
                R.drawable.gv_software,
                R.drawable.gv_speed,
                R.drawable.gv_fangdao,
                R.drawable.gv_refuse,
                R.drawable.gv_flow,
                R.drawable.gv_more};
        String name[]=new String[]{
             "软件管理","清理加速","防盗追踪","黑名单","流量统计","更多"};

        for(int i=0;i<pic.length;i++)
        {
            Grbean grbean=new Grbean();
            grbean.setName(name[i]);
            grbean.setPicid(pic[i]);
            list.add(grbean);
        }
        //图片adapter
        GridviewAdapter adapter=new GridviewAdapter(this,list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 2:
                        //防丢失
                        Intent intent1 =new Intent(HomeActivity.this,LostfindActivity.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        //黑名单
                        Intent intent2 =new Intent(HomeActivity.this,BlackmanActivity.class);
                        startActivity(intent2);
                        break;
                    case 5:
                        //更多
                        Intent intent5 =new  Intent(HomeActivity.this,SetActivity.class);
                        startActivity(intent5);
                        break;
                    case 0:
                        //软件管理
                        Intent intent3=new Intent(HomeActivity.this,ApkManagerActivity.class);
                        startActivity(intent3);
                        break;
                    case 1:
                        //清理加速
                        Intent intent4=new Intent(HomeActivity.this,ProgressActivity.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent6=new Intent(HomeActivity.this,FlowDataActivity.class);
                        startActivity(intent6);
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
