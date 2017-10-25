package com.leaklibrary.activiy;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class BaseActivity extends Activity {

    private static final int PERMISSION_CODE = 100;

    private PermissionResult permissionResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void setPermissionResult(PermissionResult permissionResult) {
        this.permissionResult = permissionResult;
    }

    protected void checkPermission(String... permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (permissionResult != null) {
                    permissionResult.onPermissionResult(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, permission), permission);
                }
            }
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
            return;
        }

        if (permissionResult != null) {
            for (String per : permissions) {
                permissionResult.onPermissionResult(true, per);
            }
        }
    }


    public interface PermissionResult {
        void onPermissionResult(boolean isGranted, String permission);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            final int length = grantResults.length;
            for (int i = 0; i < length; i++) {
                String permission = permissions[i];
                if (permissionResult != null) {
                    permissionResult.onPermissionResult(grantResults[i] == PackageManager.PERMISSION_GRANTED, permission);
                }
            }
        }
    }
}
