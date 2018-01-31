package com.gxut.baseutil.widget;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.gxut.baseutil.R;


/**
 * Created by Bitlike on 2018/1/25.
 */

public abstract class WaitDialog extends AppCompatDialog {

    private final ProgressView mProgressView;

    public WaitDialog(Context context) {
        this(context, R.style.LoadDialogStyle);

    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
        mProgressView = getProgressView();
        setContentView(mProgressView);
    }

    protected abstract ProgressView getProgressView();


    public final void show(boolean cancelable, CharSequence content) {
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        show();
        mProgressView.startAnimation(content);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
