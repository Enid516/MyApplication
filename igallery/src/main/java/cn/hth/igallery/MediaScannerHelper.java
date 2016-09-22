package cn.hth.igallery;

import android.content.Context;

import java.util.List;

import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.util.MediaUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Enid on 2016/9/22.
 */
public class MediaScannerHelper {
    public static void generateImages(Subscription subscription, Observer<List<ImageModel>> observer, final Context context, final int page, final int limit) {
        subscription = Observable
                .create(new Observable.OnSubscribe<List<ImageModel>>() {
                    @Override
                    public void call(Subscriber<? super List<ImageModel>> subscriber) {
                        List<ImageModel> imageList = MediaUtil.getImages(context, page, limit);
                        subscriber.onNext(imageList);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getSmall
}
