package com.leaklibrary.util;

import android.util.Log;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class LogUtil {

    private static final String TAG = "LEAK";

    public static void logd(String message) {
        Log.d(TAG, message);
    }

    public static void logv(String message) {
        Log.v(TAG, message);
    }

    public static void loge(String message) {
        Log.e(TAG, message);
    }
}
