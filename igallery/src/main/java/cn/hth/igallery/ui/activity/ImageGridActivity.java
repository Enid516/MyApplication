package cn.hth.igallery.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enid.library.HLibrary;
import com.enid.library.HLibraryConfig;
import com.enid.library.permission.PermissionListener;
import com.enid.library.utils.HViewUtil;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.MediaScannerHelper;
import cn.hth.igallery.R;
import cn.hth.igallery.anim.Animation;
import cn.hth.igallery.anim.AnimationListener;
import cn.hth.igallery.anim.SlideInUnderneathAnimation;
import cn.hth.igallery.anim.SlideOutUnderneathAnimation;
import cn.hth.igallery.model.BucketModel;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.rxbus.RxBus;
import cn.hth.igallery.rxbus.event.ImageCropResultEvent;
import cn.hth.igallery.rxbus.event.ImageMultipleResultEvent;
import cn.hth.igallery.ui.adapter.BucketListAdapter;
import cn.hth.igallery.ui.adapter.ImageGridAdapter;
import cn.hth.igallery.util.GalleryUtil;
import cn.hth.igallery.util.MediaUtil;
import rx.Observer;

/**
 * Created by enid on 2016/9/7.
 * scanner image for select
 */
public class ImageGridActivity extends BaseActivity implements View.OnClickListener {
    private Activity mContext;
    public static final String EXTRA_CONFIGURATION = "extra_configuration";
    private RecyclerView recyclerView;
    private ImageGridAdapter imageGridAdapter;
    private static final int REQUEST_CODE = 0x1001;
    private Button btnOK;
    private TextView btnAllImage;
    private TextView btnPreview;
    private RecyclerView recyclerViewBucket;
    private LinearLayout layoutBucketOverview;

