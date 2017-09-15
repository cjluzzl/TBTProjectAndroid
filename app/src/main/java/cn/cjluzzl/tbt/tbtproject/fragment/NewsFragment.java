package cn.cjluzzl.tbt.tbtproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.domain.HomeAndAbroadNewsData;
import cn.cjluzzl.tbt.tbtproject.pager.NewsTabDetailPager;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class NewsFragment extends Fragment {
    private TabPageIndicator mTitleIndicator;
    private ViewPager pager;
    private TitlePagerAdapter mTitlePagerAdapter;
    private ArrayList<NewsTabDetailPager> mPagerList;// = new ArrayList<TabDetailPager>();
    private ArrayList<String> titleArrayList;// = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        mPagerList = new ArrayList<NewsTabDetailPager>();
        titleArrayList = new ArrayList<String>();
        titleArrayList.add("国内新闻");
        titleArrayList.add("国外新闻");
        mPagerList.add(new NewsTabDetailPager(getActivity(),"国内新闻","http://192.168.155.1:8000/article/home_news_list/1/"));
        mPagerList.add(new NewsTabDetailPager(getActivity(),"国外新闻","http://192.168.155.1:8000/article/abroad_news_list/1/"));
        mTitleIndicator = (TabPageIndicator) view.findViewById(R.id.title_indicator);
        mTitlePagerAdapter = new TitlePagerAdapter();
        pager.setAdapter(mTitlePagerAdapter);
        mTitleIndicator.setViewPager(pager);
        return view;
    }

    class TitlePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsTabDetailPager pager = mPagerList.get(position);
            pager.initView();
            pager.initData();
            container.addView(pager.mRootView);
            return pager.mRootView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
        //返回页面标题，用于页签的显示
        @Override
        public CharSequence getPageTitle(int position) {
            System.out.println("哈哈哈" + titleArrayList.get(position));
            return titleArrayList.get(position);
        }
    }



}
