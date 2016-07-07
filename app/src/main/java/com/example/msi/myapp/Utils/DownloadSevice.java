package com.example.msi.myapp.Utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.msi.myapp.Interface.OnLoadListener;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * 文 件 名: DownloadSevice
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/7 11:53
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class DownloadSevice implements Runnable {
    private Context context;
    private OnLoadListener listener;
    private String url;

    public DownloadSevice(Context context,String url,OnLoadListener listener){
        this.context = context;
        this.listener = listener;
        this.url = url;
    }

    @Override
    public void run() {
        File file = null;
        try {
            file = Glide.with(context)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            if (file!=null){
                listener.compled(file);
            }else {
                listener.error();
            }
        }

    }
}
