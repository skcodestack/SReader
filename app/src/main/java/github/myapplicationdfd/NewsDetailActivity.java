package github.myapplicationdfd;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import github.myapplicationdfd.activity.base.BaseAppCompateActivity;
import github.myapplicationdfd.utils.CommonUtils;

/**
 * Author root
 * Date: 2016/8/31.
 */
public class NewsDetailActivity extends BaseAppCompateActivity {

    private WebView webView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private Toolbar toolbar;

    @Override
    protected void initEvents() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetailActivity.this.finish();
            }
        });


        String url = getIntent().getStringExtra("url");
        if (CommonUtils.isEmpty(url)) {
            toggleShowEmpty(true, "no web  pager!", null);
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);// 表示支持js
        settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
        settings.setUseWideViewPort(true);// 支持双击缩放
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                toggleShowLoading(true, "loading...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                toggleShowLoading(false, "");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(url);
    }

    @Override
    protected View getTagetView() {
        return webView;
    }

    @Override
    protected View initRootView() {
        View inflate = mActivity.getLayoutInflater().inflate(R.layout.news_detial_layout, null);
        toolbar = (Toolbar) inflate.findViewById(R.id.common_toolbar);
        webView = (WebView) inflate.findViewById(R.id.wv_web);
        String title=getIntent().getStringExtra("title");
        toolbar.setTitle(title);
        return inflate;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.BOTTOM;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }


}
