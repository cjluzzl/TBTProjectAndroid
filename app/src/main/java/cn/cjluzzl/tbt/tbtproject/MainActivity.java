package cn.cjluzzl.tbt.tbtproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import cn.cjluzzl.tbt.tbtproject.fragment.HomeFragment;
import cn.cjluzzl.tbt.tbtproject.fragment.MeFragment;
import cn.cjluzzl.tbt.tbtproject.fragment.MessageFragment;
import cn.cjluzzl.tbt.tbtproject.fragment.NewsFragment;
import cn.cjluzzl.tbt.tbtproject.view.TabIndicatorView;

public class MainActivity extends FragmentActivity {
    private final static String TAG_HOME = "home";
    private final static String TAG_NEWS = "news";
    private final static String TAG_MESSAGE = "message";
    private final static String TAG_ME = "me";

    private FragmentTabHost tabhost;
    private TabIndicatorView homeIndicator;
    private TabIndicatorView newsIndicator;
    private TabIndicatorView messageIndicator;
    private TabIndicatorView meIndicator;
    public URI uri;
    public WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化百度云推送
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"gfV4sLlGDlcM86A96rGBOrnT");
        PushSettings.enableDebugMode(getApplicationContext(),true);

        //初始化WebSocket
        //initSocket();




        //1.初始化TabHost
        tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.activity_home_container);

        //2.新建TabSpec --- chat
        TabHost.TabSpec spec = tabhost.newTabSpec(TAG_HOME);
        //spec.setIndicator("消息");
        homeIndicator = new TabIndicatorView(this);
        homeIndicator.setTabTitle("首页");
        homeIndicator.setTabIcon(R.drawable.tab_icon_home_normal,R.drawable.tab_icon_home_focus);

        homeIndicator.setTabSelected(true);
        spec.setIndicator(homeIndicator);
        //3.添加TabSpec
        tabhost.addTab(spec, HomeFragment.class, null);

        //2.新建TabSpec --connect
        spec = tabhost.newTabSpec(TAG_NEWS);
        newsIndicator = new TabIndicatorView(this);
        newsIndicator.setTabTitle("新闻");
        newsIndicator.setTabIcon(R.drawable.tab_icon_news_normal,R.drawable.tab_icon_news_focus);

        //connectIndicator.setTabSelected(true);
        spec.setIndicator(newsIndicator);
        //3.添加TabSpec
        tabhost.addTab(spec, NewsFragment.class, null);

        //2.新建TabSpec --- discover
        spec = tabhost.newTabSpec(TAG_MESSAGE);
        //spec.setIndicator("消息");
        messageIndicator = new TabIndicatorView(this);
        messageIndicator.setTabTitle("消息");
        messageIndicator.setTabIcon(R.drawable.tab_icon_message_normal,R.drawable.tab_icon_message_focus);

        messageIndicator.setTabUnReadCount(2);
        spec.setIndicator(messageIndicator);
        //3.添加TabSpec
        tabhost.addTab(spec, MessageFragment.class, null);

        //2.新建TabSpec --- me
        spec = tabhost.newTabSpec(TAG_ME);
        //spec.setIndicator("消息");
        meIndicator = new TabIndicatorView(this);
        meIndicator.setTabTitle("个人中心");
        meIndicator.setTabIcon(R.drawable.tab_icon_me_normal,R.drawable.tab_icon_me_focus);

        //meIndicator.setTabSelected(true);
        spec.setIndicator(meIndicator);
        //3.添加TabSpec
        tabhost.addTab(spec, MeFragment.class, null);

        //去掉tab间的分割线
        tabhost.getTabWidget().setDividerDrawable(android.R.color.white);

        //监听选中事件
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                homeIndicator.setTabSelected(false);
                messageIndicator.setTabSelected(false);
                newsIndicator.setTabSelected(false);
                meIndicator.setTabSelected(false);
                switch (tabId){
                    case TAG_HOME:
                        homeIndicator.setTabSelected(true);
                        break;
                    case TAG_NEWS:
                        newsIndicator.setTabSelected(true);
                        break;
                    case TAG_MESSAGE:
                        messageIndicator.setTabSelected(true);
                        break;
                    case TAG_ME:
                        meIndicator.setTabSelected(true);
                        break;
                }

            }
        });
    }

    public void initSocket(){
        try {
            uri = new URI("ws://192.168.155.1:8000/users/test/cjluzzl/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketClient = new WebSocketClient(uri, new Draft_6455()) {
            public String res;
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("webSocketClient的onOpen()方法");
            }

            @Override
            public void onMessage(String s) {
                res = s;
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

            public String getMessage(){
                return res;
            }
        };


        try {
            boolean isConnectBlocking = webSocketClient.connectBlocking();
            System.out.println("连接锁定状态为:" + isConnectBlocking);
            webSocketClient.getDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
