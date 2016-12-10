package com.yh.mohudaily.module.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.MyhotRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.entity.HotNewsInfo;
import com.yh.mohudaily.mvp.contract.HotNewsContract;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.mvp.presenter.HotNewsPresenterImpl;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.ToastUtil;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HotFragment extends RxLazyFragment implements HotNewsContract.View, MyhotRecyclerViewAdapter.OnHotFragmentInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private HotNewsPresenterImpl hotNewsPresenter;
    private ArrayList<HotNewsInfo.Hot> hots =new ArrayList<>();
    private MyhotRecyclerViewAdapter myhotRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HotFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HotFragment newInstance(int columnCount) {
        HotFragment fragment = new HotFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.LogE("onCreate");
        hotNewsPresenter = new HotNewsPresenterImpl(this);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    public void finishCreateView(View view, Bundle state) {
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            myhotRecyclerViewAdapter = new MyhotRecyclerViewAdapter(hots);
            myhotRecyclerViewAdapter.setOnHotFragmentInteractionListener(this);
            recyclerView.setAdapter(myhotRecyclerViewAdapter);
        }


    }

    @Override
    protected void lazyLoad() {
        if(hotNewsPresenter!=null){
            hotNewsPresenter.loadHotNews();
            isFirstLoad = false;
        }
    }

    @Override
    public void newsLoadFailure() {

    }

    @Override
    public void newLoadSuccess(ArrayList<HotNewsInfo.Hot> hots) {
        this.hots.addAll(hots);
        myhotRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHotFragmentInteraction(HotNewsInfo.Hot item) {
        ToastUtil.showToast("item click");
    }

}
