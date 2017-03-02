package me.isming.xitek.bbs.model;

import java.util.List;

import me.isming.xitek.bbs.model.bean.MineInfo;
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

    @Multipart
    @POST("/MobileApi/getuserinfo.php")
    Observable<MineInfo> login(@Part("username") String userName, @Part("password") String password);
}
