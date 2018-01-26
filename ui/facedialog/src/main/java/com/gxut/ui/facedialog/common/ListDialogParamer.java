package com.gxut.ui.facedialog.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bitlike on 2018/1/26.
 */

public class ListDialogParamer<T extends Parcelable> extends DialogParamer {
    public boolean multi;
    public OnMultiSelectListener<T> onMultiSelectListener;
    public OnItemSelectListener<T> onItemSelectListener;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.multi ? (byte) 1 : (byte) 0);
    }

    public ListDialogParamer() {
    }

    protected ListDialogParamer(Parcel in) {
        super(in);
        this.multi = in.readByte() != 0;
    }

    public static final Creator<ListDialogParamer> CREATOR = new Creator<ListDialogParamer>() {
        @Override
        public ListDialogParamer createFromParcel(Parcel source) {
            return new ListDialogParamer(source);
        }

        @Override
        public ListDialogParamer[] newArray(int size) {
            return new ListDialogParamer[size];
        }
    };
}
