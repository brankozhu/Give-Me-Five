package com.example.branko.animation360.engine;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.branko.animation360.view.FloatCircleView;
import com.example.branko.animation360.view.FloatMenuView;

import java.lang.reflect.Field;


/**
 * Created by branko on 12/1/16.
 */
public class FloatViewManager {
    private static FloatViewManager mFloatViewManager;
    private Context mContext;
    //通过windowManager来操控浮窗体
    private WindowManager mWindowManager;
    private FloatCircleView mFloatCircleView;
    private WindowManager.LayoutParams params;
    private FloatMenuView mFloatMenuView;

    private View.OnTouchListener circleviewTouchListener = new View.OnTouchListener() {
        float startx;
        float starty;
        float x0;
        float y0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startx = event.getRawX();
                    starty = event.getRawY();
                    x0 = event.getRawX();
                    y0 = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = event.getRawX();
                    float y = event.getRawY();
                    float dx = x - startx;
                    float dy = y - starty;
                    params.x += dx;
                    params.y += dy;
                    mFloatCircleView.setDragState(true);
                    mWindowManager.updateViewLayout(mFloatCircleView, params);
                    startx = x;
                    starty = y;
                    break;
                case MotionEvent.ACTION_UP:
                    float x1 = event.getRawX();
                    if (x1 > getScreenWidth() / 2) {
                        params.x = getScreenWidth() - mFloatCircleView.width;
                    } else {
                        params.x = 0;
                    }
                    mFloatCircleView.setDragState(false);
                    mWindowManager.updateViewLayout(mFloatCircleView, params);
                    if (Math.abs(x1 - x0) > 6) {
                        return true;
                    } else {
                        return false;
                    }
            }
            return false;
        }
    };

    public int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        return screenWidth;
    }

    public int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        return screenHeight;
    }

    public int getStatusHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            return mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }


    }

    private FloatViewManager(Context context) {
        this.mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mFloatCircleView = new FloatCircleView(mContext);
        mFloatCircleView.setOnTouchListener(circleviewTouchListener);
        mFloatCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "OnClick", Toast.LENGTH_LONG).show();
                //隐藏circleView 显示菜单栏 开启动画
                mWindowManager.removeView(mFloatCircleView);
                showFloatMenuView();
                mFloatMenuView.startAnimation();
            }
        });

        mFloatMenuView = new FloatMenuView(mContext);
    }

    private void showFloatMenuView() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = getScreenWidth();
        params.height = getScreenHeight() - getStatusHeight();
        params.gravity = Gravity.BOTTOM | Gravity.LEFT;
        params.x = 0;
        params.y = 0;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.format = PixelFormat.RGBA_8888;
        mWindowManager.addView(mFloatMenuView, params);
    }

    public static FloatViewManager getFloatViewManagerInstance(Context context) {
        if (mFloatViewManager == null) {
            synchronized (FloatViewManager.class) {
                mFloatViewManager = new FloatViewManager(context);
            }
        }
        return mFloatViewManager;
    }


    //展示浮窗小球
    public void showFloatCircleView() {
        if (params == null) {
            params = new WindowManager.LayoutParams();
            params.width = mFloatCircleView.width;
            params.height = mFloatCircleView.height;
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 0;
            params.y = 0;
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            params.format = PixelFormat.RGBA_8888;
        }

        mWindowManager.addView(mFloatCircleView, params);
    }

    public void hideFloatMenuView() {
        mWindowManager.removeView(mFloatMenuView);
    }
}
