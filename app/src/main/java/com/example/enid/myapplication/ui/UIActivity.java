package com.example.enid.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.enid.library.utils.HLogUtil;
import com.example.enid.myapplication.DividerListActivity;
import com.example.enid.myapplication.R;
import com.example.enid.myapplication.ScrollViewActivity;
import com.example.enid.myapplication.fragmenttabhost.FragmentTabHostActivity;
import com.example.enid.myapplication.fragmenttest.FragmentTestActivity;
import com.example.enid.myapplication.view.ListViewAnalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hth.igallery.GalleryOperator;

/**
 * Created by big_love on 2017/2/27.
 */

public class UIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ui);
        ListView listView = (ListView) findViewById(R.id.list);
        String[] from = new String[]{"key"};
        int[] to = new int[]{android.R.id.text1};
        listView.setAdapter(new SimpleAdapter(this, getData(), R.layout.activity_list_item, from, to));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Class clazz = classArray[position];
            Intent intent = new Intent(UIActivity.this, clazz);
            if (intent != null) {
                startActivity(intent);
            }
        });
    }

    private Class[] classArray = new Class[]{ScrollViewActivity.class, DividerListActivity.class, ListViewAnalyse.class, FragmentTabHostActivity.class, FragmentTestActivity.class,
            PercentFrameLayoutActivity.class};
    private List<Map<String, String>> getData() {
        String[] titles = new String[]{"Scroll View", "Divider ListView", "ListView Test", "FragmentTabHost", "FragmentTest", "PercentFrameLayout"};
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("key", titles[i]);
            data.add(map);
        }
        return data;
    }
}
