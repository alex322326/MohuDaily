package com.yh.mohudaily.module.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.pager.HeadPagerAdapter;
import com.yh.mohudaily.adapter.MyhomeRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.entity.TopStoryBean;
import com.yh.mohudaily.mvp.contract.NewsContract;
import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.module.NewsContentActivity;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.mvp.presenter.MohuNewsPresenterImpl;
import com.yh.mohudaily.util.Constant;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.SharePrefUtil;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HomeFragment extends RxLazyFragment implements NewsContract.View, MyhomeRecyclerViewAdapter.OnHomeFragmentInteractionListener {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private MohuNewsPresenterImpl newsPresenter;
    //标志位 fragment是否可见
    private MyhomeRecyclerViewAdapter myhomeRecyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    //日期
    private String currentDate;

    private ArrayList<StoryBean> stories = new ArrayList<>();
    private ArrayList<TopStoryBean> top_stories = new ArrayList<>();
    private int mohuzhi;
    private ViewPager header_viewpager;
    private HeadPagerAdapter headPagerAdapter;
    private CircleIndicator indicator;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mohuzhi = SharePrefUtil.getInt(Constant.MOHUZHI,0);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {
        Context context = view.getContext();
        linearLayoutManager = new LinearLayoutManager(context);
        recycleview.setLayoutManager(linearLayoutManager);
        myhomeRecyclerViewAdapter = new MyhomeRecyclerViewAdapter(stories,context);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View header = layoutInflater.inflate( R.layout.news_header_layout,null);
        header_viewpager = (ViewPager) header.findViewById(R.id.header_viewpager);
        indicator = (CircleIndicator) header.findViewById(R.id.indicator);
        headPagerAdapter = new HeadPagerAdapter(top_stories,context);
        header_viewpager.setAdapter(headPagerAdapter);
        indicator.setViewPager(header_viewpager);
        headPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        myhomeRecyclerViewAdapter.setOnHomeFragmentInteractionListener(this);
        myhomeRecyclerViewAdapter.addHeadView(header);
        recycleview.setAdapter(myhomeRecyclerViewAdapter);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                newsPresenter.loadNews("");
            }
        });
    }

    private boolean isRefresh = false;
    @Override
    protected void lazyLoad() {
        newsPresenter = new MohuNewsPresenterImpl(this);
        newsPresenter.loadNews("");
        isFirstLoad = false;
    }

    /**
     * 新闻列表加载成功
     * @param storieslist
     * @param isLoadMore
     * @param date
     */
    @Override
    public void loadNewsSuccess(ArrayList<StoryBean> storieslist,boolean isLoadMore,String date) {
        currentDate = date;
        //更新刷新列表
        if(isRefresh){
            swipe_layout.setRefreshing(false);
            isRefresh = false;
        }
        //刷新时清空列表
        if(!isLoadMore){
            stories.clear();
        }
        stories.addAll(storieslist);
        //懒加载 通知adapter数据集合发生改变
        myhomeRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadTopNewsSuccess(ArrayList<TopStoryBean> stories) {
        top_stories.clear();
        top_stories.addAll(stories);
        headPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadFailure() {

    }
    @Override
    public void scrollToListBottom() {
        super.scrollToListBottom();
        newsPresenter.loadNews(currentDate);
    }

    /**
     * item点击
     * @param item
     */
    @Override
    public void onHomeFragmentInteraction(StoryBean item) {
//        ToastUtil.showToast("item click:" + item.getId());
        SharePrefUtil.putInt(Constant.MOHUZHI,++mohuzhi);
        Intent intent = new Intent(getActivity(),NewsContentActivity.class);
        intent.putExtra("story_id",item.getId());
        intent.putExtra("story_title",item.getTitle());
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("story",item);
//        intent.putExtras(bundle);
        startActivity(intent);
    }
    /**
     * recycleView平滑滚动到指定位置
     */
    @Override
    public void scrollHead() {
        super.scrollHead();
        recycleview.smoothScrollToPosition(0);
    }
}
