package com.cuizehui.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cuizehui.Databases.Blackmdb;

import com.example.cuizehui.mobilesoder.R;

import java.util.List;

import domain.BlackmanBean;

/**黑名单listview adapter
 * Created by cuizehui on 2016/4/29.
 */
public class BlackmanlvAdaper  extends BaseAdapter {
Context context;
    List<BlackmanBean> list;
    public BlackmanlvAdaper(Context context , List list){
        this.context=context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        viewHolder holder=null;
        if(convertView==null){
            convertView= layoutInflater.inflate(R.layout.blacklv_item,null);
           holder=new viewHolder();
           holder.numbertv= (TextView) convertView.findViewById(R.id.numberitem_tv);
           holder.modetv= (TextView) convertView.findViewById(R.id.modeitem_tv);
            holder.imageView= (ImageView) convertView.findViewById(R.id.delete_ImageV);
              holder.imageView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      //删除按钮的点击事件

                      showdialog();
                      Blackmdb mdb=Blackmdb.getInstance(context);
                      //数据库删除
                      mdb.deletblack(list.get(position).getPhonenumber());
                     //通知重绘的前提是listview 内的数据改变了
                      list.remove(position);
                      notifyDataSetChanged();

                  }
              });
            convertView.setTag(holder);
        }
        //如果不为把这个holder 给下面
        else {
          holder= (viewHolder) convertView.getTag();
        }
          holder.numbertv.setText(list.get(position).getPhonenumber());
          String s=null;
        switch (list.get(position).getMode()){

            case 1:s="短信拦截";
                break;
            case  2: s="电话拦截";
                break;
            case 3: s="全部拦截";
                break;
        }
          holder.modetv.setText(s);
        return convertView;
    }

    private void showdialog() {

    }

    public final class viewHolder{
     public   TextView numbertv,modetv;
     public ImageView imageView;
  }
}
