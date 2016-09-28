package cn.hth.igallery.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.fragment.ImageChoiceStatusFragment;
import cn.hth.igallery.ui.fragment.ImageGridFragment;
import cn.hth.igallery.ui.fragment.ImagePreviewFragment;
import cn.hth.igallery.util.ImageScanner;
import cn.hth.igallery.util.LogUtil;

/**
 * Created by Enid on 2016/9/7.
 */
public class ImageScannerActivity extends FragmentActivity {

    private ImageScanner mScanner;
    private Activity mContext;
    private ImageChoiceStatusFragment imageChoiceStatusFragment;
    private ImageGridFragment imageGridFragment;
    private ImagePreviewFragment imagePreviewFragment;
    private List<ImageModel> mImageList;

    public static final String EXTRA_CONFIGURATION = "extra_configuration";
    private Configuration mConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_scanner);
        mContext = this;
        init();

    }

    private void init() {
        getIntentData();
        initImageStatusFragment();
        mScanner = new ImageScanner(this, new ImageScanner.ImageScannerCallBack() {
            @Override
            public void onCompleted(List<ImageModel> imageList) {
                mImageList = imageList;
                selectImageGridFragment();
            }
        });
    }

    private void getIntentData() {
        LogUtil.d("-->>" + "getIntentData");
        Bundle bundle = getIntent().getExtras();
        mConfiguration = (Configuration) bundle.getSerializable(EXTRA_CONFIGURATION);
        LogUtil.d("-->>" + "after getSerializable");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        checkPermissionResult(requestCode, permissions, grantResults);
    }

    private void checkPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == ImageScanner.READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mScanner.scan(this);
            } else {
                tipPermissionDialog();
            }
        }
    }

    private void tipPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("应用功能需要请求SD卡访问权限");
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScanner.scan(mContext);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String[] fragmentTags = {"image_grid_fragment","image_preview_fragment"};
    private Fragment mContent;
    public void switchContent(Fragment from,Fragment to,int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (from == null) {
            mContent = to;
            ft.add(R.id.frame_image_scanner, to,fragmentTags[position]);
            ft.commit();
            return;
        }
        if (mContent != to) {
            mContent = to;
            if (!to.isAdded()) {
                ft.hide(from);
                ft.add(R.id.frame_image_scanner, to,fragmentTags[position]);
                ft.commit();
            } else {
                ft.hide(from).show(to).commit();
            }
        }
    }

    public void initImageStatusFragment() {
        if (imageChoiceStatusFragment == null) {
            imageChoiceStatusFragment = new ImageChoiceStatusFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frame_image_choice_status, imageChoiceStatusFragment).commit();

            imageChoiceStatusFragment.setOnChoiceStatusListener(new ImageChoiceStatusFragment.OnChoiceStatusListener() {
                @Override
                public void onBack() {
                    goBack();
                }

                @Override
                public void onCompleted() {

                }
            });
        }
    }
    private void goBack() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment frag: fragmentList
             ) {
            LogUtil.i("----" + frag.getTag());
        }
    }

    /**
     * 选择显示ImageGridFragment
     */
    public void selectImageGridFragment( ) {
        LogUtil.i("--->selectImageGridFragment");
        if (mImageList == null)
            return;
        if (imageGridFragment == null) {
            imageGridFragment = new ImageGridFragment();
            Bundle bundle = new Bundle();
            mConfiguration.setImageList(mImageList);
            bundle.putSerializable(ImageGridFragment.EXTRA_CONFIGURATION,mConfiguration);
            imageGridFragment.setArguments(bundle);
        }
        switchContent(mContent,imageGridFragment,0);
    }

    /**
     * 选择显示ImagePreviewFragment
     */
    public void selectImagePreviewFragment(int currentIndex) {
        LogUtil.i("--->selectImagePreviewFragment");
        if (mImageList == null)
            return;
        if (imagePreviewFragment == null) {
            imagePreviewFragment = new ImagePreviewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagePreviewFragment.IMAGE_EXTRA, (ArrayList)mImageList);
            bundle.putInt(ImagePreviewFragment.IMAGE_CURRENT_INDEX_EXTRA,currentIndex);
            imagePreviewFragment.setArguments(bundle);
        }
        switchContent(mContent,imagePreviewFragment,1);
    }


    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }
}
