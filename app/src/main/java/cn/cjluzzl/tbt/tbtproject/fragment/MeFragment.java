package cn.cjluzzl.tbt.tbtproject.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.activity.LoginActivity;
import cn.cjluzzl.tbt.tbtproject.activity.PersonalInfomationActivity;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class MeFragment extends Fragment {
    ListView lvFun;
    TextView tvUserName;
    Button btnPersonalInfo;
    Button btnLogin;
    Button btnRegister;
    Button btnLogout;
    SharedPreferences sp;
    String userName;
    SettingAdapter mSettingAdapter = new SettingAdapter();
    Activity mActivity;
    public String[] funName = {
            "浏览记录","关注的标签","会员购买","软件更新","缓存清理"
    };
    public int[] funIcon = {
        R.drawable.icon_watch_list, R.drawable.icon_bookmark,
            R.drawable.icon_vip, R.drawable.icon_update,
            R.drawable.icon_clear
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        if(requestCode == 200 && resultCode == 200){
            System.out.println("登录成功并返回页面");
            btnLogin.setVisibility(View.INVISIBLE);
        }
        if(requestCode == 200 && resultCode == 100){
            System.out.println("登录失败并返回页面");
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        tvUserName = (TextView) view.findViewById(R.id.tv_me_username);
        btnPersonalInfo = (Button) view.findViewById(R.id.btn_me_info);
        btnLogin = (Button) view.findViewById(R.id.btn_me_login);
        btnLogout = (Button) view.findViewById(R.id.btn_me_logout);
        btnRegister = (Button) view.findViewById(R.id.btn_me_register);
        sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userName = sp.getString("username","");
        if(!TextUtils.isEmpty(userName)){
            btnLogin.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
            tvUserName.setText(userName);
        }else {
            btnPersonalInfo.setVisibility(View.INVISIBLE);
        }

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, PersonalInfomationActivity.class);
                mActivity.startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, LoginActivity.class);
                mActivity.startActivityForResult(intent,200);

            }
        });

        lvFun = (ListView) view.findViewById(R.id.lv_me_fun);

        lvFun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("你点击了");
            }
        });
        lvFun.setAdapter(mSettingAdapter);

        return view;
    }

    class SettingAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return funName.length;
        }

        @Override
        public String getItem(int position) {
            return funName[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final SettingHolder holder;
            if(convertView == null){
                holder = new SettingHolder();
                convertView = View.inflate(getActivity(), R.layout.setting_simple_item, null);
                holder.titleName = (TextView) convertView.findViewById(R.id.tv_setting_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.iv_setting_icon);
                convertView.setTag(holder);
            }else {
                holder = (SettingHolder) convertView.getTag();
            }
            holder.icon.setImageResource(funIcon[position]);
            holder.titleName.setText(funName[position]);
            return convertView;
        }
    }
    static class SettingHolder{
        public ImageView icon;
        public TextView titleName;

    }
}
