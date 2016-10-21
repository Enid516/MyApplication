package cn.hth.igallery.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.ui.activity.PreViewActivity;

/**
 * Created by Enid on 2016/10/18.
 */

public class GridBottomBannerFragment extends Fragment{
    private Button btnPreview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_grid_bottom_banner,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View  view) {
        view.findViewById(R.id.frag_grid_bottom_btn_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnPreview = (Button) view.findViewById(R.id.frag_grid_bottom_btn_preview);
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PreViewActivity.class);
                getContext().startActivity(intent);
            }
        });

    }

    public void check() {
        btnPreview.setText("预览"+Configuration.getConfiguration().getSelectedList().size());
    }
}
