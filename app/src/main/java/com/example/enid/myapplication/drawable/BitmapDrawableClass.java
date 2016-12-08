package com.example.enid.myapplication.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.enid.myapplication.R;
import com.example.enid.myapplication.app.MyApplication;

/**
 * Created by big_love on 2016/12/6.
 */

public class BitmapDrawableClass {
    public static Drawable getBitmapDrawable(){
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),R.drawable.phone);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(MyApplication.getInstance().getResources(),bitmap);
        return bitmapDrawable;
    }
}
