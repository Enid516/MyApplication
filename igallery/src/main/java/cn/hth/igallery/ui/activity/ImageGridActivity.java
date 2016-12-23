package cn.hth.igallery.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.adapter.ImageGridAdapter;
import cn.hth.igallery.util.GalleryUtil;
import cn.hth.igallery.util.ImageScanner;

/**
 * Created by Enid on 2016/9/7.
 * scanner image for select
 */
public class ImageGridActivity extends BaseActivity implements View.OnClickListener {
    private ImageScanner mScanner;
    private Activity mContext;
    public static final String EXTRA_CONFIGURATION = "extra_configuration";
    private RecyclerView recyclerView;
    private ImageGridAdapter imageGridAdapter;
    private static final int REQUEST_CODE = 0x1001;
    private Button btnOK;
    private TextView btnAllImage;
    private TextView btnPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_grid);
        mContext = this;
        init();
    }

    private void init() {
        initView();
        getIntentData();
        setAdapter();
        getScanImage();
        if (mConfiguration.getChoiceModel() == Configuration.ImageChoiceModel.SINGLE) {
            btnOK.setVisibility(View.INVISIBLE);
            btnPreview.setVisibility(View.INVISIBLE);
        }
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        Configuration configuration = (Configuration) bundle.getSerializable(EXTRA_CONFIGURATION);
        mConfiguration = configuration;
    }

    private void getScanImage() {
        mScanner = new ImageScanner(this, new ImageScanner.ImageScannerCallBack() {
            @Override
            public void onCompleted(List<ImageModel> imageList) {
                if (imageList != null) {
                    mConfiguration.setImageList(imageList);
                    imageGridAdapter.setData(mConfiguration);
                }
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnAllImage = (TextView) findViewById(R.id.btnAllImage);
        btnPreview = (TextView) findViewById(R.id.btnPreview);


        //set click listener
        findViewById(R.id.btnReturn).setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnAllImage.setOnClickListener(this);
        btnPreview.setOnClickListener(this);
    }

    private void setAdapter() {
        imageGridAdapter = new ImageGridAdapter(this, mConfiguration);
        //register on item onclick listener
        imageGridAdapter.setOnItemOnClickListener(new ImageGridAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                startImagePreviewActivity(0, position);
            }

            @Override
            public void onItemCheck() {
                btnOK.setText(GalleryUtil.getBtnOKStirng(mConfiguration.getSelectedList().size(), mConfiguration.getMaxChoiceSize()));
                btnPreview.setText(GalleryUtil.getBtnPreviewString(mConfiguration.getSelectedList().size()));
            }
        });
        recyclerView.setAdapter(imageGridAdapter);
    }

    /**
     * 跳转到图片预览界面
     *
     * @param type     0 预览所有图片
     *                 1 预览已选图片
     * @param position viewPager显示的当前页
     */
    private void startImagePreviewActivity(int type, int position) {
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra(ImagePreviewActivity.IMAGE_PREVIEW_TYPE_EXTRA, type);
        intent.putExtra(ImagePreviewActivity.IMAGE_CURRENT_INDEX_EXTRA, position);
        intent.putExtra(ImagePreviewActivity.IMAGE_CONFIGURATION_EXTRA, mConfiguration);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            mConfiguration = (Configuration) data.getSerializableExtra(ImagePreviewActivity.IMAGE_CONFIGURATION_EXTRA);
            imageGridAdapter.setData(mConfiguration);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 存储卡访问权限
    ///////////////////////////////////////////////////////////////////////////

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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnOK) {
            finish();
        } else if (i == R.id.btnReturn) {
            finish();
        } else if (i == R.id.btnPreview) {
            if (mConfiguration.getSelectedList().size() > 0)
                startImagePreviewActivity(1, 0);
        }
    }
}
