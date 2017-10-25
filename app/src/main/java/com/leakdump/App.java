package com.leakdump;

import android.app.Application;

import com.leaklibrary.LeakConfigure;
import com.leaklibrary.LeakFactory;
import com.leaklibrary.util.LogUtil;

/**
 * Created by guofeng
 * on 2017/10/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.loge("APP的onCreate");
        final LeakFactory instance = LeakFactory.getInstance();
        instance.setLeakConfigure(LeakConfigure.defaultConfig(this));
        instance.install();
    }



}
