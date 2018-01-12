package com.gxut.simple.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.gxut.baseutil.base.BaseToolBarActivity;
import com.gxut.baseutil.util.JSONUtil;
import com.gxut.baseutil.util.LogUtil;
import com.gxut.simple.R;
import com.gxut.ui.commonui.baserecycler.adapter.GridDecoration;
import com.gxut.ui.commonui.baserecycler.adapter.RecycleAdapter;
import com.gxut.ui.commonui.baserecycler.adapter.SimpleRecycleAdapter;
import com.gxut.ui.commonui.baserecycler.listener.OnRecyclerClickLister;
import com.gxut.ui.commonui.baserecycler.model.SimpleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitlike on 2018/1/12.
 */

public class CommonUiActivity extends BaseToolBarActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected int initLayout() {
        return R.layout.activity_common_ui;
    }

    @Override
    protected void init() throws Exception {
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        List<SimpleModel> models = new ArrayList<>();
        for (int i = 0; i < 49; i++)
            models.add(new SimpleModel("名字" + i, "副标题" + i));
        SimpleRecycleAdapter mAdapter = new SimpleRecycleAdapter(ct, models);
        mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener<SimpleModel>() {
            @Override
            public void itemClick(SimpleModel model, int position) {
                LogUtil.i("gongpengming",JSONUtil.toJSONString(model));
                LogUtil.i("gongpengming","position=" + position);
            }
        });
        mAdapter.setMulti(true);
        mRecyclerView.addOnItemTouchListener(new OnRecyclerClickLister(ct) {
            @Override
            public void onItemClick(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position) {
                LogUtil.i("gongpengming", "onItemClick rv==" + (rv == mRecyclerView));
                LogUtil.i("gongpengming", "position==" + position);
            }

            @Override
            public void onItemLongClick(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position) {
                LogUtil.i("gongpengming", "onItemLongClick rv==" + (rv == mRecyclerView));
                LogUtil.i("gongpengming", "position==" + position);
            }

            @Override
            public void onItemDouble(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position) {
                LogUtil.i("gongpengming", "onItemDouble rv==" + (rv == mRecyclerView));
                LogUtil.i("gongpengming", "position==" + position);
            }
        });
        mRecyclerView.addItemDecoration(new GridDecoration());
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

    }
}
