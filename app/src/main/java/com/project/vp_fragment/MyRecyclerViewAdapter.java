package com.project.vp_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private final List<Bean> beans;
    private final Context context;
    private onRecyclerItemClickListener listener;

    public MyRecyclerViewAdapter(List<Bean> beans, Context context) {
        this.beans = beans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.view_home_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(beans.get(position).getName());
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    /**
     * CardView的高度好像取决于它里面内容的高度
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_homeRv_image);
            textView = itemView.findViewById(R.id.tv_homeRv_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onRecyclerItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setRecyclerItemClickListener(onRecyclerItemClickListener listener){
        this.listener = listener;
    }

    public interface onRecyclerItemClickListener{
        void onRecyclerItemClick(int position);
    }
}
