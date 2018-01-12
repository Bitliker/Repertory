package com.gxut.ui.commonui.baserecycler.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 只接受点击事件，当长按一个item时候，也可以触发itemclick
 */
public abstract class OnRecyclerItemDoubleListener extends RecyclerGestureDetector {

    public OnRecyclerItemDoubleListener(Context context) {
        super(context);
    }

    @Override
    public GestureDetector.SimpleOnGestureListener getmGestureListener() {
        return new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                handleEvent(e);
                return super.onDoubleTap(e);
            }
        };
    }
}