package cn.cjluzzl.tbt.tbtproject.domain;

import java.util.ArrayList;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/14 16:35.
 */

public class ArticleJsonData {
    public String meta;
    public String retcode;
    public String more;
    public ArrayList<ArticleDetaileData> data;

    public class ArticleDetaileData{
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
