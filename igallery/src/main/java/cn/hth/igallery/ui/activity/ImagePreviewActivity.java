package cn.hth.igallery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.util.GalleryUtil;
import cn.hth.igallery.util.ImageLoaderUtils;
import cn.hth.igallery.util.LogUtil;

/**
 * Created by Enid on 2016/10/17.
 */

public class ImagePreviewActivity extends BaseActivity implements View.OnClickListener {
    public static final int IMAGE_PREVIEW_TYPE_ALL_EXTRA = 0;
    public static final int IMAGE_PREVIEW_TYPE_SELECTED_EXTRA = 1;
    public static final String IMAGE_CURRENT_INDEX_EXTRA = "imageCurrentIndex";
    public static final String IMAGE_PREVIEW_TYPE_EXTRA = "imagePreviewType";
    public static final String IMAGE_CONFIGURATION_EXTRA = "configuration";
    private int mCurrentIndex;
    private int mPreviewType;
    private ViewPager viewPager;
    private CheckBox checkBox;
    private List<ImageModel> previewList = new ArrayList<>();
    private Button btnOK;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_preview);
        init();
    }

    private void init() {
        initView();
        getData();
        initData();
        setListener();
        //设置进入activity显示的page
        viewPager.setCurrentItem(mCurrentIndex);
        if (mConfiguration.getChoiceModel() == Configuration.ImageChoiceModel.MULTIPLE) {
            checkBox.setChecked(mConfiguration.getSelectedList().contains(previewList.get(mCurrentIndex)));
        } else {
            checkBox.setVisibility(View.GONE);
        }
        textTitle.setText((mCurrentIndex + 1) + "/" + previewList.size());
        btnOK.setText(GalleryUtil.getBtnOKStirng(mConfiguration.getSelectedList().size(), mConfiguration.getMaxChoiceSize()));
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        findViewById(R.id.btnReturn).setOnClickListener(this);
        btnOK = (Button) findViewById(R.id.btnOK);
        textTitle = (TextView) findViewById(R.id.textTitle);
    }

    private void getData() {
        Intent data = getIntent();
        mCurrentIndex = data.getIntExtra(ImagePreviewActivity.IMAGE_CURRENT_INDEX_EXTRA, 0);
        mPreviewType = data.getIntExtra(ImagePreviewActivity.IMAGE_PREVIEW_TYPE_EXTRA, 0);
        mConfiguration = (Configuration) data.getSerializableExtra(ImagePreviewActivity.IMAGE_CONFIGURATION_EXTRA);
        previewList.addAll(mPreviewType == 0 ? mConfiguration.getImageList() : mConfiguration.getSelectedList());
    }

    private void initData() {
        //生成ImageView集合
        final List<ImageView> listView = new ArrayList<>();
        ImageView imageView;
        for (ImageModel image : previewList) {
            imageView = new ImageView(this);
            ImageLoaderUtils.getInstance(this).displayImage(image.getOriginalPath(), imageView);
            imageView.setOnClickListener(new ImageViewClickListener());
            listView.add(imageView);
        }

        //set adapter
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return listView.size();
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
    }

    private void setListener() {
        //添加页面改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                ImageModel imageModel = previewList.get(position);
                checkBox.setChecked(mConfiguration.getSelectedList().contains(imageModel));
                textTitle.setText((position + 1) + "/" + previewList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置checkBox 点击监听
        if (mConfiguration.getChoiceModel() == Configuration.ImageChoiceModel.MULTIPLE) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.i("checkBox is checked :" + checkBox.isChecked());
                    ImageModel imageModel = previewList.get(mCurrentIndex);
                    if (checkBox.isChecked()) {
                        String message = mConfiguration.addSelectImage(imageModel);
                        if (!TextUtils.isEmpty(message)) {
                            Toast.makeText(ImagePreviewActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                        if (!mConfiguration.getSelectedList().contains(imageModel)) {
                            checkBox.setChecked(false);
                        }
                    } else {
                        mConfiguration.removeSelectImage(imageModel);
                    }
                    btnOK.setText(GalleryUtil.getBtnOKStirng(mConfiguration.getSelectedList().size(), mConfiguration.getMaxChoiceSize()));
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnOK) {
            finish();
        } else if (i == R.id.btnReturn) {
            finish();
        }
    }

    class ImageViewClickListener implements View.OnClickListener {
        public ImageViewClickListener() {

        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public void onBackPressed() {
        //setResult（）要放在finish()方法之前调用，onBackPressed()方法会自动调用finish（）方法
        //所以这里setResult()要放到super.onBackPressed()之前
        Intent data = new Intent();
        data.putExtra(IMAGE_CONFIGURATION_EXTRA, mConfiguration);
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }
}
