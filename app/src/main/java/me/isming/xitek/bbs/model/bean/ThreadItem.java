package me.isming.xitek.bbs.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sam on 17/3/2.
 */
public class ThreadItem implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.threadid);
        dest.writeString(this.title);
        dest.writeLong(this.lastpost);
        dest.writeInt(this.forumid);
        dest.writeString(this.replycount);
        dest.writeString(this.postusername);
        dest.writeLong(this.userid);
        dest.writeString(this.lastposter);
        dest.writeLong(this.dateline);
        dest.writeString(this.views);
        dest.writeString(this.uploadfp);
        dest.writeString(this.uploadthumb);
        dest.writeInt(this.elite);
        dest.writeString(this.sumscore);
        dest.writeInt(this.open);
        dest.writeInt(this.waterthread);
    }

    public ThreadItem() {
    }

    protected ThreadItem(Parcel in) {
        this.threadid = in.readString();
        this.title = in.readString();
        this.lastpost = in.readLong();
        this.forumid = in.readInt();
        this.replycount = in.readString();
        this.postusername = in.readString();
        this.userid = in.readLong();
        this.lastposter = in.readString();
        this.dateline = in.readLong();
        this.views = in.readString();
        this.uploadfp = in.readString();
        this.uploadthumb = in.readString();
        this.elite = in.readInt();
        this.sumscore = in.readString();
        this.open = in.readInt();
        this.waterthread = in.readInt();
    }

    public static final Parcelable.Creator<ThreadItem> CREATOR = new Parcelable.Creator<ThreadItem>() {
        @Override
        public ThreadItem createFromParcel(Parcel source) {
            return new ThreadItem(source);
        }

        @Override
        public ThreadItem[] newArray(int size) {
            return new ThreadItem[size];
        }
    };
}
