package com.leaklibrary.task;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.leaklibrary.LeakFactory;
import com.leaklibrary.LeakManager;
import com.leaklibrary.info.WeakObjectInfo;
import com.leaklibrary.util.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.leaklibrary.LeakManager.addLeakQueue;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public class LoopTask implements Runnable {

    /**
     * 存放所有监控对象
     */

    private static volatile List<WeakObjectInfo> MONITORS_QUEUE = new ArrayList<>();


    private static final Object TASK_LOCK = new Object();


    private long leakTimeOut;

    public LoopTask(long leakTimeOut) {
        this.leakTimeOut = leakTimeOut;
    }

    /**
     * 添加到监控队列
     */
    public static void addTask(Activity activity) {
        synchronized (TASK_LOCK) {
            WeakObjectInfo weakObjectInfo = new WeakObjectInfo<>(System.currentTimeMillis(), activity);
            MONITORS_QUEUE.add(weakObjectInfo);
        }
    }

    @Override
    public void run() {
        synchronized (TASK_LOCK) {
            LogUtil.loge("监控队列大小" + MONITORS_QUEUE.size());
            Iterator iterator = MONITORS_QUEUE.iterator();
            while (iterator.hasNext()) {
                WeakObjectInfo weakObjectInfo = (WeakObjectInfo) iterator.next();
                if (weakObjectInfo == null) continue;
                //怀疑内存泄露
                final long time = System.currentTimeMillis() - weakObjectInfo.getTime();
                LogUtil.loge("计算时间" + time + "=" + leakTimeOut);
                if (time < leakTimeOut) continue;
                iterator.remove();
                final Object obj = weakObjectInfo.get();
                if (obj != null) {
                    gc();
                    if (weakObjectInfo.get() != null) {
                        LogUtil.loge("添加" + obj + "对象到泄露队列");
                        addLeakQueue(weakObjectInfo);
                        /**启动线程轮训泄露队列 */
                        LeakManager.loopLeak().run();
                        /**Toast提示用户 */
                        handler.sendEmptyMessage(MESSAGE_TOAST);
                    }
                }
            }

        }
    }

    private void gc() {
        Runtime.getRuntime().gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().runFinalization();
    }

    private static final int MESSAGE_TOAST = 100;

    private static Handler handler = new Handler(LeakFactory.getInstance().getLeakConfigure().getApplication().getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case MESSAGE_TOAST:
                    Toast.makeText(LeakFactory.getInstance().getLeakConfigure().getApplication().getApplicationContext(), "发现泄露,点击通知栏查看", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });


}
