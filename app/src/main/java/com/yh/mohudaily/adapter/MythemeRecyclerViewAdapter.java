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
import com.yh.mohudaily.entity.ThemesBean;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;

import java.util.List;

/**
 */
public class MythemeRecyclerViewAdapter extends RecyclerView.Adapter<MythemeRecyclerViewAdapter.ViewHolder> {

    private final List<ThemesBean.Theme> mValues;
    private OnThemeFragmentInteractionListener mListener;

    public MythemeRecyclerViewAdapter(List<ThemesBean.Theme> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_theme2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTitleView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getDescription());
        if(holder.mItem.getThumbnail()!=null){
            Picasso.with(MohuDaily.getInstance())
                    .load(holder.mItem.getThumbnail())
                    .placeholder(R.mipmap.mohu)
                    .into(holder.mIcon);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onThemeFragmentInteraction(holder.mItem);
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
        public final TextView mTitleView;
        public final TextView mContentView;
        private final ImageView mIcon;
        public ThemesBean.Theme mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIcon = (ImageView) view.findViewById(R.id.icon);
            mTitleView = (TextView) view.findViewById(R.id.title);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface OnThemeFragmentInteractionListener{
        void onThemeFragmentInteraction(ThemesBean.Theme item);
    }
    public void setOnThemeFragmentInteractionListener(OnThemeFragmentInteractionListener listener){
        mListener = listener;
    }
}
