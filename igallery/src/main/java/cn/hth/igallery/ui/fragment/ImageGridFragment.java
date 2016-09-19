package cn.hth.igallery.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.activity.ImageScannerActivity;
import cn.hth.igallery.ui.adapter.ImageGridAdapter;

/**
 * Created by Enid on 2016/9/7.
 */
public class ImageGridFragment extends Fragment {
    public static final String IMAGE_EXTRA = "imageList";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_image_grid, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        //init view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_frag_image_scanner);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        //get data
        ArrayList<ImageModel> images = (ArrayList<ImageModel>) getArguments().getSerializable(IMAGE_EXTRA);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getContext(), images);
        recyclerView.setAdapter(imageGridAdapter);

        //register on item onclick listener
        imageGridAdapter.setOnItemOnClickListener(new ImageGridAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ((ImageScannerActivity)getActivity()).selectImagePreviewFragment(position);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
