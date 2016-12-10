package com.yh.mohudaily.module;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yh.mohudaily.R;
import com.yh.mohudaily.adapter.pager.HomePagerAdapter;
import com.yh.mohudaily.base.RxLazyFragment;
import com.yh.mohudaily.module.dialog.IconPickDialog;
import com.yh.mohudaily.util.Constant;
import com.yh.mohudaily.util.LogUtil;
import com.yh.mohudaily.util.ToastUtil;
import com.yh.mohudaily.util.imgutil.LocalImageUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends RxAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SlidingTabLayout sliding_tabs;
    private ViewPager mViewpager;
    private HomePagerAdapter mHomeAdapter;
    private long firstTime;
    private CircleImageView mIcon;
    private Toolbar toolbar;
    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 100;
    private static int output_Y = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sliding_tabs = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        initView();
        initViewPager();
    }
    private void initView() {
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxLazyFragment item = (RxLazyFragment) mHomeAdapter.getItem(mViewpager.getCurrentItem());
                item.scrollHead();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header_layout = navigationView.getHeaderView(0);
        mIcon = (CircleImageView) header_layout.findViewById(R.id.user_icon);
        setHeaderImage();
        navigationView.setNavigationItemSelectedListener(this);
        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this).setTitle("头像选择").setSingleChoiceItems(new String[]{"相册", "相机"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        switch (i) {
                            case 0:
                                choseFromPictures();
                                break;
                            case 1:
                                choseFromCamera();
                                break;
                        }

                    }
                }).create().show();
            }


        });
    }
    private void initViewPager() {
        mHomeAdapter = new HomePagerAdapter(getSupportFragmentManager(),
                getApplicationContext());
        mViewpager.setAdapter(mHomeAdapter);
        mViewpager.setCurrentItem(0);
        sliding_tabs.setViewPager(mViewpager);
    }
    /**
     * 相机选取头像
     */
    private void choseFromPictures() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, Constant.CODE_GALLERY_REQUEST);
    }
    /**
     * 相册选取头像
     */
    private void choseFromCamera() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), Constant.IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, Constant.CODE_CAMERA_REQUEST);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
    private Bitmap icon_bitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            ToastUtil.showToast("cancle");
            return;
        }

        switch (requestCode) {
            case Constant.CODE_GALLERY_REQUEST:
                cropRawPhoto(data.getData());
                break;

            case Constant.CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            Constant.IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    ToastUtil.showToast("no sdcard");
                }

                break;

            case Constant.CODE_RESULT_REQUEST:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    icon_bitmap = extras.getParcelable("data");
                    AndPermission.with(this)
                            .requestCode(PERMISSION_WRITE_CODE)
                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .send();

                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, Constant.CODE_RESULT_REQUEST);
    }
    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setHeaderImage() {
        Bitmap bitmap = LocalImageUtil.getDiskBitmap(Environment.getExternalStorageDirectory().getPath() + Constant.IMAGE_FILE_NAME);
        mIcon.setImageBitmap(bitmap);
    }

    private static final int PERMISSION_WRITE_CODE=100;
    private static final int PERMISSION_READ_CODE=101;
    private static final int PERMISSION_CAMERA_CODE=102;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionYes(100)
    private void getWriteYes() {
        setHeaderImage();
    }

    @PermissionNo(100)
    private void getWriteNo() {
        ToastUtil.showToast("获取权限失败！");
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_mofazhi) {
            startActivity(new Intent(MainActivity.this, MohuZhiActivity.class));
        } else if (id == R.id.nav_dailymo) {
            startActivity(new Intent(MainActivity.this,ExtrasActivity.class));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Snackbar sb = Snackbar.make(drawer, "再按一次退出", Snackbar.LENGTH_SHORT);
                sb.getView().setBackgroundColor(getResources().getColor(android.R.color.black));
                sb.show();
                firstTime = secondTime;
            } else {
                finish();
            }
        }
    }


}
