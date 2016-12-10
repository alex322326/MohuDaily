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
import com.yh.mohudaily.entity.CommentInfo;
import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LongCommentsRecyclerViewAdapter extends RecyclerView.Adapter<LongCommentsRecyclerViewAdapter.ViewHolder> {

    private final List<CommentInfo.Comment> mValues;
    private OnCommentFragmentInteractionListener mListener;

    public LongCommentsRecyclerViewAdapter(List<CommentInfo.Comment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if(holder.mItem.getAvatar()!=null){
            Picasso.with(MohuDaily.getInstance()).load(holder.mItem.getAvatar()).placeholder(R.mipmap.mohu).into(holder.mAvatar);
        }
        holder.mContentView.setText(mValues.get(position).getContent());
        holder.comment_author.setText(holder.mItem.getAuthor());
//        holder.comment_likes.setText(holder.mItem.getLikes()+"");
        Date date = new Date(holder.mItem.getTime());
        SimpleDateFormat spf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        holder.comment_time.setText(spf.format(date));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCommentFragmentInteraction(holder.mItem);
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
        public final ImageView mAvatar;
        public final TextView mContentView;
        public final TextView comment_likes;
        public final TextView comment_time;
        public final TextView comment_author;
        public CommentInfo.Comment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatar = (ImageView) view.findViewById(R.id.avatar);
            mContentView = (TextView) view.findViewById(R.id.commnet_content);
            comment_likes = (TextView) view.findViewById(R.id.comment_likes);
            comment_time = (TextView) view.findViewById(R.id.comment_time);
            comment_author = (TextView) view.findViewById(R.id.comment_author);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public interface OnCommentFragmentInteractionListener{
        void onCommentFragmentInteraction(CommentInfo.Comment item);
    }
    public void setOnCommentFragmentInteractionListener(OnCommentFragmentInteractionListener listener){
        mListener = listener;
    }
}
