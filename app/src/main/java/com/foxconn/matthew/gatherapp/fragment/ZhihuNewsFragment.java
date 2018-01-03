package com.foxconn.matthew.gatherapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.activity.ZhihuNewsDetailActivity;
import com.foxconn.matthew.gatherapp.adapter.ZhihuNewsAdapter;
import com.foxconn.matthew.gatherapp.base.BaseFragment;
import com.foxconn.matthew.gatherapp.gson.LatestNews_Zhihu;
import com.foxconn.matthew.gatherapp.gson.Story_Zhihu;
import com.foxconn.matthew.gatherapp.gson.TopStory_Zhihu;
import com.foxconn.matthew.gatherapp.utils.HttpUtil;
import com.foxconn.matthew.gatherapp.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Matthew on 2017/11/22.
 */

public class ZhihuNewsFragment extends BaseFragment {

    private static final String STORY_ID="story_id";
    private LatestNews_Zhihu mLatestNewsZhihu;
    private List<String> topStoryImages=new ArrayList<>();
    private List<String> topStoryTitles=new ArrayList<>();
    private List<Integer> topStoryIds=new ArrayList<>();
    private List<Story_Zhihu> mStories=new ArrayList<>();
    private ZhihuNewsAdapter mNewsAdapter;
    private Context mContext;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.bgaBanner)
    BGABanner mBGABanner;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void init(Bundle savedInstanceState) {
        mBGABanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner bgaBanner, View view, Object o, int i) {
                //Toast.makeText(getActivity(), "Clicked~", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), ZhihuNewsDetailActivity.class);
                intent.putExtra(STORY_ID,topStoryIds.get(i));
                startActivity(intent);
            }
        });
        mBGABanner.setAdapter(new BGABanner.Adapter<ImageView,String>() {
            @Override
            public void fillBannerItem(BGABanner bgaBanner, ImageView imageView, String s, int i) {
                Glide.with(getActivity())
                        .load(s)
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder)
                        .dontAnimate()
                        .centerCrop()
                        .into(imageView);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mNewsAdapter=new ZhihuNewsAdapter(mStories,getActivity());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
       /* mRecyclerView.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.HORIZONTAL, 2,getResources().getColor(R.color.devideLine)));*/
        mRecyclerView.setAdapter(mNewsAdapter);
        loadData();
    }

    private void loadData() {
        HttpUtil.getLatestNews(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络异常，稍后重试~", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                LogUtil.e(TAG, responseContent);
                mLatestNewsZhihu = new Gson().fromJson(responseContent, LatestNews_Zhihu.class);
                topStoryTitles.clear();
                topStoryImages.clear();
                topStoryIds.clear();
                for(TopStory_Zhihu topStoryZhihu : mLatestNewsZhihu.top_stories){
                    topStoryImages.add(topStoryZhihu.image);
                    topStoryTitles.add(topStoryZhihu.title);
                    topStoryIds.add(topStoryZhihu.id);
                }
                //mStories.clear();
                for(Story_Zhihu storyZhihu : mLatestNewsZhihu.stories){
                    mStories.add(storyZhihu);
                }
                LogUtil.e(TAG,mStories.size()+"");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBGABanner.setData(topStoryImages,topStoryTitles);
                        mNewsAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.activity_zhihu_news;
    }
}
