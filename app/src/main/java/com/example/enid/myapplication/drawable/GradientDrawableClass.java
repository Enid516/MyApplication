package com.example.enid.myapplication.drawable;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by big_love on 2016/12/6.
 */

public class GradientDrawableClass {
    public static Drawable getGradientDrawable(){
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR,new int[]{0xFFFF0000,0xFF00FF00,0xFF0000FF});
//        gradientDrawable.setShape(GradientDrawable.RING);
//        gradientDrawable.setGradientCenter(200,200);
//        gradientDrawable.setGradientRadius(100);
        gradientDrawable.setStroke(6, Color.parseColor("#666666"));
        gradientDrawable.setColor(0xFFFF0000);
//        gradientDrawable.setL
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);

        return gradientDrawable;
    }
}
