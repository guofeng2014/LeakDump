package com.leaklibrary.util;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

import java.util.List;

import static android.content.pm.PackageManager.GET_SERVICES;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public final class CheckUtil {


    public static <T> void checkNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message + "can not be null");
        }
    }


    /**
     * @param context
     * @param serviceClass
     * @return
     */
    public static boolean isInServiceProcess(Context context, Class<? extends Service> serviceClass) {

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), GET_SERVICES);
            String mainProcess = packageInfo.applicationInfo.processName;

            ComponentName component = new ComponentName(context, serviceClass);
            ServiceInfo serviceInfo = packageManager.getServiceInfo(component, 0);
            if (serviceInfo.processName.equals(mainProcess)) {
                return false;
            }


            int myPid = android.os.Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.RunningAppProcessInfo myProcess = null;

            List<ActivityManager.RunningAppProcessInfo> runningProcesss = activityManager.getRunningAppProcesses();
            if (runningProcesss != null) {
                for (ActivityManager.RunningAppProcessInfo process : runningProcesss) {
                    if (process.pid == myPid) {
                        myProcess = process;
                        break;
                    }
                }
            }

            if (myProcess != null) {
                return myProcess.processName.equals(serviceInfo.processName);
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
