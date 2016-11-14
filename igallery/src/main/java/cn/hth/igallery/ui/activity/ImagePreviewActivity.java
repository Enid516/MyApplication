package cn.hth.igallery.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import cn.hth.igallery.R;
import cn.hth.igallery.ui.fragment.ImagePreviewFragment;

/**
 * Created by Enid on 2016/10/17.
 */

public class ImagePreviewActivity extends BaseActivity {
    private ImagePreviewFragment fragImagePreview;
    private int mCurrentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_preview);
        init();
    }

    public static void actionStart(Context context,int currentIndex){
        Intent intent = new Intent(context,ImagePreviewActivity.class);
        intent.putExtra(ImagePreviewFragment.IMAGE_CURRENT_INDEX_EXTRA,currentIndex);
        context.startActivity(intent);
    }

    private void init() {
        getData();
        addFragment();
    }

    private void getData(){
        Intent data = getIntent();
        mCurrentIndex = data.getIntExtra(ImagePreviewFragment.IMAGE_CURRENT_INDEX_EXTRA,0);
    }

    private void addFragment() {
        if (fragImagePreview == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragImagePreview = new ImagePreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ImagePreviewFragment.IMAGE_CURRENT_INDEX_EXTRA,mCurrentIndex);
            fragImagePreview.setArguments(bundle);
            ft.add(R.id.frame_image_preview,fragImagePreview);
            ft.commit();
        }

    }
}
