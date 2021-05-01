package com.android.poinku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.poinku.R;
import com.android.poinku.api.response.NilaiResponse;

import java.util.List;

public class NilaiAdapter extends RecyclerView.Adapter<NilaiAdapter.ViewHolder> {
    private List<NilaiResponse.DetailNilai> list;

    public NilaiAdapter(List<NilaiResponse.DetailNilai> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nilai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewNilai.setText(list.get(position).nilai);
        holder.textViewPoinMinimal.setText(list.get(position).poinMinimal);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNilai, textViewPoinMinimal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNilai = itemView.findViewById(R.id.textViewNilai);
            textViewPoinMinimal = itemView.findViewById(R.id.textViewPoinMinimal);
        }
    }
}
