package cn.hth.igallery.media;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import cn.hth.igallery.util.LogUtil;

/**
 * Created by Enid on 2016/9/8.
 * 多媒体文件扫描类
 */
public class MediaScanner {
    private MediaScannerConnection.MediaScannerConnectionClient mediaScannerConnectionClient;
    private MediaScannerConnection mediaScannerConnection;
    private MediaScannerCallBack mediaScannerCallBack;
    public String[] filePaths = null;
    private String fileType = null;

    public MediaScanner(Context context) {
        if (mediaScannerConnectionClient == null) {
            mediaScannerConnectionClient = new ScannerConnectionClient();
        }
        if (mediaScannerConnection == null) {
            mediaScannerConnection = new MediaScannerConnection(context, mediaScannerConnectionClient);
        }
    }

    public class ScannerConnectionClient implements MediaScannerConnection.MediaScannerConnectionClient {

        @Override
        public void onMediaScannerConnected() {
            if (filePaths != null && fileType != null) {
                for (String path : filePaths) {
                    mediaScannerConnection.scanFile(path,fileType);
                }
            }
        }

        @Override
        public void onScanCompleted(String s, Uri uri) {
            LogUtil.i("----->s:" + s + "uri: " + uri );
            mediaScannerConnection.disconnect();
            if (mediaScannerCallBack != null) {
                mediaScannerCallBack.onScanCompleted(new String[]{s});
            }
            filePaths = null;
            fileType = null;
        }
    }

    public void scanImage(MediaScannerCallBack mediaScannerCallBack) {
        scanImage(Environment.getExternalStorageDirectory().getAbsolutePath(), mediaScannerCallBack);
    }

    public void scanImage(String path,MediaScannerCallBack mediaScannerCallBack) {
        scanImage(new String[]{path},mediaScannerCallBack);
    }

    public void scanImage(String[] path,MediaScannerCallBack mediaScannerCallBack) {
        scanFile(path, "image/jpeg",mediaScannerCallBack);
    }

    public void scanFile(String[] path, String fileType,MediaScannerCallBack mediaScannerCallBack) {
        this.filePaths = path;
        this.fileType = fileType;
        this.mediaScannerCallBack = mediaScannerCallBack;
        mediaScannerConnection.connect();
    }

    public interface MediaScannerCallBack{
        void onScanCompleted(String[] path);
    }

}
