package com.yh.mohudaily.adapter;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.widget.TextureVideoView;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.CommentInfo;
import com.yh.mohudaily.entity.VideoBean;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyVideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoHolder> implements ItemsProvider {

    private final List<VideoBean> mValues;
    private OnItemClickListener mListener;
    private RecyclerView recyclerView;
    public MyVideoRecyclerViewAdapter(List<VideoBean> items,RecyclerView recyclerView) {
        mValues = items;
        this.recyclerView = recyclerView;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_video_item, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getText());

        holder.video.setVideoPath(mValues.get(position).getVideo_uri());
        holder.video.start();
//        holder.comment_likes.setText(holder.mItem.getLikes()+"");
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

    @Override
    public ListItem getListItem(int position) {
        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(position);
        if (holder instanceof ListItem) {
            return (ListItem) holder;
        }
        return null;
    }

    @Override
    public int listItemSize() {
        return getItemCount();
    }


//    public class ViewHolder extends RecyclerView.ViewHolder implements TextureVideoView.MediaPlayerCallback,ListItem,ItemsProvider {
//        public final View mView;
//        public final TextView title;
//        public final TextureVideoView video;
//        public MediaPlayer mediaPlayer;
//        public VideoBean mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            title = (TextView) view.findViewById(R.id.video_title);
//            video = (TextureVideoView) view.findViewById(R.id.video_view);
//            video.setMediaPlayerCallback(this);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + title.getText() + "'";
//        }
//
//        @Override
//        public void onPrepared(MediaPlayer mediaPlayer) {
//            this.mediaPlayer = mediaPlayer;
//        }
//
//        @Override
//        public void onCompletion(MediaPlayer mediaPlayer) {
//
//        }
//
//        @Override
//        public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
//
//        }
//
//        @Override
//        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
//
//        }
//
//        @Override
//        public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
//            return false;
//        }
//
//        @Override
//        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//            return false;
//        }
//
//        @Override
//        public void setActive(View view, int i) {
//
//        }
//
//        @Override
//        public void deactivate(View view, int i) {
//
//        }
//
//        @Override
//        public ListItem getListItem(int i) {
//            return null;
//        }
//
//        @Override
//        public int listItemSize() {
//            return 0;
//        }
//    }

    public interface OnItemClickListener{
        void onItemClick(VideoBean item);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
