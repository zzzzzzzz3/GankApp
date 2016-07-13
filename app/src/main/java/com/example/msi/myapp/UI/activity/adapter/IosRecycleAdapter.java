package com.example.msi.myapp.UI.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.msi.myapp.Interface.ShareToWB;
import com.example.msi.myapp.R;
import com.example.msi.myapp.UI.activity.ShareActivity;
import com.example.msi.myapp.Utils.DateUtil;
import com.example.msi.myapp.UI.activity.IosActivity;
import com.example.msi.myapp.module.IosResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class IosRecycleAdapter extends RecyclerView.Adapter<IosRecycleAdapter.ViewHolder>{
    private static final String TAG = "AndroidRecycleAdapter";
    private List<IosResult> results;
    private Context context;
    private ShareToWB shareToWB;

    public IosRecycleAdapter(Context context, List<IosResult> results){
        this.context = context;
        this.results = results;
        this.shareToWB = (ShareToWB) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ios_itemview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, IosActivity.class);
                intent.putExtra("ios",results.get(position));
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
                shareToWB.share(results.get(position).getUrl(),results.get(position).getDesc());

            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(context, IosActivity.class);
                intent.putExtra("ios",results.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results==null?0:results.size();
    }

    public void addItem(List<IosResult> results){
        this.results = results;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.card_view_ios)
        CardView cardView;
        @Bind(R.id.photo_ios)
        ImageView imageView;
        @Bind(R.id.title_ios)
        TextView title;
        @Bind(R.id.desc_ios)
        TextView desc;
        @Bind(R.id.btn_share_ios)
        Button share;
        @Bind(R.id.btn_more_ios)
        Button more;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
