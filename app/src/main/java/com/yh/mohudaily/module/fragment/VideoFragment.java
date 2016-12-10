package com.yh.mohudaily.module.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter;
import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.MyVideoRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.entity.VideoBean;
import com.yh.mohudaily.mvp.contract.VideoContract;
import com.yh.mohudaily.mvp.presenter.VideoPresenterImpl;
import com.yh.mohudaily.util.ToastUtil;

import java.util.ArrayList;

/**
 * 主题 页面
 */
public class VideoFragment extends RxLazyFragment implements MyVideoRecyclerViewAdapter.OnItemClickListener, VideoContract.View {

    private ArrayList<VideoBean> videos = new ArrayList<>();
    private static int currentPage = 1;
    private boolean isRefresh = false;
    private MyVideoRecyclerViewAdapter myVideoRecyclerViewAdapter;
    private VideoPresenterImpl videoPresenter;
    private SingleListViewItemActiveCalculator mCalculator;
    private int mScrollState;


    public VideoFragment() {
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {
        Context context = view.getContext();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myVideoRecyclerViewAdapter = new MyVideoRecyclerViewAdapter(videos,recycleview);
        myVideoRecyclerViewAdapter.setOnItemClickListener(this);
        recycleview.setAdapter(myVideoRecyclerViewAdapter);
        mCalculator = new SingleListViewItemActiveCalculator(myVideoRecyclerViewAdapter,
                new RecyclerViewItemPositionGetter(manager, recycleview));
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mScrollState = newState;
                if(newState == RecyclerView.SCROLL_STATE_IDLE && recyclerView.getAdapter().getItemCount() > 0){
                    mCalculator.onScrollStateIdle();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCalculator.onScrolled(mScrollState);
            }
        });
        videoPresenter = new VideoPresenterImpl(this);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                videoPresenter.getVideos(1);
                currentPage =1;
            }
        });

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(videoPresenter!=null){
            videoPresenter.getVideos(1);
            currentPage++;
            isFirstLoad = false;
        }
    }



    @Override
    public void scrollToListBottom() {
        super.scrollToListBottom();
        videoPresenter.getVideos(currentPage++);
    }



    @Override
    public void onItemClick(VideoBean item) {

    }
    /**
     * 数据加载成功回调
     * @param videos
     */
    @Override
    public void loadVideoSuccess(ArrayList<VideoBean> videos, int currentpage) {
        if(!isRefresh){
            this.videos.addAll(videos);
        }else {
            this.videos.clear();
            this.videos.addAll(videos);
            currentPage=2;
        }
        isRefresh =false;
        swipe_layout.setRefreshing(isRefresh);
        myVideoRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void loadVideoFailure(Throwable e) {
        ToastUtil.showToast(">_<,出错了！" + e.toString());
    }

    @Override
    protected void onInvisible() {
//        myVideoRecyclerViewAdapter.onDetachedFromRecyclerView(recycleview);
    }
}
