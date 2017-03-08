package me.isming.xitek.bbs.model.bean;

/**
 * Created by sam on 17/3/2.
 */
public class PostItem {
    public String postid;
    public String threadid;
    public long userid;
    public String username;
    public long dateline;
    public String uploadfp;
    public String uploads;
    public int score;
    public int scored;
    public int step;
    public String exif;
    public String content;
    public String quote;

    public String getPositionText() {
        switch (step) {
            case 0:
            case 1:
                return "楼主";
            default:
                return step + "#";
        }
    }

}
