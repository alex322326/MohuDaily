package com.yh.mohudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.BeautyImageBean;
import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.util.imgutil.MohuImageLoader;
import com.yh.mohudaily.util.imgutil.ResizeTransformation;

import java.util.List;

/**
 */
public class MyBeautyRecyclerViewAdapter extends RecyclerView.Adapter<MyBeautyRecyclerViewAdapter.ViewHolder> {

    private final List<BeautyImageBean> mValues;
    private OnItemClickListener mListener;

    public MyBeautyRecyclerViewAdapter(List<BeautyImageBean> items) {
        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.images_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem.getImg()!=null){
            Picasso.with(MohuDaily.getInstance())
                    .load(holder.mItem.getImg())
                    .transform(new ResizeTransformation())
                    .placeholder(R.mipmap.mohu)
                    .into(holder.img);
        }
        holder.mTitle.setText(holder.mItem.getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onItemClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView img;
        public final TextView mTitle;
        public BeautyImageBean mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            img = (ImageView) view.findViewById(R.id.img_beauty);
            mTitle = (TextView) view.findViewById(R.id.img_title);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public interface OnItemClickListener{
        void onItemClick(BeautyImageBean item);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
