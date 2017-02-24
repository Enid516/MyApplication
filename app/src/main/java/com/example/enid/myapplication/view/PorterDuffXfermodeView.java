package com.example.enid.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.example.enid.myapplication.R;

/**
 * Created by big_love on 2017/1/13.
 */

public class PorterDuffXfermodeView extends View {
    private Paint mPaint;
    private Bitmap srcBitmap, dstBitmap;
    private RectF srcRect, dstRect;
    private Xfermode mXfermode;
    private PorterDuff.Mode mPorterDuffMode = PorterDuff.Mode.XOR;
    private int centerX;
    private int centerY;

    public PorterDuffXfermodeView(Context context) {
        super(context);
        init();
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PorterDuffXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        dstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pd_dst);
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pd_src);
        mXfermode = new PorterDuffXfermode(mPorterDuffMode);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //将画布设为白色背景
        canvas.drawColor(Color.WHITE);

        //将图像合成处理放到缓存中进行
        int saveCount = canvas.saveLayer(srcRect, mPaint, Canvas.ALL_SAVE_FLAG);
        //绘制目标图
//        canvas.drawBitmap(dstBitmap, null, dstRect, mPaint);
        canvas.drawCircle(centerX, centerY, centerX / 2, mPaint);
        //设置混合模式
        mPaint.setXfermode(mXfermode);
        //绘制原图
        canvas.drawBitmap(srcBitmap, null, srcRect, mPaint);
        //清除混合模式
        mPaint.setXfermode(null);
        //还原画布
        canvas.restoreToCount(saveCount);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = w <= h ? w : h;
        centerX = w / 2;
        centerY = h / 2;
        int quarterWidth = width / 4;
        srcRect = new RectF(centerX - quarterWidth * 2, centerY - quarterWidth, centerX, centerY + quarterWidth);
        dstRect = new RectF(centerX - quarterWidth, centerY - quarterWidth, centerX + quarterWidth, centerY + quarterWidth);
    }
}


