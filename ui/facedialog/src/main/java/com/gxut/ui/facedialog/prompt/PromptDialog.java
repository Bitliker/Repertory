package com.gxut.ui.facedialog.prompt;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxut.ui.facedialog.R;
import com.gxut.ui.facedialog.common.Param;

/**
 * Created by Bitlike on 2018/1/22.
 */

public class PromptDialog extends DialogFragment implements View.OnClickListener {
    private final String TAG = "PromptDialog";
    private final String TITLE = "title";
    private final String CONTENT = "content";
    private final String ANIMATIONS_IDS = "animationsIds";

    @SuppressLint("ValidFragment")
    private PromptDialog() {
    }

    public static PromptDialog newInstance() {
        return new PromptDialog();
    }


    private Param.PromptParam promptParam;
    private View.OnClickListener sureOnclickListener, cancelOnclickListener;

    public void setPromptParam(Param.PromptParam promptParam) {
        this.promptParam = promptParam;
    }

    public void setCancelOnclickListener(View.OnClickListener cancelOnclickListener) {
        this.cancelOnclickListener = cancelOnclickListener;
    }

    public void setSureOnclickListener(View.OnClickListener sureOnclickListener) {
        this.sureOnclickListener = sureOnclickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.LoadDialogStyle);
        dialog.setCanceledOnTouchOutside(promptParam.canceledOnTouchOutside);
        dialog.setCancelable(promptParam.cancelable);
        if (promptParam.animationsIds > 0) {
            dialog.getWindow().setWindowAnimations(promptParam.animationsIds);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_dialog_prompt, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView contentTv = view.findViewById(R.id.contentTv);
        TextView sureTv = view.findViewById(R.id.sureTv);
        TextView cancelTv = view.findViewById(R.id.cancelTv);
        Bundle bundle = getArguments();
        if (bundle != null) {
            CharSequence title = bundle.getCharSequence(TITLE, "提示");
            CharSequence content = bundle.getCharSequence(CONTENT, "");
            titleTv.setText(title);
            contentTv.setText(content);
        }

        if (this.promptParam != null) {
            int textSize = Math.max(this.promptParam.sureTextSize, this.promptParam.cancelTextSize);
            if (!TextUtils.isEmpty(this.promptParam.sureText)) {
                sureTv.setText(this.promptParam.sureText);
                sureTv.setTextSize(textSize);
                if (this.promptParam.sureTextColor > 0) {
                    sureTv.setTextColor(this.promptParam.sureTextColor);
                }
            }
            if (!TextUtils.isEmpty(this.promptParam.cancelText)) {
                cancelTv.setText(this.promptParam.cancelText);
                cancelTv.setTextSize(textSize);
                if (this.promptParam.cancelTextColor > 0) {
                    cancelTv.setTextColor(this.promptParam.cancelTextColor);
                }

            }
        }
        cancelTv.setOnClickListener(this);
        sureTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancelTv) {
            if (cancelOnclickListener != null) {
                cancelOnclickListener.onClick(v);
            }
        } else if (v.getId() == R.id.sureTv) {
            if (sureOnclickListener != null) {
                sureOnclickListener.onClick(v);
            }
        }
        dismiss();
    }

    public void show(FragmentManager mManager, CharSequence title, CharSequence content) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(TITLE, title);
        bundle.putCharSequence(CONTENT, content);
        setArguments(bundle);
        show(mManager, TAG);
    }


}
