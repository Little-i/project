package com.project.vp_fragment.recovery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;

import java.util.List;

public class RecoveryRecyclerViewAdapter extends RecyclerView.Adapter<RecoveryRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<Bean> beans;

    public RecoveryRecyclerViewAdapter(Context context, List<Bean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //解决在View.inflater情况下CardView宽度不能铺满：使用LayoutInflater.from()
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_recovery_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvContent.setText(beans.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final CardView cvItem;
        private final TextView tvContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.recovery_item);
            tvContent = itemView.findViewById(R.id.tv_recoveryContent);
        }
    }
}
