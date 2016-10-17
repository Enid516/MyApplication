package cn.hth.igallery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import cn.hth.igallery.Configuration;
import cn.hth.igallery.R;

/**
 * Created by Enid on 2016/9/23.
 */
public class ImageChoiceStatusFragment extends Fragment {
    public static final String EXTRA_CONFIGURATION = "extra_configuration";
    private Button btnComplete;
    Configuration mConfiguration;
    private ChoiceStatusFragmentCallBack mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_image_choice_status, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        btnComplete = (Button) view.findViewById(R.id.btn_complete_frag_image_status);
        view.findViewById(R.id.btn_back_frag_image_status).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onBack();
                }
            }
        });
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCompleted();
                }
            }
        });

        Bundle bundle = getArguments();
        mConfiguration = (Configuration) bundle.getSerializable(EXTRA_CONFIGURATION);
    }

    public void setListener(ChoiceStatusFragmentCallBack listener) {
        this.mListener = listener;
    }


    public void setSelectSize() {
        btnComplete.setText("完成(" + mConfiguration.getSelectedList().size() + " / " + mConfiguration.getMaxChoiceSize() + ")");
    }

    public interface ChoiceStatusFragmentCallBack {
        public void onBack();

        public void onCompleted();
    }
}
