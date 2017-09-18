package cn.cjluzzl.tbt.tbtproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.cjluzzl.tbt.tbtproject.R;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/18 16:17.
 */

public class ArticleDetailActivity extends Activity {
    WebView mWebView;
    ImageButton btnBack;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.article_page_detail);
        mWebView = (WebView) findViewById(R.id.wb_article_detail_page);
        btnBack = (ImageButton) findViewById(R.id.btn_article_detail_back);
        tvTitle = (TextView) findViewById(R.id.tv_article_detail_title);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        mWebView.loadUrl(url);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
