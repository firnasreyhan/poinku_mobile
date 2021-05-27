package com.android.poinku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.poinku.R;
import com.android.poinku.api.response.PoinResponse;

import java.util.List;

public class PoinAdapter extends RecyclerView.Adapter<PoinAdapter.ViewHolder> {
    private List<PoinResponse.DetailPoin> list;

    public PoinAdapter(List<PoinResponse.DetailPoin> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewJenis.setText(list.get(position).jenis);
        holder.textViewLingkup.setText(list.get(position).lingkup);
        holder.textViewPeran.setText(list.get(position).peran);
        holder.textViewPoin.setText(list.get(position).poin + " Poin");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewJenis, textViewLingkup, textViewPeran, textViewPoin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewJenis = itemView.findViewById(R.id.textViewJenis);
            textViewLingkup = itemView.findViewById(R.id.textViewLingkup);
            textViewPeran = itemView.findViewById(R.id.textViewPeran);
            textViewPoin = itemView.findViewById(R.id.textViewPoin);
        }
    }
}
