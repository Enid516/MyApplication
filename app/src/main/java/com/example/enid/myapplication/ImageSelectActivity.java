package com.example.enid.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.GalleryOperator;
import cn.hth.igallery.model.ImageModel;

/**
 * Created by Enid on 2016/9/21.
 */
public class ImageSelectActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_select);
        final RadioButton button = (RadioButton) findViewById(R.id.btn_single);
        findViewById(R.id.btn_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration.ImageChoiceModel choiceModel;
                if (button.isChecked()) {
                    choiceModel = Configuration.ImageChoiceModel.SINGLE;
                } else {
                    choiceModel = Configuration.ImageChoiceModel.MULTIPLE;
                }
                GalleryOperator.getInstance(ImageSelectActivity.this)
                        .setChoiceModel(choiceModel)
                        .setMaxSize(9)
                        .selected(new ArrayList<ImageModel>())
                        .openGallery(ImageSelectActivity.this);

            }
        });
    }
}
