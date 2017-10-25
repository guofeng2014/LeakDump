package com.leaklibrary.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leaklibrary.task.LoopTask;
import com.leaklibrary.util.FileUtil;
import com.leaklibrary.util.NotifyUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by guofeng
 * on 2017/10/24.
 */

public class LoopService extends Service {

    private static ScheduledExecutorService DEFAULT_EXECUTOR = Executors.newScheduledThreadPool(1);

    private static final String DATA_PERIOD = "DATA_PERIOD";
    private static final String DATA_PATH = "DATA_PATH";
    private static final String DATA_LEAK_TIME_OUT = "DATA_LEAK_TIME_OUT";

    private long period;

    private long leakTimeOut;

    public static void startLoopService(Context context, long period, String path, long timeout) {
        Intent service = new Intent(context, LoopService.class);
        service.putExtra(DATA_PERIOD, period);
        service.putExtra(DATA_PATH, path);
        service.putExtra(DATA_LEAK_TIME_OUT, timeout);
        context.startService(service);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            period = intent.getLongExtra(DATA_PERIOD, 0);
            leakTimeOut = intent.getLongExtra(DATA_LEAK_TIME_OUT, 0);
            String path = intent.getStringExtra(DATA_PATH);
            NotifyUtil.showNotification(getApplicationContext());
            FileUtil.deleteFile(path);
            startLoopLeakTask();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void startLoopLeakTask() {
        /** 启动一个轮训线程,每隔period时间轮训一次*/
        DEFAULT_EXECUTOR.scheduleAtFixedRate(new LoopTask(leakTimeOut), 0, period, TimeUnit.MILLISECONDS);
    }

}
