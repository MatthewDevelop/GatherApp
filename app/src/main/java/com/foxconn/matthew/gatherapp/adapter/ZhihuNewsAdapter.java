package com.foxconn.matthew.gatherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.activity.ZhihuNewsDetailActivity;
import com.foxconn.matthew.gatherapp.gson.Story_Zhihu;
import com.foxconn.matthew.gatherapp.gson.TopStory_Zhihu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by Matthew on 2017/11/20.
 */

public class ZhihuNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ZhihuNewsAdapter";
    private static final String STORY_ID = "story_id";
    private List<Story_Zhihu> mStories;
//    List<TopStory_Zhihu> topStorys;
//    private List<String> topStoryImages;
//    private List<String> topStoryTitles;
//    private List<Integer> topStoryIds;
    private Context mContext;
    /*@BindView(R.id.top_story_image)
    ImageView mImageView;
    @BindView(R.id.top_story_title)
    TextView mTextView;*/

    private static final int TYPE_BANNER = 0;
    private static final int TYPE_NORMAL = 1;
    private View mHeader = null;

    public ZhihuNewsAdapter(List<Story_Zhihu> stories/*, List<Integer> topStoryIds, List<String> topStoryImages, List<String> topStoryTitles, List<TopStory_Zhihu> topStorys*/, Context context) {
        this.mStories = stories;
        this.mContext = context;
//        this.topStoryIds = topStoryIds;
//        this.topStoryImages = topStoryImages;
//        this.topStoryTitles = topStoryTitles;
//        this.topStorys = topStorys;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeader != null && viewType == TYPE_BANNER) {
            /*final View bannerView = LayoutInflater.from(mContext).inflate(R.layout.zhihu_news_banner, parent, false);*/
            final BannerHolder bannerHolder = new BannerHolder(mHeader);
            /*bannerHolder.bGABanner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner bgaBanner, View view, Object o, int i) {
                    Intent intent=new Intent(mContext, ZhihuNewsDetailActivity.class);
                    intent.putExtra(STORY_ID,topStorys.get(i).id);
                    mContext.startActivity(intent);
                }
            });*/
            /*bannerHolder.bGABanner.setAdapter(new BGABanner.Adapter<ImageView,String>() {
                @Override
                public void fillBannerItem(BGABanner bgaBanner, ImageView imageView, String s, int i) {
                    Glide.with(mContext)
                            .load(s)
                            .placeholder(R.drawable.holder)
                            .error(R.drawable.holder)
                            .dontAnimate()
                            .centerCrop()
                            .into(imageView);
                }
            });*/
            return bannerHolder;
        } else {
            final View normalView = LayoutInflater.from(mContext).inflate(R.layout.zhihu_news_item, parent, false);
            final NormalViewHolder normalHolder = new NormalViewHolder(normalView);
            normalView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext, ZhihuNewsDetailActivity.class);
                    intent.putExtra(STORY_ID, mStories.get(normalHolder.getAdapterPosition() - 1).id);
                    mContext.startActivity(intent);
                }
            });
            return normalHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_BANNER) {
            //Log.e(TAG, "onBindViewHolder: TYPE_BANNER" );
            //((BannerHolder)holder).bGABanner.setData(topStoryImages,topStoryTitles);
            return;
        } else {
            //Log.e(TAG, "onBindViewHolder: TYPE_NORMAL" );
            position = mHeader == null ? position : position - 1;
            ((NormalViewHolder) holder).titleText.setText(mStories.get(position).title);
            Glide.with(mContext).load(mStories.get(position).images[0])
                    .placeholder(R.drawable.holder)
                    .error(R.drawable.holder)
                    .dontAnimate()
                    .centerCrop()
                    .into(((NormalViewHolder) holder).titleImage);
        }

    }


    @Override
    public int getItemCount() {
        return mHeader == null ? mStories.size() : mStories.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeader == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_BANNER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setHeader(View header) {
        mHeader = header;
    }


    class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView titleImage;
        @BindView(R.id.title)
        TextView titleText;

        public NormalViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeader) {
                return;
            }
            ButterKnife.bind(this, itemView);
        }
    }

    class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bgaBanner)
        BGABanner bGABanner;

        public BannerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
