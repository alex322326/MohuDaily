package com.yh.mohudaily.module;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yh.mohudaily.R;
import com.yh.mohudaily.mvp.contract.StartIamgeContract;
import com.yh.mohudaily.mvp.presenter.StartIamgePresenterImpl;
import com.yh.mohudaily.util.LogUtil;

public class SplashActivity extends AppCompatActivity implements StartIamgeContract.StartView{
    private StartIamgePresenterImpl startIamgePresenter;
    private ImageView startImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startImage = (ImageView) findViewById(R.id.splash_pic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //加载图片
        startIamgePresenter = new StartIamgePresenterImpl(this);
        startIamgePresenter.loadStartIamge();
    }

    @Override
    public void imageLoadFailure(Throwable e) {
        LogUtil.LogE("imageLoadFailure :"+e.toString());
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void iamgeLoading() {

    }

    @Override
    public void imageLoaded(String imgUrl) {
        LogUtil.LogE("imageLoaded:"+imgUrl);
        Picasso.with(this).load(imgUrl).into(startImage);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(startImage, View.SCALE_X,
                1.0f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(startImage, View.SCALE_Y,
                1.0f, 1.5f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(3000);
        enter.playTogether(scaleX, scaleY);
        enter.setTarget(startImage);
        enter.start();
        enter.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    @Override
    public void setPresenter(StartIamgeContract.Presenter presenter) {
        startIamgePresenter = (StartIamgePresenterImpl) presenter;
    }


}
