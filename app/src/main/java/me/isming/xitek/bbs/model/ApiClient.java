package me.isming.xitek.bbs.model;

import me.isming.xitek.bbs.model.WujiServices;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by sam on 17/3/2.
 */
public class ApiClient {
    private static WujiServices sApiServices;
    public static WujiServices getApiServices() {
        if (sApiServices == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()));
            builder.addConverterFactory(GsonConverterFactory.create());
            builder.baseUrl("http://forum.xitek.com");
            Retrofit retrofit = builder.build();
            sApiServices = retrofit.create(WujiServices.class);
        }
        return sApiServices;
    }

}
