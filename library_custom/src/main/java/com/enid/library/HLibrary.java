package com.enid.library;
import android.app.Application;
import android.graphics.Color;

/**
 * Created by enid_ho on 2016/12/6.
 */

public class HLibrary {
    private static HLibrary mInstance;
    private Application mApplication;
    private HLibraryConfig mHLibraryConfig;

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

    public void initHLibraryCOnfig(HLibraryConfig config) {
        this.mHLibraryConfig = config;
    }

    public HLibraryConfig getHLibraryConfig() {
        if (mHLibraryConfig == null) {
            mHLibraryConfig = new HLibraryConfig();
            mHLibraryConfig.setmMainColor(Color.parseColor("#ffffff"));
            mHLibraryConfig.setmMainColorPress(Color.parseColor("#ffffff"));
        }
        return mHLibraryConfig;
    }
}
