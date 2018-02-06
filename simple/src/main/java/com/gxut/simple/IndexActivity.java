package com.gxut.simple;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gxut.baseutil.util.LogUtil;
import com.gxut.baseutil.util.ToastUtils;
import com.gxut.simple.ui.activity.BaseActivity;
import com.gxut.simple.ui.activity.TestBaseActivity;
import com.gxut.simple.ui.activity.TestRefreshActivity;
import com.gxut.ui.facedialog.list.ListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bitlike on 2018/2/6.
 */

public class IndexActivity extends BaseActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected int initLayout() {
        return R.layout.activity_index;
    }

    @Override
    protected void init() throws Exception {
        ToastUtils.init(getApplicationContext());
        initView();
        initData();
    }


    private void initView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ct, LinearLayout.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ct));
    }

    private void initData() {

        ArrayList<ListModel<Class>> models = new ArrayList<>();
        models.add(new ListModel<Class>(false, "基础库运用", TestBaseActivity.class));
        models.add(new ListModel<Class>(false, "刷新控件", TestRefreshActivity.class));
        mRecyclerView.setAdapter(new ListAdapter(models));
    }

    private class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        private List<ListModel<java.lang.Class>> models;

        public ListAdapter(List<ListModel<java.lang.Class>> models) {
            this.models = models;
        }


        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListAdapter.ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
            ListModel<java.lang.Class> model = models.get(position);
            holder.contentTv.setText(model.getContent());
            holder.itemView.setBackgroundResource(com.gxut.ui.facedialog.R.drawable.selector_white_hint_bg);
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
                        ListModel<java.lang.Class> model = models.get(position);

                        startActivity(new Intent(ct,model.getData()));
                        LogUtil.i("点击了" + position);
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
