package com.example.msi.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 文 件 名: MyRecycleAdapter
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/3 16:46
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    private Context context;
    private List<News> datas;

    public MyRecycleAdapter(Context context,List<News> datas){
        this.datas = datas;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    //在这里对内容页面里的view进行操作
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int j = position;

        holder.title.setText(datas.get(position).getTitle());
        holder.desc.setText(datas.get(position).getDec());
        holder.imageView.setImageResource(datas.get(position).getPhotoId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("news", datas.get(j));
                context.startActivity(intent);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, datas.get(j).getDec());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, datas.get(j).getTitle()));//自定义标题
            }
        });

        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("news", datas.get(j));
                context.startActivity(intent);
            }
        });
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView title;
        TextView desc;
        Button share;
        Button readmore;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            imageView = (ImageView) itemView.findViewById(R.id.news_photo);
            title = (TextView) itemView.findViewById(R.id.news_title);
            desc = (TextView) itemView.findViewById(R.id.news_desc);
            share = (Button) itemView.findViewById(R.id.btn_share);
            readmore = (Button) itemView.findViewById(R.id.btn_more);
            title.setBackgroundColor(Color.argb(20, 0, 0, 0));
        }
    }
}

