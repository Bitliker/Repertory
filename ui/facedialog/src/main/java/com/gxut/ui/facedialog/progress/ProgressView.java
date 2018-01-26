package com.gxut.ui.facedialog.progress;

import android.content.Context;
import android.widget.RelativeLayout;

import java.io.Serializable;

/**
 * Created by Bitlike on 2018/1/2.
 */

public abstract class ProgressView extends RelativeLayout implements Serializable {
    public ProgressView(Context context) {
        super(context);
    }


    protected abstract void startAnimation(CharSequence message);

}
