package com.example.enid.myapplication.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.enid.myapplication.R;

/**
 * Created by big_love on 2016/12/5.
 * http://blog.csdn.net/lmj623565791/article/details/37815413
 */

public class CustomDialogFragment extends DialogFragment{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View inflate = layoutInflater.inflate(R.layout.dialog_custom_fragment, null);
        builder.setView(inflate)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });
        return builder.create();
    }
}
