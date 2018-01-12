package com.foxconn.matthew.gatherapp.test.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.base.BaseFragment;
import com.foxconn.matthew.gatherapp.test.adapter.TestPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author:Matthew
 * @date:2018/1/12
 * @email:guocheng0816@163.com
 */

public class PagerTestFragment extends BaseFragment {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    List<ImageView> images;
    List<Integer> imageIds;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            mHandler.sendEmptyMessageDelayed(0,3000);
        }
    };
    private TestPagerAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        mAdapter = new TestPagerAdapter(images,imageIds);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE/2-((Integer.MAX_VALUE/2)%images.size()));
        mHandler.sendEmptyMessageDelayed(0,2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }

    private void initData() {
        images=new ArrayList<>();
        for (int i=0;i<4;i++){
            images.add(new ImageView(getContext()));
        }
        imageIds=new ArrayList<>();
        imageIds.add(R.drawable.a20180108);
        imageIds.add(R.drawable.a20180109);
        imageIds.add(R.drawable.a20180110);
        imageIds.add(R.drawable.a20180111);
        /*ImageView imageView1=new ImageView(getContext());
        imageView1.setImageResource(R.drawable.a20180108);
        ImageView imageView2=new ImageView(getContext());
        imageView2.setImageResource(R.drawable.a20180109);
        ImageView imageView3=new ImageView(getContext());
        imageView3.setImageResource(R.drawable.a20180110);
        ImageView imageView4=new ImageView(getContext());
        imageView4.setImageResource(R.drawable.a20180111);
        images.add(imageView1);
        images.add(imageView2);
        images.add(imageView3);
        images.add(imageView4);*/
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pager_test_layout;
    }
}
