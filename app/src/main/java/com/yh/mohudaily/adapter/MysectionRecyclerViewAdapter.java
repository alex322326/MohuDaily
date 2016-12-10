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
import com.yh.mohudaily.entity.SectionsInfo;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.util.imgutil.MohuImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MysectionRecyclerViewAdapter extends RecyclerView.Adapter<MysectionRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SectionsInfo.Section> mSections;
    private OnSectionFragmentInteractionListener mListener;
    private final MohuImageLoader loader;

    public MysectionRecyclerViewAdapter(ArrayList<SectionsInfo.Section> items, Context context) {
        loader = MohuImageLoader.build(context);
        mSections = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_section, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mSections.get(position);
        if(holder.mItem.getThumbnail()!=null){
//            Picasso.with(MohuDaily.getInstance())
//                    .load(holder.mItem.getThumbnail())
//                    .placeholder(R.mipmap.mohu)
//                    .into(holder.mIcon);
            String url = holder.mItem.getThumbnail();
            loader.bindBitmap(url,holder.mIcon,100,100,true);
        }
        holder.mTitle.setText(mSections.get(position).getName());
        holder.mContentView.setText(mSections.get(position).getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSectionFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIcon;
        public final TextView mTitle;
        public final TextView mContentView;
        public SectionsInfo.Section mItem;

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
    public interface OnSectionFragmentInteractionListener{
        void onSectionFragmentInteraction(SectionsInfo.Section item);
    }
    public void setOnSectionFragmentInteractionListener(OnSectionFragmentInteractionListener listener){
        mListener = listener;
    }
}
