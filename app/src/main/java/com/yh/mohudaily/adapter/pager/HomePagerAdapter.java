package com.yh.mohudaily.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yh.mohudaily.R;
import com.yh.mohudaily.module.fragment.BeautyFragment;
import com.yh.mohudaily.module.fragment.HomeFragment;
import com.yh.mohudaily.module.fragment.HotFragment;
import com.yh.mohudaily.module.fragment.VideoFragment;

/**
 * Created by hcc on 16/8/4 14:12
 * 100332338@qq.com
 * <p/>
 * 主界面Fragment模块Adapter
 */
public class HomePagerAdapter extends FragmentPagerAdapter
{

    private final String[] TITLES;
//    private Fragment[] fragments;
    private Fragment[] fragments;
    public HomePagerAdapter(FragmentManager fm, Context context)
    {

        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position)
    {
        if (fragments[position] == null)
        {
            switch (position)
            {
                case 0:
                    fragments[position] = HomeFragment.newInstance(1);
                    break;
                case 1:
                    fragments[position] = BeautyFragment.newInstance();
                    break;
                case 2:
//                    fragments[position] = SectionFragment.newInstance(1);
                    fragments[position]= VideoFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = HotFragment.newInstance(1);
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
