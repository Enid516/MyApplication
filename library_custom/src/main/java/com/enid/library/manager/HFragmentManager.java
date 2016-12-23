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
    //remove
    ///////////////////////////////////////////////////////////////////////////
    public HFragmentManager remove(Fragment... fragments) {
        if (fragments != null && fragments.length > 0) {
            FragmentTransaction fragmentTransaction = beginTransaction();
            Fragment fragment = null;
            for (int i = 0; i < fragments.length; i++) {
                fragment = fragments[i];
                if (fragment != null) {
                    fragmentTransaction.remove(fragment);
                }
            }
        }
        return this;
    }

    ///////////////////////////////////////////////////////////////////////////
    // add
    ///////////////////////////////////////////////////////////////////////////
    public Fragment add(int container, Class<? extends Fragment> fragmentClazz) {
        return add(container, fragmentClazz, null, false);
    }

    public Fragment add(int container, Class<? extends Fragment> fragmentClazz, Bundle args) {
        return add(container, fragmentClazz, args, false);
    }

    public Fragment add(int container, Class<? extends Fragment> fragmentClazz, boolean addToBackStack) {
        return add(container, fragmentClazz, null, addToBackStack);
    }

    public Fragment add(int container, Class<? extends Fragment> fragmentClazz, Bundle args, boolean addToBackStack) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentClazz.getSimpleName());
        if (fragment == null) {
            fragment = getNewFragment(fragmentClazz);
        }
        return add(container, fragment, args, addToBackStack);
    }

    public Fragment add(int container, Fragment fragment) {
        return add(container, fragment, null, false);
    }

    public Fragment add(int container, Fragment fragment, Bundle args) {
        return add(container, fragment, args, false);
    }

    public Fragment add(int container, Fragment fragment, boolean addToBackSack) {
        return add(container, fragment, null, addToBackSack);
    }

    public Fragment add(int container, Fragment fragment, Bundle args, boolean addToBackStack) {
        if (fragment != null) {
            putFragmentData(fragment, args);
            FragmentTransaction fragmentTransaction = beginTransaction();
            String tag = fragment.getClass().getSimpleName();
            fragmentTransaction.add(container, fragment, tag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
        return fragment;
    }

    ///////////////////////////////////////////////////////////////////////////
    // replace 新的Fragment关联到某一个container
    ///////////////////////////////////////////////////////////////////////////
    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz) {
        return replace(container, fragmentClazz, null, false);
    }

    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz, Bundle args) {
        return replace(container, fragmentClazz, args, false);
    }

    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz, boolean addToBackStack) {
        return replace(container, fragmentClazz, null, addToBackStack);
    }

    public Fragment replace(int container, Class<? extends Fragment> fragmentClazz, Bundle args, boolean addToBackStack) {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentClazz.getSimpleName());
        if (fragment == null) {
            fragment = getNewFragment(fragmentClazz);
        }
        return replace(container, fragment, args, addToBackStack);
    }

    public Fragment replace(int container, Fragment fragment) {
        return replace(container, fragment, null, false);
    }

    public Fragment replace(int container, Fragment fragment, Bundle args) {
        return replace(container, fragment, args, false);
    }

    public Fragment replace(int container, Fragment fragment, boolean addToBackStack) {
        return replace(container, fragment, null, addToBackStack);
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
        return toggle(container, hideFragment, showFragmentClazz, null, false);
    }

    public Fragment toggle(int container, Fragment hideFragment, Class<? extends Fragment> showFragmentClazz, Bundle args) {
        return toggle(container, hideFragment, showFragmentClazz, args, false);
    }

    public Fragment toggle(int container, Fragment hideFragment, Class<? extends Fragment> showFragmentClazz, boolean addToBackStack) {
        return toggle(container, hideFragment, showFragmentClazz, null, addToBackStack);
    }

    public Fragment toggle(int container, Fragment hideFragment, Class<? extends Fragment> showFragmentClazz, Bundle args, boolean addToBackStack) {
        String tag = showFragmentClazz.getSimpleName();
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getNewFragment(showFragmentClazz);
        }
        return toggle(container, hideFragment, fragment, args, addToBackStack);
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment) {
        return toggle(container, hideFragment, showFragment, null, false);
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment, Bundle args) {
        return toggle(container, hideFragment, showFragment, args, false);
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment, boolean addToBackStack) {
        return toggle(container, hideFragment, showFragment, null, addToBackStack);
    }

    public Fragment toggle(int container, Fragment hideFragment, Fragment showFragment, Bundle args, boolean addToBackStack) {
        if (showFragment != null && showFragment != hideFragment) {
            putFragmentData(showFragment, args);
            FragmentTransaction fragmentTransaction = beginTransaction();
            if (hideFragment == null) {
                hideFragment = mFragmentLastToggle;
            }
            if (hideFragment != null) {
                fragmentTransaction.hide(hideFragment);
            }
            if (!showFragment.isAdded() && container > 0) {
                String tag = showFragment.getClass().getSimpleName();
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
