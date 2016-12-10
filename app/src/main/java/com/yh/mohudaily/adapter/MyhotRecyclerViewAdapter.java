package com.yh.mohudaily.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;

import java.util.List;

/**
 */
public class MyhotRecyclerViewAdapter extends RecyclerView.Adapter<MyhotRecyclerViewAdapter.ViewHolder> {

    private final List<HotNewsInfo.Hot> mValues;
    private OnHotFragmentInteractionListener mListener;

    public MyhotRecyclerViewAdapter(List<HotNewsInfo.Hot> items) {
        mValues = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem.getThumbnail()!=null){
            Picasso.with(MohuDaily.getInstance())
                    .load(holder.mItem.getThumbnail())
                    .placeholder(R.mipmap.mohu)
                    .into(holder.mIcon);
        }
        holder.mTitle.setText(holder.mItem.getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHotFragmentInteraction(holder.mItem);
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
        public ImageView mIcon;
        public final TextView mTitle;
        public final TextView mContentView;
        public HotNewsInfo.Hot mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIcon = (ImageView) view.findViewById(R.id.icon);
            mTitle = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface OnHotFragmentInteractionListener{
        void onHotFragmentInteraction(HotNewsInfo.Hot item);
    }
    public void setOnHotFragmentInteractionListener(OnHotFragmentInteractionListener listener){
        mListener = listener;
    }
}
