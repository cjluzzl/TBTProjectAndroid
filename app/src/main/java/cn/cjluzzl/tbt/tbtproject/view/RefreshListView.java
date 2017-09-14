package cn.cjluzzl.tbt.tbtproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/8/5 15:52.
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    private static final int STATE_PULL_REFRESH = 0;//下拉刷新
    private static final int STATE_RELEASE_REFRESH = 1;//松开
    private static final int STATE_REFRESHING = 2;//刷新中

    private View myHeaderView;
    private int myHeaderViewHeight;

    private View myFooterView;
    private int myFooterViewHeight;

    private int startY = -1;

    private int currentState = STATE_PULL_REFRESH;

    private ImageView imageView;
    private ProgressBar pr;
    private TextView tvTitle;
    private TextView tvDate;

    private RotateAnimation animUp;
    private RotateAnimation animDown;

    OnRefreshListener mRefreshListener;
    public RefreshListView(Context context) {
        super(context);
        initHeaderView(context);
        initMyFooterView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView(context);
        initMyFooterView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initMyFooterView(context);

    }

//    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initHeaderView(context);
//        initMyFooterView(context);
//    }



    /**
     * 初始化头布局
     */
    private void initHeaderView(Context context) {
        myHeaderView = View.inflate(context, R.layout.refresh_header, null);

        this.addHeaderView(myHeaderView);
        myHeaderView.measure(0,0);
        imageView = (ImageView) myHeaderView.findViewById(R.id.iv_refresh_arr);
        pr = (ProgressBar) findViewById(R.id.pb_refresh_progress);
        tvTitle = (TextView) findViewById(R.id.tv_refresh_title);
        tvDate = (TextView) findViewById(R.id.tv_refresh_time);
        tvDate.setText("最后更新时间" + getCurrentTime());
        initArrowAnim();
        myHeaderViewHeight = myHeaderView.getMeasuredHeight();
        myHeaderView.setPadding(0,-myHeaderViewHeight,0,0);
    }


    public void initMyFooterView(Context context){
        myFooterView  = View.inflate(context, R.layout.refresh_listview_footer,null);
        this.addFooterView(myFooterView);
        myFooterView.measure(0,0);
        myFooterViewHeight = myFooterView.getMeasuredHeight();
        myFooterView.setPadding(0,-myFooterViewHeight,0,0);
        this.setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                if(-1 == startY){//确保startY有效
                    startY = (int) ev.getRawY();
                }

                if(currentState == STATE_REFRESHING){//正在刷新时不做处理
                    break;
                }
                int endY = (int) ev.getRawY();

                int dy = endY -startY;//垂直距离偏移量
                //确保偏移量大于0 且 当前item是第一个item才允许下拉
                if(dy > 0 && getFirstVisiblePosition() ==0){

                    int padding = dy - myHeaderViewHeight;
                    myHeaderView.setPadding(0,padding,0,0);
                    if(padding >0 && currentState!= STATE_RELEASE_REFRESH){//状态改为松开刷新
                        currentState = STATE_RELEASE_REFRESH;
                        refreshHeaderView();
                    }else if(padding <= 0 && currentState!= STATE_PULL_REFRESH){
                        currentState = STATE_PULL_REFRESH;
                        refreshHeaderView();
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if(currentState == STATE_RELEASE_REFRESH){
                    currentState = STATE_REFRESHING;
                    myHeaderView.setPadding(0,0,0,0);
                    refreshHeaderView();
                }else if(currentState == STATE_PULL_REFRESH){
                    myHeaderView.setPadding(0,-myHeaderViewHeight,0,0);
                }
                break;
            default:
                break;


        }
        return super.onTouchEvent(ev);
    }

    private void refreshHeaderView() {

        switch (currentState){
            case STATE_PULL_REFRESH:
                tvTitle.setText("下拉刷新");
                imageView.setVisibility(VISIBLE);
                imageView.startAnimation(animDown);
                pr.setVisibility(INVISIBLE);
                break;
            case STATE_RELEASE_REFRESH:
                tvTitle.setText("松开刷新");
                imageView.setVisibility(VISIBLE);
                imageView.startAnimation(animUp);
                pr.setVisibility(INVISIBLE);
                break;
            case STATE_REFRESHING:
                tvTitle.setText("正在刷新······");
                imageView.clearAnimation();
                imageView.setVisibility(INVISIBLE);
                pr.setVisibility(VISIBLE);
                if(mRefreshListener != null){
                    mRefreshListener.onRefresh();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化箭头动画
     */
    private void initArrowAnim() {
        // 箭头向上动画
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        // 箭头向下动画
        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);

    }
    //收起下拉刷新的控件


    public interface OnRefreshListener {
        public void onRefresh();
        public void onLoadMore();

    }
    public void setOnRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    /*
	 * 收起下拉刷新的控件
	 */
    public void onRefreshComplete() {
        if(isLoadMore){
            myFooterView.setPadding(0,-myFooterViewHeight,0,0);
            isLoadMore = false;
        }else {
            currentState = STATE_PULL_REFRESH;
            tvTitle.setText("下拉刷新");
            imageView.setVisibility(View.VISIBLE);
            pr.setVisibility(View.INVISIBLE);

            myHeaderView.setPadding(0, -myHeaderViewHeight, 0, 0);// 隐藏
            //Toast.makeText(getContext(), "数据刷新完成",Toast.LENGTH_SHORT).show();
        }



    }

    //获取当前时间
    public String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private boolean isLoadMore;
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("正在下滑");
        if(scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING){
            if(getLastVisiblePosition() == getCount()-1 && !isLoadMore){
                System.out.println("到底了。。。。");
                myFooterView.setPadding(0,0,0,0);
                setSelection(getCount()-1);
                isLoadMore = true;
                if(mRefreshListener != null){
                    mRefreshListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


}
