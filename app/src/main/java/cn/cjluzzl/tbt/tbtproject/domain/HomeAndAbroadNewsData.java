package cn.cjluzzl.tbt.tbtproject.domain;

import java.util.ArrayList;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/4 14:18.
 */

public class HomeAndAbroadNewsData {
    public String meta;
    public String retcode;
    public String more;
    public ArrayList<ArticleData> data;

    public class ArticleData{
        public String url;
        public String image;
        public String type;
        public String title;
        public String shortContent;
        public String pubTime;
        public String showCount;
        public String trade;
    }
}
