package com.example.enid.myapplication.drawable;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by big_love on 2016/12/7.
 */

public class LayerDrawableClass {
    public static LayerDrawable getLayerDrawable() {
        GradientDrawable firstDrawable = new GradientDrawable();
        GradientDrawable secondDrawable = new GradientDrawable();
        firstDrawable.setShape(GradientDrawable.RECTANGLE);
        secondDrawable.setShape(GradientDrawable.RECTANGLE);
        firstDrawable.setColor(0xFF666666);
        secondDrawable.setColor(0xFFFF0000);
        firstDrawable.setCornerRadii(new float[]{20f,20f,0f,0f,0f,0f,20f,20f});
        secondDrawable.setCornerRadii(new float[]{20f,20f,0f,0f,0f,0f,20f,20f});
        GradientDrawable[] gradientDrawables = {firstDrawable, secondDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(gradientDrawables);
        layerDrawable.setLayerInset(1,4,4,4,4);
        StateListDrawable stateListDrawable = new StateListDrawable();
        return layerDrawable;
    }
}
