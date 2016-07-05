package com.example.msi.myapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msi.myapp.R;
import com.example.msi.myapp.Utils.DateUtil;
import com.example.msi.myapp.activity.AndroidActivity;
import com.example.msi.myapp.module.AndroidResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文 件 名: AndroidRecycleAdapter
 * 创 建 人: ZhangRonghua
 * 创建日期: 2016/7/5 19:15
 * 邮   箱: qq798435167@gmail.com
 * 博   客: http://zzzzzzzz3.github.io
 * 修改时间：
 * 修改备注：
 */
public class AndroidRecycleAdapter extends RecyclerView.Adapter<AndroidRecycleAdapter.ViewHolder>{
    private static final String TAG = "AndroidRecycleAdapter";
    private List<AndroidResult> results;
    private Context context;

    public AndroidRecycleAdapter(Context context,List<AndroidResult> results){
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.android_itemview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AndroidActivity.class);
                intent.putExtra("android",results.get(position));
                context.startActivity(intent);
            }
        });
        holder.desc.setText(results.get(position).getDesc());
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
            Date inputDate = inputFormat.parse(results.get(position).getCreatedAt());
            String outputDate = DateUtil.format(inputDate);
            holder.title.setText(outputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, results.get(position).getDesc());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, results.get(position).getId()));
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, AndroidActivity.class);
                intent.putExtra("android",results.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results==null?0:results.size();
    }

    public void addItem(List<AndroidResult> results){
        this.results = results;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.card_view_android)
        CardView cardView;
        @Bind(R.id.photo_android)
        ImageView imageView;
        @Bind(R.id.title_android)
        TextView title;
        @Bind(R.id.desc_android)
        TextView desc;
        @Bind(R.id.btn_share_android)
        Button share;
        @Bind(R.id.btn_more_android)
        Button more;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
