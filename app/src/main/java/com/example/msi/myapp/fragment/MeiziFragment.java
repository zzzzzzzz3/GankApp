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
import com.xlf.nrl.NsRefreshLayout;

import java.util.ArrayList;
import java.util.List;

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

    public MeiziFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        meiziResults = new ArrayList<MeiziResult>();
        for (int i = 0; i <10 ; i++) {
            MeiziResult meiziresult = new MeiziResult();
            meiziresult.setUrl(getArguments().getInt("index")+"");
            meiziresult.setDesc(getArguments().getString("content"));
            //Log.d(TAG,meiziresult.desc);
            meiziResults.add(meiziresult);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        nsRefreshLayout = (NsRefreshLayout) inflater.inflate(R.layout.fragment_layout,container,false);
        RecyclerView recyclerView = (RecyclerView) nsRefreshLayout.findViewById(R.id.rv_test);
        MeiziRecycleAdapter myRecycleAdapter = new MeiziRecycleAdapter(context,meiziResults);
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
