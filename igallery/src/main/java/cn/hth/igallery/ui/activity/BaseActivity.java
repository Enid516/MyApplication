package cn.hth.igallery.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import cn.hth.igallery.Configuration;

/**
 * Created by Enid on 2016/10/25.
 */

public class BaseActivity extends FragmentActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Configuration.getConfiguration().setContext(this);
    }
}