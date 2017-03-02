package me.isming.xitek.bbs.util;

import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * Created by sam on 17/3/2.
 */
public class Forums {

    public static SparseArray<String> sForums = new SparseArray<>(100);

    static {
        sForums.put(2, "摄影大家坛");
        sForums.put(48, "行行色色");
        sForums.put(46, "人文与纪实");
        sForums.put(40, "黑白摄影");
        sForums.put(42, "自然摄影");
        sForums.put(91, "商业摄影");
        sForums.put(56, "初学者乐园");
        sForums.put(90, "人造光及影棚技巧");
        sForums.put(75, "摄影活动信息");
        sForums.put(68, "人像摄影");
        sForums.put(108, "她摄影");

        sForums.put(84, "Canon SLR/DSLM论坛");
        sForums.put(85, "α|Sony/KM/M SLR/DSLM论坛");
        sForums.put(86, "Nikon SLR/DSLM论坛");
        sForums.put(88, "4/3-M 4/3论坛");
        sForums.put(87, "Pentax SLR/DSLM论坛");
        sForums.put(92, "Sigma论坛");
        sForums.put(102, "Tamron论坛");
        sForums.put(107, "Fujifile SLR/DSLM论坛");
        sForums.put(71, "视听论坛");
        sForums.put(80, "电脑手机论坛");
        sForums.put(113, "手机摄影");
        sForums.put(44, "二手论坛");
        sForums.put(59, "其他DSLR/DSLM论坛");
        sForums.put(7, "数码相机");
        sForums.put(11, "胶片单反相机");
        sForums.put(97, "大画幅相机");
        sForums.put(13, "中画幅相机");
        sForums.put(66, "数码暗房");
        sForums.put(58, "旁轴相机");
        sForums.put(64, "摄影镜头");
        sForums.put(65, "摄影附件");
        sForums.put(82, "空穴来风");

        sForums.put(3, "玩主论坛");
        sForums.put(69, "汽车论坛");
        sForums.put(37, "望远镜&天文");
        sForums.put(38, "越野车爱好者之家");
        sForums.put(83, "户外运动及装备");
        sForums.put(47, "体育论坛");

        sForums.put(39, "各地交流");
        sForums.put(100, "特别论坛：爱心无忌");
        sForums.put(52, "建议、咨询及公告");
        sForums.put(61, "西安论坛");
        sForums.put(50, "无忌交流论坛");
        sForums.put(103, "无忌财富论坛");
        sForums.put(109, "美食论坛");
    }

    public static String getForumName(int id) {
        return sForums.get(id);
    }



}
