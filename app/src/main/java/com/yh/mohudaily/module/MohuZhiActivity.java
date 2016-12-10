package com.yh.mohudaily.module;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.yh.mohudaily.R;
import com.yh.mohudaily.util.Constant;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.SharePrefUtil;
import com.yh.mohudaily.view.MohuView;
import com.yh.mohudaily.view.SwipeBackLayout;

import io.netopen.hotbitmapgg.view.NewCreditSesameView;

/**
 * Created by YH on 2016/12/7.
 */
public class MohuZhiActivity extends AppCompatActivity{

    private final int[] mColors = new int[]{
            0xFFFF80AB,
            0xFFFF4081,
            0xFFFF5177,
            0xFFFF7997
    };
    private MohuView view;
    private RelativeLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mohuzhi = SharePrefUtil.getInt(Constant.MOHUZHI, 0);
        setContentView(R.layout.activity_mohuzhi);
        SwipeBackLayout swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.bind();
        layout = (RelativeLayout) findViewById(R.id.layout);
        view = (MohuView) findViewById(R.id.mohuzhi);
        view.setSesameValues(mohuzhi);
        startColorChangeAnim();
    }

    // The background color gradient animation Simply illustrates the effect Can customize according to your need
    public void startColorChangeAnim()
    {

        ObjectAnimator animator = ObjectAnimator.ofInt(layout, "backgroundColor", mColors);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }
}
