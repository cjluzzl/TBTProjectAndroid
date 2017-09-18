package cn.cjluzzl.tbt.tbtproject.domain;

import java.util.ArrayList;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/18 14:55.
 */

public class MessageData {
    public String meta;
    public String retcode;
    public String more;
    public ArrayList<MessageDetailData> data;

    public class MessageDetailData{
        public String from_user;
        public String send_time;
        public String msg;
    }

}
