package com.enid.library.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import com.enid.library.manager.HFragmentManager;

/**
 * Created by big_love on 2016/12/6.
 */

public class HBaseActivity extends FragmentActivity {
    private HFragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public HFragmentManager getHFragmentManager() {
        if (mFragmentManager == null) {
            mFragmentManager = new HFragmentManager(getSupportFragmentManager());
        }
        return mFragmentManager;
    }

}
