package com.leaklibrary.dialog;


import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class Loading {

    private static ProgressDialog dialog;

    public static void show(Context context) {
        if (dialog != null) {
            return;
        }
        dialog = new ProgressDialog(context);
        dialog.setTitle("温馨提示");
        dialog.setMessage("加载中...");
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
