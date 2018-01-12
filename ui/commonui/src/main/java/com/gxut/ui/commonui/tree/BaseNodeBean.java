package com.gxut.ui.commonui.tree;

/**
 * 基础节点实体类，所有自定义的对象必须继承与该对象
 * Created by Bitliker on 2017/2/24.
 */
public class BaseNodeBean {

    /*相对本身id*/
    private int id;
    /*当前节点父节点id*/
    private int pid;
    /*显示的标题*/
    private String title;
    /*可能用于显示的副标题*/
    private String subTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
