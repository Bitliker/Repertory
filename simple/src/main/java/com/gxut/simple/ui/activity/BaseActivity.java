package com.gxut.simple.ui.activity;

import com.gxut.baseutil.base.BaseToolBarActivity;
import com.gxut.ui.facedialog.FaceDialogBuilder;

/**
 * Created by Bitlike on 2018/1/22.
 */

public abstract class BaseActivity extends BaseToolBarActivity {
    protected FaceDialogBuilder dialogBuilder;

    protected final void showProgress(boolean canCancel, String message) {
        dialogBuilder = new FaceDialogBuilder(ct)
//                .setType(FaceDialogBuilder.TYPE_LOADING)
                .setContent(message)
                .show();
    }

    protected final void showProgress(boolean canCancel) {
        showProgress(canCancel, null);
    }

    protected final void showProgress() {
        showProgress(true, null);
    }

    protected final void dismissProgress() {
        if (dialogBuilder != null) {
            dialogBuilder.dismiss();
        }
    }


}
