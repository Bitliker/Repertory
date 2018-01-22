package com.gxut.ui.facedialog.common;

import com.gxut.ui.facedialog.progress.ProgressView;

/**
 * Created by Bitlike on 2018/1/22.
 */

public class Param {
    public boolean canceledOnTouchOutside;
    public boolean cancelable;
    public int cancelTextColor;
    public int cancelTextSize;
    public int animationsIds;//动画效果
    public CharSequence cancelText;

    public Param() {
        this.canceledOnTouchOutside = true;
        this.cancelable = true;
        this.cancelTextColor = -1;
        this.cancelTextSize = 16;
        this.cancelText = "取消";
        this.animationsIds = -1;
    }

    public static class PromptParam extends Param {
        public int sureTextColor;
        public int sureTextSize;
        public CharSequence sureText;

        public PromptParam() {
            super();
            this.sureTextColor = -1;
            this.sureTextSize = 18;
            this.sureText = "确定";

        }
    }


    public static class LoadParam {
        public boolean canceledOnTouchOutside;
        public ProgressView mProgressView;

        public LoadParam() {
            this.canceledOnTouchOutside = true;
        }
    }

    public interface OnPromptConfig {
        void config(PromptParam param);
    }

    public interface OnLoadConfig {
        void config(LoadParam param);
    }
}
