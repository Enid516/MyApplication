package com.enid.library.manager;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by enid_ho on 2016/12/8.
 */

public class HActivityManager {
    private static HActivityManager mInstance;
    private Stack<Activity> mActivityStack;

    private HActivityManager() {
        mActivityStack = new Stack<>();
    }

    public synchronized static HActivityManager getInstance() {
        if (mInstance == null) {
            mInstance = new HActivityManager();
        }
        return mInstance;
    }

    ///////////////////////////////////////////////////////////////////////////
    // activity life method
    ///////////////////////////////////////////////////////////////////////////
    public void onCreate(Activity activity) {
        addActivity(activity);
    }

    public void onResume(Activity activity) {
        addActivity(activity);
    }

    public void onDestroy(Activity activity) {
        removeActivity(activity);
    }

    ///////////////////////////////////////////////////////////////////////////
    // mActivityStack method
    ///////////////////////////////////////////////////////////////////////////

    private void addActivity(Activity activity) {
        if (!mActivityStack.contains(activity)) {
            mActivityStack.add(activity);
        }
    }

    private void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }

    }

    private void removeLastActivity() {
        Activity lastActivity = getLastActivity();
        if (lastActivity != null) {
            mActivityStack.remove(getLastActivity());
        }
    }

    private Activity getLastActivity() {
        Activity activity = null;
        if (!mActivityStack.empty()) {
            activity = mActivityStack.lastElement();
        }
        return activity;
    }

    public void finishAllActivity() {
        for (Activity activity : mActivityStack) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        mActivityStack.clear();
    }

}
