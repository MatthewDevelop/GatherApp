package com.foxconn.matthew.gatherapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Matthew on 2017/11/22.
 */

public abstract class BaseFragment extends Fragment {
    public final String TAG=getClass().getSimpleName();
    private View mRootView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView=inflater.inflate(getLayoutResId(),container,false);
        mUnbinder= ButterKnife.bind(this,mRootView);
        init(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutResId();
}
