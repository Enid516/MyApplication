package com.example.enid.myapplication.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Enid on 2016/8/29.
 */
public class DividerDrawable extends Drawable{
    private Drawable mDrawable;
    private Rect mRectBounds;
    private DividerBounds mDividerBounds;

    public DividerDrawable(Drawable mDrawable, DividerBounds mDividerBounds) {
        this.mDrawable = mDrawable;
        this.mDividerBounds = mDividerBounds;
    }

    @Override
    public void draw(Canvas canvas) {
        mDrawable.draw(canvas);
    }

    @Override
    public void setAlpha(int i) {
        mDrawable.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mDrawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return mDrawable.getOpacity();
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        if (mDividerBounds != null) {
            if (mRectBounds == null) {
                mRectBounds = new Rect(left,top,right,bottom);
            }
            mRectBounds = mDividerBounds.getBounds(mRectBounds,left,top,right,bottom);
            left = mRectBounds.left;
            top = mRectBounds.top;
            right = mRectBounds.right;
            bottom = mRectBounds.bottom;
        }
        mDrawable.setBounds(left,top,right,bottom);
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }
}
