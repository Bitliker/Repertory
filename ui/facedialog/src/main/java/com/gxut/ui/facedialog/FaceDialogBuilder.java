package com.gxut.ui.facedialog;

import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.gxut.ui.facedialog.common.Param;
import com.gxut.ui.facedialog.progress.LoadProgress;
import com.gxut.ui.facedialog.prompt.PromptDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Bitlike on 2018/1/22.
 */

public class FaceDialogBuilder {
    public final static int TYPE_PROMPT = 0, TYPE_LOADING = 1, TYPE_LIST = 3, TYPE_SELECT = 4;

    private FragmentActivity context;//上下文
    private int type;//类型
    private CharSequence title;//标题
    private CharSequence content;//内容

    private View.OnClickListener sureOnclickListener, cancelOnclickListener;//确认取消按钮事件
    private Param.PromptParam promptParam;//提示框配置信息
    private Param.LoadParam loadParam;//进度条配置信息
    private DialogFragment dialog;


    public FaceDialogBuilder(Context context) {
        if (context instanceof FragmentActivity) {
            this.context = (FragmentActivity) context;
        } else {
            new ClassCastException("context Should by FragmentActivity or FragmentActivity Subclass!!");
        }
    }

    private FaceDialogBuilder setType(@Duration int type) {
        this.type = type;
        return this;
    }

    public FaceDialogBuilder setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public FaceDialogBuilder setTitle(int titleId) {
        if (this.context != null) {
            this.title = this.context.getString(titleId);
        }
        return this;
    }


    public FaceDialogBuilder setContent(CharSequence content) {
        this.content = content;
        return this;
    }

    public FaceDialogBuilder setContent(int contentId) {
        if (this.context != null) {
            this.content = this.context.getString(contentId);
        }
        return this;
    }


    public FaceDialogBuilder setSureOnclickListener(View.OnClickListener sureOnclickListener) {
        this.sureOnclickListener = sureOnclickListener;
        return this;
    }

    public FaceDialogBuilder setCancelOnclickListener(View.OnClickListener cancelOnclickListener) {
        this.cancelOnclickListener = cancelOnclickListener;
        return this;
    }

    public FaceDialogBuilder setPromptConfig(Param.OnPromptConfig onConfig) {
        if (onConfig != null) {
            onConfig.config(this.promptParam = new Param.PromptParam());
            setType(TYPE_PROMPT);
        }
        return this;
    }

    public FaceDialogBuilder setLoadParam(Param.OnLoadConfig onConfig) {
        if (onConfig != null) {
            onConfig.config(this.loadParam = new Param.LoadParam());
            setType(TYPE_LOADING);
        }
        return this;
    }

    public FaceDialogBuilder show() {
        switch (this.type) {
            case TYPE_LOADING:
                return showLoading();
            case TYPE_PROMPT:
            default:
                return showPrompt();
        }
    }

    private FaceDialogBuilder showPrompt() {
        dialog = PromptDialog.newInstance();
        PromptDialog promptDialog = (PromptDialog) dialog;
        promptDialog.setPromptParam(promptParam == null ? promptParam = new Param.PromptParam() : promptParam);
        promptDialog.setCancelOnclickListener(cancelOnclickListener);
        promptDialog.setSureOnclickListener(sureOnclickListener);
        promptDialog.show(context.getFragmentManager(), title, content);
        return null;
    }


    public FaceDialogBuilder showLoading() {
        dialog = LoadProgress.newInstance();
        LoadProgress loadProgress = (LoadProgress) dialog;
        loadProgress.setLoadParam(loadParam == null ? loadParam = new Param.LoadParam() : loadParam);
        loadProgress.show(this.context.getFragmentManager(), this.content);
        return this;
    }

    public void dismiss() {
        if (this.dialog != null && this.dialog.isVisible()) {
            this.dialog.dismiss();
        }
    }

    @IntDef({TYPE_LOADING, TYPE_PROMPT, TYPE_LIST, TYPE_SELECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }


}
