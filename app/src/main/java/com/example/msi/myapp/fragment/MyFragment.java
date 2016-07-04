package com.example.msi.myapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.myapp.Interface.DoSth;
import com.example.msi.myapp.adapter.MyRecycleAdapter;
import com.example.msi.myapp.R;
import com.example.msi.myapp.module.News;
import com.xlf.nrl.NsRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: MyFragment
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/2 13:31
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MyFragment extends Fragment implements NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener{
    private static final String TAG = "MyFragment";
    private boolean loadMoreEnable = true;
    private Context context;
    private DoSth doSth;
    private NsRefreshLayout nsRefreshLayout;
    private List<News> datas;

    //初始化数据
    public MyFragment(){
        datas = new ArrayList<News>();
        for (int i = 0; i <10 ; i++) {
            News news = new News("HELLO","this is "+i+" news", R.drawable.photo);
            datas.add(news);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        doSth = (DoSth) context;
    }

    //在这里对View进行初始化
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         nsRefreshLayout= (NsRefreshLayout) inflater.inflate(R.layout.fragment_layout,container,false);
        //设置recycleView
        RecyclerView recyclerView = (RecyclerView) nsRefreshLayout.findViewById(R.id.rv_test);
        MyRecycleAdapter myRecycleAdapter = new MyRecycleAdapter(context,datas);
        recyclerView.setAdapter(myRecycleAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //refreshview设置监听
        nsRefreshLayout.setRefreshLayoutController(this);
        nsRefreshLayout.setRefreshLayoutListener(this);
        return nsRefreshLayout;
    }

    @Override
    public boolean isPullRefreshEnable() {
        return true;
    }

    @Override
    public boolean isPullLoadEnable() {
        return true;
    }

    //完成刷新的动作
    @Override
    public void onRefresh() {
        nsRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
              nsRefreshLayout.finishPullRefresh();
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        nsRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                nsRefreshLayout.finishPullLoad();
            }
        },1000);
    }
}
