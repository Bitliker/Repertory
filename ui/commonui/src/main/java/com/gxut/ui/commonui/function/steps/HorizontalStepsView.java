package com.gxut.ui.commonui.function.steps;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.gxut.ui.commonui.R;
import com.gxut.ui.commonui.util.DisplayUtil;


/**
 * Created by Bitliker on 2017/5/22.
 */

public class HorizontalStepsView extends View {

    private int padding;//内边距
    private int bgWidth;
    private boolean clickAble;//是否可以点击
    private boolean slideAble;//是否可以滑动
    private int maxScrollX;

    private Scroller mScroller;//滑动控件
    private Surface surface;//绘制变量
    private int progress;//当前进度，注意，不能大于titles的长度
    private String[] titles = {"初次沟通", "立项评估", "产品演示"};

    public HorizontalStepsView(Context context) {
        this(context, null);
    }

    public HorizontalStepsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surface = new Surface();
        padding = DisplayUtil.dip2px(context, 10);
        mScroller = new Scroller(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalStepsView);
        clickAble = a.getBoolean(R.styleable.HorizontalStepsView_click_able, true);
        slideAble = a.getBoolean(R.styleable.HorizontalStepsView_slide_able, true);
        surface.itemWidth = (int) a.getDimension(R.styleable.HorizontalStepsView_item_width, 80);
        surface.stepRadius = (int) a.getDimension(R.styleable.HorizontalStepsView_step_radius, DisplayUtil.dip2px(context, 9));
        surface.lineSize = a.getDimension(R.styleable.HorizontalStepsView_line_size, DisplayUtil.dip2px(context, 3));
        surface.textSize = a.getDimension(R.styleable.HorizontalStepsView_text_size, DisplayUtil.dip2px(context, 11));
        surface.finishColor = a.getColor(R.styleable.HorizontalStepsView_finish_color, Color.parseColor("#FFA500"));
        surface.unfinishColor = a.getColor(R.styleable.HorizontalStepsView_unfinish_color, Color.parseColor("#cdcbcc"));
        a.recycle();
        surface.currentStepRadius = surface.stepRadius + DisplayUtil.dip2px(context, 5);
        surface.init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY)
            bgWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        else
            bgWidth = DisplayUtil.dip2px(getContext(), 311);

        int bgHeight;
        if (heightMode == MeasureSpec.EXACTLY)
            bgHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        else
            bgHeight = DisplayUtil.dip2px(getContext(), 80);
        //获取到显示的宽高
        surface.startX = padding + surface.stepRadius;
        setXaxis();
        surface.lineY = Math.min(surface.currentStepRadius + DisplayUtil.dip2px(getContext(), 5), bgHeight / 2);
    }

    private float downX, downY, moveX, lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getRawX();
                downX = lastX + getScrollX();
                downY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!slideAble) break;
                moveX = event.getRawX();
                float diff = lastX - moveX;
                lastX = moveX;
                int scrollX = getScrollX();
                if (diff + scrollX <= 0)
                    scrollBy(0, 0);
                else if (diff + scrollX >= maxScrollX)
                    scrollBy(maxScrollX - scrollX, 0);
                else
                    scrollBy((int) diff, 0);
                break;
            case MotionEvent.ACTION_UP:
                if (!clickAble) break;
                float upX = event.getX() + getScrollX();
                float upY = event.getY();
                if (upX - downX < 30 && upY - downY < 30) {
                    if (upY < surface.lineY + surface.stepRadius && upY > surface.lineY - surface.stepRadius) {
                        for (int i = 0; i < titles.length; i++) {
                            float x = surface.startX + surface.itemWidth * i;//获得当前的X坐标
                            if (upX < x + surface.stepRadius && upX > x - surface.stepRadius && onClickListener != null) {
                                String message = titles[i];
                                onClickListener.onClick(i, message);
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawPointsAndText(canvas);
    }


    private void drawPointsAndText(Canvas canvas) {
        if (titles == null || titles.length <= 0) return;
        for (int i = 0; i < titles.length; i++) {
            float x = surface.startX + surface.itemWidth * i;//获得当前的X坐标
            int color = i <= progress ? surface.finishColor : surface.unfinishColor;
            //外围点
            drawCurrentPoints(canvas, i, x);
            //绘制索引点
            drawPointsAndNum(canvas, i, x, color);

            //绘制描述文字
            drawText(canvas, i, color);
        }
    }

    private void drawLine(Canvas canvas) {
        if (progress > 0) {
            float middle = surface.startX + surface.itemWidth * progress;//获取中间点
            surface.linePaint.setColor(surface.finishColor);
            canvas.drawLine(surface.startX, surface.lineY, middle, surface.lineY, surface.linePaint);
            surface.linePaint.setColor(surface.unfinishColor);
            canvas.drawLine(middle, surface.lineY, surface.endX, surface.lineY, surface.linePaint);
        } else {
            surface.linePaint.setColor(surface.unfinishColor);
            canvas.drawLine(surface.startX, surface.lineY, surface.endX, surface.lineY, surface.linePaint);
        }
    }

    private void drawCurrentPoints(Canvas canvas, int i, float x) {
        if (progress == i) {
            canvas.drawCircle(x, surface.lineY, surface.currentStepRadius, surface.currentPaint);
        }
    }

    private void drawPointsAndNum(Canvas canvas, int i, float x, int color) {
        surface.stepPaint.setColor(color);
        canvas.drawCircle(x, surface.lineY, surface.stepRadius, surface.stepPaint);
        canvas.drawText(String.valueOf(i + 1), x, surface.lineY + (surface.stepTextPaint.getTextSize() - surface.lineSize) / 2, surface.stepTextPaint);
    }

    private void drawText(Canvas canvas, int i, int color) {
        String title = titles[i];
        surface.textPaint.setColor(color);
        float textX = surface.textPaint.measureText(title);
        float textY = surface.lineY + surface.currentStepRadius + surface.textPaint.getTextSize();
        if (textX >= (surface.itemWidth - padding)) {
            String mag1 = title.substring(0, title.length() / 2);
            String mag2 = title.substring(title.length() / 2, title.length());
            canvas.drawText(mag1, surface.startX + (i * surface.itemWidth), textY, surface.textPaint);
            canvas.drawText(mag2, surface.startX + (i * surface.itemWidth), textY + surface.textPaint.getTextSize(), surface.textPaint);
        } else {
            canvas.drawText(title, surface.startX + (i * surface.itemWidth), textY, surface.textPaint);
        }
    }


    private class Surface {
        private int finishColor;//已经完成的进度颜色
        private int unfinishColor;//当前进度提示颜色
        private int itemWidth;//item宽度
        private int stepRadius;//点的半径
        private int currentStepRadius;//当前点半径
        private float lineSize;//线的大小
        private float textSize;//字体大小
        int startX, endX, lineY;

        public Paint linePaint, currentPaint, stepPaint, textPaint, stepTextPaint;

        public void init() {
            linePaint = new Paint();
            linePaint.setTextAlign(Paint.Align.CENTER);
            linePaint.setAntiAlias(true);
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setStrokeWidth(lineSize);
            linePaint.setTextSize(lineSize);
            stepPaint = new Paint(linePaint);
            currentPaint = new Paint(linePaint);
            currentPaint.setColor(Color.parseColor("#FFB6C1"));
            textPaint = new Paint(linePaint);
            textPaint.setTextSize(textSize);
            stepTextPaint = new Paint(linePaint);
            stepTextPaint.setColor(Color.WHITE);
            stepTextPaint.setTextSize(stepRadius);

        }
    }

    private void setXaxis() {
        if (slideAble) {
            surface.endX = surface.startX + surface.itemWidth * titles.length;
            maxScrollX = (surface.endX + padding + surface.stepRadius) - bgWidth;
        } else {
            surface.endX = bgWidth - padding - surface.stepRadius;
        }
        surface.itemWidth = (surface.endX - surface.startX) / (titles.length - 1);

    }


    public void setProgress(int progress, String[] titles) {
        if (titles == null || progress > titles.length)
            return;
        this.titles = titles;
        this.progress = Math.min(progress, titles.length - 1);
        setXaxis();
        invalidate();
    }


    public HorizontalStepsView setClickAble(boolean clickAble) {
        this.clickAble = clickAble;
        return this;
    }

    public HorizontalStepsView setSlideAble(boolean slideAble) {
        this.slideAble = slideAble;
        return this;
    }

    public HorizontalStepsView setItemWidth(int itemWidth) {
        surface.itemWidth = itemWidth;
        return this;
    }

    public HorizontalStepsView setStepRadius(int stepRadius) {
        surface.stepRadius = stepRadius;
        return this;
    }

    public HorizontalStepsView setLineSize(int lineSize) {
        surface.lineSize = lineSize;
        return this;
    }

    public HorizontalStepsView setTextSize(int textSize) {
        surface.textSize = textSize;
        return this;
    }

    public HorizontalStepsView setFinishColor(int finishColor) {
        surface.finishColor = finishColor;
        return this;
    }

    public HorizontalStepsView setUnfinishColor(int unfinishColor) {
        surface.unfinishColor = unfinishColor;
        return this;
    }


    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, String title);
    }

}
