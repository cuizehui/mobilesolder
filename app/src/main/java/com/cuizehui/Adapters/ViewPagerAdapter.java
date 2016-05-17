package com.cuizehui.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;

/**
 * Created by cuizehui on 2016/5/11.
 */
public class ViewPagerAdapter extends PagerAdapter {
    List<ImageView> imageviews ;
    Context context;
    public ViewPagerAdapter(List<ImageView> imageviews, Context context) {
             this.imageviews=imageviews;
         this.context=context;
    }
//个数
    @Override
    public int getCount() {
        return imageviews.size();
    }
//是否由对象生成界面 ，，如果不一样就生成
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
//从viewGroup 中移除
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageviews.get(position));
    }
// 添加到组里面 这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageviews.get(position));
        return imageviews.get(position);
    }
}
