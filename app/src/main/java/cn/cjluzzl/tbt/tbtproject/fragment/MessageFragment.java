package cn.cjluzzl.tbt.tbtproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class MessageFragment extends Fragment {
    public Button btnSend;
    public WebSocketClient webSocketClient;
    public EditText tv;
    public String text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        btnSend = (Button) view.findViewById(R.id.btn_send);
        tv = (EditText) view.findViewById(R.id.tv_test_text);
        try {
            System.out.println("websocket的初始化方法启动了");
            initData();
            System.out.println("websocket的初始化方法结束了");
        } catch (URISyntaxException e) {
            System.out.println("websocket的初始化方法出现问题");
            e.printStackTrace();
        }
        webSocketClient.connect();


//        try {
//            webSocketClient.connectBlocking();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text = tv.getText().toString();
                System.out.println("发送消息按钮被点击了");
                webSocketClient.send(text);
                System.out.println("webSocket的开启状态是"+webSocketClient.isOpen());
                if(webSocketClient.isOpen()){
                    System.out.println("webSocket的开启状态是"+webSocketClient.isOpen());
                }else{
                    System.out.println("webSocket的开启状态是"+webSocketClient.isOpen());
                    try {
                        webSocketClient.connectBlocking();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("webSocket的开启状态是"+webSocketClient.isOpen());
                }

            }
        });

        return view;
    }


    public void initData() throws URISyntaxException {
        webSocketClient = new WebSocketClient(new URI("ws://192.168.155.1:8000/users/test/")) {
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
                System.out.println("webSocket方法关闭了" + s);
            }

            @Override
            public void onError(Exception e) {
                System.out.println("webSocket的onError()");
            }
        };
    }
}
