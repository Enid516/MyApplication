package com.example.enid.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.enid.myapplication.receiver.NetworkConnectChangeReceiver;
import com.example.enid.myapplication.ui.UIActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private ListView listView;
    private NetworkConnectChangeReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(2000);
        setContentView(R.layout.activity_main);
        register();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void init() {
        initList();
        register();
    }

    private void register() {
        mReceiver = new NetworkConnectChangeReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mReceiver, intentFilter);
    }

    private void initList() {
        listView = (ListView) findViewById(R.id.list);
        String[] from = new String[]{"key"};
        int[] to = new int[]{android.R.id.text1};
        listView.setAdapter(new SimpleAdapter(this, getData(), R.layout.activity_list_item, from, to));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Class clazz = classArray[position];
            Intent intent = new Intent(MainActivity.this, clazz);
            if (intent != null) {
                startActivity(intent);
            }
        });
    }
    private Class[] classArray = new Class[]{UIActivity.class, ImageSelectActivity.class};
    private List<Map<String, String>> getData() {
        String[] titles = new String[]{"UI","iGallery"};
        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("key", titles[i]);
            data.add(map);
        }
        return data;
    }
}


