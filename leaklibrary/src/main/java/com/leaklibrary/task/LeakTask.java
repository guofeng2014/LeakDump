package com.leaklibrary.task;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.leaklibrary.LeakConfigure;
import com.leaklibrary.LeakFactory;
import com.leaklibrary.info.WeakObjectInfo;
import com.leaklibrary.util.FileUtil;
import com.leaklibrary.util.LogUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public class LeakTask implements Runnable {


    private final Object LEAK_LOCK;

    private List<WeakObjectInfo> LEAK_QUEUE;

    public LeakTask(List<WeakObjectInfo> LEAK_QUEUE, Object LEAK_LOCK) {
        this.LEAK_QUEUE = LEAK_QUEUE;
        this.LEAK_LOCK = LEAK_LOCK;
    }

    @Override
    public void run() {
        LogUtil.loge("开始检测泄露队列");
        synchronized (LEAK_LOCK) {
            Iterator iterator = LEAK_QUEUE.iterator();
            while (iterator.hasNext()) {
                WeakObjectInfo weakObjectInfo = (WeakObjectInfo) iterator.next();
                if (weakObjectInfo == null) continue;
                final Object obj = weakObjectInfo.get();
                if (obj == null) continue;
                final LeakConfigure leakConfigure = LeakFactory.getInstance().getLeakConfigure();
                if (leakConfigure == null) continue;
                int code = ContextCompat.checkSelfPermission(leakConfigure.getApplication().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                //有权限,把文件存储SD卡
                if (PackageManager.PERMISSION_GRANTED == code) {
                    FileUtil.writeFile(leakConfigure.getFilepath(), obj.toString(), weakObjectInfo.getTime());
                }
            }
        }

    }


}
