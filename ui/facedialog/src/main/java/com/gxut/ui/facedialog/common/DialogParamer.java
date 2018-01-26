package com.gxut.ui.facedialog.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

/**
 * Created by Bitlike on 2018/1/22.
 */

public class DialogParamer implements Parcelable {
    public boolean canceledOnTouchOutside;
    public boolean cancelable;
    public int gravity;
    public int animationsStyle;//动画效果

    public DialogParamer() {
        this.gravity = Gravity.CENTER;
        this.canceledOnTouchOutside = true;
        this.cancelable = true;
        this.animationsStyle = -1;
    }

    public static final Creator<DialogParamer> CREATOR = new Creator<DialogParamer>() {
        @Override
        public DialogParamer createFromParcel(Parcel in) {
            return new DialogParamer(in);
        }

        @Override
        public DialogParamer[] newArray(int size) {
            return new DialogParamer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.canceledOnTouchOutside ? (byte) 1 : (byte) 0);
        dest.writeByte(this.cancelable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.gravity);
        dest.writeInt(this.animationsStyle);
    }

    protected DialogParamer(Parcel in) {
        this.canceledOnTouchOutside = in.readByte() != 0;
        this.cancelable = in.readByte() != 0;
        this.gravity = in.readInt();
        this.animationsStyle = in.readInt();
    }

}
