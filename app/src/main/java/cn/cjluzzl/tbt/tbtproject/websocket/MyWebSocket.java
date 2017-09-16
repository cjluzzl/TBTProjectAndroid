package cn.cjluzzl.tbt.tbtproject.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/16 0:26.
 */

public class MyWebSocket extends WebSocketClient {


    public MyWebSocket(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("webSocketClient的onOpen()方法");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("接收到的消息是" + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("webSocket方法关闭了i:" + i);
        System.out.println("webSocket方法关闭了s:" + s);
        System.out.println("webSocket方法关闭了b:" + b);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("webSocket的onError()");
    }
}
