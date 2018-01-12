package com.foxconn.matthew.gatherapp.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.foxconn.matthew.gatherapp.base.BaseActivityWithActionBar;
import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.fragment.ZhihuNewsFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivityWithActionBar {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        mFragmentManager=getSupportFragmentManager();
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container,new ZhihuNewsFragment());
        mFragmentTransaction.commit();
    }


}
