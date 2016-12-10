package com.yh.mohudaily.module;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yh.mohudaily.MohuDaily;
import com.yh.mohudaily.R;
import com.yh.mohudaily.mvp.contract.NewsContentContract;
import com.yh.mohudaily.entity.NewsContentBean;
import com.yh.mohudaily.entity.NewsExtra;
import com.yh.mohudaily.entity.StoryBean;
import com.yh.mohudaily.mvp.presenter.NewsContentPresenterImpl;
import com.yh.mohudaily.util.Constant;
import com.yh.mohudaily.util.HtmlUtil;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.SharePrefUtil;
import com.yh.mohudaily.util.ToastUtil;
import com.yh.mohudaily.view.MySwipeBackActivity;
import com.yh.mohudaily.view.SwipeBackLayout;


public class NewsContentActivity extends AppCompatActivity implements NewsContentContract.View {
    private ImageView header_img;
    private NewsContentPresenterImpl newsContentPresenter;
    private WebView mWebView;

    private ActionBar mActionBar;
    private TextView content_title;
    //新闻额外信息
    private MenuItem itemCommentNum;
    private MenuItem itemPariseNum;
    private MenuItem itemParise;
    private int popularity;
    private NewsExtra mExtra;
    //分享链接
    private String share_url;
    private int story_id;
    private String story_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        story_id = getIntent().getIntExtra("story_id",0);
        story_title = getIntent().getStringExtra("story_title");
        SwipeBackLayout swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.bind();
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.body_web);
        header_img = (ImageView) findViewById(R.id.content_header);
        content_title = (TextView) findViewById(R.id.tv_content_title);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
//        mCollapsingToolbarLayout.setTitleEnabled(true);
        mActionBar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, share_url);
                intent.setType("text/plain");
                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(intent, "分享到"));
            }
        });
        content_title.setText(story_title);
        //公司详情
//        mWebView.loadData(getHtmlData(act.getDescription()), "text/html; charset=utf-8", "utf-8");
    }

    /**
     * 数据初始化 请求页面内容
     */
    private void initData() {
        if(story_id!=0){
            newsContentPresenter = new NewsContentPresenterImpl(this);
            newsContentPresenter.getNewsContent(story_id+"");

        }

    }

    @Override
    public void getNewsContentFailure() {
        LogUtil.LogE("failure");
    }

    @Override
    public void getNewsContentSuccess(NewsContentBean content) {
        newsContentPresenter.getNewsExtras(content.getId()+"");
        share_url = content.getShare_url();
        //为header设置图片
        if(content.getImage()!=null){
            Picasso.with(MohuDaily.getInstance()).load(content.getImage()).into(header_img);
        }
        String htmlData = HtmlUtil.createHtmlData(content);
        mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void getNewsExtrasFailure() {
        ToastUtil.showToast("加载额外信息失败...");
    }

    @Override
    public void getNewsExtrasSuccess(NewsExtra extra) {
        mExtra = extra;
        itemCommentNum.setTitle(extra.getComments());
        itemCommentNum.setVisible(true);
        itemPariseNum.setVisible(true);
        itemPariseNum.setTitle(extra.getPopularity());
        popularity = Integer.parseInt(extra.getPopularity());
        LogUtil.LogE("extra:"+extra.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_content, menu);
        itemCommentNum = menu.findItem(R.id.menu_action_comment_num);
        itemPariseNum = menu.findItem(R.id.menu_action_parise_num);
        itemParise = menu.findItem(R.id.menu_action_parise);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_action_fav:
                //查看新闻推荐者
                return true;

            case R.id.menu_action_comment:
                // 查看新闻评论
                Intent intent = new Intent(NewsContentActivity.this,NewsCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("extra",mExtra);
                intent.putExtra("id",story_id);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;

            case R.id.menu_action_parise:
                //执行点赞动画
                AnimationUtils.loadAnimation(NewsContentActivity.this, R.anim.anim_small);
                itemParise.setIcon(R.drawable.praised);
                ToastUtil.showToast( "+1s" );
                int mohuzhi = SharePrefUtil.getInt(Constant.MOHUZHI, 0);
                SharePrefUtil.putInt(Constant.MOHUZHI,++mohuzhi);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
