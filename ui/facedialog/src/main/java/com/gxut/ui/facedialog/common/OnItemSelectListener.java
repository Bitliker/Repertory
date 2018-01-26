package com.gxut.ui.facedialog.common;

import android.os.Parcelable;

import com.gxut.ui.facedialog.list.ListModel;

/**
 * Created by Bitlike on 2018/1/26.
 */

public interface  OnItemSelectListener<T extends Parcelable> {
     void click(ListModel<T> models);
}
