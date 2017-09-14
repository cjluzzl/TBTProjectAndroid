package cn.cjluzzl.tbt.tbtproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import cn.cjluzzl.tbt.tbtproject.R;
import cn.cjluzzl.tbt.tbtproject.pager.ArticleDetailPager;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/2 13:42.
 */

public class HomeFragment extends Fragment {
    private TabPageIndicator mTitleIndicator;
    private ViewPager pager;
    private TitlePagerAdapter mTitlePagerAdapter;
    private ArrayList<ArticleDetailPager> mPagerList;// = new ArrayList<TabDetailPager>();
    private ArrayList<String> titleArrayList;// = new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        mPagerList = new ArrayList<ArticleDetailPager>();
        titleArrayList = new ArrayList<String>();
        titleArrayList.add("科研成果");
        titleArrayList.add("一带一路");
        titleArrayList.add("基础知识");
        titleArrayList.add("监督评价");
        titleArrayList.add("专家介绍");
        mPagerList.add(new ArticleDetailPager(getActivity(),"科研成果","http://192.168.23.1:8000/article/"));
        mPagerList.add(new ArticleDetailPager(getActivity(),"一带一路","http://192.168.23.1:8000/article/"));
        mPagerList.add(new ArticleDetailPager(getActivity(),"基础知识","http://192.168.23.1:8000/article/"));
        mPagerList.add(new ArticleDetailPager(getActivity(),"监督评价","http://192.168.23.1:8000/article/"));
        mPagerList.add(new ArticleDetailPager(getActivity(),"专家介绍","http://192.168.23.1:8000/article/"));
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
            ArticleDetailPager pager = mPagerList.get(position);
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
