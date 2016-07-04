package com.example.msi.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.msi.myapp.adapter.PagerFragmentAdapter;
import com.example.msi.myapp.R;
import com.example.msi.myapp.module.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文 件 名: NewsActivity
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/6/30 16:32
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：消息的页面
 */
public class NewsActivity extends AppCompatActivity {
    private static final String TAG = "NewsActivity";

    @Bind(R.id.colltoolbarlayout_test)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.image_test)
    ImageView imageView;
    @Bind(R.id.toolbar_test)
    Toolbar toolbar;
    @Bind(R.id.tablayout_test)
    TabLayout tabLayout;
    @Bind(R.id.vp_test)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toobar设置返回按钮的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //获得从上一层页面携带过来的信息
        Intent intent = getIntent();
        News item = (News) intent.getExtras().get("news");
        //设置页面的相关信息
        collapsingToolbarLayout.setTitle(item.getTitle());
        imageView.setImageResource(item.getPhotoId());
        List<String> tags = new ArrayList<>(2);
        tags.add("one");
        tags.add("two");
        //tabLayout和viewPager绑定
        viewPager.setAdapter(new PagerFragmentAdapter(getSupportFragmentManager(), tags, item.getDec()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

}
