package com.yh.mohudaily.module.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.LongCommentsRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.mvp.contract.CommentsContract;
import com.yh.mohudaily.entity.CommentInfo;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.mvp.presenter.LongCommentsPresenterImpl;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.ToastUtil;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LongCommentsFragment extends RxLazyFragment implements LongCommentsRecyclerViewAdapter.OnCommentFragmentInteractionListener, CommentsContract.View {

    private static final String NEWS_ID = "news_id";
    private LongCommentsPresenterImpl commentsPresenter;
    //标志位 fragment是否可见
    private LinearLayoutManager linearLayoutManager;
    private LongCommentsRecyclerViewAdapter longCommentsRecyclerViewAdapter;
    private ArrayList<CommentInfo.Comment> comments = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LongCommentsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LongCommentsFragment newInstance(String id) {
        LongCommentsFragment fragment = new LongCommentsFragment();
        Bundle args = new Bundle();
        args.putCharSequence(NEWS_ID, id);
        fragment.setArguments(args);
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
        linearLayoutManager = new LinearLayoutManager(context);
        recycleview.setLayoutManager(linearLayoutManager);
        longCommentsRecyclerViewAdapter = new LongCommentsRecyclerViewAdapter(comments);
        longCommentsRecyclerViewAdapter.setOnCommentFragmentInteractionListener(this);
        recycleview.setAdapter(longCommentsRecyclerViewAdapter);
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE&&linearLayoutManager.findLastVisibleItemPosition()>=linearLayoutManager.getItemCount()-1){
                    ToastUtil.showToast("scroll bottom");
                }
            }
        });
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        if(comments.size()==0){
            commentsPresenter = new LongCommentsPresenterImpl(this);
            Bundle bundle = getArguments();
            commentsPresenter.loadLongComments((String) bundle.get(NEWS_ID));
        }
        isFirstLoad = false;

    }


    /**
     * item点击
     * @param item
     */
    @Override
    public void onCommentFragmentInteraction(CommentInfo.Comment item) {

    }

    @Override
    public void loadLongCommentsSuccess(ArrayList<CommentInfo.Comment> longComments) {
        LogUtil.LogE("load success:"+longComments.size());
        comments.addAll(longComments);
        longCommentsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadShortCommentsSuccess(ArrayList<CommentInfo.Comment> longComments) {
        //not use
    }

    @Override
    public void loadLongCommnetsFailure(Throwable e) {
        ToastUtil.showToast("Comments Error:"+e.toString());
    }

    @Override
    public void loadShoryCommnetsFailure(Throwable e) {
        //not use
    }

    /**
     * Fragment不可见时存储List
     */


}
