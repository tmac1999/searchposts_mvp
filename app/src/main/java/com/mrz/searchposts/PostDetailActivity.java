package com.mrz.searchposts;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mrz.searchposts.component.BaseThemeActivity;

public class PostDetailActivity extends BaseThemeActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_postdetail);
		String url_postfix = getIntent().getStringExtra("url");
		WebView wv_post = (WebView) findViewById(R.id.wv_post);
		String url = "http://tieba.baidu.com"+url_postfix;
		wv_post.loadUrl(url);
		wv_post.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}
		});
	}
}
