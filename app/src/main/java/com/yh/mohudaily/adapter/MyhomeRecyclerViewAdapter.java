package com.yh.mohudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.MohuNewsBean;
import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.module.fragment.HomeFragment;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.imgutil.MohuImageLoader;

import java.util.List;

/**
 */
public class MyhomeRecyclerViewAdapter extends RecyclerView.Adapter<MyhomeRecyclerViewAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1;

    private View headView;
    private final List<StoryBean> mValues;
    private final MohuImageLoader loader;
    private OnHomeFragmentInteractionListener mListener;
    private int headViewSize = 0;
    private boolean isAddHead = false;

    public MyhomeRecyclerViewAdapter(List<StoryBean> items, Context context) {
        loader = MohuImageLoader.build(context);
        mValues = items;
    }

    public void addHeadView(View view) {
        headView = view;
        headViewSize = 1;
        isAddHead = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = headView;
                break;

            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_home2, parent, false);
                break;

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        LogUtil.LogE("size:"+getItemCount()+",data size:"+mValues.size()+",bind position:"+position);
        holder.mItem = mValues.get(position-1);
        if (holder.mItem.getImages() != null) {
            Picasso.with(MohuDaily.getInstance())
                    .load(holder.mItem.getImages()
                            .get(0))
                    .placeholder(R.mipmap.mohu)
                    .error(R.mipmap.mohu)
                    .into(holder.mIconView);
        }
        holder.mContentView.setText(holder.mItem.getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onHomeFragmentInteraction(holder.mItem);
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if (headViewSize == 1 && position == 0) {
            type = TYPE_HEADER;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return mValues.size() + headViewSize;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ImageView mIconView;
        public TextView mContentView;
        public StoryBean mItem;

        public ViewHolder(View view) {
            super(view);
            if(view==headView)return;
            mView = view;
            mIconView = (ImageView) view.findViewById(R.id.icon);
            mContentView = (TextView) view.findViewById(R.id.content);

        }

    }

    public interface OnHomeFragmentInteractionListener {
        void onHomeFragmentInteraction(StoryBean item);
    }

    public void setOnHomeFragmentInteractionListener(OnHomeFragmentInteractionListener listener) {
        mListener = listener;
    }


}
