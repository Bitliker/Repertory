package com.gxut.ui.refreshlayout.refresh.smart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.gxut.ui.refreshlayout.refresh.BaseRefreshLayout;
import com.gxut.ui.refreshlayout.refresh.BaseRefreshView;


/**
 * Created by Bitliker on 2017/9/18.
 */

public class SmartRefreshLayout extends BaseRefreshLayout {

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected BaseRefreshView getHeader(LayoutInflater mInflater) {
        return new SmartRefreshHeader(getContext(), mInflater);
    }

    @Override
    protected BaseRefreshView getFooter(LayoutInflater mInflater) {
        return new SmartRefreshFooter(getContext(), mInflater);
    }
}
