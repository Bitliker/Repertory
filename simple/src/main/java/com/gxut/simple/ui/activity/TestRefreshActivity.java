package com.gxut.simple.ui.activity;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gxut.baseutil.util.JSONUtil;
import com.gxut.baseutil.util.ToastUtils;
import com.gxut.simple.R;
import com.gxut.simple.model.User;
import com.gxut.ui.facedialog.list.ListModel;
import com.gxut.ui.refreshlayout.refresh.BaseRefreshLayout;
import com.gxut.ui.refreshlayout.refresh.smart.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitlike on 2018/2/6.
 */

public class TestRefreshActivity extends BaseActivity {
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected int initLayout() {
        return R.layout.activity_test_refresh;
    }

    @Override
    protected void init() throws Exception {
        initView();
        initData();
    }

    private void initView() {
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        mSmartRefreshLayout.setEnablePullDown(true);
        mSmartRefreshLayout.setEnabledPullUp(true);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ct, LinearLayout.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ct));
        mSmartRefreshLayout.setOnRefreshListener(new BaseRefreshLayout.onRefreshListener() {
            @Override
            public void onRefresh() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSmartRefreshLayout.stopRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSmartRefreshLayout.stopRefresh();
                    }
                }, 2000);
            }
        });


    }


    private void initData() {
        ArrayList<ListModel<User>> models = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            models.add(new ListModel("名字" + i));
        }
        mRecyclerView.setAdapter(new ListAdapter(false, models));

    }


    private class ListAdapter<T> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private List<ListModel<T>> selectModels;
        private List<ListModel<T>> models;
        private boolean multi;

        public ListAdapter(boolean multi, List<ListModel<T>> models) {
            this.models = models;
            this.multi = multi;
            if (multi) {
                selectModels = new ArrayList<>();
                if (this.models != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (ListModel<T> e : ListAdapter.this.models) {
                                if (e.isClicked()) {
                                    selectModels.add(e);
                                }
                            }
                        }
                    }).start();
                }
            }
        }

        public void selectAll(boolean selectAll) {
            for (ListModel<T> e : models) {
                e.setClicked(selectAll);
            }
            if (selectModels == null) {
                selectModels = new ArrayList<>();
            } else {
                selectModels.clear();
            }
            if (selectAll) {
                selectModels.addAll(models);
            }
            notifyDataSetChanged();
        }


        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListAdapter.ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
            ListModel<T> model = models.get(position);
            holder.contentTv.setText(model.getContent());

            if (multi && model.isClicked()) {
                holder.itemView.setBackgroundResource(com.gxut.ui.facedialog.R.drawable.cyan_bg);
            } else {
                holder.itemView.setBackgroundResource(com.gxut.ui.facedialog.R.drawable.selector_white_hint_bg);
            }
            holder.itemView.setPadding(20, 40, 20, 40);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return models == null ? 0 : models.size();
        }


        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null && v.getTag() != null && v.getTag() instanceof Integer) {
                    int position = (int) v.getTag();
                    if (getItemCount() > position) {
                        ListModel<T> model = models.get(position);
                        ToastUtils.showShort(JSONUtil.toJSONString(model));
                    }
                }
            }
        };

        class ViewHolder extends RecyclerView.ViewHolder {
            AppCompatTextView contentTv;

            public ViewHolder(ViewGroup mViewGroup) {
                this(LayoutInflater.from(ct).inflate(com.gxut.ui.facedialog.R.layout.item_list_dialog, mViewGroup, false));
            }

            public ViewHolder(View itemView) {
                super(itemView);
                contentTv = itemView.findViewById(com.gxut.ui.facedialog.R.id.contentTv);
            }
        }
    }
}
