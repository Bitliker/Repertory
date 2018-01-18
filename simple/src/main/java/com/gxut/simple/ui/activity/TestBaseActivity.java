package com.gxut.simple.ui.activity;

import android.view.View;

import com.gxut.baseutil.base.BaseToolBarActivity;
import com.gxut.baseutil.util.ToastUtils;
import com.gxut.simple.R;

/**
 * Created by Bitlike on 2018/1/18.
 */

public class TestBaseActivity extends BaseToolBarActivity {
    @Override
    protected int initLayout() {
        return R.layout.activity_testbase;
    }

    @Override
    protected void init() throws Exception {
        ToastUtils.init(ct);
        findViewById(R.id.testBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtils.showShort("这个是个提示");
//                showProgress();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dismissProgress();
//                    }
//                }, 1000);
//
            }
        });
    }
}
