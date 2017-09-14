package cn.cjluzzl.tbt.tbtproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 10:28.
 */

public class TabIndicatorView extends RelativeLayout {
    private TextView titleView;
    private ImageView ivTabIcon;
    private TextView tvTabUnRead;
    private int normalIconId;
    private int focusIconId;

    public TabIndicatorView(Context context) {
        //super(context);
        this(context,null);
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = View.inflate(context, R.layout.tab_indicator, this);
        titleView = (TextView) view.findViewById(R.id.tab_indicator_hint);
        ivTabIcon = (ImageView) view.findViewById(R.id.tab_indicator_icon);
        tvTabUnRead = (TextView) view.findViewById(R.id.tab_indicator_unread);
        setTabUnReadCount(0);

    }

    //设置Tab的title
    public void setTabTitle(String title){
       titleView.setText(title);
    }

    public void setTabTitle(int titleId){
        titleView.setText(titleId);
    }
    //初始化图标
    public void setTabIcon(int normalIconId, int focusIconId){
        this.normalIconId = normalIconId;
        this.focusIconId = focusIconId;

        ivTabIcon.setImageResource(normalIconId);
    }
    //设置未读消息数
    public void setTabUnReadCount(int unreadcount){
        if(unreadcount <= 0){
            tvTabUnRead.setVisibility(View.GONE);
        }else {
            if(unreadcount <= 99)
                tvTabUnRead.setText(unreadcount + "");
            else
                tvTabUnRead.setText("99+");
            tvTabUnRead.setVisibility(View.VISIBLE);
        }

    }
    //设置选中时切换图标
    public void setTabSelected(boolean selected){
        if(selected){
            ivTabIcon.setImageResource(focusIconId);
        }else{
            ivTabIcon.setImageResource(normalIconId);
        }

    }

}
