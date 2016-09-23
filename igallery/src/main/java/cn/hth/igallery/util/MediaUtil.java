package cn.hth.igallery.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hth.igallery.model.BucketModel;
import cn.hth.igallery.model.ImageModel;

/**
 * Created by Enid on 2016/9/8.
 */
public class MediaUtil {
    /**
     * 从数据库中获取图片信息
     *
     * @param context
     * @param page
     * @param limit
     * @return
     */
    public static ArrayList<ImageModel> getImages(Context context, int page, int limit) {
        int offset = (page - 1) * limit;

        ArrayList<ImageModel> imageList = new ArrayList<ImageModel>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATA
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC LIMIT " + limit + " OFFSET " + offset;//跳过offset条数据，取limit条数据
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    imageList.add(parseCursorCreateThumbnail(context, cursor));
                } while (cursor.moveToNext());
                if (!cursor.isClosed()) {
                    cursor.close();
                    cursor = null;
                }
            }
        }
        return imageList;
    }

    /**
     * 获取所有的media文件夹
     * 获取存储卡中Image文件的文件夹相关信息
     * 添加bucket到列表中，已经存在的bucket信息不再添加
     *
     * @return
     */
    public static List<BucketModel> getBucketList(Context context) {
        List<BucketModel> bucketList = new ArrayList<BucketModel>();
        BucketModel allImage = new BucketModel();
        allImage.setBucketName("所有图片");
        allImage.setBucketId(String.valueOf(Integer.MIN_VALUE));
        bucketList.add(allImage);

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                BucketModel bucket;
                do {
                    bucket = new BucketModel();
                    String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                    bucket.setBucketId(bucketId);
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    bucket.setCover(data);
                    String coverName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    bucket.setBucketName(coverName);
                    LogUtil.i("-->" + bucketId);
                    LogUtil.i("-->" + data);
                    LogUtil.i("-->" + coverName);
                    if (TextUtils.isEmpty(allImage.getCover())) {
                        allImage.setCover(data);
                    }

                    //判断bucketList 中是否已经存在当前目录文件
                    if (bucketList.contains(bucket)) {
                        continue;
                    }

                    Cursor c = context.getContentResolver().query(uri, projection, MediaStore.Images.Media.BUCKET_ID + " = ?", new String[]{bucketId}, null);
                    if (c != null && c.getCount() > 0) {
                        bucket.setImageCount(c.getCount());
                    }
                    if (c != null && !c.isClosed()) {
                        c.close();
                        c = null;
                    }
                    bucketList.add(bucket);
                } while (cursor.moveToNext());
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                    cursor = null;
                }
            }
        }
        return bucketList;
    }

    /**
     * 解析Cursor生成ImageModel,并且生成缩略图信息
     *
     * @param context
     * @param cursor
     * @return
     */
    public static ImageModel parseCursorCreateThumbnail(Context context, Cursor cursor) {
        ImageModel image = new ImageModel();
        String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
        String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        image.setId(id);
        image.setTitle(title);
        image.setOriginalPath(data);

        //缩略图信息
        String name = FilenameUtils.getName(data);
        File thumbnailBig = createThumbnailBigFileName(context,data);
        File thumbnailSmall = createThumbnailSmallFileName(context,data);
        image.setThumbnailBigPath(thumbnailBig.getAbsolutePath());
        image.setThumbnailSmallPath(thumbnailSmall.getAbsolutePath());

        return image;
    }

    public static File createThumbnailBigFileName(Context context, String originalPath) {
        File storeFile = StorageUtils.getCacheDirectory(context);
        File bigThumbFile = new File(storeFile, "thumbnail_big_" + FilenameUtils.getName(originalPath));
        return bigThumbFile;
    }

    public static File createThumbnailSmallFileName(Context context, String originalPath) {
        File storeFile = StorageUtils.getCacheDirectory(context);
        File smallThumbFile = new File(storeFile, "thumbnail_small_" + FilenameUtils.getName(originalPath));
        return smallThumbFile;
    }


}