    private int mCurrentPage = 0;
    private static final int MAX_LIMIT = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_grid);
        mContext = this;
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().clear();
    }

    private void init() {
        //init view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        btnOK = (Button) findViewById(R.id.btnOK);
        btnAllImage = (TextView) findViewById(R.id.btnAllImage);
        btnPreview = (TextView) findViewById(R.id.btnPreview);
        recyclerViewBucket = (RecyclerView) findViewById(R.id.recyclerViewBucket);
        recyclerViewBucket.setLayoutManager(new LinearLayoutManager(this));
        layoutBucketOverview = (LinearLayout) findViewById(R.id.layoutBucketOverview);

        //set click listener
        findViewById(R.id.btnReturn).setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnAllImage.setOnClickListener(this);
        btnPreview.setOnClickListener(this);
        layoutBucketOverview.setOnClickListener(this);

        //get intent data
        Bundle bundle = getIntent().getExtras();
        Configuration configuration = (Configuration) bundle.getSerializable(EXTRA_CONFIGURATION);
        mConfiguration = configuration;

        //set adapter
        imageGridAdapter = new ImageGridAdapter(this, mConfiguration);
        //register on item onclick listener
        imageGridAdapter.setOnItemOnClickListener(new ImageGridAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (mConfiguration.getChoiceModel() == Configuration.ImageChoiceModel.SINGLE) {
                    String path = mConfiguration.getImageList().get(position).getOriginalPath();
                    startCropActivity(Uri.fromFile(new File(path)));
                } else {
                    startImagePreviewActivity(0, position);
                }
            }

            @Override
            public void onItemCheck() {
                btnOK.setText(GalleryUtil.getBtnOKString(mConfiguration.getSelectedList().size(), mConfiguration.getMaxChoiceSize()));
                btnPreview.setText(GalleryUtil.getBtnPreviewString(mConfiguration.getSelectedList().size()));
            }
        });
        recyclerView.setAdapter(imageGridAdapter);

        getImagesData();
        if (mConfiguration.getChoiceModel() == Configuration.ImageChoiceModel.SINGLE) {
            btnOK.setVisibility(View.INVISIBLE);
            btnPreview.setVisibility(View.INVISIBLE);
        }
    }

    private void getImagesData() {
        requestRuntimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionListener() {
            @Override
            public void onGranted() {
                getImagesWithBucketId(MediaUtil.ALL_IMAGES_BUCKETID);
                getBuckets();
            }

            @Override
            public void onDenied(List<String> permissions) {
                for (String permission :
                        permissions) {
                    Toast.makeText(ImageGridActivity.this, "denied " + permission + " permission ", Toast.LENGTH_SHORT).show();
                }
                showPermissionDialog();
            }
        });
    }

    private void getImagesWithBucketId(String bucketId) {
        Observer<List<ImageModel>> observer = new Observer<List<ImageModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ImageModel> imageModels) {
                mConfiguration.getImageList().addAll(imageModels);
                imageGridAdapter.setData(mConfiguration);
                imageGridAdapter.notifyDataSetChanged();
            }
        };
        MediaScannerHelper.generateImagesWithBucketId(observer, this, bucketId,mCurrentPage + 1, MAX_LIMIT);
    }

    private void getBuckets() {
        Observer<List<BucketModel>> observer = new Observer<List<BucketModel>>() {

            private BucketListAdapter bucketListAdapter;

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final List<BucketModel> bucketModels) {
                bucketListAdapter = new BucketListAdapter(ImageGridActivity.this, bucketModels);
                bucketListAdapter.setOnItemOnClickListener(new BucketListAdapter.OnItemOnClickListener() {
                    @Override
                    public void onItemClick(BucketModel bucketModel, int position) {
                        if (!bucketModel.getBucketId().equals(bucketListAdapter.getSelectedBucket().getBucketId())) {
                            bucketListAdapter.setSelectedBucket(bucketModel);
                            bucketListAdapter.notifyDataSetChanged();
                            btnAllImage.setText(bucketModels.get(position).getBucketName());
                            getImagesWithBucketId(bucketModel.getBucketId());
                        }
                        showBucketOverview(false);
                    }
                });
                bucketListAdapter.setSelectedBucket(bucketModels.get(0));
                recyclerViewBucket.setAdapter(bucketListAdapter);
            }
        };
        MediaScannerHelper.getImageBuckets(observer, this, 0, Integer.MAX_VALUE);
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

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";

    private void startCropActivity(Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(ImageGridActivity.this);
    }

    /**
     * In most cases you need only to set crop aspect ration and max size for resulting image.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.useSourceImageAspectRatio();
        uCrop = uCrop.withAspectRatio(1, 1);
        uCrop = uCrop.withMaxResultSize(1000, 1000);

        return uCrop;
    }

    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);

        //color
        HLibraryConfig config = HLibrary.getInstance().getHLibraryConfig();
        options.setStatusBarColor(config.getmStatusBarColor());
        options.setToolbarColor(config.getmToolbarColor());
        options.setActiveWidgetColor(config.getmActiiveWidgetColor());
        options.setToolbarWidgetColor(config.getmToolbarWidgetColor());
        options.setCropFrameColor(config.getmCropFrameColor());
        return uCrop.withOptions(options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            mConfiguration = (Configuration) data.getSerializableExtra(ImagePreviewActivity.IMAGE_CONFIGURATION_EXTRA);
            if (resultCode == ImagePreviewActivity.RESULT_CODE_SELECTED) {
                btnOK.setText(GalleryUtil.getBtnOKString(mConfiguration.getSelectedList().size(), mConfiguration.getMaxChoiceSize()));
                btnPreview.setText(GalleryUtil.getBtnPreviewString(mConfiguration.getSelectedList().size()));
                imageGridAdapter.setData(mConfiguration);
            } else if (resultCode == ImagePreviewActivity.RESULT_CODE_COMPLETED) {
                ImageMultipleResultEvent event = new ImageMultipleResultEvent(mConfiguration.getSelectedList());
                RxBus.getInstance().post(event);
                finish();
            }
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                Toast.makeText(ImageGridActivity.this, "resultUri" + resultUri.toString(), Toast.LENGTH_SHORT).show();
                ImageModel imageModel = new ImageModel();
                imageModel.setOriginalPath(resultUri.getPath());
                ImageCropResultEvent event = new ImageCropResultEvent(imageModel);
                RxBus.getInstance().post(event);
                finish();
            } else {
                Toast.makeText(ImageGridActivity.this, "toast_cannot_retrieve_cropped_image ", Toast.LENGTH_SHORT).show();
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
                getImagesData();
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
            ImageMultipleResultEvent imageSelectedResult = new ImageMultipleResultEvent(mConfiguration.getSelectedList());
            RxBus.getInstance().post(imageSelectedResult);
            finish();
        } else if (i == R.id.btnReturn) {
            finish();
        } else if (i == R.id.btnPreview) {
            if (mConfiguration.getSelectedList().size() > 0)
                startImagePreviewActivity(1, 0);
        } else if (i == R.id.btnAllImage || i == R.id.layoutBucketOverview) {
            if (layoutBucketOverview.getVisibility() == View.VISIBLE) {
                showBucketOverview(false);
            } else {
                showBucketOverview(true);
            }

        }
    }

    /**
     * 显示或隐藏bucket列表
     *
     * @param isShow isShow 的值为true显示bucket列表，否则隐藏bucket列表
     */
    private void showBucketOverview(boolean isShow) {
        if (isShow) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.height = HViewUtil.getScreenHeight() / 4 * 3;
            layoutParams.gravity = Gravity.BOTTOM;
            recyclerViewBucket.setLayoutParams(layoutParams);

            layoutBucketOverview.setVisibility(View.VISIBLE);
            new SlideInUnderneathAnimation(recyclerViewBucket)
                    .setDirection(Animation.DIRECTION_DOWN)
                    .setDuration(Animation.DURATION_DEFAULT)
                    .setListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {

                        }
                    }).animate();
        } else {
            new SlideOutUnderneathAnimation(recyclerViewBucket)
                    .setDirection(Animation.DIRECTION_DOWN)
                    .setDuration(Animation.DURATION_DEFAULT)
                    .setListener(new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layoutBucketOverview.setVisibility(View.INVISIBLE);
                        }
                    }).animate();

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (layoutBucketOverview.getVisibility() == View.VISIBLE) {
                showBucketOverview(false);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
