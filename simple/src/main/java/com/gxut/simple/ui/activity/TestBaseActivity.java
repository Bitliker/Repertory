package com.gxut.simple.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.gxut.baseutil.util.DisplayUtil;
import com.gxut.baseutil.util.ToastUtils;
import com.gxut.simple.R;
import com.gxut.simple.model.User;
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
        findViewById(R.id.pop).setOnClickListener(this);
    }


    private void showListDialog() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("名字" + i);
            user.setAge(i);
            user.setSix(i % 2 == 0 ? "男" : "女");
            users.add(user);
        }

        ArrayList<ListModel<User>> models = new ArrayList<>();

        for (User user : users) {
            models.add(new ListModel<User>(false, user.getName(), user));
        }


        new FaceDialog.Builder<User>(this)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setTitle("这个是标题")
                .setNegative("不取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("gongpengming", "不取消");
                    }
                })
                .setModels(models, new OnMultiSelectListener<User>() {
                    @Override
                    public void selected(List<ListModel<User>> listModels) {
                        for (ListModel<User> e : listModels) {
                            Log.i("gongpengming", "___________________________");
                            Log.i("gongpengming", "e=" + e.isClicked());
                            Log.i("gongpengming", "e=" + e.getContent());
                            User u = e.getData();
                            Log.i("gongpengming", "u=" + u.getAge());
                            Log.i("gongpengming", "u=" + u.getSix());
                            Log.i("gongpengming", "u=" + u.getName());
                        }
                    }
                })
                .show();


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    private void showParamDiaLog() {
        new FaceDialog.Builder(this)
                .setContent("这个是内容这个是内容这个是内容这个是内容这个是内容这个是内容")
                .setPositive(null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("gongpengming", "setNegative");
                    }
                })
                .show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progree:
                showProgress();
                break;
            case R.id.prompt:
                showParamDiaLog();
                break;
            case R.id.list:
                showListDialog();
                break;
            case R.id.pop:
                showPop();
                break;
        }
    }

    private PopupWindow mPopupWindow = null;

    private void showPop() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(this);
            View contentView = LayoutInflater.from(ct).inflate(R.layout.pop_change_passwork, null);
            mPopupWindow.setContentView(contentView);
            mPopupWindow.setBackgroundDrawable(getDrawable(R.drawable.radian_white_bg));
            mPopupWindow.setHeight(DisplayUtil.getScreenWidth(this) * 2 / 3);
            mPopupWindow.setWidth(DisplayUtil.getScreenWidth(this) * 3 / 4);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    DisplayUtil.backgroundAlpha(ct, 1f);
                }
            });
        }
        if (!mPopupWindow.isShowing()) {
            DisplayUtil.backgroundAlpha(this, 0.4f);
            mPopupWindow.showAtLocation(mPopupWindow.getContentView(), Gravity.CENTER, 0, 0);
        }
    }
}
