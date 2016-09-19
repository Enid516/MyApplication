package com.example.enid.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.enid.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enid on 2016/9/6.
 */
public class ListViewAnalyse extends Activity {
    private Context mContext;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.act_listview_analyse);
        listView = (ListView) findViewById(R.id.list_view);

        final MyAdapter myAdapter = new MyAdapter(getData());
        listView.setAdapter(myAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myAdapter.notifyDataSetChanged();
                Log.i("tag", "checked item " + listView.getCheckedItemPositions());
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        private List<String> gData;

        public MyAdapter(List<String> gData) {
            this.gData = gData;
        }

        @Override
        public int getCount() {
            return dataCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                Log.d("log", "viewHolder is null");
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_lv_listview_analyse, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                Log.d("log", "viewHolder is not null");
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (listView.isItemChecked(position)) {
                viewHolder.tv.setText(gData.get(position) + "-->selected!!");


            } else {
                viewHolder.tv.setText(gData.get(position));
            }
            return convertView;
        }
    }

    private int dataCount = 40;

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            data.add("the" + i + "item data");
        }
        return data;
    }

    class ViewHolder {
        TextView tv;

        public ViewHolder(View convertView) {
            tv = (TextView) convertView.findViewById(R.id.tv_listview_analyse);
        }
    }
}
