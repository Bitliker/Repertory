package com.gxut.ui.commonui.baserecycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxut.ui.commonui.baserecycler.listener.OnRecyclerItemClickListener;

import java.util.List;

/**
 * 相关REcyclerView适配器的通用封装类，简化一些操作和重复代码，简化itemclick操作
 * Created by Bitlike on 2018/1/12.
 */
public abstract class RecycleAdapter<T, VH extends RecycleAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context ct;
    private List<T> models;
    private OnRecyclerItemClickListener onRecyclerClickLister;
    private OnItemClickListener<T> onItemClickListener;


    public RecycleAdapter(Context ct, List<T> models) {
        this.ct = ct;
        if (this.ct == null) {
            throw new NullPointerException("ct can`t be null");
        }
        this.models = models;
        if (onRecyclerClickLister == null) {
            onRecyclerClickLister = new OnRecyclerItemClickListener(ct) {
                @Override
                public void onItemTouch(RecyclerView rv, RecyclerView.ViewHolder viewHolder, int position) {
                    T model = getModel(position);
                    itemClick(model, position);
                    if (RecycleAdapter.this.onItemClickListener != null) {
                        RecycleAdapter.this.onItemClickListener.itemClick(model, position);
                    }
                }
            };
        }
    }

    public List<T> getModels() {
        return models;
    }


    public T getModel(int position) {
        return (models != null && models.size() > position&&position>=0) ? models.get(position) : null;
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

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

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


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (this.onItemClickListener != null && onRecyclerClickLister != null) {
            recyclerView.addOnItemTouchListener(onRecyclerClickLister);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (this.onItemClickListener != null && onRecyclerClickLister != null) {
            recyclerView.removeOnItemTouchListener(onRecyclerClickLister);
        }
    }


    protected void itemClick(T model, int position) {

    }

    public interface OnItemClickListener<T> {
        void itemClick(T model, int position);
    }

}
