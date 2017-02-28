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
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

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

        NoHttp.initialize(this,new NoHttp.Config()
                .setConnectTimeout(2000)
                .setReadTimeout(2000)
                .setNetworkExecutor(new OkHttpNetworkExecutor()));
        Logger.setDebug(true);//开启NoHttp调试模式
        Logger.setTag("NoHttpTest");//设置NoHttp的Log的Tag
//        NoHttp.initialize(this);//NoHttp默认初始化
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
