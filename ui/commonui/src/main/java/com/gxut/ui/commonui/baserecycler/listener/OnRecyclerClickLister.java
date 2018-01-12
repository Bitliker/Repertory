package com.gxut.ui.commonui.baserecycler.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Bitliker on 2017/9/18.
 */

public abstract class OnRecyclerClickLister extends RecyclerGestureDetector {

    private int detector = -1;

    public OnRecyclerClickLister(Context context) {
        super(context);
    }

    public abstract void onItemClick(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position);

    public abstract void onItemLongClick(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position);

    public abstract void onItemDouble(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position);


    @Override
    public final void onItemTouch(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position) {
        switch (detector) {
            case 1:
                onItemClick(rv, viewHolder, position);
                break;
            case 2:
                onItemLongClick(rv, viewHolder, position);
                break;
            case 3:
                onItemDouble(rv, viewHolder, position);
                break;
        }
        detector = -1;
    }

    @Override
    protected final void handleEvent(MotionEvent e) {
        super.handleEvent(e);
    }

    @Override
    public GestureDetector.SimpleOnGestureListener getmGestureListener() {
        return new GestureDetector.SimpleOnGestureListener() {

            //长按时候触发
            @Override
            public void onLongPress(MotionEvent e) {
                detector = 2;
                handleEvent(e);
                super.onLongPress(e);
            }

            //单击确认，长按和双击不触发
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                detector = 1;
                handleEvent(e);
                return super.onSingleTapConfirmed(e);
            }

            //双击触发唯一
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                detector = 3;
                handleEvent(e);
                return super.onDoubleTap(e);
            }
        };
    }


}