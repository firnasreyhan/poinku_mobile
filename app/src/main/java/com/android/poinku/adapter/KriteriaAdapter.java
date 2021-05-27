package com.android.poinku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.poinku.R;
import com.android.poinku.api.response.KriteriaResponse;

import java.util.List;

public class KriteriaAdapter extends RecyclerView.Adapter<KriteriaAdapter.ViewHolder> {
    private List<KriteriaResponse.DetailKriteria> list;

    public KriteriaAdapter(List<KriteriaResponse.DetailKriteria> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kriteria, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewJenis.setText(list.get(position).jenis);
        holder.textViewLingkup.setText(list.get(position).lingkup);
        holder.textViewJumlahKegiatan.setText(list.get(position).jumlah + " Kegiatan");
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
        private final TextView textViewJenis, textViewLingkup, textViewJumlahKegiatan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewJenis = itemView.findViewById(R.id.textViewJenis);
            textViewLingkup = itemView.findViewById(R.id.textViewLingkup);
            textViewJumlahKegiatan = itemView.findViewById(R.id.textViewJumlahKegiatan);
        }
    }
}
