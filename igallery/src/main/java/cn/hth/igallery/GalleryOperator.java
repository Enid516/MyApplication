package cn.hth.igallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.activity.ImageScannerActivity;
import cn.hth.igallery.util.LogUtil;
import cn.hth.igallery.util.Utils;

/**
 * Created by Enid on 2016/9/21.
 */
public class GalleryOperator {
    private static GalleryOperator INSTANCE;
    private Configuration configuration = new Configuration();

    private GalleryOperator() {
    }

    public static GalleryOperator getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GalleryOperator();
        }
//        INSTANCE.configuration.setContext(context);
        return INSTANCE;
    }

    public GalleryOperator setChoiceModel(Configuration.ImageChoiceModel choiceModel) {
        configuration.setChoiceModel(choiceModel);
        return this;
    }

    public GalleryOperator setMaxSize(@IntRange(from = 1) int size) {
        configuration.setMaxChoiceSize(size);
        return this;
    }

    public GalleryOperator selected(@NonNull ArrayList<ImageModel> selectedList) {
        configuration.setSelectedList(selectedList);
        return this;
    }

    public void openGallery(Context context) {
        execute(context);
    }

    private void execute(Context context) {
//        Context context = configuration.getContext();
        if (context == null)
            return;
        if (!Utils.checkSD()) {
            Toast.makeText(context,"SD卡不存在",Toast.LENGTH_SHORT).show();
            return;
        }

        LogUtil.d("-->>" + "before put bundle");
        Intent intent = new Intent(context, ImageScannerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImageScannerActivity.EXTRA_CONFIGURATION,configuration);
        LogUtil.d("-->>" + "after put putSerializable");
        intent.putExtras(bundle);
        LogUtil.d("-->>" + "after put bundle");


        context.startActivity(intent);
    }

}
