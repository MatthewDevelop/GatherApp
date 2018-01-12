package com.foxconn.matthew.gatherapp.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.base.BaseFragment;

import butterknife.BindView;

/**
 * @author:Matthew
 * @date:2018/1/3
 * @email:guocheng0816@163.com
 */

public class WanAndroidFragment extends BaseFragment {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void init(Bundle savedInstanceState) {
        loadData();
    }

    private void loadData() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_wan_android;
    }


}
