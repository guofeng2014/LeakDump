package com.leaklibrary;

import com.leaklibrary.info.WeakObjectInfo;
import com.leaklibrary.task.LeakTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public final class LeakManager {

    /**
     * 存放所以已经内存泄露的对象
     */
    private static volatile List<WeakObjectInfo> LEAK_QUEUE = new ArrayList<>();


    public static List<WeakObjectInfo> getLeakQueue() {
        return LEAK_QUEUE;
    }

    private static final Object LEAK_LOCK = new Object();

    public static void addLeakQueue(WeakObjectInfo weakObjectInfo) {
        synchronized (LEAK_LOCK) {
            LEAK_QUEUE.add(weakObjectInfo);
        }
    }

    public static Runnable loopLeak() {
        return new LeakTask(LEAK_QUEUE, LEAK_LOCK);
    }


}
