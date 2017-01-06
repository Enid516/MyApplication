package cn.hth.igallery.rxbus.event;

import java.util.List;

import cn.hth.igallery.model.ImageModel;

/**
 * Created by enid on 2017/1/5.
 *
 * 完成图片选择需要发送的事件类
 *
 *
 */

public class ImageSelectedResult {
    private List<ImageModel> imageModelList;

    public ImageSelectedResult(List<ImageModel> imageModelList) {
        this.imageModelList = imageModelList;
    }

    public List<ImageModel> getImageModelList() {
        return imageModelList;
    }
}
