package com.foxconn.matthew.gatherapp.test.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.foxconn.matthew.gatherapp.MyApp;

import java.util.List;

/**
 * @author:Matthew
 * @date:2018/1/12
 * @email:guocheng0816@163.com
 */

public class TestPagerAdapter extends PagerAdapter {

    List<ImageView> images;
    List<Integer> imageIds;

    public TestPagerAdapter(List<ImageView> images, List<Integer> imageIds) {
        this.images = images;
        this.imageIds=imageIds;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view=images.get(position%images.size());
        Glide.with(MyApp.getContext()).load(imageIds.get(position%imageIds.size())).into(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
