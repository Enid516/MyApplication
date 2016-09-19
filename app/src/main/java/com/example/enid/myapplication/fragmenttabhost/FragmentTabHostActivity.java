package com.example.enid.myapplication.fragmenttabhost;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.enid.myapplication.R;

/**
 * Created by Enid on 2016/9/18.
 */
public class FragmentTabHostActivity extends FragmentActivity {
    private String texts[] = {"首页", "消息", "好友", "广场", "更多"};
    private Class fragmentArray[] = {FragmentPage1.class,FragmentPage2.class,FragmentPage3.class,FragmentPage4.class,FragmentPage5.class};
    private FragmentTabHost fragmenttabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fragment_tab_host);


        fragmenttabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmenttabHost.setup(this, getSupportFragmentManager(), R.id.maincontent);

        for (int i = 0; i < texts.length; i++) {
            TabHost.TabSpec tabSpec = fragmenttabHost.newTabSpec(texts[i]).setIndicator(getView(i));
            fragmenttabHost.addTab(tabSpec,fragmentArray[i],null);
            //设置背景
//            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bt_selector);

        }

    }

    private View getView(int i) {
        View view = View.inflate(this,R.layout.tab_content,null);
        TextView text = (TextView) view.findViewById(R.id.text_tab_content);
        text.setText(texts[i]);
        return view;
    }
}
