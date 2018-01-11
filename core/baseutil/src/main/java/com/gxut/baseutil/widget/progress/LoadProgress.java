package com.gxut.baseutil.widget.progress;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxut.baseutil.R;


/**
 * Created by Bitlike on 2018/1/2.
 */

public class LoadProgress extends DialogFragment {
    private static final String KEY_CANCELED_TOUCH_OUTSIDE = "canceledOnTouchOutside";
    private static final String KEY_SHOW_MESSAGE = "KEY_SHOW_MESSAGE";
    private static final String TAG = "LoadProgress";

    private FragmentManager mManager;
    private ProgressView mProgressView;

    @SuppressLint("ValidFragment")
    private LoadProgress() {
    }

    public static LoadProgress newInstance(FragmentManager mManager, ProgressView mProgressView) {
        Bundle args = new Bundle();
        LoadProgress fragment = new LoadProgress();
        fragment.setArguments(args);
        fragment.setManager(mManager);
        fragment.setProgressView(mProgressView);
        return fragment;
    }

    private void setManager(FragmentManager mManager) {
        this.mManager = mManager;
    }

    private void setProgressView(ProgressView mProgressView) {
        this.mProgressView = mProgressView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
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
            String message = bundle.getString(KEY_SHOW_MESSAGE, "");
            this.mProgressView.startAnimation(message);
        }
    }


    public void show() {
        show(true, null);
    }

    public void show(boolean canceledOnTouchOutside) {
        show(canceledOnTouchOutside, null);
    }

    public void show(boolean canceledOnTouchOutside, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SHOW_MESSAGE, message);
        bundle.putBoolean(KEY_CANCELED_TOUCH_OUTSIDE, canceledOnTouchOutside);
        setArguments(bundle);
        show(mManager, TAG);
    }
}
