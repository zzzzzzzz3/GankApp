package com.example.msi.myapp.UI.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.msi.myapp.R;
import com.example.msi.myapp.UI.activity.MeiziActivity;
import com.example.msi.myapp.module.MeiziResult;

import java.util.List;

/**
 * 文 件 名: MeiziRecycleAdapter
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/4 12:28
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MeiziRecycleAdapter extends RecyclerView.Adapter<MeiziRecycleAdapter.ViewHolder>{
    private static final String TAG = "MeiziRecycleAdapter";
    private List<MeiziResult> datas;
    private Context context;
    private int itemHeight;

    public MeiziRecycleAdapter(Context context,List<MeiziResult> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meizi_itemview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int j = position;
        SimpleTarget target = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                holder.imageView.setImageDrawable(new GlideBitmapDrawable(null,resource){
                });
            }
        };
        Glide.with(this.context)
                .load(datas.get(j).getUrl())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//仅缓存原图
                .into(target);
        Log.d(TAG,holder.imageView.getHeight()+"");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MeiziActivity.class);
                intent.putExtra("meizi",  datas.get(j));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.d("datasize",datas.size()+"");
        return datas==null?0:datas.size();
    }

    public void addItem(List<MeiziResult> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = (ImageView) itemView.findViewById(R.id.meizi_image);
        }
        public void setImageView(){
        }
    }
}
