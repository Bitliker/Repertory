package com.gxut.code.network.response;

import com.gxut.code.network.request.Tags;

/**
 * Created by Bitliker on 2017/7/18.
 */

public interface OnHttpCallback {
    void onSuccess(int what, String message, Tags tags);

    void onFailure(int what, String message, Tags tags);
}
