package com.yh.mohudaily.adapter;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.waynell.videolist.visibility.items.ListItem;
import com.waynell.videolist.visibility.scroll.ItemsProvider;
import com.waynell.videolist.widget.TextureVideoView;
import com.yh.mohudaily.R;
import com.yh.mohudaily.entity.VideoBean;
import com.yh.mohudaily.util.LogUtil;

/**
 * Created by YH on 2016/12/6.
 */

public class VideoHolder extends RecyclerView.ViewHolder implements TextureVideoView.MediaPlayerCallback, ListItem {
    public final View mView;
    public final TextView title;
    public final ImageView controller;
    public final TextureVideoView video;
    public final ProgressBar progressBar;
    public VideoBean mItem;


    public VideoHolder(View view) {
        super(view);
        mView = view;
        title = (TextView) view.findViewById(R.id.video_title);
        controller = (ImageView) view.findViewById(R.id.video_controll);
        video = (TextureVideoView) view.findViewById(R.id.video_view);
        progressBar = (ProgressBar) view.findViewById(R.id.video_progress);
        video.setMediaPlayerCallback(this);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(video.isPlaying()){
                    video.pause();
                }else {
                    video.start();
                }
            }
        });
    }

    private MediaPlayer mediaPlayer;

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        LogUtil.LogE("video onPrepared");
        this.mediaPlayer = mediaPlayer;
        progressBar.setVisibility(View.GONE);
        mediaPlayer.pause();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        LogUtil.LogE("video onCompletion:");

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
        LogUtil.LogE("video onVideoSizeChanged");
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        LogUtil.LogE("video onInfo");
        return false;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void setActive(View view, int i) {
        TextureVideoView video = (TextureVideoView) view;
        video.start();

    }

    @Override
    public void deactivate(View view, int i) {
        TextureVideoView video = (TextureVideoView) view;
        video.pause();
    }

}
