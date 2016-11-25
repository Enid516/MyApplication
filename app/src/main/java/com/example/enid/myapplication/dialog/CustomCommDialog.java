package com.example.enid.myapplication.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.enid.myapplication.R;
import com.example.enid.myapplication.util.ResourcesUtil;
import com.example.enid.myapplication.util.ViewUtil;

/**
 * 提供多选项的底部显示的对话框
 * Created by enid on 2016/4/20.
 */
public class CustomCommDialog extends AlertDialog implements View.OnClickListener{
    private static final String ARG_CANCEL_BUTTON_TITLE = "cancel_button_title";
    private static final String ARG_OTHER_BUTTON_TITLES = "other_button_titles";
    private static final String ARG_CANCELABLE_ONTOUCHOUTSIDE = "cancelable_ontouchoutside";

    private LinearLayout llSelectItem;
    private Bundle bundleBuilder;
    private Context mContext;

    private DialogActionListener mListener;

    protected CustomCommDialog(Context context, Bundle bundle) {
        super(context);
        this.mContext = context;
        this.bundleBuilder = bundle;
    }

    protected CustomCommDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected CustomCommDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_comm);
        Window window = getWindow();
        WindowManager.LayoutParams windowparams = window.getAttributes();
        windowparams.width = ViewUtil.getScreenWidth();
        Rect rect = new Rect();
        View view1 = window.getDecorView();
        view1.getWindowVisibleDisplayFrame(rect);
        window.setWindowAnimations(R.style.AnimationDialog);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(windowparams);
        window.setGravity(Gravity.BOTTOM);
        init();
    }


    private void init(){
        llSelectItem = (LinearLayout) findViewById(R.id.ll_select_item);
        findViewById(R.id.item_tv_cancel).setOnClickListener(this);
        createItems();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.item_tv_cancel) {
            cancel();
        }else{
            if (mListener != null) {
                mListener.onSelectItemClick(this,v.getId());
            }
            cancel();
        }
    }

    private void createItems(){
        String cancelTitle = bundleBuilder.getString(ARG_CANCEL_BUTTON_TITLE);
        String[] itemTitles = bundleBuilder.getStringArray(ARG_OTHER_BUTTON_TITLES);
        Boolean onTouchoutside = bundleBuilder.getBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE);
        for (int i = 0; i < itemTitles.length; i++) {
            TextView textView = new TextView(mContext);
            textView.setText(itemTitles[i]);
            textView.setId(i);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setBackgroundResource(R.drawable.selector_item_bottom_tint_line);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, ViewUtil.sp2px(14),0, ViewUtil.sp2px(14));

            LinearLayout.LayoutParams linearLayoutMW = ViewUtil.getLayoutParamsLinearLayoutMW();
            llSelectItem.addView(textView, linearLayoutMW);
            textView.setOnClickListener(this);
        }
    }

    public void setListener(DialogActionListener listener){
        this.mListener = listener;
    }

    public static class Builder{
        private Context bContext;
        private boolean cancelOnTouchOutside = true;
        private String[] selectItems;
        private String cancelTitle;
        private DialogActionListener bListener;

        public Builder(Context context){
            this.bContext = context;
        }



        public Builder setSelectItem(String...strings){
            this.selectItems = strings;
            return this;
        }

        public Builder setCancelButtonTitle(String string){
            this.cancelTitle = string;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancelOnTouchOutside) {
            this.cancelOnTouchOutside = cancelOnTouchOutside;
            return this;
        }

        public Builder setListener(DialogActionListener listener){
            this.bListener = listener;
            return this;
        }

        public String[] getSelectItem(){
            return this.selectItems;
        }

        public CustomCommDialog create(){
            CustomCommDialog dialog = new CustomCommDialog(bContext,prepareArguments());
            dialog.setListener(bListener);
            return dialog;
        }

        public Bundle prepareArguments() {
            Bundle bundle = new Bundle();
            bundle.putString(ARG_CANCEL_BUTTON_TITLE, cancelTitle);
            bundle.putStringArray(ARG_OTHER_BUTTON_TITLES, selectItems);
            bundle.putBoolean(ARG_CANCELABLE_ONTOUCHOUTSIDE, cancelOnTouchOutside);
            return bundle;
        }

        public CustomCommDialog show() {
            final CustomCommDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    public static interface DialogActionListener {

        void onDismiss(CustomCommDialog action, boolean isCancel);

        void onSelectItemClick(CustomCommDialog action, int index);
    }


}
