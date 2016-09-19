package com.example.enid.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends ViewGroup {

    public static final int DEFAULT_DURATION = 2000;
    private static final String TAG = "CustomView";
    private boolean goLeft = true;
    private static final int DEFAULT_FLING_LENG_MIN = 10;

    private LinearLayout L1, L2, L3;
    private Scroller mScroller;
    private Context mContext;
    private int currentScreen = 0;
    private int startX = 0;
    private int moveStartX = 0;
    private int stopX = 0;
    private int moveX = 0;
    private int hasMove = 0;
    private int childSize = 0;

    private GestureDetector mGestureDetector;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(mContext);
        }
        for (int i = 0; i < 1000; i++) {
            initLinearLayout();
        }
        currentScreen = 1000 * 3 / 2;
        moveToScreenIndex();

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "单击屏幕");
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG, "双击屏幕");
                return super.onDoubleTap(e);
            }
        });
    }

    private void initLinearLayout() {
        L1 = new LinearLayout(mContext);
        L1.setBackgroundColor(Color.parseColor("#FFC0CB"));
        addView(L1);

        L2 = new LinearLayout(mContext);
        L2.setBackgroundColor(Color.parseColor("#8B008B"));
        addView(L2);

        L3 = new LinearLayout(mContext);
        L3.setBackgroundColor(Color.parseColor("#0000FF"));
        addView(L3);

        childSize += 3;
    }

    /**
     * 移动到固定位置
     *
     * @param x
     */
    public void move(int x) {
        hasMove += x;
        scrollTo(hasMove, 0);
    }

    /**
     * 移动到某个子界面
     */
    public void moveToScreenIndex() {
        hasMove = currentScreen * getWidth();
        scrollTo(hasMove, 0);
    }

    /**
     * 在onMeasure中计算childView的测量值以及模式，以及设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "-----  onMeasure()  -----");
        //获取ViewGroup上级容器为其推荐的宽高和计算模式
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int measureModeHeigth = MeasureSpec.getMode(heightMeasureSpec);

        //设置该ViewGroup的大小
        setMeasuredDimension(measuredWidth, measuredHeight);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            //设置每个子视图的大小
            childAt.measure(getWidth(), ScrollViewActivity.scrrenHeight);
        }
    }

    /**
     * 设置所有childView的绘制区域
     *
     * @param b
     * @param i
     * @param i1
     * @param i2
     * @param i3
     */
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        Log.d(TAG, "-----  onLayout()  -----");
        int childCount = getChildCount();
        int startLeft = 0, startTop = 0;
        for (int j = 0; j < childCount; j++) {
            View childAt = getChildAt(j);
            childAt.layout(startLeft, startTop, startLeft + getWidth(), startTop + ScrollViewActivity.scrrenHeight);
            startLeft += getWidth();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                moveStartX = startX;
                break;
            case MotionEvent.ACTION_MOVE://随触控滚动
                moveX = (int) event.getX();
                move(moveStartX - moveX);
                moveStartX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                stopX = (int) event.getX();
                if (Math.abs(startX - stopX) > DEFAULT_FLING_LENG_MIN) {
                    currentScreen = startX - stopX > 0 ? currentScreen + 1 : currentScreen - 1;
                    currentScreen = currentScreen < 0 ? 0 : currentScreen;
                    currentScreen = currentScreen > childSize ? childSize : currentScreen;
                    moveToScreenIndex();
                }
                break;
            default:
                break;
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.d(TAG, "------ mScroller getCurrX -----" + mScroller.getCurrX());
//            scrollTo(mScroller.getCurrX(),0);
            postInvalidate();
        }
        super.computeScroll();
    }
}