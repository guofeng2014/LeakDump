package com.leaklibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.leaklibrary.service.LoopService;
import com.leaklibrary.task.LoopTask;
import com.leaklibrary.util.CheckUtil;
import com.leaklibrary.util.LogUtil;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public final class LeakFactory {

    private static LeakFactory instance = new LeakFactory();

    public static LeakFactory getInstance() {
        return instance;
    }

    private LeakConfigure leakConfigure;

    public void setLeakConfigure(LeakConfigure leakConfigure) {
        this.leakConfigure = leakConfigure;
    }

    public LeakConfigure getLeakConfigure() {
        return leakConfigure;
    }


    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void install() {

        CheckUtil.checkNull(leakConfigure, "leakConfigure");

        final Application application = leakConfigure.getApplication();

        LogUtil.loge("install" + android.os.Process.myPid());

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LoopTask.addTask(activity);
                LogUtil.loge("添加" + activity + "到监控队列");
            }
        });

        /**启动轮训service*/
        final Context context = application.getApplicationContext();
        final int schedulePeriod = leakConfigure.getSchedulePeriod();
        final String filepath = leakConfigure.getFilepath();
        final long leakTimeOut = leakConfigure.getLeakTimeOut();
        LoopService.startLoopService(context, schedulePeriod, filepath, leakTimeOut);
    }


}
