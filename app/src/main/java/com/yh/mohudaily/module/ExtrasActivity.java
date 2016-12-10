package com.yh.mohudaily.module;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yh.mohudaily.R;
import com.yh.mohudaily.util.ToastUtil;
import com.yh.mohudaily.view.SwipeBackLayout;

/**
 * Created by YH on 2016/12/9.
 */
public class ExtrasActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int LOCATION_PERMISSION_CODE=100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);
        SwipeBackLayout swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.bind();

        findViewById(R.id.btn_request1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_request1:
                requestLocationPermission();
                break;
            default:
                break;
        }
    }

    private void requestLocationPermission() {
        AndPermission.with(this)
                .requestCode(LOCATION_PERMISSION_CODE)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION)
                .rationale(rationaleListener)
                .send();
    }

    @PermissionYes(LOCATION_PERMISSION_CODE)
    private void getLocationYes(){
        ToastUtil.showToast("getLocationYes");
    }

    @PermissionNo(LOCATION_PERMISSION_CODE)
    private void getLocationNo(){
        ToastUtil.showToast("getLocationNo");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(ExtrasActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("您已拒绝过定位权限，没有定位权限,如果选择不再提醒，请在应用管理重新设置权限！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };
}
