package com.example.enid.myapplication.app;

import android.app.Application;
import android.provider.Settings;

import com.enid.library.HLibrary;
import com.enid.library.handler.CrashHandler;
import com.enid.library.manager.HActivityManager;

/**
 * Created by big_love on 2016/11/25.
 */

public class MyApplication extends Application{
    private static MyApplication mInstance = null;
    private String actionFrom = "";
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    public synchronized static MyApplication getInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    private void init() {
        HLibrary.getInstance().init(getInstance());
//        CrashHandler.getInstance().init();
    }

    public void exitApp(){
        HActivityManager.getInstance().finishAllActivity();
        //杀掉当前程序的进程
        android.os.Process.killProcess(android.os.Process.myPid());
        //退出程序
        System.exit(0);
    }

}
