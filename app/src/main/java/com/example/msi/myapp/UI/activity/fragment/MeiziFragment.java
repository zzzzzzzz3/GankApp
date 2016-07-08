package com.example.msi.myapp.UI.activity.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msi.myapp.R;
import com.example.msi.myapp.UI.activity.adapter.MeiziRecycleAdapter;
import com.example.msi.myapp.module.MeiziResult;
import com.example.msi.myapp.presenter.Data;
import com.xlf.nrl.NsRefreshLayout;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

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
    private NsRefreshLayout nsRefreshLayout;
    private Subscription subscription;
    private MeiziRecycleAdapter myRecycleAdapter;
    private List<MeiziResult> meiziResults;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        this.context = context;
        subscription =Data.getINSTANCE().getData(new Subscriber<List<MeiziResult>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<MeiziResult> meiziResult) {
                meiziResults = meiziResult;
                myRecycleAdapter.addItem(meiziResult);

            }
        },10,1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nsRefreshLayout = (NsRefreshLayout) inflater.inflate(R.layout.fragment_layout,container,false);
        RecyclerView recyclerView = (RecyclerView) nsRefreshLayout.findViewById(R.id.rv_test);
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);
        myRecycleAdapter = new MeiziRecycleAdapter(context,meiziResults);
        recyclerView.setLayoutManager(layoutManager);
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
        unsubscribe();
        //再次请求必须新建subscriber
        subscription=Data.getINSTANCE().getData(new Subscriber<List<MeiziResult>>() {
            @Override
            public void onCompleted() {

                if (nsRefreshLayout!=null) {
                    nsRefreshLayout.finishPullRefresh();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<MeiziResult> meiziResult) {
                meiziResults = meiziResult;
                myRecycleAdapter.addItem(meiziResults);
            }
        }, myRecycleAdapter.getItemCount(), 1);

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

    @Override
    public void onLoadMore() {
        subscription=Data.getINSTANCE().getData(new Subscriber<List<MeiziResult>>() {
            @Override
            public void onCompleted() {

                if (nsRefreshLayout!=null) {
                    nsRefreshLayout.finishPullLoad();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<MeiziResult> meiziResult) {
                meiziResults = meiziResult;
                myRecycleAdapter.addItem(meiziResults);
            }
        }, myRecycleAdapter.getItemCount()+10, 1);
    }

}
