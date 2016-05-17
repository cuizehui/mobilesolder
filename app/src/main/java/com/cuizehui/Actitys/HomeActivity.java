package com.cuizehui.Actitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuizehui.Adapters.GridviewAdapter;
import com.cuizehui.Adapters.ViewPagerAdapter;
import com.example.cuizehui.mobilesoder.R;

import java.util.ArrayList;
import java.util.List;

import domain.Grbean;
import lecho.lib.hellocharts.model.PieChartData;

public class HomeActivity extends Activity {
    GridView gridView;//Grid view
    private ViewPager vp;
    private int[] vppic;
    public PieChartData pieview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initgr();
        initviewpager();
        initpoints();


    }


    private void initpoints() {
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
    }


    /**
     * 初始化viewpager
     */
    private void initviewpager() {
        vp= (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter vpadapter=new ViewPagerAdapter(getImageviews(),this);
        vp.setAdapter(vpadapter);

    }
    private List<ImageView> getImageviews() {
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
    }

    /**
     * 初始化gridview
     */
    private void initgr() {
        gridView= (GridView) findViewById(R.id.gr);
        //图片id初始化
        List<Grbean> list=new ArrayList();
        int  pic[]=new int[]{
                R.drawable.down_diannaojiuyuan,
                R.drawable.down_ruanjianguanjia,
                R.drawable.down_qingli_group,
                R.drawable.down_shamuma_new,
                R.drawable.down_tijian,
                R.drawable.down_xiufuie,
                R.drawable.down_xiufuloudong,
                R.drawable.gameoptimize_32,
                R.drawable.down_youhua_group};
        String name[]=new String[]{
             "进程管理","软件管家","清理加速","木马查杀","扫描","设置","流量监控","安全卫士","加速"
        };

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
                    case 0:
                        Intent intent1 =new Intent(HomeActivity.this,LostfindActivity.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 =new Intent(HomeActivity.this,BlackmanActivity.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        Intent intent5 =new  Intent(HomeActivity.this,SetActivity.class);
                        startActivity(intent5);
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
