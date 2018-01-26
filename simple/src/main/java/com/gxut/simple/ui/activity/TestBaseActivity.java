package com.gxut.simple.ui.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.gxut.baseutil.util.ToastUtils;
import com.gxut.simple.R;
import com.gxut.ui.facedialog.FaceDialog;
import com.gxut.ui.facedialog.common.OnMultiSelectListener;
import com.gxut.ui.facedialog.list.ListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitlike on 2018/1/18.
 */

public class TestBaseActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int initLayout() {
        return R.layout.activity_testbase;
    }

    @Override
    protected void init() throws Exception {
        ToastUtils.init(ct);

        findViewById(R.id.progree).setOnClickListener(this);
        findViewById(R.id.prompt).setOnClickListener(this);
        findViewById(R.id.list).setOnClickListener(this);
    }


    private void showListDialog() {
        ArrayList<ListModel> models = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ListModel e = new ListModel();
            e.setContent("这个是内容" + i);
            models.add(e);
        }
        new FaceDialog.Builder<>(this)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("这个是标题")
                .setGravity(Gravity.BOTTOM)
                .setNegative("不取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("gongpengming", "不取消");
                    }
                })
                .setModels(models, new OnMultiSelectListener() {
                    @Override
                    public void selected(List list) {
                        Log.i("gongpengming", "listModels=" + list.size());
                    }
                })
                .show();


    }

    private void showLoad() {
        new FaceDialog.Builder<>(this)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setContent("这个是内容")
                .showWait();
    }

    private void showParamDiaLog() {

        new FaceDialog.Builder(this)
                .setCancelable(false)
                .setTitle("标题")
                .setCanceledOnTouchOutside(false)
                .setContent("这个是内容这个是内容这个是内容这个是内容这个是内容这个是内容")
                .setPositive(null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("gongpengming", "setNegative");
                    }
                })
                .show();
//          .setPositive(null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("gongpengming", "setPositive");
//            }
//        })
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progree:
                showLoad();
                break;
            case R.id.prompt:
                showParamDiaLog();
                break;
            case R.id.list:
                showListDialog();
                break;
        }
    }
}
