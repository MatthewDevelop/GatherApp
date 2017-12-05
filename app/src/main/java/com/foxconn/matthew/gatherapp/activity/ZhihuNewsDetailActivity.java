package com.foxconn.matthew.gatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.base.BaseActivity;
import com.foxconn.matthew.gatherapp.gson.NewsDetail;
import com.foxconn.matthew.gatherapp.utils.HttpUtil;
import com.foxconn.matthew.gatherapp.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Matthew on 2017/11/27.
 */

public class ZhihuNewsDetailActivity extends BaseActivity {
    private static final String TAG = "ZhihuNewsDetailActivity";
    private static final String STORY_ID = "story_id";
    @BindView(R.id.tv_title)
    TextView mTv_title;
    @BindView(R.id.iv_image)
    ImageView mIv_bg;
    @BindView(R.id.article_detail)
    WebView mWv_content;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private NewsDetail mNewsDetail;

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        int id = intent.getIntExtra(STORY_ID, -1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (id != -1) {
            loadData(id);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_zhihu_news_detail;
    }

    private void loadData(int id) {
        HttpUtil.getNewsDetailById(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ZhihuNewsDetailActivity.this, "网络异常，稍后重试~", Toast.LENGTH_SHORT).show();
//                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseContent = response.body().string();
                LogUtil.e(TAG, responseContent);
                mNewsDetail = new Gson().fromJson(responseContent, NewsDetail.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        collapsingToolbarLayout.setTitle(mNewsDetail.title);
//                        mTv_title.setText(mNewsDetail.title);
                        Glide.with(ZhihuNewsDetailActivity.this)
                                .load(mNewsDetail.image)
                                .placeholder(R.drawable.holder)
                                .error(R.drawable.holder)
                                .dontAnimate()
                                .centerCrop()
                                .into(mIv_bg);
                        mWv_content.loadDataWithBaseURL(null, mNewsDetail.content, "text/html", "utf-8", null);
                    }
                });
            }
        });
    }
}
