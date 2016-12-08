package com.example.enid.myapplication.app;

import android.app.Application;

import com.enid.library.HLibrary;

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
        HLibrary.getInstance().init(getInstance());
    }

    public synchronized static MyApplication getInstance(){
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }

}
