package com.segway.launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.VH> {

    private final Context mContext;
    private final List<Item> list;
    private OnAppClickListener mListener;

    public AppAdapter(Context content, List<Item> list) {
        this.mContext = content;
        this.list = list;
    }

    public void setListener(OnAppClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.ivIcon.setImageDrawable(list.get(position).icon);
        holder.tvName.setText(list.get(position).name);
        if (mListener != null) {
            holder.itemView.setOnClickListener(v -> mListener.onAppClick(position, list.get(position)));
        }
    }

    public static class VH extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        public VH(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.icon);
            tvName = itemView.findViewById(R.id.name);
        }
    }

    public interface OnAppClickListener {
        void onAppClick(int position, Item which);
    }
}
