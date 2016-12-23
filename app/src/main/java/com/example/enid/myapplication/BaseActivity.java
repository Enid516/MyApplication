package com.example.enid.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by big_love on 2016/11/11.
 */

public class BaseActivity extends Activity{
    protected SDSlidingFinishLayout mSDFinishLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewServer.get(this).addWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }
    @Override
    public void setContentView(int layoutResID) {
        View contentView = getLayoutInflater().inflate(layoutResID, null);
        setContentView(contentView);
    }

    @Override
    public void setContentView(View view) {
        view = setSlidingFinishLayout(view);
        super.setContentView(view);
    }

    /**
     * 设置侧滑关闭activity
     *
     * @param view
     * @return
     */
    private View setSlidingFinishLayout(View view) {
        mSDFinishLayout = (SDSlidingFinishLayout) getLayoutInflater().inflate(R.layout.view_finish_wrapper, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mSDFinishLayout.addView(view, params);
        mSDFinishLayout.setmListener(new SDSlidingFinishLayout.SDSlidingFinishLayoutListener() {
            @Override
            public void onFinish() {
                finish();
            }

            @Override
            public void onScrolling() {

            }

            @Override
            public void onScrollToStart() {

            }
        });
        return mSDFinishLayout;
    }
}
