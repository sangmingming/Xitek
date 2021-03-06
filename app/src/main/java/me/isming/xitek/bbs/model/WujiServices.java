package me.isming.xitek.bbs.model;

import java.util.List;

import me.isming.xitek.bbs.model.bean.MineInfo;
import me.isming.xitek.bbs.model.bean.PostItem;
import me.isming.xitek.bbs.model.bean.ThreadItem;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sam on 17/3/2.
 */
public interface WujiServices {

    @GET("/MobileApi/forum2014.php")
    Observable<List<ThreadItem>> getThreadList(@Query("fid") String fid, @Query("page") int page,
                                               @Query("username") String userName,
                                               @Query("u") String u, @Query("ver") String ver);

    @GET("/MobileApi/thread.php?uid=0&u=0&reload=0&nopic=0&u=0")
    Observable<List<PostItem>> getPostList(@Query("tid") String tid, @Query("page") int page,
                                           @Query("order") int order, @Query("nouid") int nouid,
                                           @Query("username") String username);

    @Multipart
    @POST("/MobileApi/getuserinfo.php")
    Observable<MineInfo> login(@Part("username") String userName, @Part("password") String password);
}
