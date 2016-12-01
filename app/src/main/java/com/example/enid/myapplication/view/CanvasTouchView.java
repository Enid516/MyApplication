package com.example.enid.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by big_love on 2016/12/1.
 * getX & getRawX
 *
 * invalidate & postInvalidate
 */

public class CanvasTouchView extends View{
    private float downX = -1;
    private float downY = -1;
    private int mScreenWidth;
    private int mScreenHeight;
    private Paint mPaint;
    public CanvasTouchView(Context context) {
        super(context);
    }

    public CanvasTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CanvasTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_SCROLL:
                downX = event.getX();
                downY = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                downX = -1;
                downY = -1;
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinateView(canvas);
        drawTranslateCoordinateView(canvas);
        drawTouchPoint(canvas);
    }

    private void drawCoordinateView(Canvas canvas){
        canvas.save();
        canvas.translate(10,10);
        mPaint.setColor(Color.CYAN);
        canvas.drawLine(0,0,mScreenWidth,0,mPaint);
        canvas.drawLine(0,0,0,mScreenHeight,mPaint);
        canvas.restore();
    }

    private void drawTranslateCoordinateView(Canvas canvas) {
        canvas.translate(mScreenWidth/2,mScreenHeight/2);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0,0,mScreenWidth,0,mPaint);
        canvas.drawLine(0,0,0,mScreenHeight,mPaint);
    }

    private void drawTouchPoint(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        float[] pts = {downX,downY};
        if (pts[0] == -1 && pts[1] == -1 )
            return;

        //获取当前矩阵的逆矩阵
        Matrix invertMatrix = new Matrix();
        canvas.getMatrix().invert(invertMatrix);
        //使用mapPoint将触摸位置转换为画布坐标
        invertMatrix.mapPoints(pts);
        canvas.drawCircle(pts[0],pts[1],20,mPaint);
    }
}
