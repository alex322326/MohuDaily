package com.yh.mohudaily.module.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.MyBeautyRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.mvp.contract.BeautyImageContract;
import com.yh.mohudaily.entity.BeautyImageBean;
import com.yh.mohudaily.mvp.presenter.BeautyImagePresenterImpl;
import com.yh.mohudaily.util.ToastUtil;

import java.util.ArrayList;

/**
 * 主题 页面
 */
public class BeautyFragment extends RxLazyFragment implements BeautyImageContract.View, MyBeautyRecyclerViewAdapter.OnItemClickListener {

    private BeautyImagePresenterImpl beautyImagePresenter;
    private ArrayList<BeautyImageBean> images = new ArrayList<>();
    private MyBeautyRecyclerViewAdapter myBeautyRecyclerViewAdapter;
    private static int currentPage = 1;
    private boolean isRefresh = false;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public BeautyFragment() {
    }

    public static BeautyFragment newInstance() {
        BeautyFragment fragment = new BeautyFragment();
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
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleview.setLayoutManager(staggeredGridLayoutManager);
        myBeautyRecyclerViewAdapter = new MyBeautyRecyclerViewAdapter(images);
        myBeautyRecyclerViewAdapter.setOnItemClickListener(this);
        recycleview.setAdapter(myBeautyRecyclerViewAdapter);
        beautyImagePresenter = new BeautyImagePresenterImpl(this);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                beautyImagePresenter.getBeautyImages(1);
            }
        });

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        beautyImagePresenter.getBeautyImages(1);
        currentPage++;
        isFirstLoad = false;
    }

    /**
     * 数据加载成功回调
     * @param images
     */
    @Override
    public void loadBeautyImageSuccess(ArrayList<BeautyImageBean> images) {
        if(!isRefresh){
            this.images.addAll(images);
        }else {
            this.images.clear();
            this.images.addAll(images);
            currentPage=2;
        }
        isRefresh =false;
        swipe_layout.setRefreshing(isRefresh);
        myBeautyRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadBeautyImageFailure(Throwable e) {
        ToastUtil.showToast(">_<,出错了！" + e.toString());
    }

    @Override
    public void scrollToListBottom() {
        super.scrollToListBottom();
        beautyImagePresenter.getBeautyImages(currentPage++);
    }


    @Override
    public void onItemClick(BeautyImageBean item) {
        //TODO 大图浏览

    }
}
