package com.example.msi.myapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.myapp.R;
import com.example.msi.myapp.adapter.AndroidRecycleAdapter;
import com.example.msi.myapp.module.AndroidResult;
import com.example.msi.myapp.presenter.Data;
import com.xlf.nrl.NsRefreshLayout;

import java.util.List;

import rx.Subscriber;

/**
 * 文 件 名: AndroidFragment
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 11:45
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class AndroidFragment extends Fragment implements NsRefreshLayout.NsRefreshLayoutListener,NsRefreshLayout.NsRefreshLayoutController{
    private static final String TAG = "AndroidFragment";

    private Context context;
    private NsRefreshLayout nsRefreshLayout;
    private RecyclerView recyclerView;
    private AndroidRecycleAdapter adapter;
    private Subscriber<List<AndroidResult>> subscriber;
    private List<AndroidResult> results;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        subscriber = new Subscriber<List<AndroidResult>>() {
            @Override
            public void onCompleted() {
                adapter.addItem(results);
                subscriber.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AndroidResult> result) {
                results = result;

            }
        };
        Data.getINSTANCE().getAndroidData(subscriber,10,1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nsRefreshLayout = (NsRefreshLayout) inflater.inflate(R.layout.fragment_layout,container,false);
        recyclerView = (RecyclerView) nsRefreshLayout.findViewById(R.id.rv_test);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AndroidRecycleAdapter(context,results);
        recyclerView.setAdapter(adapter);
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

    @Override
    public void onRefresh() {
        nsRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                nsRefreshLayout.finishPullRefresh();
            }
        },500);
    }

    @Override
    public void onLoadMore() {
        nsRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                nsRefreshLayout.finishPullLoad();
            }
        },500);
    }
}
