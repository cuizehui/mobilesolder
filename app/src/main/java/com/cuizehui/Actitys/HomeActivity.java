package com.cuizehui.Actitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActionbar();
        initnegtive();
        initgr();
       // initviewpager();
       //  initpoints();


    }

    private void initnegtive() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent(HomeActivity.this,problemActivity.class);

                switch (item.getItemId()){
                    case R.id.menu_home:
                        drawer.closeDrawers();
                         break;
                    case R.id.menu_user:
                        startActivity(intent);
                        break;
                    case R.id.menu_feedback:
                        startActivity(intent);
                        break;
                    case R.id.menu_phone:
                        startActivity(intent);
                        break;
                    case R.id.menu_about:
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    private void initActionbar() {
      Toolbar toolbar= (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_more);
        //将按钮显示在上面
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
                R.drawable.gvv_speed,
                R.drawable.gvv_flow,
                R.drawable.gvv_software,
                R.drawable.gvv_refuse,
                R.drawable.gvv_fangdao,
                R.drawable.gvv_more};
        String name[]=new String[]{
                "清理加速","流量统计","软件管理","黑名单","防盗追踪","更多"};

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
                    case 4:
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
                    case 2:
                        //软件管理
                        Intent intent3=new Intent(HomeActivity.this,ApkManagerActivity.class);
                        startActivity(intent3);
                        break;
                    case 0:
                        //清理加速
                        Intent intent4=new Intent(HomeActivity.this,ProgressActivity.class);
                        startActivity(intent4);
                        break;
                    case 1:
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
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
         switch (item.getItemId()){
           case android.R.id.home:
               //负责拉开
               Log.d("执行拉开","拉开");
               drawer.openDrawer(GravityCompat.START);
               break;
       }

        return true;
    }
}
