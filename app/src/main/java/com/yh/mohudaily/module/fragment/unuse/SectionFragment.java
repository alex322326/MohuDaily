package com.yh.mohudaily.module.fragment.unuse;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.MysectionRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.mvp.contract.SectionContract;
import com.yh.mohudaily.entity.SectionsInfo;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.mvp.presenter.SectionPresenterImpl;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 * 专栏 页面
 */
public class SectionFragment extends RxLazyFragment implements SectionContract.View, MysectionRecyclerViewAdapter.OnSectionFragmentInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private SectionPresenterImpl sectionPresenter;
    private ArrayList<SectionsInfo.Section> sections = new ArrayList<>();
    private MysectionRecyclerViewAdapter mysectionRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SectionFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SectionFragment newInstance(int columnCount) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Context context = view.getContext();
            if (mColumnCount <= 1) {
                recycleview.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recycleview.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mysectionRecyclerViewAdapter = new MysectionRecyclerViewAdapter(sections,context);
            mysectionRecyclerViewAdapter.setOnSectionFragmentInteractionListener(this);
        recycleview.setAdapter(mysectionRecyclerViewAdapter);
        }



    /**
     * 数据加载方法  Fragment可见时调用
     */
    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        //专栏页面presenter
        sectionPresenter = new SectionPresenterImpl(this);
        sectionPresenter.loadSectionData();
        isFirstLoad = false;
    }


    @Override
    public void loadFailure() {

    }

    @Override
    public void loadSuccess(ArrayList<SectionsInfo.Section> sections) {
        this.sections.addAll(sections);
        mysectionRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSectionFragmentInteraction(SectionsInfo.Section item) {

    }
}
