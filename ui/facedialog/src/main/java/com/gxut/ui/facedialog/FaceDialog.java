package com.gxut.ui.facedialog;

import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.gxut.ui.facedialog.common.DialogParamer;
import com.gxut.ui.facedialog.common.FaceDialogFragment;
import com.gxut.ui.facedialog.common.ListDialogParamer;
import com.gxut.ui.facedialog.common.OnItemSelectListener;
import com.gxut.ui.facedialog.common.OnMultiSelectListener;
import com.gxut.ui.facedialog.common.WidgetParamer;
import com.gxut.ui.facedialog.list.ListDialog;
import com.gxut.ui.facedialog.list.ListModel;
import com.gxut.ui.facedialog.prompt.PromptDialog;

import java.util.ArrayList;

/**
 * Created by Bitlike on 2018/1/26.
 */

public class FaceDialog {

    private FaceDialogFragment mFaceDialogFragment;


    public void dismiss() {
        if (mFaceDialogFragment != null && mFaceDialogFragment.isVisible()) {
            mFaceDialogFragment.dismiss();
        }
    }

    public static class Builder<T> {
        private final int DEF_GRAVITY = -126783;

        private FragmentActivity context;

        private boolean canceledOnTouchOutside;
        private boolean cancelable;
        private int gravity;
        private int animationsStyle;//动画效果
        private OnMultiSelectListener<T> onMultiSelectListener;
        private OnItemSelectListener<T> onItemSelectListener;
        private ArrayList<ListModel<T>> models;

        private CharSequence title;
        private CharSequence content;
        private WidgetParamer sureWidgetParamer;
        private WidgetParamer cancelWidgetParamer;


        public Builder(FragmentActivity context) {
            this.context = context;
            this.gravity = DEF_GRAVITY;
            this.cancelable = true;
            this.canceledOnTouchOutside = true;
            this.animationsStyle = -1;
            PackageManager pm = context.getPackageManager();
            this.title = context.getApplicationInfo().loadLabel(pm).toString();
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setContent(CharSequence content) {
            this.content = content;
            return this;
        }

        public Builder setModels(ArrayList<ListModel<T>> models) {
            this.models = models;
            return this;
        }

        public Builder setModels(ArrayList<ListModel<T>> models, OnMultiSelectListener<T> onMultiSelectListener) {
            this.models = models;
            return setOnMultiSelectListener(onMultiSelectListener);
        }

        public Builder setModels(ArrayList<ListModel<T>> models, OnItemSelectListener<T> onItemSelectListener) {
            this.models = models;
            return setOnItemSelectListener(onItemSelectListener);
        }

        public Builder setAnimationsStyle(int animationsStyle) {
            this.animationsStyle = animationsStyle;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }


        private Builder setOnItemSelectListener(OnItemSelectListener<T> onItemSelectListener) {
            this.onItemSelectListener = onItemSelectListener;
            return this;
        }

        private Builder setOnMultiSelectListener(OnMultiSelectListener<T> onMultiSelectListener) {
            this.onMultiSelectListener = onMultiSelectListener;
            return this;
        }

        public Builder setNegative(int textSize, int textColor, String text, View.OnClickListener onClickListener) {
            cancelWidgetParamer = new WidgetParamer(TextUtils.isEmpty(text) ? "取消" : text);
            cancelWidgetParamer.textSize = textSize;
            cancelWidgetParamer.textColor = textColor;
            cancelWidgetParamer.onClickListener = onClickListener;
            return this;
        }

        public Builder setNegative(String text, View.OnClickListener onClickListener) {
            return setNegative(-1, -1, text, onClickListener);
        }

        public Builder setPositive(int textSize, int textColor, String text, View.OnClickListener onClickListener) {
            sureWidgetParamer = new WidgetParamer(TextUtils.isEmpty(text) ? "确定" : text);
            sureWidgetParamer.textSize = textSize;
            sureWidgetParamer.textColor = textColor;
            sureWidgetParamer.onClickListener = onClickListener;
            return this;
        }

        public Builder setPositive(String text, View.OnClickListener onClickListener) {
            return setPositive(-1, -1, text, onClickListener);
        }


        private void setDialogParamer(DialogParamer mDialogParamer) {
            mDialogParamer.animationsStyle = this.animationsStyle;
            mDialogParamer.cancelable = this.cancelable;
            mDialogParamer.canceledOnTouchOutside = this.canceledOnTouchOutside;
            mDialogParamer.gravity = this.gravity;

        }

        public FaceDialog show() {
            if (models != null) {//列表
                ListDialog mListDialog = new ListDialog();
                ListDialogParamer mDialogParamer = new ListDialogParamer();
                setDialogParamer(mDialogParamer);
                if (mDialogParamer.gravity == DEF_GRAVITY) {
                    mDialogParamer.gravity = Gravity.CENTER;
                }
                mDialogParamer.onItemSelectListener = this.onItemSelectListener;
                mDialogParamer.onMultiSelectListener = this.onMultiSelectListener;
                mDialogParamer.multi = onMultiSelectListener != null;
                if (this.cancelWidgetParamer == null) {
                    this.cancelWidgetParamer = new WidgetParamer("取消");
                }
                mListDialog.show(context.getSupportFragmentManager(), title, this.models, mDialogParamer, this.cancelWidgetParamer);
                return ceateFaceDialog(mListDialog);
            } else if (content != null && title != null) {
                PromptDialog promptDialog = new PromptDialog();
                DialogParamer mDialogParamer = new DialogParamer();
                setDialogParamer(mDialogParamer);
                promptDialog.show(context.getSupportFragmentManager(), title, content,
                        mDialogParamer, this.sureWidgetParamer, this.cancelWidgetParamer);
                return ceateFaceDialog(promptDialog);
            }
            return null;
        }

        private FaceDialog ceateFaceDialog(FaceDialogFragment mFaceDialogFragment) {
            FaceDialog mFaceDialog = new FaceDialog();
            mFaceDialog.mFaceDialogFragment = mFaceDialogFragment;
            return mFaceDialog;
        }





    }

}
