package com.cuizehui.Actitys;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cuizehui.utils.ProgressEngine;
import com.example.cuizehui.mobilesoder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import domain.ProgressBean;

/**
 * 进程管理AM
 */
public class ProgressActivity extends AppCompatActivity {

    private ListView pg_listview;
    private CopyOnWriteArrayList<ProgressBean> sysprogress;
    private CopyOnWriteArrayList<ProgressBean> userprogress;
    private List<ProgressBean> progressBeens;
    private ActivityManager am;
    private PgAdapter pgadapter;
    private int cleannum=0;
    //进程个数信息
    private TextView pgnum;
    //进程内存信息
    private TextView memytext;
    //可用内存数
    private long avilem;
    //释放的内存书
    private long liveem=0;
    //总内存
    private long tolem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initToolbar();
        initLV();
        initprogressbar();
    }

    private void initprogressbar() {
      memytext = (TextView) findViewById(R.id.text_memry);
        pgnum= (TextView) findViewById(R.id.progress_numtv);
        pgnum.setText("进程个数："+(progressBeens.size()));
       tolem=ProgressEngine.getmemeryinfo(this)[0];
         avilem= ProgressEngine.getmemeryinfo(this)[1];

        memytext.setText("总内存信息："+Formatter.formatFileSize(this,tolem)+"   可用内存信息："+Formatter.formatFileSize(this,avilem));
        ProgressBar pbar = (ProgressBar)findViewById(R.id.progress_progressbar);
        String tolermem= Formatter.formatFileSize(this,tolem);
        String avilmem= Formatter.formatFileSize(this,avilem);

        pbar.setProgress(spacetoint(avilmem));
        pbar.setMax(spacetoint(tolermem));
        Log.d("所占比例",""+spacetoint(avilmem)+":"+spacetoint(tolermem));
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

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("进程管理");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_clean,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.clean:
                for (ProgressBean usebean:userprogress){
                    if (usebean.getPackName().equals(getPackageName())) {
                        continue;
                    }
                    cleanprogress(usebean);
                }

                Toast.makeText(this,"清理"+cleannum+"个进程    "+"释放了"+Formatter.formatFileSize(this,liveem)+"内存",Toast.LENGTH_SHORT).show();

                //传说中的迭代异常  既然不能处理总的 采取分批处理
                /* for (ProgressBean progressBean:progressBeens){

                    cleanprogress(progressBean);
                }*/
                break;
        }
        return true;
    }

    private void initLV() {
        pg_listview = (ListView) findViewById(R.id.progress_lv);
        //获取数据：
        sysprogress = new CopyOnWriteArrayList<>();
        userprogress = new CopyOnWriteArrayList<>();
        progressBeens =  ProgressEngine.getProgressBeans(this);
        for (ProgressBean progressbeen : progressBeens) {
            if (progressbeen.isPisSystem()) {
                sysprogress.add(progressbeen);
            } else {
                userprogress.add(progressbeen);
            }
        }

        pgadapter = new PgAdapter();
        pg_listview.setAdapter(pgadapter);
        pg_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(position!=userprogress.size()){
                   ProgressBean clickpg = (ProgressBean) pg_listview.getItemAtPosition(position);

                   cleanprogress(clickpg);
               }
                       }
        });
    }

    private void cleanprogress(ProgressBean clickpg) {
        if (clickpg.getPackName().equals(getPackageName())) {
            Toast.makeText(this,"该进程不能清理",Toast.LENGTH_SHORT).show();
        }else {

            cleannum=cleannum+1;
            liveem=clickpg.getPmemSize()+liveem;
            am= (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            //真实清理进程
            if(clickpg.isPisSystem()){
                am.killBackgroundProcesses(clickpg.getPackName());
                sysprogress.remove(clickpg);
            }
            else {
                am.killBackgroundProcesses(clickpg.getPackName());
                //界面本条数据
                userprogress.remove(clickpg);
                //
            }
            progressBeens.remove(clickpg);
            pgadapter.notifyDataSetChanged();
            pgnum.setText("进程个数："+(progressBeens.size()));

            memytext.setText("总内存信息："+Formatter.formatFileSize(this,tolem)+"   可用内存信息："+Formatter.formatFileSize(this,avilem));

        }

    }

    class PgAdapter extends BaseAdapter {
        public PgAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return progressBeens.size()+1;
        }

        /**
         * 匹配个别项
         *
         * @param position
         * @return
         */
        @Override
        public ProgressBean getItem(int position) {
            ProgressBean progressBean ;
            if (position < userprogress.size()) {

                progressBean = userprogress.get(position);

                return progressBean;
            } else {
                progressBean = sysprogress.get(position - userprogress.size()-1);
                return progressBean;
            }
        }

        //没用
        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == userprogress.size()) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText("用户进程：");
                return textView;
            } else {

                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                PgViewHolder holder = null;
                if (convertView != null && convertView instanceof RelativeLayout) {
                    holder = (PgViewHolder) convertView.getTag();
                } else {
                    holder = new PgViewHolder();
                    convertView = layoutInflater.inflate(R.layout.progress_lvitem, null);
                    holder.iconImageview = (ImageView) convertView.findViewById(R.id.progress_pic);
                    holder.name = (TextView) convertView.findViewById(R.id.progress_name);
                    holder.size = (TextView) convertView.findViewById(R.id.progress_size);
                    convertView.setTag(holder);
                }
                holder.iconImageview.setImageDrawable(getItem(position).getPicon());
                holder.name.setText(getItem(position).getPname());
                holder.size.setText(Formatter.formatFileSize(getApplicationContext(), getItem(position).getPmemSize()));

                return convertView;
            }


        }

        class PgViewHolder {
            ImageView iconImageview;
            TextView name, size;
        }
    }
}
