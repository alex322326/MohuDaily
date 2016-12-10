package com.yh.mohudaily.module.fragment.unuse;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.MythemeRecyclerViewAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.mvp.contract.ThemesContract;
import com.yh.mohudaily.entity.ThemesBean;
import com.yh.mohudaily.module.OnListFragmentInteractionListener;
import com.yh.mohudaily.mvp.presenter.ThemesPresenterImpl;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 * 主题 页面
 *
 */
public class ThemeFragment extends RxLazyFragment implements ThemesContract.View, MythemeRecyclerViewAdapter.OnThemeFragmentInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ThemesPresenterImpl themesPresenter;
    private MythemeRecyclerViewAdapter mythemeRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ThemeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ThemeFragment newInstance(int columnCount) {
        ThemeFragment fragment = new ThemeFragment();
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
            mythemeRecyclerViewAdapter = new MythemeRecyclerViewAdapter(themes);
            mythemeRecyclerViewAdapter.setOnThemeFragmentInteractionListener(this);
            recycleview.setAdapter(mythemeRecyclerViewAdapter);
        }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        themesPresenter = new ThemesPresenterImpl(this);
        themesPresenter.loadSections();
        isFirstLoad = false;
    }

    @Override
    public void sectionsLoading() {

    }
    private ArrayList<ThemesBean.Theme> themes = new ArrayList<>();
    @Override
    public void sectionsLoaded(ArrayList<ThemesBean.Theme> sections) {
        themes.addAll(sections);
        mythemeRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onThemeFragmentInteraction(ThemesBean.Theme item) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
