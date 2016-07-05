package com.example.msi.myapp.fragment;

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
import com.example.msi.myapp.adapter.MeiziRecycleAdapter;
import com.example.msi.myapp.adapter.MyRecycleAdapter;
import com.example.msi.myapp.module.MeiziResult;
import com.example.msi.myapp.presenter.Data;
import com.xlf.nrl.NsRefreshLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 文 件 名: MeiziFragment
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 11:45
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MeiziFragment extends Fragment implements NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener{
    private static final String TAG = "MeiziFragment";


    private Context context;
    private List<MeiziResult> meiziResults;
    private NsRefreshLayout nsRefreshLayout;
    private Subscriber<List<MeiziResult>> subscriber;
    private MeiziRecycleAdapter myRecycleAdapter;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.context = context;
        subscriber = new Subscriber<List<MeiziResult>>() {
            @Override
            public void onCompleted() {
                subscriber.unsubscribe();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("123","error");
            }

            @Override
            public void onNext(List<MeiziResult> meiziResult) {
                meiziResults = meiziResult;
                Log.d("123",meiziResults.toString());
                myRecycleAdapter.addItem(meiziResults);
            }
        };
        Data.getINSTANCE().getData(subscriber,10,1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nsRefreshLayout = (NsRefreshLayout) inflater.inflate(R.layout.fragment_layout,container,false);
        RecyclerView recyclerView = (RecyclerView) nsRefreshLayout.findViewById(R.id.rv_test);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        myRecycleAdapter = new MeiziRecycleAdapter(context,meiziResults);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myRecycleAdapter);
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

    @Override
    public void onRefresh() {
        myRecycleAdapter.notifyDataSetChanged();
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
