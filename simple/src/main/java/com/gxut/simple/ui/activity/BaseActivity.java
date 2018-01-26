package com.gxut.simple.ui.activity;

import com.gxut.baseutil.base.BaseToolBarActivity;

/**
 * Created by Bitlike on 2018/1/22.
 */

public abstract class BaseActivity extends BaseToolBarActivity {

    protected final void showProgress(boolean canCancel, String message) {

    }

    protected final void showProgress(boolean canCancel) {
        showProgress(canCancel, null);
    }

    protected final void showProgress() {
        showProgress(true, null);
    }

    protected final void dismissProgress() {

    }


}
