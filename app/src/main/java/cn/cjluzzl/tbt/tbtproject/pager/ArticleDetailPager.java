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

import org.w3c.dom.Text;

import java.util.ArrayList;

import cn.cjluzzl.tbt.tbtproject.MainActivity;
import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.domain.ArticleJsonData;
import cn.cjluzzl.tbt.tbtproject.domain.HomeAndAbroadNewsData;
import cn.cjluzzl.tbt.tbtproject.view.RefreshListView;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 14:56.
 */

public class ArticleDetailPager {
    public View mRootView;
    public Activity mActivity;
    public String tag;
    public String url;
    public String moreUrl;
    private RefreshListView lvNews;
    private ArticleJsonData newsData;
    private ArrayList<ArticleJsonData.ArticleDetaileData> mNewsDetailDataList;

    private ArticleAdapter mNewsAdapter;
    public ArticleDetailPager(Activity mActivity, String tag,String url){
        this.mActivity = mActivity;
        this.tag = tag;
        this.url = url;
    }
    public void initView(){
        mRootView = View.inflate(mActivity, R.layout.pager_common_title, null);
        lvNews = (RefreshListView) mRootView.findViewById(R.id.lv_news);

//        newsData.add("hhhh");
//        newsData.add("asdfsad");
//        newsData.add("hhhhh");
        mNewsAdapter = new ArticleAdapter();
        lvNews.setAdapter(mNewsAdapter);
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
        newsData = gson.fromJson(result, ArticleJsonData.class);
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
            ArrayList<ArticleJsonData.ArticleDetaileData> tempData = new ArrayList<ArticleJsonData.ArticleDetaileData>();
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

    class ArticleAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mNewsDetailDataList.size();
        }

        @Override
        public ArticleJsonData.ArticleDetaileData getItem(int position) {
            return mNewsDetailDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mActivity,R.layout.article_simple_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_title);
            textView.setText(mNewsDetailDataList.get(position).title);
            return convertView;
        }
    }

}
