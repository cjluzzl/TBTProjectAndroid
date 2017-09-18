package cn.cjluzzl.tbt.tbtproject.fragment;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ServiceConfigurationError;

import cn.cjluzzl.tbt.tbtproject.MainActivity;
import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.service.BindService;
import cn.cjluzzl.tbt.tbtproject.service.FirstService;
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
    Intent intent;
    Button bind, unbind, getServiceStatus;

    BindService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("--Service Connected--");
            binder = (BindService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("--Service Disconnected--");
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(getActivity(), BindService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        bind = (Button) view.findViewById(R.id.btn_bind);
        unbind = (Button) view.findViewById(R.id.btn_unbind);
        getServiceStatus = (Button) view.findViewById(R.id.btn_getSerciceStatus);

        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().bindService(intent, conn, Service.BIND_AUTO_CREATE);
                System.out.println("bind点击了");
            }
        });

        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().unbindService(conn);
                System.out.println("unbind点击了");
            }
        });

        getServiceStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Service的count值为:"+ binder.getCount(),Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("MessageFragment onCreateView()");
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
