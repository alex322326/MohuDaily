package com.yh.mohudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.MohuNewsBean;
import com.yh.mohudaily.entity.StoryBean;

import java.util.List;

/**
 * Created by hcc on 2016/10/3 16:08
 * 100332338@qq.com
 * <p>
 * 活动中心adapter
 */

public class HomeContentAdapter extends AbsRecyclerViewAdapter
{

    private List<StoryBean> stories;

    public HomeContentAdapter(RecyclerView recyclerView, List<StoryBean> stories)
    {
        super(recyclerView);
        this.stories = stories;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_home_content_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position)
    {

        if (holder instanceof ItemViewHolder)
        {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            StoryBean storyBean = stories.get(position);
//            Picasso.with(getContext())
//                    .load(storyBean.getImages().get(0))
//                    .into(itemViewHolder.mImage);

            itemViewHolder.mTitle.setText(storyBean.getTitle());
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount()
    {

        return stories.size();
    }

    private class ItemViewHolder extends ClickableViewHolder
    {

        ImageView mImage;

        TextView mTitle;

        ImageView mState;

        public ItemViewHolder(View itemView)
        {

            super(itemView);
            mImage = $(R.id.item_image);
            mTitle = $(R.id.item_title);
        }
    }
}
