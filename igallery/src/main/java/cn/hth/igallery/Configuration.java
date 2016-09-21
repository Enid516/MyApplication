package cn.hth.igallery;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.model.ImageModel;

/**
 * Created by Enid on 2016/9/21.
 */
public class Configuration implements Serializable{
    private static final long serialVersionUID = -1;
    private Context context;
    private int maxChoiceSize = 1;
    /** the selected image list*/
    private ArrayList<ImageModel> selectedList;
    /** the all image list*/
    private ArrayList<ImageModel> imageList;

    public enum ImageChoiceModel implements Serializable{
        SINGLE,
        MULTIPLE
    }
    /** the default image choice model is single*/
    private ImageChoiceModel choiceModel = ImageChoiceModel.SINGLE;

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

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

    public void setImageList(ArrayList<ImageModel> imageList) {
        this.imageList = imageList;
    }

    public void addSelectImage(ImageModel imageModel) {
        if (selectedList == null)
            return;
        if (!selectedList.contains(imageModel))
            selectedList.add(imageModel);
    }
    public void removeSelectImage(ImageModel imageModel) {
        if (selectedList == null)
            return;
        if (selectedList.contains(imageModel))
            selectedList.remove(imageModel);
    }
}
