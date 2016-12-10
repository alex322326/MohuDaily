package com.yh.mohudaily.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yh.mohudaily.module.fragment.LongCommentsFragment;
import com.yh.mohudaily.module.fragment.ShortCommentsFragment;

/**
 * Created by hcc on 16/8/4 14:12
 * 100332338@qq.com
 * <p/>
 * 主界面Fragment模块Adapter
 */
public class CommentsPagerAdapter extends FragmentPagerAdapter
{

    private final String[] TITLES;
//    private Fragment[] fragments;
    private Fragment[] fragments;
    private String id;
    public CommentsPagerAdapter(FragmentManager fm, Context context,String[] titles,String id)
    {

        super(fm);
        TITLES = titles;
        fragments = new Fragment[TITLES.length];
        this.id = id;
    }

    @Override
    public Fragment getItem(int position)
    {
        if (fragments[position] == null)
        {
            switch (position)
            {
                case 0:
                    fragments[position] = LongCommentsFragment.newInstance(id);
                    break;
                case 1:
                    fragments[position] = ShortCommentsFragment.newInstance(id);
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount()
    {

        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {

        return TITLES[position];
    }
}
