package com.enid.library.fragment;

import android.support.v4.app.Fragment;

import com.enid.library.manager.HFragmentManager;

/**
 * Created by big_love on 2016/12/6.
 */

public class HBaseFragment extends Fragment{
    private HFragmentManager mFragmentManager;

    public HFragmentManager getHFragmentManager() {
        if (mFragmentManager == null) {
            mFragmentManager = new HFragmentManager(getChildFragmentManager());
        }
        return mFragmentManager;
    }
}
