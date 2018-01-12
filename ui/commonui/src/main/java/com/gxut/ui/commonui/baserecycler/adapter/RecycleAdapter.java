package com.gxut.ui.commonui.baserecycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 相关REcyclerView适配器的通用封装类，简化一些操作和重复代码，简化itemclick操作
 * Created by Bitlike on 2018/1/12.
        */
public abstract class RecycleAdapter<T, VH extends RecycleAdapter.ViewHolder> extends RecyclerView.Adapter<VH> implements View.OnClickListener {

    private Context ct;
    private List<T> models;


    public RecycleAdapter(Context ct, List<T> models) {
        this.ct = ct;
        if (this.ct == null) {
            throw new NullPointerException("ct can`t be null");
        }
        this.models = models;
    }

    public List<T> getModels() {
        return models;
    }


    public T getModel(int position) {
        return models != null && models.size() > position ? models.get(position) : null;
    }

    public void setModels(List<T> models) {
        this.models = models;
    }

    public void addModels(List<T> models) {
        if (models == null || models.size() == 0) {
            this.models = models;
            notifyDataSetChanged();
        } else {
            int startItem = this.models.size();
            this.models.addAll(models);
            notifyItemRangeInserted(startItem, this.models.size());
            notifyItemRangeChanged(startItem, this.models.size());
        }
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    private LayoutInflater mInflater;

    public LayoutInflater getInflater() {
        return mInflater == null ? mInflater = LayoutInflater.from(ct) : mInflater;
    }

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        bindData(holder, position);
        if (this.onItemClickListener != null) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
        }
    }

    public abstract void bindData(VH holder, int position);

    protected abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ViewGroup mViewGroup, int layoutId) {
            this(getInflater().inflate(layoutId, mViewGroup, false));
        }

        private ViewHolder(View itemView) {
            super(itemView);
            initItemView(itemView);
        }

        public abstract void initItemView(View itemView);
    }


    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener<T> onItemClickListener;

    public interface OnItemClickListener<T> {
        void itemClick(T model, int position);
    }

    @Override
    public final void onClick(View v) {
        if (v == null || v.getTag() == null || onItemClickListener == null) {
            return;
        }
        if (v.getTag() instanceof Integer) {
            int position = (int) v.getTag();
            T model = getModel(position);
            onItemClickListener.itemClick(model, position);
            itemClick(model, position);
        }
    }

    protected void itemClick(T model, int position) {

    }
}
