package com.gxut.ui.facedialog.list;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Bitlike on 2018/1/24.
 */

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint mPaint;

    public RecyclerItemDecoration(int dividerHeight) {
        this.dividerHeight = dividerHeight;
        mPaint = new Paint();
        mPaint.setColor(0xE8E8E8E8);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = this.dividerHeight;
    }
}
