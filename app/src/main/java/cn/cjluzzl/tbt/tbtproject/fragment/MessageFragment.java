package cn.cjluzzl.tbt.tbtproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URI;
import cn.cjluzzl.tbt.tbtproject.MainActivity;
import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.websocket.MyWebSocket;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class MessageFragment extends Fragment {
    public Button btnSend;
    public MyWebSocket webSocketClient;
    public EditText tv;
    public String text;
    public URI uri;
    public TextView tvShow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("MessageFragment onStart()方法调用了");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("MessageFragment onPause()方法调用了");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("MessageFragment onDestroy()方法调用了");
    }
}
