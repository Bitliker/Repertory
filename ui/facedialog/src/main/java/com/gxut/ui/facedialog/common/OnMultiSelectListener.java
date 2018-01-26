package com.gxut.ui.facedialog.common;


import com.gxut.ui.facedialog.list.ListModel;

import java.util.List;

/**
 * Created by Bitlike on 2018/1/26.
 */

public interface OnMultiSelectListener<T>  {
     void selected(List<ListModel<T>> models);
}
