package com.enid.library;
import android.app.Application;

/**
 * Created by enid_ho on 2016/12/6.
 */

public class HLibrary {
    private static HLibrary mInstance;
    private Application mApplication;

    private HLibrary(){}

    public static HLibrary getInstance(){
        if (mInstance == null) {
            mInstance = new HLibrary();
        }
        return mInstance;
    }

    public void init(Application application) {
        this.mApplication = application;
    }

    public Application getApplication(){
        return  mApplication;
    }
}
