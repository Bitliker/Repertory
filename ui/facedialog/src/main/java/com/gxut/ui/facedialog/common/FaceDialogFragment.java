package com.gxut.ui.facedialog.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxut.ui.facedialog.R;

/**
 * Created by Bitlike on 2018/1/25.
 */

public abstract class FaceDialogFragment extends AppCompatDialogFragment {
    protected final String TAG = getClass().getSimpleName();
    protected final String KEY_DIALOG_PARAMER = "DialogParamer";

    private View rootView;

    public final <T extends View> T findViewById(@IdRes int id) {
        if (id <= 0 || rootView == null) {
            return null;
        }
        return rootView.findViewById(id);
    }

    protected WidgetParamer getWidgetParamer(String key) {
        Bundle args = getArguments();
        WidgetParamer mWidgetParamer = null;
        if (args != null && key != null) {
            mWidgetParamer = args.getParcelable(key);
        }
        return mWidgetParamer;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getInflater(), container, false);
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getDialogStyle());
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside());
        dialog.setCancelable(cancelAble());
        int animationsStyle = getAnimationsStyle();
        if (animationsStyle > 0) {
            dialog.getWindow().setWindowAnimations(animationsStyle);
        }
        dialog.getWindow().setGravity(getGravity());
        return dialog;
    }

    protected int getDialogStyle() {
        return R.style.LoadDialogStyle;
    }

    protected boolean canceledOnTouchOutside() {
        DialogParamer mDialogParamer = getDialogParamer();
        if (mDialogParamer != null) {
            return mDialogParamer.canceledOnTouchOutside;
        }
        return true;
    }

    protected int getAnimationsStyle() {
        DialogParamer mDialogParamer = getDialogParamer();
        if (mDialogParamer != null) {
            return mDialogParamer.animationsStyle;
        }
        return -1;
    }

    protected int getGravity() {
        DialogParamer mDialogParamer = getDialogParamer();
        if (mDialogParamer != null) {
            return mDialogParamer.gravity;
        }
        return -1;
    }

    protected boolean cancelAble() {
        DialogParamer mDialogParamer = getDialogParamer();
        if (mDialogParamer != null) {
            return mDialogParamer.cancelable;
        }
        return true;
    }

    protected DialogParamer getDialogParamer() {
        Bundle args = getArguments();
        DialogParamer mDialogParamer = null;
        if (args != null) {
            mDialogParamer = args.getParcelable(KEY_DIALOG_PARAMER);
        }
        return mDialogParamer;
    }

    protected abstract void initView(View view);

    protected abstract int getInflater();
}
