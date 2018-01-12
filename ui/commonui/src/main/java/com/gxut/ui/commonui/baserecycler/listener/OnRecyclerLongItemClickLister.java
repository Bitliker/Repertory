package com.gxut.ui.commonui.baserecycler.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Bitliker on 2017/9/18.
 */

public abstract class OnRecyclerLongItemClickLister extends RecyclerGestureDetector {

    public OnRecyclerLongItemClickLister(Context context) {
        super(context);
    }

    @Override
    public GestureDetector.SimpleOnGestureListener getmGestureListener() {
        return new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                handleEvent(e);
            }
        };
    }

}
