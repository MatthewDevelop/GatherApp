package com.foxconn.matthew.gatherapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.LogTime;
import com.foxconn.matthew.gatherapp.R;
import com.foxconn.matthew.gatherapp.activity.ZhihuNewsDetailActivity;
import com.foxconn.matthew.gatherapp.gson.Story;
import com.foxconn.matthew.gatherapp.gson.TopStory;
import com.foxconn.matthew.gatherapp.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew on 2017/11/20.
 */

public class ZhihuNewsAdapter extends RecyclerView.Adapter<ZhihuNewsAdapter.ViewHolder> {
    private static final String TAG = "ZhihuNewsAdapter";
    private static final String STORY_ID="story_id";
    private List<Story> mStories;
    private Context mContext;
    @BindView(R.id.top_story_image)
    ImageView mImageView;
    @BindView(R.id.top_story_title)
    TextView mTextView;

    public ZhihuNewsAdapter(List<Story> stories, Context context) {
        this.mStories = stories;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.zhihu_news_item, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getAdapterPosition();
                Intent intent=new Intent(mContext, ZhihuNewsDetailActivity.class);
                intent.putExtra(STORY_ID,mStories.get(holder.getAdapterPosition()).id);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleText.setText(mStories.get(position).title);
        Glide.with(mContext).load(mStories.get(position).images[0])
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .dontAnimate()
                .centerCrop()
                .into(holder.titleImage);
    }

    @Override
    public int getItemCount() {
        //LogUtil.e(TAG,mStories.size()+"");
        return mStories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView titleImage;
        @BindView(R.id.title)
        TextView titleText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
