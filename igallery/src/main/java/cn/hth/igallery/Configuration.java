package cn.hth.igallery;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.util.LogUtil;

/**
 * Created by Enid on 2016/9/21.
 */
public class Configuration implements Serializable{
    private static final long serialVersionUID = -1;
    private static Configuration mConfiguration;
//    private Context context;
    /** the max size of select images*/
    private int maxChoiceSize = 1;
    /** the selected image list*/
    private List<ImageModel> selectedList;
    /** the all image list*/
    private List<ImageModel> imageList;

    public enum ImageChoiceModel implements Serializable{
        SINGLE,
        MULTIPLE
    }
    /** the default image choice model is single*/
    private ImageChoiceModel choiceModel = ImageChoiceModel.SINGLE;

//    public Context getContext() {
//        return this.context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }

    public int getMaxChoiceSize() {
        return maxChoiceSize;
    }

    public void setMaxChoiceSize(int maxChoiceSize) {
        this.maxChoiceSize = maxChoiceSize;
    }

    public ImageChoiceModel getChoiceModel() {
        return choiceModel;
    }

    public void setChoiceModel(ImageChoiceModel mChoiceModel) {
        this.choiceModel = mChoiceModel;
    }

    public List<ImageModel> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(ArrayList<ImageModel> selectedList) {
        this.selectedList = selectedList;
    }

    public List<ImageModel> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageModel> imageList) {
        this.imageList = imageList;
    }

    public String addSelectImage(ImageModel imageModel) {
        if (selectedList == null)
            return "null";
        if (selectedList.size() >= maxChoiceSize){
            return "最多选择" + maxChoiceSize +"张图片";
        }
        if (!selectedList.contains(imageModel))
            selectedList.add(imageModel);
        return null;
    }
    public void removeSelectImage(ImageModel imageModel) {
        if (selectedList == null)
            return;
        if (selectedList.contains(imageModel))
            selectedList.remove(imageModel);
    }

    public static void setConfiguration(Configuration configuration) {
        mConfiguration = configuration;
    }
    public static Configuration getConfiguration() {
        return mConfiguration;
    }

}
