package me.isming.xitek.bbs.model.bean;

/**
 * Created by sam on 17/3/2.
 */
public class ThreadItem {
    public String threadid;
    public String title;
    public long lastpost;
    public int forumid;
    public String replycount;
    public String postusername;
    public long userid;
    public String lastposter;
    public long dateline;
    public String views;
    public String uploadfp;
    public String uploadthumb;
    public int elite;
    public String sumscore;
    public int open;
    public int waterthread;

    @Override
    public String toString() {
        return "ThreadItem{" +
                "threadid='" + threadid + '\'' +
                ", title='" + title + '\'' +
                ", lastpost=" + lastpost +
                ", forumid=" + forumid +
                ", replycount='" + replycount + '\'' +
                ", postusername='" + postusername + '\'' +
                ", userid=" + userid +
                ", lastposter='" + lastposter + '\'' +
                ", dateline=" + dateline +
                ", views='" + views + '\'' +
                ", uploadfp='" + uploadfp + '\'' +
                ", uploadthumb='" + uploadthumb + '\'' +
                ", elite=" + elite +
                ", sumscore='" + sumscore + '\'' +
                ", open=" + open +
                ", waterthread=" + waterthread +
                '}';
    }
}
