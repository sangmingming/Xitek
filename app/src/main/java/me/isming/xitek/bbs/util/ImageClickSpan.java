package me.isming.xitek.bbs.util;

import android.os.Parcelable;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import me.isming.xitek.bbs.ui.PhotoViewActivity;

/**
 * Created by sam on 17/3/6.
 */
public class ImageClickSpan extends URLSpan {
    public ImageClickSpan(String url) {
        super(url);
    }

    @Override
    public void onClick(View widget) {
        PhotoViewActivity.show(widget.getContext(), getURL());
    }


    public void updateDrawState(TextPaint ds) {
    }

}
