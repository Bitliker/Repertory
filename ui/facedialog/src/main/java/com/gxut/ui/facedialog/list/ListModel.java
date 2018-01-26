package com.gxut.ui.facedialog.list;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bitlike on 2018/1/22.
 */

public class ListModel<T extends Parcelable> implements Parcelable {
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
        dest.writeString(this.data.getClass().getName());
        dest.writeParcelable(this.data, flags);
    }

    public ListModel() {
    }

    protected ListModel(Parcel in) {
        this.clicked = in.readByte() != 0;
        this.content = in.readParcelable(CharSequence.class.getClassLoader());
        String dataName = in.readString();
        try {
            this.data = in.readParcelable(Class.forName(dataName).getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
