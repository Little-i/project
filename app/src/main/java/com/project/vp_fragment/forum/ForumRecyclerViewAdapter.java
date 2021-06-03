package com.project.vp_fragment.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.ShowToast;

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.MyViewHolder> {
    private final List<Bean> beans;
    private final Context context;

    public ForumRecyclerViewAdapter(List<Bean> beans, Context context) {
        this.beans = beans;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(context, R.layout.view_forum_recycler_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvContent.setText(beans.get(position).getContent());
        holder.tvAuthorName.setText(beans.get(position).getName());
        holder.circleImageView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return beans == null ? 0 : beans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivHeart;
        private final TextView tvAuthorName;
        private final TextView tvContent;
        private final CircleImageView circleImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LinearLayout llAuthor = itemView.findViewById(R.id.ll_author);
            ImageView ivComment = itemView.findViewById(R.id.iv_comment);
            ivHeart = itemView.findViewById(R.id.iv_heart);
            tvAuthorName = itemView.findViewById(R.id.tv_authorName);
            tvContent = itemView.findViewById(R.id.tv_content);
            circleImageView = itemView.findViewById(R.id.circleIv);
            ivHeart.setOnClickListener(this);
            ivComment.setOnClickListener(this);
            llAuthor.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClickListener(getAdapterPosition());
                    }
                }
            });
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_heart:
                    ivHeart.setSelected(!ivHeart.isSelected());
                    break;
                case R.id.iv_comment:
                    ShowToast.shortToast(context, "评论功能尚未实装");
                    break;
                case R.id.ll_author:
                    ShowToast.shortToast(context, "click author: " + getAdapterPosition());
                    break;
            }
        }
    }

    /**
     * item点击事件的对外接口
     */
    private onRecyclerItemClickListener listener;

    public void setRecyclerItemClickListener(onRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public interface onRecyclerItemClickListener {
        void onItemClickListener(int position);
    }
}
