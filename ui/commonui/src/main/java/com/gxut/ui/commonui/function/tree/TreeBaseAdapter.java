package com.gxut.ui.commonui.function.tree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bitliker on 2017/2/27.
 */

public abstract class TreeBaseAdapter extends RecyclerView.Adapter<TreeBaseAdapter.BaseViewHolder> {
    protected Context mContext;

    /*是否显示选择框*/
    private boolean isHideCB;
    /*显示的节点*/
    private List<Node> showNode;
    /*所有节点*/
    private List<Node> allNode;


    /**
     * @param mContext
     * @param datas              数据源
     * @param defaultExpandLevel 是否默认展开叶子节点
     * @param isHideCB           隐藏CB控件
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public <T extends BaseNodeBean> TreeBaseAdapter(Context mContext, List<T> datas, int defaultExpandLevel, boolean isHideCB)
            throws IllegalArgumentException, IllegalAccessException {
        this.mContext = mContext;
        this.isHideCB = isHideCB;
        //先对所有专递过来的数据源进行处理操作，将它转变成List<Node>列表
        this.allNode = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        this.showNode = TreeHelper.getVisibleNode(this.allNode);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Node node = showNode.get(position);
        bindView(holder, node);
        bindEvent(holder, node, position);

    }

    protected void bindEvent(BaseViewHolder holder, final Node node, final int position) {
        if (holder.itemView == null) return;
        holder.itemView.setPadding(node.getLevel() * 50, 3, 3, 3);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                if (onTreeNodeChangeListener != null)
                    onTreeNodeChangeListener.onItemClick(node);
            }
        });
        if (holder.cb != null) {
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    TreeHelper.setNodeChecked(node, isChecked);
                    if (onTreeNodeChangeListener != null)
                        onTreeNodeChangeListener.onCheckChange(node, isChecked, TreeHelper.getClickedNodes(allNode));
                    notifyDataSetChanged();
                }
            });
        }
    }

    protected void bindView(BaseViewHolder holder, Node node) {
        if (holder.cb != null) {
            holder.cb.setOnCheckedChangeListener(null);
            holder.cb.setChecked(node.isChecked());
        }
        if (holder.title != null &&  node.getTitle().length()>0) {
            holder.title.setText(node.getTitle());
        }
        if (holder.titleSub != null && node.getSubTitle().length()>0) {
            holder.titleSub.setText(node.getSubTitle());
        }
    }

    @Override
    public int getItemCount() {
        return showNode==null||showNode.isEmpty()?0:showNode.size();
    }


    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        if (getItemCount() <= position) return;
        Node n = showNode.get(position);
        int defIndex = showNode.size();
        if (n != null) {// 排除传入参数错误异常
            if (!n.isLeaf()) {//如果不是最后一页
                boolean afterClickExpandStatus = !n.isExpand();//点击后当前叶子节点展开状态
                n.setExpand(afterClickExpandStatus);
                showNode = TreeHelper.getVisibleNode(allNode);
                defIndex = Math.abs(defIndex - showNode.size());
                if (afterClickExpandStatus) {
                    notifyItemRangeInserted(position, defIndex);
                } else {
                    notifyItemRangeRemoved(position, defIndex);
                }
                notifyItemRangeChanged(position, showNode.size() - 1);
            }
        }
    }


    protected class BaseViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cb;
        public TextView title;
        public TextView titleSub;
        public ImageView pointImg;

        public BaseViewHolder(View itemView) {
            super(itemView);
            initItemView(this, itemView);
        }
    }

    /**
     * 注意添加时候必须把父类的View视图惊醒初始化
     *
     * @param itemView
     * @return
     */
    protected abstract void initItemView(BaseViewHolder holder, View itemView);

    private OnTreeNodeChangeListener onTreeNodeChangeListener;

    public void setOnTreeNodeChangeListener(OnTreeNodeChangeListener onTreeNodeChangeListener) {
        this.onTreeNodeChangeListener = onTreeNodeChangeListener;
    }

    public interface OnTreeNodeChangeListener {
        /**
         * 处理node click事件
         *
         * @param node
         */
        void onItemClick(Node node);

        /**
         * 处理checkbox选择改变事件
         *
         * @param node
         * @param checkedNodes
         */
        void onCheckChange(Node node, boolean isSelectAll, List<Node> checkedNodes);
    }

}
