package com.example.branko.animation360.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;



/**
 * Created by branko on 12/5/16.
 */
public class MyProgreeView extends View {
    private int width = 200;
    private int height = 200;
    private Paint circlePaint;
    private Paint progreePaint;
    private Paint textPaint;
    private Canvas bitmapCanvas;
    private Path mPath=new Path();
    private int progress = 50;
    private int max= 100;
    private Bitmap bitmap;
    private int currentProgress;
    private GestureDetector detector;
    private int count =50;
    private boolean isSingleTap=false;

    private Handler mHandler=new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    public MyProgreeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.argb(0xff, 0x3a, 0x8c, 0x6c));

        progreePaint = new Paint();
        progreePaint.setAntiAlias(true);
        progreePaint.setColor(Color.argb(0xff, 0x4e, 0xc9, 0x63));
        progreePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25);

        bitmap= Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);

        detector=new GestureDetector(getContext(),new MyGestureDetectorListener());
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        setClickable(true);
    }



    public MyProgreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyProgreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyProgreeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bitmapCanvas.drawCircle(width / 2, height / 2, width / 2, circlePaint);
        mPath.reset();
        float y=(1-(float)currentProgress/max)*height;
        mPath.moveTo(width, y);
        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.lineTo(0, y);
        if(!isSingleTap) {
            float d = (1 - ((float) currentProgress / progress)) * 10;
            for (int i = 0; i < 5; i++) {
                mPath.rQuadTo(10, -d, 20, 0);
                mPath.rQuadTo(10, d, 20, 0);
            }
        }else{
            float d = (float) count / 50 * 10;
            if(count%2==0){
                for (int i = 0; i < 5; i++) {
                    mPath.rQuadTo(20, -d, 40, 0);
                    mPath.rQuadTo(20, d, 40, 0);
                }
            }else{
                for (int i = 0; i < 5; i++) {
                    mPath.rQuadTo(20, d, 40, 0);
                    mPath.rQuadTo(20, -d, 40, 0);
                }
            }
        }
        mPath.close();
        bitmapCanvas.drawPath(mPath,progreePaint);
        String text=(int)(((float)currentProgress/max)*100)+"%";
        float textWidth=textPaint.measureText(text);
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float baseLine=height/2-(metrics.ascent+ metrics.descent)/2;
        bitmapCanvas.drawText(text,width/2-textWidth/2,baseLine,textPaint);
        canvas.drawBitmap(bitmap,0,0,null);
    }


     class MyGestureDetectorListener extends GestureDetector.SimpleOnGestureListener {

         @Override
         public boolean onDoubleTap(MotionEvent e) {
             Toast.makeText(getContext(),"shuang",Toast.LENGTH_LONG).show();
             startDoubleTapAnimation();
             return super.onDoubleTap(e);
         }

         @Override
         public boolean onSingleTapConfirmed(MotionEvent e) {
             Toast.makeText(getContext(),"dan",Toast.LENGTH_LONG).show();
             isSingleTap = true;
             currentProgress=progress;
             startSingleTapAnimation();
             return super.onSingleTapConfirmed(e);
         }


     }

    private void startSingleTapAnimation() {
        mHandler.postDelayed(singleTapRunnable,200);
    }
    private SingleTapRunnable singleTapRunnable= new SingleTapRunnable();
    class SingleTapRunnable implements Runnable{

        @Override
        public void run() {
            count--;
            if(count>=0){
                invalidate();
                mHandler.postDelayed(singleTapRunnable,200);
            }else{
                mHandler.removeCallbacks(singleTapRunnable);
                count=50;
            }

        }
    }

    private void startDoubleTapAnimation() {
        mHandler.postDelayed(doubleTapRunnable,50);
    }

    private DoubleTapRunnable doubleTapRunnable=new DoubleTapRunnable();
    class DoubleTapRunnable implements Runnable{

        @Override
        public void run() {
            currentProgress++;
            if(currentProgress <= progress){
                invalidate();
                mHandler.postDelayed(doubleTapRunnable,50);
            }else{
                mHandler.removeCallbacks(doubleTapRunnable);
                currentProgress=0;
            }
        }
    }
}

