package com.example.enid.myapplication.drawable;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

/**
 * Created by big_love on 2016/12/7.
 */

public class ShapeDrawableClass {
    public static ShapeDrawable getShapeDrawable() {
        float outerRadius = 10;
        float[] outerRadii = new float[]{outerRadius,outerRadius,outerRadius,outerRadius,outerRadius,outerRadius,outerRadius,outerRadius};
        float innerRadius = 10;
        float[] innerRadii = new float[]{innerRadius,innerRadius,innerRadius,innerRadius,innerRadius,innerRadius,innerRadius,innerRadius};
        RoundRectShape shape = new RoundRectShape(outerRadii,null,innerRadii);
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.setPadding(23,23,23,23);
        shapeDrawable.setShape(shape);
        return shapeDrawable;
    }
}
