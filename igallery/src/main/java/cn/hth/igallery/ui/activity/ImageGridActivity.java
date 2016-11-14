package cn.hth.igallery.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.fragment.ImageGridBottomFragment;
import cn.hth.igallery.ui.fragment.ImageGridFragment;
import cn.hth.igallery.ui.fragment.ImageGridTopFragment;
import cn.hth.igallery.util.ImageScanner;
import cn.hth.igallery.util.LogUtil;

/**
 * Created by Enid on 2016/9/7.
 * scanner image for select
 */
public class ImageGridActivity extends BaseActivity {
    private ImageScanner mScanner;
    private Activity mContext;
    private ImageGridTopFragment imageGridTopFragment;
    private ImageGridFragment imageGridFragment;
    private ImageGridBottomFragment iamgeridBottomFragment;
    public static final String EXTRA_CONFIGURATION = "extra_configuration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_grid);
        mContext = this;
        init();
    }

    private void init() {
        getIntentData();
        mScanner = new ImageScanner(this, new ImageScanner.ImageScannerCallBack() {
            @Override
            public void onCompleted(List<ImageModel> imageList) {
                if (imageList != null) {
                    Configuration.getConfiguration().setImageList(imageList);
                    addFragment();
                }
            }
        });
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        Configuration configuration = (Configuration) bundle.getSerializable(EXTRA_CONFIGURATION);
        Configuration.setConfiguration(configuration);
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
        if (imageGridTopFragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            imageGridTopFragment = new ImageGridTopFragment();
            imageGridTopFragment.setListener(new ImageGridTopFragment.ChoiceStatusFragmentCallBack() {
                @Override
                public void onBack() {
                    finish();
                }

                @Override
                public void onCompleted() {
                    List<ImageModel> list = Configuration.getConfiguration().getSelectedList();
                    LogUtil.i(" 选择的图片数量: " + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        LogUtil.i("第" + (i + 1) + "张：" + list.get(i).getThumbnailSmallPath());
                    }
                }
            });
            ft.add(R.id.frame_image_grid_top, imageGridTopFragment).commit();
        }
        //add image grid fragment
        if (imageGridFragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            imageGridFragment = new ImageGridFragment();
            imageGridFragment.setListener(new ImageGridFragment.ImageGridFragmentCallBack() {
                @Override
                public void onSelect() {
                    imageGridTopFragment.setSelectSize();
                    iamgeridBottomFragment.check();
                }

                @Override
                public void onItemClick(int position) {
                    ImagePreviewActivity.actionStart(ImageGridActivity.this,position);
                }
            });
            ft.add(R.id.frame_image_scanner, imageGridFragment).commit();
        }

        //add grid bottom fragment
        if (iamgeridBottomFragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            iamgeridBottomFragment = new ImageGridBottomFragment();
//            gridBottomBannerFragment.setListener(new ImageGridFragment.ImageGridFragmentCallBack() {
//                @Override
//                public void onSelect() {
//                    imageChoiceStatusFragment.setSelectSize();
//                }
//            });
            ft.add(R.id.frame_grid_bottom, iamgeridBottomFragment).commit();
        }
    }

    public int getScreenWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
}
