package cn.cjluzzl.tbt.tbtproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class MessageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_message, container, false);


        return view;
    }
}
