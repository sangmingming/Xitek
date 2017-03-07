package me.isming.xitek.bbs.model.bean;

import java.util.List;

/**
 * Created by sam on 17/3/7.
 */
public class BoardEntity {

    public String label;
    public List<BoardItem> list;

    public static class BoardItem {
        public String fid;
        public String label;
    }
}
