package com.leaklibrary;

import android.app.Application;
import android.text.TextUtils;

import com.leaklibrary.util.CheckUtil;
import com.leaklibrary.util.SDUtil;

import java.io.Serializable;

/**
 * Created by guofeng
 * on 2017/10/21.
 */

public class LeakConfigure implements Serializable {

    /**默认泄露时间,如果超过一分钟weakReference对象还存在,认为泄露*/
    private static final long DEFAULT_LEAK_TIME = 1000 * 60;

    /**默认遍历周期时间2秒 */
    private static final int DEFAULT_SCHEDULE_PERIOD = 2000;

    /**超过这个时间如果对象还没有被回收,认为内存泄露 ,单位毫秒*/
    private long leakTimeOut;

    /**内存泄露文件保存的路径*/
    private String filepath;

    private Application application;

    /**遍历周期,单位毫秒*/
    private int schedulePeriod;

    public int getSchedulePeriod() {
        return schedulePeriod;
    }

    public Application getApplication() {
        return application;
    }

    public long getLeakTimeOut() {
        return leakTimeOut;
    }


    public String getFilepath() {
        return filepath;
    }


    public LeakConfigure(Application application, long leakTimeOut, String filepath, int schedulePeriod) {

        CheckUtil.checkNull(application, "application");
        this.application = application;

        if (leakTimeOut <= 0) {
            this.leakTimeOut = DEFAULT_LEAK_TIME;
        } else {
            this.leakTimeOut = leakTimeOut;
        }


        if (TextUtils.isEmpty(filepath)) {
            this.filepath = SDUtil.getSdRoot(application.getApplicationContext());
        } else {
            this.filepath = filepath;
        }

        if (schedulePeriod <= 0) {
            this.schedulePeriod = DEFAULT_SCHEDULE_PERIOD;
        } else {
            this.schedulePeriod = schedulePeriod;
        }

    }

    public static class Builder {

        private Application application;

        private long leakTimeOut;


        private String filepath;

        private int schedulePeriod;


        public Builder setSchedulePeriod(int schedulePeriod) {
            this.schedulePeriod = schedulePeriod;
            return this;
        }

        public Builder setApplication(Application application) {
            this.application = application;
            return this;
        }

        public Builder setLeakTimeOut(long leakTimeOut) {
            this.leakTimeOut = leakTimeOut;
            return this;
        }


        public Builder setFilepath(String filepath) {
            this.filepath = filepath;
            return this;
        }

        public LeakConfigure build() {
            return new LeakConfigure(application, leakTimeOut, filepath, schedulePeriod);
        }
    }


    /**
     * 获取默认的配置
     *
     * @param application
     * @return
     */
    public static LeakConfigure defaultConfig(Application application) {
        return new Builder().setApplication(application)
                .setLeakTimeOut(DEFAULT_LEAK_TIME)
                .setSchedulePeriod(DEFAULT_SCHEDULE_PERIOD)
                .setFilepath(SDUtil.getSdRoot(application.getApplicationContext()))
                .build();
    }
}
