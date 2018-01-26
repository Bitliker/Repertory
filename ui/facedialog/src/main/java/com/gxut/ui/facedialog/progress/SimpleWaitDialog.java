package com.gxut.ui.facedialog.progress;

/**
 * Created by Bitlike on 2018/1/25.
 */

public class SimpleWaitDialog extends WaitDialog {
    protected ProgressView getProgressView() {
        return new SimpleProgressView(getActivity());
    }

}
