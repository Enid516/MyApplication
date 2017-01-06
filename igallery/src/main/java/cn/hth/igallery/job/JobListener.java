package cn.hth.igallery.job;

/**
 * Created by big_love on 2016/12/29.
 */

public interface JobListener {
    void onCreateSuccess(Job job);
    void onCreateFailed();
}