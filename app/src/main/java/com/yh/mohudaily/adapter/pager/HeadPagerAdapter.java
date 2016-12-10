package com.yh.mohudaily.adapter.pager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.entity.TopStoryBean;
import com.yh.mohudaily.module.NewsContentActivity;
import com.yh.mohudaily.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by YH on 2016/12/9.
 */
public class HeadPagerAdapter extends PagerAdapter{
    private ArrayList<TopStoryBean> datas =new ArrayList<>();
    private Context mContext;
    public HeadPagerAdapter(ArrayList<TopStoryBean> top_stories, Context context) {
        datas = top_stories;
        mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView((View) object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.item_header, null);
        ImageView image = (ImageView) view.findViewById(R.id.header_page);
        TextView title = (TextView) view.findViewById(R.id.header_title);
        final TopStoryBean entity = datas.get(position);
        String url =entity.getImages();
        title.setText(entity.getTitle());
        Picasso.with(MohuDaily.getInstance()).load(url).into(image);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsContentActivity.class);
                intent.putExtra("story_id",entity.getId());
                intent.putExtra("story_title",entity.getTitle());
                mContext.startActivity(intent);

            }
        });
        return view;

    }
}
