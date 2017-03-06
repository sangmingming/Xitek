package me.isming.xitek.bbs.util;

import android.content.Context;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by sam on 17/3/2.
 */
public class CommonSubscriber<T> extends rx.Subscriber<T> {

    public CommonSubscriber() {
        this(null);
    }

    public CommonSubscriber(Context context) {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {

    }
}
