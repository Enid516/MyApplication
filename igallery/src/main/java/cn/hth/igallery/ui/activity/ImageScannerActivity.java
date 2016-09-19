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

import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
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
    private ImageGridFragment imageGridFragment;
    private ImagePreviewFragment imagePreviewFragment;
    private ArrayList<ImageModel> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_scanner);
        mContext = this;
        init();

    }

    private void init() {
        mScanner = new ImageScanner(this, new ImageScanner.ImageScannerCallBack() {
            @Override
            public void onCompleted(ArrayList<ImageModel> imageList) {
                mImageList = imageList;
                selectImageGridFragment();
            }
        });
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

    /**
     * 选择显示ImageGridFragment
     */
    public void selectImageGridFragment( ) {
        LogUtil.i("--->selectImageGridFragment");
        if (mImageList == null)
            return;
        if (imageGridFragment == null) {
            imageGridFragment = new ImageGridFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImageGridFragment.IMAGE_EXTRA, mImageList);
        imageGridFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (imagePreviewFragment != null)
            ft.remove(imagePreviewFragment);
        ft.add(R.id.frame_image_scanner, imageGridFragment);
        ft.commit();
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
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImagePreviewFragment.IMAGE_EXTRA, mImageList);
        bundle.putInt(ImagePreviewFragment.IMAGE_CURRENT_INDEX_EXTRA,currentIndex);
        imagePreviewFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (imagePreviewFragment != null)
            ft.remove(imageGridFragment);
        ft.add(R.id.frame_image_scanner, imagePreviewFragment);
        ft.commit();
    }


    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }

}
