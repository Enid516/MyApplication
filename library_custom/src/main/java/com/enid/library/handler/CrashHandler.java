package com.enid.library.handler;

import com.enid.library.utils.HLogUtil;

/**
 * Created by big_love on 2016/12/9.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (mInstance == null) {
            mInstance = new CrashHandler();
        }
        return mInstance;
    }

    public void init() {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        HLogUtil.e(thread.getName() + throwable.getMessage());
        mUncaughtExceptionHandler.uncaughtException(thread,throwable);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            HLogUtil.e("error : " + e);
//        }
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
