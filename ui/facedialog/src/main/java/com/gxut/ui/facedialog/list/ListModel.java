package com.gxut.ui.facedialog.list;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bitlike on 2018/1/22.
 */

public class ListModel<T> implements Parcelable {
    private boolean clicked;
    private String content;
    private T data;


    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }


    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.clicked ? (byte) 1 : (byte) 0);
        dest.writeString(this.content);
    }

    public ListModel(String content) {
        this.content = content;
    }

    public ListModel(boolean clicked, String content) {
        this.clicked = clicked;
        this.content = content;
    }

    public ListModel(boolean clicked, String content, T data) {
        this.clicked = clicked;
        this.content = content;
        this.data = data;
    }

    public ListModel() {
    }

    protected ListModel(Parcel in) {
        this.clicked = in.readByte() != 0;
        this.content = in.readParcelable(CharSequence.class.getClassLoader());

    }

    public static final Parcelable.Creator<ListModel> CREATOR = new Parcelable.Creator<ListModel>() {
        @Override
        public ListModel createFromParcel(Parcel source) {
            return new ListModel(source);
        }

        @Override
        public ListModel[] newArray(int size) {
            return new ListModel[size];
        }
    };
}
