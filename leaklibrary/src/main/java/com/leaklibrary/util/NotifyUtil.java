package com.leaklibrary.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.leaklibrary.activiy.LeakDetailActivity;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class NotifyUtil {

    public static void showNotification(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, new Intent(context, LeakDetailActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setContentTitle("内存泄露");
        mBuilder.setContentText("点击查看内存泄露位置");
        mBuilder.setAutoCancel(false);
        mBuilder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        mBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(1, mBuilder.build());

    }


}
