package com.example.enid.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Enid on 2016/8/29.
 * found in http://blog.csdn.net/megatronkings/article/details/52156312
 */
public class ScrollViewActivity extends Activity implements View.OnClickListener{
    private TextView tv;
    private int i = 1;
    private CustomView customView;

    public static int screenWidth;
    public static int scrrenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scroll_view);
        tv = (TextView) findViewById(R.id.tv);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        customView = (CustomView) findViewById(R.id.customView);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        //获得屏幕分辨率大小
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels ;
        scrrenHeight = metric.heightPixels;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn) {

        }
    }

    private void test() {
        int stepX = -10;
        int stepY = -20;
        tv.scrollTo(stepX * i, stepY * i);
        System.out.println("------------>getScrollX:"+tv.getScrollX());
        System.out.println("------------>getScrollY:"+tv.getScrollY());
    }
}
