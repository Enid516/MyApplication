package com.enid.library.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by enid_ho on 2016/12/8.
 */

public class HFragmentManager {
    private FragmentManager mFragmentManager;
    private Fragment mFragmentLastToggle;

    public HFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    private FragmentTransaction beginTransaction() {
        return mFragmentManager.beginTransaction();
    }

    ///////////////////////////////////////////////////////////////////////////
    // replace 新的Fragment关联到某一个container
    ///////////////////////////////////////////////////////////////////////////
    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz) {
        return replace(container, fragmentClazz, null);
    }

    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz, Bundle args) {
        return replace(container, fragmentClazz, args, false);
    }

    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz, Bundle args, boolean addToBackStack) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentClazz.getSimpleName());
        if (fragment == null) {
            fragment = getNewFragment(fragmentClazz);
        }
        return replace(container, fragment, args, addToBackStack);
    }

    public Fragment replace(int container, Fragment fragment) {
        return replace(container, fragment, null);
    }

    public Fragment replace(int container, Fragment fragment, boolean addToBackStack) {
        return replace(container, fragment, null, addToBackStack);
    }
    public Fragment replace(int container, Fragment fragment, Bundle args) {
        return replace(container, fragment, args, false);
    }

    public Fragment replace(int container, Fragment fragment, Bundle args, boolean addToBackStack) {
        if (fragment != null) {
            putFragmentData(fragment, args);
            FragmentTransaction fragmentTransaction = beginTransaction();
            String tag = fragment.getClass().getSimpleName();
            fragmentTransaction.replace(container, fragment, tag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
        return fragment;
    }

    ///////////////////////////////////////////////////////////////////////////
    // toggle 关联多个Fragment到一个container
    // 适用于一个container在不同事件触发时，显示相应的Fragment，
    // 隐藏上一个显示的Fragment
    ///////////////////////////////////////////////////////////////////////////
    public Fragment toggle(int container, Fragment hideFragment, Class<? extends Fragment> showFragmentClazz) {
        return toggle(container, hideFragment, showFragmentClazz, null);
    }

    public Fragment toggle(int container, Fragment hideFragment, Class<? extends Fragment> showFragmentClazz, Bundle args) {
        return toggle(container, hideFragment, showFragmentClazz, args, false);
    }

    public Fragment toggle(int container, Fragment hideFragment, Class<? extends Fragment> showFragmentClazz, Bundle args, boolean addToBackStack) {
        String tag = showFragmentClazz.getSimpleName();
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getNewFragment(showFragmentClazz);
        }
        return toggle(container, hideFragment, fragment, args, addToBackStack);
    }

    private Fragment getNewFragment(Class<? extends Fragment> showFragmentClazz) {
        Fragment fragment = null;
        try {
            fragment = showFragmentClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment) {
        return toggle(container, hideFragment, showFragment, null);
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment, Bundle args) {
        return toggle(container, hideFragment, showFragment, args, false);
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment, Bundle args, boolean addToBackStack) {
        if (showFragment != null && showFragment != hideFragment) {
            String tag = showFragment.getClass().getSimpleName();
            FragmentTransaction fragmentTransaction = beginTransaction();
            putFragmentData(showFragment, args);
            if (hideFragment == null) {
                hideFragment = mFragmentLastToggle;
            }
            if (hideFragment != null) {
                fragmentTransaction.hide(hideFragment);
            }
            if (!showFragment.isAdded() && container > 0) {
                fragmentTransaction.add(container, showFragment, tag);
            } else {
                fragmentTransaction.show(showFragment);
            }
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
        mFragmentLastToggle = showFragment;
        return showFragment;
    }

    private void putFragmentData(Fragment fragment, Bundle args) {
        if (fragment == null || args == null || args.isEmpty())
            return;
        Bundle arguments = fragment.getArguments();
        if (arguments != null) {
            arguments.putAll(args);
        } else {
            fragment.setArguments(args);
        }
    }
}
