package com.gxut.baseutil.widget.progress;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.gxut.baseutil.R;

/**
 * Created by Bitlike on 2018/1/11.
 */

public class SimpleProgressView extends ProgressView {
    private AppCompatImageView progressImg;
    private AppCompatTextView progressTv;

    public SimpleProgressView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_simple_progress, this, true);
        progressImg = findViewById(R.id.progressImg);
        progressTv = findViewById(R.id.progressTv);
    }

    @Override
    protected void startAnimation(String message) {
        RotateAnimation rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1500);//设置动画持续时间
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        progressImg.setAnimation(rotate);
        progressTv.setText(message == null ? "" : message);
    }
}
