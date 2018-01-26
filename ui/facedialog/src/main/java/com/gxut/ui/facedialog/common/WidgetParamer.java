package com.gxut.ui.facedialog.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by Bitlike on 2018/1/24.
 */

public class WidgetParamer implements Parcelable {


    public int textColor;
    public int textSize;
    public String text;
    public View.OnClickListener onClickListener;

    public WidgetParamer() {
        this("");
    }

    public WidgetParamer(String text) {
        textColor = -1;
        textSize = -1;
        this.text = text;
    }


    protected WidgetParamer(Parcel in) {
        textColor = in.readInt();
        textSize = in.readInt();
        text = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(textColor);
        dest.writeInt(textSize);
        dest.writeString(text);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WidgetParamer> CREATOR = new Creator<WidgetParamer>() {
        @Override
        public WidgetParamer createFromParcel(Parcel in) {
            return new WidgetParamer(in);
        }

        @Override
        public WidgetParamer[] newArray(int size) {
            return new WidgetParamer[size];
        }
    };
}
