package com.example.msi.myapp.UI.activity.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.myapp.R;
import com.example.msi.myapp.UI.activity.adapter.AndroidRecycleAdapter;
import com.example.msi.myapp.module.AndroidResult;
import com.example.msi.myapp.presenter.Data;
import com.xlf.nrl.NsRefreshLayout;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

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
    private List<AndroidResult> results;
    private Subscription subscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("CCCCCCCCC","loading");
        this.context = context;

        Data.getINSTANCE().getAndroidData(new Subscriber<List<AndroidResult>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AndroidResult> result) {
                results = result;
                adapter.addItem(results);
            }
        },10,1);
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
        unsubscribe();
        Data.getINSTANCE().getAndroidData(new Subscriber<List<AndroidResult>>() {
            @Override
            public void onCompleted() {
                nsRefreshLayout.finishPullRefresh();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AndroidResult> result) {
                results = result;
                adapter.addItem(results);
            }
        },adapter.getItemCount(),1);
    }

    @Override
    public void onLoadMore() {
        unsubscribe();
        Data.getINSTANCE().getAndroidData(new Subscriber<List<AndroidResult>>() {
            @Override
            public void onCompleted() {
                nsRefreshLayout.finishPullLoad();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<AndroidResult> result) {
                results = result;
                adapter.addItem(results);
            }
        },adapter.getItemCount()+10,1);
    }
    public void unsubscribe(){
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }
}
