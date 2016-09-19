package cn.hth.igallery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import cn.hth.igallery.R;
import cn.hth.igallery.model.ImageModel;
import cn.hth.igallery.util.ImageLoaderUtils;
import cn.hth.igallery.util.LogUtil;


/**
 * Created by Enid on 2016/9/9.
 */
public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<ImageModel> mDatas;
    private OnItemOnClickListener mOnItemOnClickListener;

    public interface OnItemOnClickListener {
        void onItemClick(View v,int position);
    }

    public ImageGridAdapter(Context context, ArrayList<ImageModel> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    /**
     * 实现recyclerView 的OnItemOnClickListener监听
     * 原理是:
     * 获取item的View并调用setOnClickListener方法，在onClick中调用{@link OnItemOnClickListener}对象
     * 的onItemClick方法。使用时，只需相应的adapter调用setOnItemOnClickListener（OnItemOnClickListener listener）方法即可。
     * @param listener
     */
    public void setOnItemOnClickListener(OnItemOnClickListener listener) {
        this.mOnItemOnClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gv_frag_image,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoaderUtils.getInstance(mContext).displayImage(mDatas.get(position).getData(),holder.imageView);
        holder.itemView.setTag(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
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
