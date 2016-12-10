package com.yh.mohudaily.module;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.pager.CommentsPagerAdapter;
import com.yh.mohudaily.entity.NewsExtra;
import com.yh.mohudaily.util.LogUtil;

import java.io.Serializable;

/**
 * Created by YH on 2016/11/15.
 */
public class NewsCommentActivity extends AppCompatActivity{

    private ViewPager mViewPager;
    private Toolbar toolBar;
    private SlidingTabLayout slidingTabs;
    private String[] titles = new String[]{"长评(%s)","短评(%s)"};
    private int id;
    private NewsExtra extra;
    private CommentsPagerAdapter commentsPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.LogE("NewsCommentActivity CREATE...");
        Bundle extras = getIntent().getExtras();
        extra = (NewsExtra) extras.getSerializable("extra");
        id = getIntent().getIntExtra("id",0);
        setContentView(R.layout.activity_comment);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        slidingTabs = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        setSupportActionBar(toolBar);

        initViewPager();
    }

    private void initViewPager() {
        if(extra!=null){
            titles[0] = String.format(titles[0],extra.getLong_comments());
            titles[1] = String.format(titles[1],extra.getShort_comments());
        }
        commentsPagerAdapter = new CommentsPagerAdapter(getSupportFragmentManager(),NewsCommentActivity.this,titles,id==0?"":id+"");
        mViewPager.setAdapter(commentsPagerAdapter);
        slidingTabs.setViewPager(mViewPager);

    }
}
