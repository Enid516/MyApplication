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

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.ui.activity.ImageScannerActivity;
import cn.hth.igallery.ui.adapter.ImageGridAdapter;

/**
 * Created by Enid on 2016/9/7.
 */
public class ImageGridFragment extends Fragment {
    public static final String EXTRA_CONFIGURATION = "extra_configuration";
    private ImageGridFragmentCallBack mListener;

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
        Configuration configuration = (Configuration) getArguments().getSerializable(EXTRA_CONFIGURATION);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getContext(),configuration);
        recyclerView.setAdapter(imageGridAdapter);

        //register on item onclick listener
        imageGridAdapter.setOnItemOnClickListener(new ImageGridAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                if (mListener != null) {
//
//                }
            }

            @Override
            public void onItenCheck() {
                if (mListener != null) {
                    mListener.onSelect();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void setListener(ImageGridFragmentCallBack listener) {
        this.mListener = listener;
    }

    public interface ImageGridFragmentCallBack{
        void onSelect();
    }
}
