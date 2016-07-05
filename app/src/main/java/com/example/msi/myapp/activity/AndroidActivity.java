package com.example.msi.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.msi.myapp.R;
import com.example.msi.myapp.module.AndroidResult;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文 件 名: AndroidActivity
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/5 20:13
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class AndroidActivity extends AppCompatActivity {
    private static final String TAG = "AndroidActivity";

    @Bind(R.id.colltoolbarlayout_android)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.image_android)
    ImageView imageView;
    @Bind(R.id.toolbar_android)
    Toolbar toolbar;
    @Bind(R.id.content_android)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        AndroidResult result = (AndroidResult) intent.getSerializableExtra("android");
        getSupportActionBar().setTitle(result.getWho());
        webView.loadUrl(result.getUrl());
    }
}
