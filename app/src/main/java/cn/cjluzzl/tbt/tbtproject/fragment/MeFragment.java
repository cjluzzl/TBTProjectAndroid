package cn.cjluzzl.tbt.tbtproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class MeFragment extends Fragment {
    ListView lvFun;
    Button btnPersonalInfo;
    SettingAdapter mSettingAdapter = new SettingAdapter();
    public String[] funName = {
            "浏览记录","关注的标签","会员购买","软件更新","缓存清理"
    };
    public int[] funIcon = {
        R.drawable.icon_watch_list, R.drawable.icon_bookmark,
            R.drawable.icon_vip, R.drawable.icon_update,
            R.drawable.icon_clear
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        btnPersonalInfo = (Button) view.findViewById(R.id.btn_me_info);
        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "你点击了个人资料按键", Toast.LENGTH_SHORT).show();
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
