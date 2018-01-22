package com.gxut.baseutil.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxut.baseutil.R;
import com.gxut.ui.swipebacklayout.SwipeBackActivity;
import com.gxut.ui.swipebacklayout.layout.SwipeBackLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Bitlike on 2017/12/6.
 */

public abstract class BaseToolBarActivity extends SwipeBackActivity {
    protected BaseToolBarActivity ct;
    private Toolbar commonToolBar;
    private TextView commonTitleTv;
    private RelativeLayout contentRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preCreate();
        super.onCreate(savedInstanceState);
        ct = this;
        super.setContentView(R.layout.base_bar_layout);
        contentRl = findViewById(R.id.contentRl);
        if (needCommonToolBar()) {
            initCommonToolbar();
        }
        setContentView(initLayout());
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSwipeBackLayout().setEdgeTrackingEnabled(getSwipeMode());
    }


    @Override
    public void setContentView(@LayoutRes int layoutId) {
        if (layoutId > 0) {
            if (contentRl != null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(layoutId, contentRl);
            } else {
                super.setContentView(layoutId);
            }

        }
    }

    @Override
    public void setContentView(View contentView) {
        setContentView(contentView, null);
    }

    @Override
    public void setContentView(View contentView, ViewGroup.LayoutParams params) {
        if (contentView != null) {
            if (contentRl != null) {
                contentRl.removeAllViews();
                if (params == null) {
                    params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                }
                contentRl.addView(contentView, params);
            } else {
                super.setContentView(contentView);
            }
        }
    }

    private void initCommonToolbar() {
        ViewStub stub = findViewById(R.id.toolbarVs);
        stub.inflate();
        commonToolBar = findViewById(R.id.commonToolBar);
        commonTitleTv = findViewById(R.id.commonTitleTv);
        if (commonToolBar != null) {
            setSupportActionBar(commonToolBar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        if (needNavigation()) {
            setNavigation(0, null);
        }
        commonTitleTv.setText(getTitle());
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



    /*设置自定义的toolbar*/
    protected final void setToolBar(int toolBarId) {
        hidetoolBar();
        commonToolBar = contentRl.findViewById(toolBarId);
        if (commonToolBar != null) {
            setSupportActionBar(commonToolBar);
            //设置actionBar的标题是否显示，对应ActionBar.DISPLAY_SHOW_TITLE。
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /*隐藏toolbar*/
    protected final void hidetoolBar() {
        if (commonToolBar != null) {
            commonToolBar.setVisibility(View.GONE);
        }
    }

    /*设置toolbar的点击监听器*/
    protected final void setToolBarMenuClickListener(Toolbar.OnMenuItemClickListener onclick) {
        if (commonToolBar != null) {
            commonToolBar.setOnMenuItemClickListener(onclick);
        }
    }

    /*设置标题*/
    public final void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (commonTitleTv != null) {
                commonTitleTv.setText(title);
            }
            getSupportActionBar().setTitle(title);
        }
    }


    protected final void setNavigation(int iconId, View.OnClickListener onClickListener) {
        if (iconId > 0) {
            commonToolBar.setNavigationIcon(iconId);
        } else {
            commonToolBar.setNavigationIcon(R.mipmap.icon_common_back);
        }
        if (onClickListener == null) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            };
        }
        //设置返回按钮的点击事件
        commonToolBar.setNavigationOnClickListener(onClickListener);
    }


    /*初始化界面，在super.onCreate 之前*/
    protected void preCreate() {
    }

    /*是否需要通用导航栏*/
    protected boolean needCommonToolBar() {
        return true;
    }

    /*如果toobar存在的话，是否需要返回键*/
    protected boolean needNavigation() {
        return true;
    }

    /*初始化布局*/
    protected abstract int initLayout();

    /*初始化*/
    protected abstract void init() throws Exception;


    @IntDef({SwipeBackLayout.EDGE_LEFT, SwipeBackLayout.EDGE_ALL,
            SwipeBackLayout.EDGE_BOTTOM, SwipeBackLayout.EDGE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }
}
