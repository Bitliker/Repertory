package com.gxut.ui.commonui.baserecycler.listener;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Bitlike on 2018/1/12.
 */

public abstract class RecyclerGestureDetector implements RecyclerView.OnItemTouchListener {

    private final GestureDetectorCompat mGestureDetector;
    protected RecyclerView rv;

    public RecyclerGestureDetector(Context context) {
        mGestureDetector = new GestureDetectorCompat(context, getmGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        this.rv = rv;
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.rv = rv;
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemTouch(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position);

    public abstract GestureDetector.SimpleOnGestureListener getmGestureListener();


    protected void handleEvent(MotionEvent e) {
        if (rv != null) {
            View childe = rv.findChildViewUnder(e.getX(), e.getY());
            if (childe != null) {
                RecyclerView.ViewHolder viewHolder = rv.getChildViewHolder(childe);
                int position = viewHolder.getAdapterPosition();
                onItemTouch(rv, viewHolder, position);
            }
        }
    }

}
