package cn.hth.igallery.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.media.MediaScanner;
import cn.hth.igallery.model.BucketModel;
import cn.hth.igallery.model.ImageModel;

/**
 * Created by Enid on 2016/9/7.
 */
public class ImageScanner {
    public static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 100;
    private ImageScannerCallBack mImageScannerCallBack;
    public ImageScanner(Activity context,ImageScannerCallBack imageScannerCallBack) {
        this.mImageScannerCallBack = imageScannerCallBack;
        scan(context);
    }

    /**
     * 检查系统是否拥有特定（这里是访问外部存储器）权限
     * 如果没有授权这种权限，则请求获取这种权限
     * @param context
     */
    private boolean checkRuntimeDynamicPermission(@NonNull Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }

    /**
     * 开始扫描图片
     */
    public void scan(final Activity context) {
        //检查是否授权
        if (!checkRuntimeDynamicPermission(context))
            return;
        LogUtil.i("权限检测通过");
        //检查是外部存储器是否存在
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context,"SD卡不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        LogUtil.i("SD卡检测通过");
        MediaUtil.getBucketList(context);

        ArrayList<ImageModel>  imageList = MediaUtil.getImages(context,1,Integer.MAX_VALUE);
        if (imageList != null && mImageScannerCallBack != null) {
            mImageScannerCallBack.onCompleted(imageList);
        }
    }

    public interface ImageScannerCallBack {
        void onCompleted(ArrayList<ImageModel> imageList);
    }


}
