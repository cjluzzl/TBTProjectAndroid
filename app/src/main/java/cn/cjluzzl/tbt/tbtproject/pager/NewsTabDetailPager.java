package cn.cjluzzl.tbt.tbtproject.pager;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.domain.HomeAndAbroadNewsData;
import cn.cjluzzl.tbt.tbtproject.view.RefreshListView;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 14:56.
 */

public class NewsTabDetailPager {
    public View mRootView;
    public Activity mActivity;
    public String tag;
    public String url;
    public TextView textView;
    private RefreshListView lvNews;
    private HomeAndAbroadNewsData newsData;//新闻数据
    private ArrayList<HomeAndAbroadNewsData.ArticleData> mNewsDetailDataList = new ArrayList<HomeAndAbroadNewsData.ArticleData>();;
    private NewsAdapter mNewsAdapter;
    private String moreUrl;//更多链接
    public NewsTabDetailPager(Activity mActivity, String tag,String url){
        this.mActivity = mActivity;
        this.tag = tag;
        this.url = url;
    }
    public void initView(){
        mRootView = View.inflate(mActivity, R.layout.pager_title, null);
        lvNews = (RefreshListView) mRootView.findViewById(R.id.lv_news);

        mNewsAdapter = new NewsAdapter();
        getDataFromServer();
        lvNews.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mActivity, "刷新数据接口已经调用，正在刷新数据",Toast.LENGTH_SHORT).show();
                getDataFromServer();
                lvNews.onRefreshComplete();
            }

            @Override
            public void onLoadMore() {
                if(moreUrl!=null){
                    getMoreDataFromServer();
                    System.out.println("更多内容加载完毕");
                }else {
                    Toast.makeText(mActivity,"没有更多内容了",Toast.LENGTH_SHORT).show();
                    lvNews.onRefreshComplete();
                }
            }
        });
    }
    public void initData(){
        System.out.println("初始化了"+tag);
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

                lvNews.onRefreshComplete();
                Toast.makeText(mActivity, "数据刷新完成",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                System.out.println("请求失败");
                Toast.makeText(mActivity, "刷新失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                //收起下拉刷新效果
                lvNews.onRefreshComplete();
            }
        });
    }


    public void parseData(String result, boolean isMore){
        Gson gson = new Gson();
        newsData = gson.fromJson(result, HomeAndAbroadNewsData.class);
        String more = newsData.more;
        if(!TextUtils.isEmpty(more)){
            moreUrl = more;
        }else {
            moreUrl = null;
        }

        if(!isMore){
            mNewsDetailDataList = newsData.data;
            lvNews.setAdapter(mNewsAdapter);
            mNewsAdapter.notifyDataSetChanged();
        }else{
            ArrayList<HomeAndAbroadNewsData.ArticleData> tempData = new ArrayList<HomeAndAbroadNewsData.ArticleData>();
            tempData = newsData.data;
            mNewsDetailDataList.addAll(tempData);
            mNewsAdapter.notifyDataSetChanged();
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
                lvNews.onRefreshComplete();//收起下拉刷新的view
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Toast.makeText(mActivity,"请求失败",Toast.LENGTH_LONG);
                error.printStackTrace();
                System.out.println("更多数据加载失败");
                lvNews.onRefreshComplete();//收起下拉刷新的view
            }
        });
    }
    class NewsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mNewsDetailDataList.size();
        }

        @Override
        public HomeAndAbroadNewsData.ArticleData getItem(int position) {
            return mNewsDetailDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(mActivity,R.layout.news_simple_item, null);
                //标题
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_news_title);
                //摘要
                holder.tvShowContent = (TextView) convertView.findViewById(R.id.tv_news_short_content);
                //行业
                holder.tvTrade = (TextView) convertView.findViewById(R.id.tv_news_trade);
                //发布时间
                holder.pubTime = (TextView) convertView.findViewById(R.id.tv_news_time);
                //浏览量
                holder.tvShowCount = (TextView) convertView.findViewById(R.id.tv_news_show_count);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            HomeAndAbroadNewsData.ArticleData item = mNewsDetailDataList.get(position);
            holder.tvTitle.setText(item.title);
            holder.tvShowContent.setText(item.shortContent);
            holder.tvTrade.setText(item.trade);
            holder.pubTime.setText(item.pubTime);
            holder.tvShowCount.setText(item.showCount);
            return convertView;
        }
    }

    static class ViewHolder{
        public TextView tvTitle;
        public TextView tvShowContent;
        public TextView pubTime;
        public TextView tvShowCount;
        public TextView tvTrade;
    }


}
