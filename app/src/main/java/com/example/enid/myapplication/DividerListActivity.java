package com.example.enid.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enid on 2016/8/29.
 */
public class DividerListActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_divider_list);
        ListView dividerListView = (ListView) findViewById(R.id.divider_list_view);

        List<Map<String, String>> data = getData();
        String[] from = new String[]{"key"};
        int[] to = new int[]{android.R.id.text1};
        int resource = android.R.layout.activity_list_item;
        dividerListView.setAdapter(new SimpleAdapter(this,data,resource,from,to));
        ListView ls;
    }


    private List<Map<String,String>> getData(){
        List<Map<String,String>> data = new ArrayList<>();
        Map<String,String> map;
        for (int i = 0; i < 100; i++) {
            map = new HashMap<>();
            map.put("key","测试数据 ：" + i);
            data.add(map);
        }
        return data;
    }
}
