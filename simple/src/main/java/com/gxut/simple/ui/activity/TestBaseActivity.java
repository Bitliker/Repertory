package com.gxut.simple.ui.activity;

import android.view.View;

import com.gxut.baseutil.util.LogUtil;
import com.gxut.baseutil.util.ToastUtils;
import com.gxut.simple.R;
import com.gxut.ui.facedialog.FaceDialogBuilder;
import com.gxut.ui.facedialog.common.Param;

/**
 * Created by Bitlike on 2018/1/18.
 */

public class TestBaseActivity extends BaseActivity {
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
                new FaceDialogBuilder(ct)
                        .setTitle("标题")
                        .setContent("内容")
                        .setPromptConfig(new Param.OnPromptConfig() {
                            @Override
                            public void config(Param.PromptParam param) {
                                param.cancelable = false;
                                param.canceledOnTouchOutside = false;
                                param.animationsIds=R.anim.anim_simple_b_t;
                            }
                        })
                        .setSureOnclickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtil.i("setSureOnclickListener");
                            }
                        })
                        .setCancelOnclickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtil.i("setCancelOnclickListener");
                            }
                        }).show();

            }
        });
    }
}
