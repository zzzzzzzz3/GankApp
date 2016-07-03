package com.example.msi.myapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 文 件 名: PagerFragmentAdapter
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/1 18:38
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = "PagerFragmentAdapter";
    private List<String> tags;
    private String desc;

    public PagerFragmentAdapter(FragmentManager fm,List<String> tags,String desc) {
        super(fm);
        this.tags = tags;
        this.desc = desc;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerFragment.newInstance(position+1,desc);
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tags.get(position);
    }
}
