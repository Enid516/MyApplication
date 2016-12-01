package com.example.enid.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by big_love on 2016/11/30.
 */

public class RegionClickView extends View {
    private Paint mPaint;
    private Region mCircleRegion;
    private Path mCirclePath;

    public RegionClickView(Context context) {
        super(context);
        init();
    }

    public RegionClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFF4E5268);
        mCirclePath = new Path();
        mCircleRegion = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //在屏幕中间添加一个圆
        mCirclePath.addCircle(w / 2, h / 2, 300, Path.Direction.CW);
        //将剪裁边界设为视图大小
        Region globalRegion = new Region(0, 0, w, h);
        //将path添加到region中
        mCircleRegion.setPath(mCirclePath, globalRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path circlePath = mCirclePath;
        canvas.drawPath(circlePath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (mCircleRegion.contains(x, y)) {
                    Toast.makeText(this.getContext(), "click the circle", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
