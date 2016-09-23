package cn.hth.igallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.activity.ImageScannerActivity;
import cn.hth.igallery.util.ImageLoaderUtils;
import cn.hth.igallery.util.LogUtil;

/**
 * 图片预览类
 * Created by Enid on 2016/9/12.
 */
public class ImagePreviewFragment extends Fragment{
    public static final String IMAGE_EXTRA = "imageList";
    public static final String IMAGE_CURRENT_INDEX_EXTRA = "imageCurrentIndex";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_image_preview, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        //init view
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_frag_image_preview);

        //list view
        final ArrayList<ImageModel> imageList = (ArrayList<ImageModel>) getArguments().getSerializable(IMAGE_EXTRA);
        int imageIndex = getArguments().getInt(IMAGE_CURRENT_INDEX_EXTRA,0);
        final List<ImageView> listView = new ArrayList<>();
        ImageView imageView;
        for (ImageModel image : imageList) {
            imageView = new ImageView(getContext());
            ImageLoaderUtils.getInstance(getContext()).displayImage(image.getOriginalPath(),imageView);
            listView.add(imageView);
        }

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LogUtil.i("pagerAdapter: instantiateItem");
                container.addView(listView.get(position));
                return listView.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                LogUtil.i("pagerAdapter: destroyItem");
                container.removeView(listView.get(position));
            }
        });
        viewPager.setCurrentItem(imageIndex);
    }
}
