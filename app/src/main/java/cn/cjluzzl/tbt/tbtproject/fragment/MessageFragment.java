package cn.cjluzzl.tbt.tbtproject.fragment;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.ServiceConfigurationError;

import cn.cjluzzl.tbt.tbtproject.MainActivity;
import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.domain.MessageData;
import cn.cjluzzl.tbt.tbtproject.service.BindService;
import cn.cjluzzl.tbt.tbtproject.service.FirstService;
import cn.cjluzzl.tbt.tbtproject.view.RefreshListView;
import cn.cjluzzl.tbt.tbtproject.websocket.MyWebSocket;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class MessageFragment extends Fragment {
    private RefreshListView lvMessage;
    private String moreUrl;
    private MessageData mData;
    private ArrayList<MessageData.MessageDetailData> mMessageDetialData = new ArrayList<MessageData.MessageDetailData>();
    private String url = "http://192.168.155.1:8000/users/get_message/test/1/";
    private MessageAdapter messageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        lvMessage = (RefreshListView) view.findViewById(R.id.lv_message);


        lvMessage.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "刷新数据接口已经调用，正在刷新数据",Toast.LENGTH_SHORT).show();
                getDataFromServer();
                lvMessage.onRefreshComplete();
            }

            @Override
            public void onLoadMore() {
                if(moreUrl!=null){
                    getMoreDataFromServer();
                    System.out.println("更多内容加载完毕");
                }else {
                    Toast.makeText(getActivity(),"没有更多内容了",Toast.LENGTH_SHORT).show();
                    lvMessage.onRefreshComplete();
                }
            }
        });
        messageAdapter = new MessageAdapter();
        getDataFromServer();
        return view;
    }

    public void getDataFromServer(){
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = (String) responseInfo.result;

                System.out.println("页签详情页请求结果为"+ url + result);
                parseData(result, false);
                //收起下拉刷新效果

                lvMessage.onRefreshComplete();
                Toast.makeText(getActivity(), "数据刷新完成",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("请求失败");
                Toast.makeText(getActivity(), "刷新失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                //收起下拉刷新效果
                lvMessage.onRefreshComplete();
            }
        });
    }

    public void parseData(String result, boolean isMore){
        Gson gson = new Gson();
        mData = gson.fromJson(result, MessageData.class);
        String more = mData.more;
        if(!TextUtils.isEmpty(more)){
            moreUrl = more;
        }else {
            moreUrl = null;
        }

        if(!isMore){
            mMessageDetialData = mData.data;
            lvMessage.setAdapter(messageAdapter);
            messageAdapter.notifyDataSetChanged();
        }else{
            ArrayList<MessageData.MessageDetailData> tempData = new ArrayList<MessageData.MessageDetailData>();
            tempData = mData.data;
            mMessageDetialData.addAll(tempData);
            messageAdapter.notifyDataSetChanged();
        }
    }

    public void getMoreDataFromServer(){
        HttpUtils utils = new HttpUtils();

        utils.send(HttpRequest.HttpMethod.GET, moreUrl, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = (String) responseInfo.result;
                System.out.println("页签详情页请求结果为" + moreUrl +result);
                parseData(result,true);
                lvMessage.onRefreshComplete();//收起下拉刷新的view
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_LONG);
                System.out.println("MessageFragment" + "请求失败");
                error.printStackTrace();
                System.out.println("更多数据加载失败");
                lvMessage.onRefreshComplete();//收起下拉刷新的view
            }
        });
    }


    class MessageAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mMessageDetialData.size();
        }

        @Override
        public MessageData.MessageDetailData getItem(int position) {
            return mMessageDetialData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final MessageHolder holder;
            if(convertView == null){
                holder = new MessageHolder();
                convertView = View.inflate(getActivity(), R.layout.message_simple_item,null);
                holder.sendTime = (TextView) convertView.findViewById(R.id.tv_message_time);
                holder.tvMsg = (TextView) convertView.findViewById(R.id.tv_message_content);
                holder.tvMsgNull = (TextView) convertView.findViewById(R.id.tv_message_null);
                convertView.setTag(holder);
            }else{
                holder = (MessageHolder) convertView.getTag();
            }
            MessageData.MessageDetailData item = mMessageDetialData.get(position);
            holder.sendTime.setText(item.send_time);
            holder.tvMsg.setText(item.msg);
            holder.tvMsgNull.setText("");

            return convertView;
        }
    }

    static class MessageHolder{
        public TextView tvMsg;
        public TextView sendTime;
        public TextView tvMsgNull;
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
