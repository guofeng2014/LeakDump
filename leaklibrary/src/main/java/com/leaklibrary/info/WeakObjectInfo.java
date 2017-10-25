package com.leaklibrary.info;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public class WeakObjectInfo<T> extends WeakReference<T> {

    /**保存当前对象创建时间 */
    private long time;

    public long getTime() {
        return time;
    }


    public WeakObjectInfo(long time, T referent) {
        super(referent);
        this.time = time;
    }

    public WeakObjectInfo(long time, T referent, ReferenceQueue<? super T> q) {
        super(referent, q);
        this.time = time;
    }
}
