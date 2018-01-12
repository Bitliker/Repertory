package com.gxut.ui.commonui.baserecycler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gxut.ui.commonui.R;
import com.gxut.ui.commonui.baserecycler.model.SimpleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的列表展示选择例子，可选自定义高度或指定高度，数据量过大时候会在结尾以。。代替
 * autoHeight ：是否需要自动伸缩高度
 * showCb：是否显示选择Cb控件
 * isMulti：是否是多选菜单
 * Created by Bitlike on 2018/1/12.
 */

public class SimpleRecycleAdapter<T> extends RecycleAdapter<SimpleModel<T>, SimpleRecycleAdapter.ViewHolder> {
    private int lastPostion = -1;
    private boolean autoHeight = true;//是否自适应高度
    private boolean showCb = false;//是否显示选择框
    private boolean isMulti = false;//是否多选
    private List<SimpleModel<T>> selectModels;//选择中的选项


    public SimpleRecycleAdapter(Context ct, List<SimpleModel<T>> models) {
        super(ct, models);
    }

    public SimpleRecycleAdapter setAutoHeight(boolean autoHeight) {
        this.autoHeight = autoHeight;
        return this;
    }

    public SimpleRecycleAdapter setShowCb(boolean showCb) {
        this.showCb = showCb;
        return this;
    }

    public SimpleRecycleAdapter setMulti(boolean isMulti) {
        this.isMulti = isMulti;
        if (isMulti) {
            setShowCb(isMulti);
        }
        return this;
    }


    public List<SimpleModel<T>> getSelectModels() {
        return selectModels==null?selectModels=new ArrayList<>():selectModels;
    }

    public SimpleModel<T> getLastSelectModel() {
        return getModel(lastPostion);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = autoHeight ? R.layout.item_select_auto : R.layout.item_select;
        return new ViewHolder(parent, id);
    }

    @Override
    public void onBindViewHolder(SimpleRecycleAdapter.ViewHolder holder, int position) {
        SimpleModel model = getModel(position);
        if (model != null) {
            holder.nameTv.setText(model.getName());
            holder.subTv.setVisibility(TextUtils.isEmpty(model.getSub()) ? View.GONE : View.VISIBLE);
            holder.tagTv.setVisibility(TextUtils.isEmpty(model.getTag()) ? View.GONE : View.VISIBLE);
            holder.subTv.setText(model.getSub());
            holder.tagTv.setText(model.getTag());
        }
        holder.selectCB.setVisibility(showCb || isMulti ? View.VISIBLE : View.GONE);
        holder.selectCB.setFocusable(false);
        holder.selectCB.setClickable(false);
        holder.selectCB.setChecked(model.isSelect());
    }


    @Override
    protected void itemClick(SimpleModel<T> model, int position) {
        if (showCb || isMulti) {//显示选择框
            if (isMulti) {//多选
                showCbMulti(position);
            } else {
                showCbRadio(position);
            }
        }
    }

    /*当多选时候点击item*/
    private void showCbMulti(int position) {
        SimpleModel<T> model = getModel(position);
        if (model != null) {
            if (model.isSelect()) {
                getSelectModels().remove(model);
            } else {
                getSelectModels().add(model);
            }
            model.setSelect(!model.isSelect());
            notifyItemChanged(position);

        }
    }

    /*当单选有显示选择框的时候点击item*/
    private void showCbRadio(int position) {
        SimpleModel<T> model = getModel(position);
        if (lastPostion == -1 || lastPostion == position) {
            model.setSelect(!model.isSelect());
            notifyItemChanged(position);
            if (lastPostion == position) {
                lastPostion = -1;
            } else {
                lastPostion = position;
            }
        } else {
            model.setSelect(!model.isSelect());
            SimpleModel<T> lastModel = getModel(lastPostion);
            lastModel.setSelect(!lastModel.isSelect());
            notifyItemChanged(position);
            notifyItemChanged(lastPostion);
            lastPostion = position;
        }
    }


    class ViewHolder extends RecycleAdapter.ViewHolder {
        CheckBox selectCB;
        TextView nameTv, subTv, tagTv;

        public ViewHolder(ViewGroup mViewGroup, int layoutId) {
            super(mViewGroup, layoutId);
        }

        @Override
        public void initItemView(View itemView) {
            selectCB = (CheckBox) itemView.findViewById(R.id.selectCB);
            subTv = (TextView) itemView.findViewById(R.id.subTv);
            nameTv = (TextView) itemView.findViewById(R.id.nameTv);
            tagTv = (TextView) itemView.findViewById(R.id.tagTv);
        }
    }

}
