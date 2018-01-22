package com.gxut.baseutil.base;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;


import com.gxut.ui.swipebacklayout.SwipeBackActivity;
import com.gxut.ui.swipebacklayout.layout.SwipeBackLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Bitlike on 2017/12/6.
 */

public abstract class BaseActionBarActivity extends SwipeBackActivity {
    protected BaseActionBarActivity ct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        ct = this;

        View view = initLayoutView();
        if (view != null) {
            setContentView(view);
        } else {
            int layoutId = initLayout();
            if (layoutId > 0) {
                setContentView(layoutId);
            }
        }
        initActionBar();

        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSwipeBackLayout().setEdgeTrackingEnabled(getSwipeMode());
    }

    private void initActionBar() {
        if (needNavigation()) {
            ActionBar mActionBar = getSupportActionBar();
            if (mActionBar != null) {
                mActionBar.setHomeButtonEnabled(true);
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Duration
    protected int getSwipeMode() {
        return SwipeBackLayout.EDGE_LEFT;
    }

    protected final void setEnableGesture(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    protected final void setScreenScllor(boolean enable) {
        getSwipeBackLayout().setScreenScllor(enable);
    }


    /*设置标题*/
    public final void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }



    /*初始化界面，在super.onCreate 之前*/
    protected void preCreate() {
    }


    /*如果toobar存在的话，是否需要返回键*/
    protected boolean needNavigation() {
        return true;
    }

    //隐藏actionbar
    protected void hintActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.hide();
        }
    }

    /*初始化布局*/
    protected abstract int initLayout();

    protected View initLayoutView() {
        return null;
    }

    /*初始化*/
    protected abstract void init() throws Exception;


    @IntDef({SwipeBackLayout.EDGE_LEFT, SwipeBackLayout.EDGE_ALL,
            SwipeBackLayout.EDGE_BOTTOM, SwipeBackLayout.EDGE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }
}
