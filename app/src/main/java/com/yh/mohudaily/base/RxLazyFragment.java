package com.yh.mohudaily.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;
import com.waynell.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.waynell.videolist.visibility.scroll.RecyclerViewItemPositionGetter;
import com.yh.mohudaily.R;


/**
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * Fragment基类
 */
public abstract class RxLazyFragment extends RxFragment
{

    private View parentView;

    private FragmentActivity activity;

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;

    //标志位 fragment是否可见
    protected boolean isVisible;
    //标志位 第一次加载
    public boolean isFirstLoad = true;
    public RecyclerView recycleview;
    public SwipeRefreshLayout swipe_layout;

    public abstract
    @LayoutRes
    int getLayoutResId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state)
    {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        recycleview = (RecyclerView) parentView.findViewById(R.id.list);

        swipe_layout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipe_layout);

        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                    if(layoutManager instanceof GridLayoutManager){
                        //通过LayoutManager找到当前显示的最后的item的position
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof LinearLayoutManager){
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }else if(layoutManager instanceof StaggeredGridLayoutManager){
                        //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                        //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                        lastPosition = findMax(lastPositions);
                    }
                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1){
                        scrollToListBottom();
                    }

                }
            }
        });
        activity = getSupportActivity();
        return parentView;
    }

    public void scrollToListBottom() {

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        finishCreateView(view,savedInstanceState);
    }

    public abstract void finishCreateView(View view, Bundle state);

    @Override
    public void onResume()
    {

        super.onResume();
    }

    @Override
    public void onDestroyView()
    {

        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity)
    {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach()
    {

        super.onDetach();
        this.activity = null;
    }

    public FragmentActivity getSupportActivity()
    {

        return super.getActivity();
    }

    public android.app.ActionBar getSupportActionBar()
    {

        return getSupportActivity().getActionBar();
    }

    public Context getApplicationContext()
    {

        return this.activity == null ? (getActivity() == null ? null :
                getActivity().getApplicationContext()) : this.activity.getApplicationContext();
    }


    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint())
        {
            isVisible = true;
            onVisible();
        } else
        {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible()
    {
        if(isFirstLoad){
            lazyLoad();
        }
    }

    protected void lazyLoad() {}

    protected void onInvisible() {}

    public void scrollHead(){}
    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id)
    {

        return (T) parentView.findViewById(id);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
