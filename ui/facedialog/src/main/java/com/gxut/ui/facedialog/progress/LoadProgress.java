package com.gxut.ui.facedialog.progress;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxut.ui.facedialog.R;
import com.gxut.ui.facedialog.common.Param;


/**
 * Created by Bitlike on 2018/1/2.
 */

public class LoadProgress extends DialogFragment {
    private static final String KEY_CANCELED_TOUCH_OUTSIDE = "canceledOnTouchOutside";
    private static final String KEY_SHOW_MESSAGE = "KEY_SHOW_MESSAGE";
    private static final String TAG = "LoadProgress";

    private Param.LoadParam mLoadParam;
    private ProgressView mProgressView;

    @SuppressLint("ValidFragment")
    private LoadProgress() {
    }


    public static LoadProgress newInstance() {
        LoadProgress fragment = new LoadProgress();
        return fragment;
    }

    public void setLoadParam(Param.LoadParam mLoadParam) {
        this.mLoadParam = mLoadParam;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (this.mLoadParam == null || this.mLoadParam.mProgressView == null) {
            this.mProgressView = new SimpleProgressView(getActivity());
        } else {
            this.mProgressView = this.mLoadParam.mProgressView;
        }
        return this.mProgressView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.LoadDialogStyle);
        Bundle bundle = getArguments();
        boolean canceled_touch_outside = bundle.getBoolean(KEY_CANCELED_TOUCH_OUTSIDE, true);
        dialog.setCanceledOnTouchOutside(canceled_touch_outside);
        dialog.setCancelable(canceled_touch_outside);
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (this.mProgressView != null) {
            Bundle bundle = getArguments();
            CharSequence message = bundle.getString(KEY_SHOW_MESSAGE, "");
            this.mProgressView.startAnimation(message);
        }
    }


    public void show(FragmentManager mManager, CharSequence message) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_SHOW_MESSAGE, message);
        setArguments(bundle);
        show(mManager, TAG);
    }


}
