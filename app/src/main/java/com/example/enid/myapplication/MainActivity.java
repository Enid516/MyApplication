package com.example.enid.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.enid.library.dialog.CustomMultiSelectDialog;
import com.example.enid.myapplication.dataS.BlockingQueueTest;
import com.example.enid.myapplication.drawable.ShapeDrawableClass;
import com.example.enid.myapplication.fragmenttabhost.FragmentTabHostActivity;
import com.example.enid.myapplication.fragmenttest.FragmentTestActivity;
import com.example.enid.myapplication.view.ListViewAnalyse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemClock.sleep(2000);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initList();
        testDrawable();
    }

    private void testDrawable(){
        View viewById = findViewById(R.id.drawable_view);
        viewById.setBackgroundDrawable(ShapeDrawableClass.getShapeDrawable());
    }

    private void initTest() {
        BlockingQueueTest test = new BlockingQueueTest();
        for (int i = 0; i < 101; i++) {
            test.offerExit("hello");
        }
    }

    private void initList() {
        listView = (ListView) findViewById(R.id.list);
        String[] from = new String[]{"key"};
        int[] to = new int[]{android.R.id.text1};
        listView.setAdapter(new SimpleAdapter(this, getData(), android.R.layout.activity_list_item, from, to));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, ScrollViewActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, DividerListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, ListViewAnalyse.class);
                        break;
                    case 3:
                        new CustomMultiSelectDialog.Builder(MainActivity.this)
                                .setSelectItem(getResources().getString(R.string.get_from_photo), getResources().getString(R.string.take_photo))
                                .setCancelButtonTitle(getResources().getString(R.string.cancel))
                                .setListener(new CustomMultiSelectDialog.DialogActionListener() {
                                    @Override
                                    public void onSelectItemClick(CustomMultiSelectDialog action, int index) {
                                        if (index == 0) {
                                            Intent intent = new Intent(MainActivity.this, ImageSelectActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                })
                                .show();
                        break;
                    case 4:
                        intent = new Intent(MainActivity.this, FragmentTabHostActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MainActivity.this, FragmentTestActivity.class);
                        break;
                    default:
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private List<Map<String, String>> getData() {
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("key", "Scroll View");
        data.add(map1);

        Map<String, String> map2 = new HashMap<>();
        map2.put("key", "Divider ListView");
        data.add(map2);

        Map<String, String> map3 = new HashMap<>();
        map3.put("key", "ListView Test");
        data.add(map3);

        Map<String, String> map4 = new HashMap<>();
        map4.put("key", "iGallery");
        data.add(map4);

        Map<String, String> map5 = new HashMap<>();
        map5.put("key", "FragmentTabHost");
        data.add(map5);

        Map<String, String> map6 = new HashMap<>();
        map6.put("key", "FragmentTest");
        data.add(map6);
        return data;
    }
}


