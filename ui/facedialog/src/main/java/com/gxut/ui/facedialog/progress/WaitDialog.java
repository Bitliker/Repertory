package com.gxut.ui.facedialog.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxut.ui.facedialog.common.DialogParamer;
import com.gxut.ui.facedialog.common.FaceDialogFragment;

/**
 * Created by Bitlike on 2018/1/25.
 */

public abstract class WaitDialog extends FaceDialogFragment {
    private final String KEY_CONTENT = "KEY_CONTENT";

    @Override
    protected final int getInflater() {
        return 0;
    }

    @Override
    protected final void initView(View view) {
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        CharSequence content = "";
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getCharSequence("KEY_CONTENT", "");
        }
        ProgressView mProgressView = getProgressView();
        mProgressView.startAnimation(content);
        return mProgressView;
    }

    protected abstract ProgressView getProgressView();


    public final void show(FragmentManager mFragmentManager, CharSequence content, DialogParamer mDialogParamer) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_CONTENT, content);
        bundle.putParcelable(KEY_DIALOG_PARAMER, mDialogParamer);
        setArguments(bundle);
        show(mFragmentManager, TAG);
    }
}
