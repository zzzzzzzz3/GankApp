package com.example.msi.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.msi.myapp.R;
import com.example.msi.myapp.activity.MeiziActivity;
import com.example.msi.myapp.activity.NewsActivity;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int j = position;
        Glide.with(this.context).load(datas.get(j).getUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MeiziActivity.class);
                intent.putExtra("meizi", datas.get(j));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
