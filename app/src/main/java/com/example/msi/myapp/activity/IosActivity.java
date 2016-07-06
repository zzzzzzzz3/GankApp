package com.example.msi.myapp.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.msi.myapp.module.IosResult;

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
public class IosActivity extends AppCompatActivity {
    private static final String TAG = "AndroidActivity";

    @Bind(R.id.colltoolbarlayout_ios)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.image_ios)
    ImageView imageView;
    @Bind(R.id.toolbar_ios)
    Toolbar toolbar;
    @Bind(R.id.content_ios)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ios_layout);
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
        IosResult result = (IosResult) intent.getSerializableExtra("ios");
        getSupportActionBar().setTitle(result.getWho());
        webView.loadUrl(result.getUrl());
    }
}
