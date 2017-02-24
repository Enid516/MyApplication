package com.example.enid.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enid.library.dialog.CustomMultiSelectDialog;
import com.enid.library.permission.PermissionListener;
import com.enid.library.utils.HViewUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.GalleryOperator;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.rxbus.RxBusResultSubscriber;
import cn.hth.igallery.rxbus.event.ImageCropResultEvent;
import cn.hth.igallery.rxbus.event.ImageMultipleResultEvent;
import cn.hth.igallery.ui.adapter.RecyclerViewHolder;

/**
 * Created by Enid on 2016/9/21.
 */
public class ImageSelectActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private String imagePath;
    private RadioButton button;
    private List<ImageModel> mImageModelList;
    private SelectImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_select);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        button = (RadioButton) findViewById(R.id.btn_single);
        findViewById(R.id.btn_open).setOnClickListener(v -> showSelectMethodDialog());
        mImageModelList = new ArrayList<>();
        mAdapter = new SelectImageAdapter(mImageModelList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryOperator.REQUEST_CODE_OPEN_CAMERA && resultCode == RESULT_OK) {
            //create thumbnail image
            ImageModel imageModel = new ImageModel();
            imageModel.setOriginalPath(imagePath);
            imageModel.setThumbnailBigPath(imagePath);
            imageModel.setThumbnailSmallPath(imagePath);

            List<ImageModel> imageModelList = new ArrayList<>();
            imageModelList.add(imageModel);
            mImageModelList.addAll(imageModelList);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * show image select method dialog
     */
    private void showSelectMethodDialog() {
        new CustomMultiSelectDialog.Builder(ImageSelectActivity.this)
                .setSelectItem(getResources().getString(R.string.get_from_photo), getResources().getString(R.string.take_photo))
                .setCancelButtonTitle(getResources().getString(R.string.cancel))
                .setListener((action, index) -> {
                    if (index == 0) {//get from photo
                        Configuration.ImageChoiceModel choiceModel;
                        if (button.isChecked()) {
                            choiceModel = Configuration.ImageChoiceModel.SINGLE;
                            GalleryOperator.getInstance()
                                    .setChoiceModel(choiceModel)
                                    .subscribe(new RxBusResultSubscriber<ImageCropResultEvent>() {
                                        @Override
                                        protected void onEvent(ImageCropResultEvent imageCropResultEvent) {
                                            mImageModelList.clear();
                                            mImageModelList.add(imageCropResultEvent.getImageModel());
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .openGallery(ImageSelectActivity.this);
                        } else {
                            choiceModel = Configuration.ImageChoiceModel.MULTIPLE;
                            GalleryOperator.getInstance()
                                    .setChoiceModel(choiceModel)
                                    .setMaxSize(9)
                                    .selected(new ArrayList<>())
                                    .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                                        @Override
                                        protected void onEvent(ImageMultipleResultEvent imageSelectedResult) {
                                            mImageModelList.clear();
                                            mImageModelList.addAll(imageSelectedResult.getImageModelList());
                                            mAdapter.notifyDataSetChanged();
                                        }
                                    })
                                    .openGallery(ImageSelectActivity.this);
                        }
                    } else if (index == 1) {//take photo
                        requestRuntimePermission(new String[]{Manifest.permission.CAMERA}, new PermissionListener() {

                            @Override
                            public void onGranted() {
                                imagePath = GalleryOperator.getInstance().openCamera(ImageSelectActivity.this);
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                Toast.makeText(ImageSelectActivity.this, "拍照权限被拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.ViewHolder> {
        private List<ImageModel> mData;

        public SelectImageAdapter(List<ImageModel> mData) {
            this.mData = mData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.height = HViewUtil.dp2px(180);
            ImageView imageView = new ImageView(ImageSelectActivity.this);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return new ViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String path = "";
            ImageModel imageModel = mData.get(position);
            path = imageModel.getThumbnailSmallPath();
            if (TextUtils.isEmpty(path)) {
                path = imageModel.getThumbnailBigPath();
            }
            if (TextUtils.isEmpty(path)) {
                path = imageModel.getOriginalPath();
            }
            Glide.with(ImageSelectActivity.this)
                    .load(path)
                    .centerCrop()
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();

        }

        class ViewHolder extends RecyclerViewHolder {
            private ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView;
            }
        }
    }
}
