package me.isming.xitek.bbs.glide;

import android.graphics.Bitmap;

/**
 * Created by sam on 17/3/7.
 */
public interface ImageDownloadCallback {
    void onDownLoadSuccess(String path);
    void onDownLoadFailed();
}
