package cn.hth.igallery;

import android.content.Context;
import android.support.annotation.IntRange;

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
        INSTANCE.configuration.setmContext(context);
        return INSTANCE;
    }

    public GalleryOperator setChoicetModel(Configuration.ImageChoiceModel choiceModel) {
        configuration.setmChoiceModel(choiceModel);
        return this;
    }

    public GalleryOperator setMaxSize(@IntRange(from = 1) int size) {
        configuration.setMaxChoiceSize(size);
        return this;
    }

}
