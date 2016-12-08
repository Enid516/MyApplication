package com.example.enid.myapplication.fragmenttest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enid.library.fragment.HBaseFragment;
import com.enid.library.utils.HViewUtil;
import com.example.enid.myapplication.app.MyApplication;

/**
 * Created by big_love on 2016/12/8.
 */

public class FragmentA extends HBaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(MyApplication.getInstance());
        textView.setLayoutParams(HViewUtil.getLayoutParamsFrameLayoutMM());
        textView.setText("我是FragmentA");
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
