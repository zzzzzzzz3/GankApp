package com.example.msi.myapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 文 件 名: PagerFragment
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/1 18:27
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：内容页面
 */
public class PagerFragment extends Fragment {
    private static final String TAG = "PagerFragment";
    private int pagerIndex;
    private String desc;

    public static PagerFragment newInstance(int index,String desc){
        Bundle args = new Bundle();
        args.putInt("index",index);
        args.putString("desc",desc);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerIndex = getArguments().getInt("index");
        desc = getArguments().getString("desc");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager1_layout,null);
        TextView textView = (TextView) view.findViewById(R.id.pagerone_tv);
        textView.setText(desc+pagerIndex);
        return view;
    }
}
