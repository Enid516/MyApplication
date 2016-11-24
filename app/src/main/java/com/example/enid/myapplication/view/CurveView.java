package com.example.enid.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by big_love on 2016/11/22.
 */

public class CurveView extends View {
    private int widthSpace = 100;
    public CurveView(Context context) {
        super(context);
    }

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private List<Point> initPoint() {
        List<Point> points = new ArrayList<>();
        Point point;
        int hWidth = (getWidth() - widthSpace * 2) / 8;
        for (int i = 0; i < 8; i++) {
            point = new Point(widthSpace + hWidth * i,new Random(1000).nextInt());
            points.add(point);
        }
        return points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        Path path = new Path();
        path.moveTo(100, 500);
        path.cubicTo(200, 300, 300, 600,400, 400);
        path.moveTo(400, 400);
        path.cubicTo(500,100,600,400,700,300);
        canvas.drawPath(path, paint);




    }
}
