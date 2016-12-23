package cn.hth.igallery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;
import cn.hth.igallery.job.ImageThumbnailJob;
import cn.hth.igallery.job.Job;
import cn.hth.igallery.job.RxJob;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.util.ImageLoaderUtils;


/**
 * Created by Enid on 2016/9/9.
 */
public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<ImageModel> mData;
    private OnItemOnClickListener mOnItemOnClickListener;
    private Configuration mConfiguration;

    public interface OnItemOnClickListener {
        void onItemClick(View v, int position);

        void onItemCheck();
    }

    public ImageGridAdapter(Context context, Configuration configuration) {
        this.mContext = context;
        this.mConfiguration = configuration;
        this.mData = (ArrayList<ImageModel>) configuration.getImageList();
    }

    public void setData(Configuration configuration) {
        this.mConfiguration = configuration;
        mData = (ArrayList<ImageModel>) configuration.getImageList();
        notifyDataSetChanged();
    }

    /**
     * 实现recyclerView 的OnItemOnClickListener监听
     * 原理是:
     * 获取item的View并调用setOnClickListener方法，在onClick中调用{@link OnItemOnClickListener}对象
     * 的onItemClick方法。使用时，只需相应的adapter调用setOnItemOnClickListener（OnItemOnClickListener listener）方法即可。
     *
     * @param listener
     */
    public void setOnItemOnClickListener(OnItemOnClickListener listener) {
        this.mOnItemOnClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gv_frag_image, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //如果是多选则显示checkBox
        if (mConfiguration.getChoiceModel() == Configuration.ImageChoiceModel.MULTIPLE) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setOnClickListener(new CheckBoxClickListener(mData.get(position)));
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setTag(mData.get(position));

        ImageModel imageModel = mData.get(position);
        //设置checkBox是否是选中状态
        holder.checkBox.setChecked(mConfiguration.getSelectedList() == null ? false : mConfiguration.getSelectedList().contains(imageModel));
        //如果大缩略图图或小缩略图不存在，则去创建
        if (!new File(imageModel.getThumbnailBigPath()).exists() || !new File(imageModel.getThumbnailSmallPath()).exists()) {
            Job job = new ImageThumbnailJob(mContext, imageModel);
            RxJob.getInstance().addJob(job);
        }

        //显示图片
        String path = imageModel.getThumbnailSmallPath();
        if (TextUtils.isEmpty(path)) {
            path = imageModel.getThumbnailBigPath();
        }
        if (TextUtils.isEmpty(path)) {
            path = imageModel.getOriginalPath();
        }
        ImageLoaderUtils.getInstance(mContext).displayImage(path, holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //checkBox click listener
    class CheckBoxClickListener implements View.OnClickListener {
        ImageModel mImageModel;

        public CheckBoxClickListener(ImageModel imageModel) {
            this.mImageModel = imageModel;
        }

        @Override
        public void onClick(View v) {
            CheckBox checkBox = (CheckBox) v;
            String message = "";
            if (checkBox.isChecked()) {
                message = mConfiguration.addSelectImage(mImageModel);
                if (!TextUtils.isEmpty(message)) {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
                if (!mConfiguration.getSelectedList().contains(mImageModel)) {
                    checkBox.setChecked(false);
                }
            } else {
                mConfiguration.removeSelectImage(mImageModel);
            }

            if (mOnItemOnClickListener != null) {
                mOnItemOnClickListener.onItemCheck();
            }

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_image);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemOnClickListener != null) {
                mOnItemOnClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
