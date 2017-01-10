package com.example.enid.myapplication.app;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;

import com.enid.library.HLibrary;
import com.enid.library.HLibraryConfig;
import com.enid.library.handler.CrashHandler;
import com.enid.library.manager.HActivityManager;
import com.example.enid.myapplication.R;

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
        initHLibraryConfig();
//        CrashHandler.getInstance().init();
    }


    private void initHLibraryConfig() {
        HLibraryConfig config = new HLibraryConfig();
        config.setmMainColor(ContextCompat.getColor(this,R.color.main_color));
        config.setmMainColorPress(ContextCompat.getColor(this,R.color.main_color_press));
        config.setmStatusBarColor(ContextCompat.getColor(this,R.color.main_color_press));
        config.setmToolbarColor(ContextCompat.getColor(this,R.color.main_color));
        config.setmActiveWidgetColor(ContextCompat.getColor(this,R.color.color_white));
        config.setmToolbarWidgetColor(ContextCompat.getColor(this,R.color.color_white));
        config.setmCropFrameColor(ContextCompat.getColor(this,R.color.color_white));
        HLibrary.getInstance().initHLibraryCOnfig(config);
    }

    public void exitApp(){
        HActivityManager.getInstance().finishAllActivity();
        //杀掉当前程序的进程
        android.os.Process.killProcess(android.os.Process.myPid());
        //退出程序
        System.exit(0);
    }

}
