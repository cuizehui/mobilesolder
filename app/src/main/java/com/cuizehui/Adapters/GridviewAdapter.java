package com.cuizehui.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuizehui.mobilesoder.R;

import java.util.List;

import domain.Grbean;

/**初始界面 Gridview 适配器
 * Created by cuizehui on 2016/4/23.
 *
 */
public class GridviewAdapter extends BaseAdapter{
   Context context;
    List <Grbean>list;
    public  GridviewAdapter(Context context,List list){
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
    //这里不用convertview缓存
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取布局
        viewHolder holder=null;
        if(convertView==null){
            holder=new viewHolder();
            LayoutInflater inflater =LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.gritem_layout, null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.item_iv);
            holder.textView= (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(holder);
        }
        else{
            holder= (viewHolder) convertView.getTag();
        }
        holder.imageView.setBackgroundResource(list.get(position).getPicid());
        holder.textView.setText(list.get(position).getName());
        return convertView;
    }
    public final class viewHolder{
      public   ImageView imageView;
     public    TextView textView;
    }
}
