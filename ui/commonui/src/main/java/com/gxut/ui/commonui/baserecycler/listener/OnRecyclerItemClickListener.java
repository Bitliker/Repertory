package com.gxut.ui.commonui.baserecycler.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 只接受点击事件，当长按一个item时候，也可以触发itemclick
 */
public abstract class OnRecyclerItemClickListener extends RecyclerGestureDetector {

    public OnRecyclerItemClickListener(Context context) {
        super(context);
    }

    @Override
    public GestureDetector.SimpleOnGestureListener getmGestureListener() {
        return new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                handleEvent(e);
                return super.onSingleTapConfirmed(e);
            }
        };
    }
}