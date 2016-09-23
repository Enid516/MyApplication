package cn.hth.igallery.job;

/**
 * Created by Enid on 2016/9/23.
 */
public interface Job {
    public enum Result {
        SUCCESS,
        FAILURE
    }

    Result onRunJob();
}
