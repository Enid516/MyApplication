package cn.hth.igallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;

import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.ui.activity.ImageGridActivity;
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
        if (context == null)
            return;
        if (!Utils.checkSD()) {
            Toast.makeText(context, "SD卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, ImageGridActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ImageGridActivity.EXTRA_CONFIGURATION, configuration);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
