package com.example.enid.myapplication.fragmenttest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.enid.library.activity.HBaseActivity;
import com.example.enid.myapplication.R;

/**
 * Created by big_love on 2016/12/8.
 */

public class FragmentTestActivity extends HBaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
    }

    public void topA(View view) {
        getHFragmentManager().replace(R.id.container_top,new FragmentA(),true);
    }
    public void topB(View view) {
        getHFragmentManager().replace(R.id.container_top,new FragmentB(),true);
    }
    public void bottomA(View view) {
        getHFragmentManager().replace(R.id.container_bottom,new FragmentA(),true);
    }
    public void bottomB(View view) {
        getHFragmentManager().replace(R.id.container_bottom,new FragmentB(),true);

    }
}
