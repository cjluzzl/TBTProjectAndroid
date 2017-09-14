package cn.cjluzzl.tbt.tbtproject;

import android.content.Context;

import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 22:25.
 */

public class PushTestReceiver extends PushMessageReceiver {
    /**
     * 获取绑定请求的结果
     * @param context
     * @param i errorCode 服务端返回的错误码 0表示没有错误
     * @param s appid 应用id，errorCode非0时为null
     * @param s1  userId 应用user id，errorCode非0时为null
     * @param s2  channelId 应用channel id，errorCode非0时为null
     * @param s3  requestId 向服务端发起的请求id，在追查问题时有用
     */
    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
        System.out.println("on Bind PUSH_CJLUZZL_i" + i);
        System.out.println("on Bind PUSH_CJLUZZL_s" + s);
        System.out.println("on Bind PUSH_CJLUZZL_s1" + s1);
        System.out.println("on Bind PUSH_CJLUZZL_s2" + s2);
        System.out.println("on Bind PUSH_CJLUZZL_s3" + s3);
    }

    /**
     * stopWork的回调函数
     * @param context
     * @param i     errorCode 错误码，0表示从云推送解绑定成功，非0表示失败
     * @param s     requestId 分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    /**
     * setTags的回调函数
     * @param context
     * @param i     errorCode 错误码，0表示某些tag已经设置成功，非0表示所有tag的设置均失败
     * @param list  successTags 设置成功的tag
     * @param list1 failTags 设置失败的tag
     * @param s     requestId 分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }


    /**
     * delTags的回调函数
     * @param context
     * @param i     errorCode 错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败
     * @param list  successTags 成功删除的tag
     * @param list1 failTags 删除失败的tag
     * @param s     requestId 分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    /**
     * listTags的回调函数
     * @param context
     * @param i     errorCode 错误码。0表示列举tag成功；非0表示失败
     * @param list  tags 当前应用设置的所有tag
     * @param s     requestId 分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }
    /**
     * 接收透传消息的函数
     * @param context
     * @param s message 推送的消息
     * @param s1 customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String s, String s1) {
        System.out.println("on Message PUSH_CJLUZZL_s" + s);
        System.out.println("on Message PUSH_CJLUZZL_s1" + s1);
    }

    /**
     * 接收通知点击的函数
     * @param context
     * @param s title 推送的通知的标题
     * @param s1 description 推送的通知的描述
     * @param s2 customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

    }

    /**
     * 接收通知到达的函数
     * @param context
     * @param s     title 推送的通知的标题
     * @param s1    description 推送的通知的描述
     * @param s2    customContentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
