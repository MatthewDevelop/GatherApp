package com.foxconn.matthew.gatherapp.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxconn.matthew.gatherapp.base.BaseActivityWithActionBar;
import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.adapter.ZhihuNewsAdapter;
import com.foxconn.matthew.gatherapp.gson.LatestNews;
import com.foxconn.matthew.gatherapp.gson.Story;
import com.foxconn.matthew.gatherapp.gson.TopStory;
import com.foxconn.matthew.gatherapp.utils.HttpUtil;
import com.foxconn.matthew.gatherapp.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Matthew on 2017/11/20.
 */

public class ZhihuNewsActivity extends BaseActivityWithActionBar {
    private LatestNews mLatestNews;
    private List<String> topStoryImages=new ArrayList<>();
    private List<String> topStoryTitles=new ArrayList<>();
    private List<Story> mStories=new ArrayList<>();
    private ZhihuNewsAdapter mNewsAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.bgaBanner)
    BGABanner mBGABanner;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected int getContentViewResId() {
        return R.layout.activity_zhihu_news;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        mBGABanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner bgaBanner, View view, Object o, int i) {
                Toast.makeText(ZhihuNewsActivity.this, "Clicked~", Toast.LENGTH_SHORT).show();
            }
        });
        mBGABanner.setAdapter(new BGABanner.Adapter<ImageView,String>() {
            @Override
            public void fillBannerItem(BGABanner bgaBanner, ImageView imageView, String s, int i) {
                Glide.with(ZhihuNewsActivity.this)
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
        mNewsAdapter=new ZhihuNewsAdapter(mStories,ZhihuNewsActivity.this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ZhihuNewsActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mNewsAdapter);
        loadData();
    }


    private void loadData() {
        HttpUtil.getLatestNews(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ZhihuNewsActivity.this, "网络异常，稍后重试~", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                LogUtil.e(TAG, responseContent);
                mLatestNews = new Gson().fromJson(responseContent, LatestNews.class);
                topStoryTitles.clear();
                topStoryImages.clear();
                for(TopStory topStory:mLatestNews.top_stories){
                    topStoryImages.add(topStory.image);
                    topStoryTitles.add(topStory.title);
                }
                mStories.clear();
                for(Story story:mLatestNews.stories){
                    mStories.add(story);
                }
                LogUtil.e(TAG,mStories.size()+"");
                runOnUiThread(new Runnable() {
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


}
