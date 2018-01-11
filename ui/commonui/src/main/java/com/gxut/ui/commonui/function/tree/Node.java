package com.gxut.ui.commonui.function.tree;



import java.util.ArrayList;
import java.util.List;

/**
 * 节点工具类
 * Created by Bitliker on 2017/2/27.
 */

public class Node {

    private BaseNodeBean t;


    /**
     * 节点id
     */
    private int id;
    /**
     * 父节点id
     */
    private int pId;
    /**
     * 当前节点是否展开状态
     */
    private boolean isExpand = false;

    /**
     * 是否选中CB
     */
    private boolean isChecked = false;
    /**
     * 节点标题
     */
    private String title;

    /**
     * 节点副标题
     */
    private String subTitle;


    /**
     * 节点级别,所在的第几层
     */
    private int level;

    /**
     * 节点所含的子节点
     */
    private List<Node> childrenNodes;
    /**
     * 节点的父节点
     */
    private Node parent;


    public BaseNodeBean getT() {
        return t;
    }

    public void setT(BaseNodeBean t) {
        this.t = t;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title==null?"":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return   subTitle==null?"":subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Node> getChildrenNodes() {
        if (childrenNodes == null) childrenNodes = new ArrayList<>();
        return childrenNodes;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * 当父节点收起，其子节点也收起，应该返回它影响的的节点数量
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {///设置为关闭
            if (!(childrenNodes==null||childrenNodes.isEmpty()))
                for (Node node : childrenNodes) {
                    node.setExpand(isExpand);
                }
        }
    }


    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 判断是否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return childrenNodes==null||childrenNodes.isEmpty();
    }

    /**
     * 判断当前节点时候是最终状态，不可点击展开
     *
     * @return
     */
    public boolean isShowExpandIcon() {
        if (isLeaf() || isExpand)
            return true;
        return false;
    }
}
