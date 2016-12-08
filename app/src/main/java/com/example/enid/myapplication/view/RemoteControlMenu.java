package com.example.enid.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by big_love on 2016/12/1.
 */

public class RemoteControlMenu extends View {
    private Path centerPath, leftPath, topPath, rightPath, bottomPath;
    private Region centerRegion, leftRegion, topRegion, rightRegion, bottomRegion;
    private Paint mPaint;
    private int mWidth, mHeight;
    private int currentFlag = -1;
    private static final int CENTER_FLAG = 0;
    private static final int LEFT_FLAG = 1;
    private static final int TOP_FLAG = 2;
    private static final int RIGHT_FLAG = 3;
    private static final int BOTTOM_FLAG = 4;
    private Matrix mMapMatrix;

    public RemoteControlMenu(Context context) {
        super(context);
        init();
    }

    public RemoteControlMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RemoteControlMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);

        centerPath = new Path();
        leftPath = new Path();
        topPath = new Path();
        rightPath = new Path();
        bottomPath = new Path();

        centerRegion = new Region();
        leftRegion = new Region();
        topRegion = new Region();
        rightRegion = new Region();
        bottomRegion = new Region();
        mMapMatrix = new Matrix();
    }

    private int getDownTouchFlag(int x, int y) {
        if (centerRegion.contains(x, y)) {
            return CENTER_FLAG;
        } else if (leftRegion.contains(x, y)) {
            return LEFT_FLAG;
        } else if (topRegion.contains(x, y)) {
            return TOP_FLAG;
        } else if (rightRegion.contains(x, y)) {
            return RIGHT_FLAG;
        } else if (bottomRegion.contains(x, y)) {
            return BOTTOM_FLAG;
        }
        return -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float[] pts = {event.getX(), event.getY()};
        //将触摸位置转换为画布坐标
        mMapMatrix.mapPoints(pts);
        int x = (int) pts[0];
        int y = (int) pts[1];
        //测试mMapMatrix.mapPoints(pts);的位置
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentFlag = getDownTouchFlag((int) x, (int) y);


                break;
            case MotionEvent.ACTION_UP:
                currentFlag = -1;
                break;
            default:
                break;
        }
        invalidate();
//        postInvalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        mMapMatrix.reset();
        mWidth = w;
        mHeight = h;
        //设置触摸区域的大小
        Region globalRegion = new Region(-w, -h, w, h);
        int minWidth = w > h ? h : w;
        minWidth *= 0.8;

        int br = minWidth / 2;
        RectF bigCircle = new RectF(-br, -br, br, br);

        int sr = minWidth / 4;
        RectF smallCircle = new RectF(-sr, -sr, sr, sr);

        float bigSweepAngle = 84;
        float smallSweepAngle = -80;


        //根据视图大小初始化Path和Region
        centerPath.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
        centerRegion.setPath(centerPath, globalRegion);

        rightPath.addArc(bigCircle, -40, bigSweepAngle);
        rightPath.arcTo(smallCircle, 40, smallSweepAngle);
        rightRegion.setPath(rightPath, globalRegion);

        bottomPath.addArc(bigCircle, 50, bigSweepAngle);
        bottomPath.arcTo(smallCircle, 130, smallSweepAngle);
        bottomRegion.setPath(bottomPath, globalRegion);

        leftPath.addArc(bigCircle, 140, bigSweepAngle);
        leftPath.arcTo(smallCircle, 220, smallSweepAngle);
        leftRegion.setPath(leftPath, globalRegion);

        topPath.addArc(bigCircle, 230, bigSweepAngle);
        topPath.arcTo(smallCircle, 310, smallSweepAngle);
        topRegion.setPath(topPath, globalRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        if (mMapMatrix.isIdentity()) {
            canvas.getMatrix().invert(mMapMatrix);
        }

        //绘制默认按钮
        canvas.drawPath(centerPath, mPaint);
        canvas.drawPath(leftPath, mPaint);
        canvas.drawPath(topPath, mPaint);
        canvas.drawPath(rightPath, mPaint);
        canvas.drawPath(bottomPath, mPaint);

        //绘制按下的按钮
        mPaint.setColor(Color.RED);
        if (currentFlag == CENTER_FLAG) {
            canvas.drawPath(centerPath, mPaint);
        } else if (currentFlag == LEFT_FLAG) {
            canvas.drawPath(leftPath, mPaint);
        } else if (currentFlag == TOP_FLAG) {
            canvas.drawPath(topPath, mPaint);
        } else if (currentFlag == RIGHT_FLAG) {
            canvas.drawPath(rightPath, mPaint);
        } else if (currentFlag == BOTTOM_FLAG) {
            canvas.drawPath(bottomPath, mPaint);
        }
        mPaint.setColor(Color.CYAN);
    }

    private interface MenuListenerInterface {
        void onClickCenter();

        void onClickLeft();

        void onClickTop();

        void onClickRight();

        void onClickBottom();
    }
}
