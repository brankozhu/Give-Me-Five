package com.example.branko.animation360.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.branko.animation360.R;

/**
 * Created by branko on 12/1/16.
 */
public class FloatCircleView extends View {
    public int width = 150;
    public int height = 150;
    private Paint circlePaint;
    private Paint textPaint;
    private String text = "50%";
    private boolean isDraging = false;
    private Bitmap bitmap;

    public FloatCircleView(Context context) {
        super(context);
        initPaint();
    }

    public FloatCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public FloatCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initPaint() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(25);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setFakeBoldText(true);
        Bitmap src=BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        bitmap=Bitmap.createScaledBitmap(src,width,height,true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDraging) {
            canvas.drawBitmap(bitmap,0,0,null);
        } else {
            canvas.drawCircle(width / 2, height / 2, width / 2, circlePaint);
            float textwidth = textPaint.measureText(text);
            float x = width / 2 - textwidth / 2;
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            float dy = (metrics.descent + metrics.ascent) / 2;
            float y = height / 2 + dy;
            canvas.drawText(text, x, y, textPaint);
        }
    }


    public void setDragState(boolean b) {
        isDraging = b;
        invalidate();
    }
}
