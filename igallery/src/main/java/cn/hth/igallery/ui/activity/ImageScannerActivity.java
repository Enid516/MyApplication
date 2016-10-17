package cn.hth.igallery.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.fragment.ImageChoiceStatusFragment;
import cn.hth.igallery.ui.fragment.ImageGridFragment;
import cn.hth.igallery.util.ImageScanner;
import cn.hth.igallery.util.LogUtil;

/**
 * Created by Enid on 2016/9/7.
 * scanner image for select
 */
public class ImageScannerActivity extends FragmentActivity {
    private ImageScanner mScanner;
    private Activity mContext;
    private ImageChoiceStatusFragment imageChoiceStatusFragment;
    private ImageGridFragment imageGridFragment;
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

        mScanner = new ImageScanner(this, new ImageScanner.ImageScannerCallBack() {
            @Override
            public void onCompleted(List<ImageModel> imageList) {
                if (imageList != null) {
                    mConfiguration.setImageList(imageList);
                    addFragment();
                }
            }
        });
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        mConfiguration = (Configuration) bundle.getSerializable(EXTRA_CONFIGURATION);
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
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog() {
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

    public void addFragment() {

        //add statusFragment
        if (imageChoiceStatusFragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            imageChoiceStatusFragment = new ImageChoiceStatusFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImageGridFragment.EXTRA_CONFIGURATION,mConfiguration);
            imageChoiceStatusFragment.setArguments(bundle);
            imageChoiceStatusFragment.setListener(new ImageChoiceStatusFragment.ChoiceStatusFragmentCallBack() {
                @Override
                public void onBack() {
                    finish();
                }

                @Override
                public void onCompleted() {
                    List<ImageModel>  list = mConfiguration.getSelectedList();
                    LogUtil.i(" 选择的图片数量: " + list.size());
                    for (int i =0 ; i<list.size();i++) {
                        LogUtil.i("第" + (i+1) + "张：" + list.get(i).getThumbnailSmallPath());
                    }
                }
            });
            ft.add(R.id.frame_image_choice_status, imageChoiceStatusFragment).commit();
        }
        //add image grid fragment
        if (imageGridFragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            imageGridFragment = new ImageGridFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImageGridFragment.EXTRA_CONFIGURATION,mConfiguration);
            imageGridFragment.setArguments(bundle);
            imageGridFragment.setListener(new ImageGridFragment.ImageGridFragmentCallBack() {
                @Override
                public void onSelect() {
                    imageChoiceStatusFragment.setSelectSize();
                }
            });
            ft.add(R.id.frame_image_scanner,imageGridFragment).commit();
        }
    }

    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return  metrics.widthPixels;
    }
}
