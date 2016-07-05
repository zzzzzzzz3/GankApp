package com.example.msi.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.msi.myapp.R;
import com.example.msi.myapp.module.MeiziResult;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文 件 名: MeiziActivity
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 13:13
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MeiziActivity extends AppCompatActivity {
    private static final String TAG = "MeiziActivity";
    @Bind(R.id.card_view_meizi_detail)
    CardView cardView;
    @Bind(R.id.detail_meizi)
    ImageView imageView;
    @Bind(R.id.image_desc)
    TextView textView;
    @Bind(R.id.like_meizi_button)
    ImageButton likeButton;
    @Bind(R.id.share_meizi_button)
    ImageButton shareButton;
    @Bind(R.id.load_meizi_button)
    ImageButton loadButton;
    @Bind(R.id.toolbar_meizi)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meizi_layout);
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
        MeiziResult meiziResult = (MeiziResult) intent.getSerializableExtra("meizi");
        Glide.with(this).load(meiziResult.getUrl()).into(imageView);
        textView.setText(meiziResult.getDesc());
        getSupportActionBar().setTitle(meiziResult.getWho());
    }
    @OnClick(R.id.share_meizi_button)
    void share(){
        Toast.makeText(this,"share",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.like_meizi_button)
    void like(){
        Toast.makeText(this,"like",Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.load_meizi_button)
    void load(){
        Toast.makeText(this,"load image",Toast.LENGTH_SHORT).show();
    }
}
