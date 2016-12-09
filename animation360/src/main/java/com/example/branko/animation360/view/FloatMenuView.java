package com.example.branko.animation360.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.example.branko.animation360.R;
import com.example.branko.animation360.engine.FloatViewManager;

/**
 * Created by branko on 12/9/16.
 */
public class FloatMenuView extends LinearLayout {
    private LinearLayout ll;
    private TranslateAnimation animation;

    public FloatMenuView(Context context) {
        super(context);
        View root=View.inflate(getContext(), R.layout.float_menu_view, null);
        ll=(LinearLayout)root.findViewById(R.id.ll);
        animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0);
        ll.setAnimation(animation);
        animation.setDuration(500);
        animation.setFillAfter(true);
        addView(root);
        root.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FloatViewManager manager=FloatViewManager.getFloatViewManagerInstance(getContext());
                manager.hideFloatMenuView();
                manager.showFloatCircleView();
                return false;
            }
        });
    }

    public void startAnimation(){
        animation.start();
    }
}
