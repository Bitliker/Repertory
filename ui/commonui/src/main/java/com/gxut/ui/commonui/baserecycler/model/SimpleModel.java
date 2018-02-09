package com.gxut.ui.commonui.baserecycler.model;

/**
 * Created by Bitliker on 2017/7/4.
 */

public class SimpleModel<T> {
    private boolean select;
    private double sort;//用于排序的参数
    private String name;
    private String sub;
    private String tag;
    private T data;

    public SimpleModel(boolean select, String name, String sub, String tag) {
        this.select = select;
        this.name = name;
        this.sub = sub;
        this.tag = tag;
    }

    public SimpleModel(String name, String sub, String tag) {
        this.name = name;
        this.sub = sub;
        this.tag = tag;
    }

    public SimpleModel(String name, String sub) {
        this.name = name;
        this.sub = sub;
    }

    public double getSort() {
        return sort;
    }

    public void setSort(double sort) {
        this.sort = sort;
    }

    public SimpleModel(String name) {
        this.name = name;
    }

    public SimpleModel() {
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
