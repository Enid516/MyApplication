package com.example.enid.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.enid.myapplication.R;

/**
 * Created by Enid on 2016/8/29.
 */
public class DividerListView extends ListView implements DividerBounds{
    private int mDividerPaddingLeft;

    private int mDividerPaddingRight;

    public DividerListView(Context context) {
        this(context, null);
    }

    public DividerListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DividerPadding, defStyleAttr, 0);

        mDividerPaddingLeft = a.getDimensionPixelSize(R.styleable.DividerPadding_dividerPaddingLeft, 0);

        mDividerPaddingRight = a.getDimensionPixelSize(R.styleable.DividerPadding_dividerPaddingRight, 0);

        a.recycle();
    }

    @Override
    public void setDivider(Drawable divider) {
        if (divider != null) {
            divider = new DividerDrawable(divider,this);
        }
        super.setDivider(divider);
    }

    @Override
    public Rect getBounds(Rect rect, int left, int top, int right, int bottom) {
        left += mDividerPaddingLeft;
        right -= mDividerPaddingRight;
        rect.set(left,top,right,bottom);
        return rect;
    }

    public int getmDividerPaddingLeft() {
        return mDividerPaddingLeft;
    }

    public void setmDividerPaddingLeft(int mDividerPaddingLeft) {
        this.mDividerPaddingLeft = mDividerPaddingLeft;
    }

    public int getmDividerPaddingRight() {
        return mDividerPaddingRight;
    }

    public void setmDividerPaddingRight(int mDividerPaddingRight) {
        this.mDividerPaddingRight = mDividerPaddingRight;
    }
}
