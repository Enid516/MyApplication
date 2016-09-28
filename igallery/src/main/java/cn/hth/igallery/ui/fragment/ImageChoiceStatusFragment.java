package cn.hth.igallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import cn.hth.igallery.R;

/**
 * Created by Enid on 2016/9/23.
 */
public class ImageChoiceStatusFragment extends Fragment{
    private ImageButton btnBack;
    private OnChoiceStatusListener mOnChoiceStatusListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_image_choice_status,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        view.findViewById(R.id.btn_back_frag_image_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChoiceStatusListener != null) {
                    mOnChoiceStatusListener.onBack();
                }
            }
        });

        view.findViewById(R.id.btn_complete_frag_image_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChoiceStatusListener != null) {
                    mOnChoiceStatusListener.onCompleted();
                }
            }
        });
    }

    public void setOnChoiceStatusListener(OnChoiceStatusListener listener) {
        this.mOnChoiceStatusListener = listener;
    }

    public interface OnChoiceStatusListener{
        void onBack();
        void onCompleted();
    }
}
