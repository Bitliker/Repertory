package com.gxut.ui.facedialog.prompt;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import com.gxut.ui.facedialog.R;
import com.gxut.ui.facedialog.common.DialogParamer;
import com.gxut.ui.facedialog.common.FaceDialogFragment;
import com.gxut.ui.facedialog.common.WidgetParamer;

/**
 * Created by Bitlike on 2018/1/25.
 */

public class PromptDialog extends FaceDialogFragment implements View.OnClickListener {

    private final String KEY_SURE_PARAMER = "key_sure_paramer";
    private final String KEY_CANCEL_PARAMER = "key_cancel_paramer";
    private final String KEY_TITLE = "key_title";
    private final String KEY_CONTENT = "key_content";

    @Override
    protected int getInflater() {
        return R.layout.view_dialog_prompt;
    }


    @Override
    protected void initView(View view) {
        AppCompatTextView titleTv = view.findViewById(R.id.titleTv);
        AppCompatTextView contentTv = view.findViewById(R.id.contentTv);
        AppCompatTextView sureTv = view.findViewById(R.id.sureTv);
        AppCompatTextView cancelTv = view.findViewById(R.id.cancelTv);

        Bundle args = getArguments();
        if (args != null) {
            CharSequence title = args.getCharSequence(KEY_TITLE);
            CharSequence content = args.getCharSequence(KEY_CONTENT);
            if (!TextUtils.isEmpty(title)) {
                titleTv.setText(title);
            }
            if (!TextUtils.isEmpty(content)) {
                contentTv.setText(content);
            }
        }

        WidgetParamer sureWidgetParamer = getWidgetParamer(KEY_SURE_PARAMER);
        if (sureWidgetParamer == null) {
            sureTv.setVisibility(View.GONE);
            cancelTv.setBackgroundResource(R.drawable.selector_b_white_hint_bg);
        } else {
            sureTv.setText(TextUtils.isEmpty(sureWidgetParamer.text) ? "" : sureWidgetParamer.text);
            if (sureWidgetParamer.textColor > 0) {
                sureTv.setTextColor(sureWidgetParamer.textColor);
            }
            if (sureWidgetParamer.textSize > 0) {
                sureTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, sureWidgetParamer.textSize);
            }
            sureTv.setOnClickListener(this);
        }
        WidgetParamer cancelWidgetParamer = getWidgetParamer(KEY_CANCEL_PARAMER);
        if (cancelWidgetParamer == null) {
            cancelTv.setVisibility(View.GONE);
            if (sureTv.getVisibility() == View.VISIBLE) {
                sureTv.setBackgroundResource(R.drawable.selector_b_white_hint_bg);
            } else {
                findViewById(R.id.baseRl).setBackgroundResource(R.drawable.radian_white_bg);
                findViewById(R.id.bottomv).setVisibility(View.GONE);
            }
        } else {
            cancelTv.setText(TextUtils.isEmpty(cancelWidgetParamer.text) ? "" : cancelWidgetParamer.text);
            if (cancelWidgetParamer.textColor > 0) {
                cancelTv.setTextColor(cancelWidgetParamer.textColor);
            }
            if (cancelWidgetParamer.textSize > 0) {
                cancelTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelWidgetParamer.textSize);
            }
        }
        cancelTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String key = null;
        if (v.getId() == R.id.sureTv) {
            key = KEY_SURE_PARAMER;
        } else if (v.getId() == R.id.cancelTv) {
            key = KEY_CANCEL_PARAMER;
        }
        WidgetParamer mWidgetParamer = getWidgetParamer(key);
        if (mWidgetParamer != null && mWidgetParamer.onClickListener != null) {
            mWidgetParamer.onClickListener.onClick(v);
        }
        dismiss();
    }


    public void show(FragmentManager mManager, CharSequence title, CharSequence content, DialogParamer mDialogParamer,
                     WidgetParamer sureWidgetParamer, WidgetParamer cancelWidgetParamer) {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(KEY_TITLE, title);
        bundle.putCharSequence(KEY_CONTENT, content);
        bundle.putParcelable(KEY_SURE_PARAMER, sureWidgetParamer);
        bundle.putParcelable(KEY_CANCEL_PARAMER, cancelWidgetParamer);
        bundle.putParcelable(KEY_DIALOG_PARAMER, mDialogParamer);
        setArguments(bundle);
        show(mManager, TAG);
    }


}
