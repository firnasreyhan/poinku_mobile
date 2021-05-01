package com.android.poinku.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.poinku.R;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.view.activity.DaftarJenisTugasKhususActivity;

import java.util.List;

public class DaftarTugasKhususAdapter extends RecyclerView.Adapter<DaftarTugasKhususAdapter.ViewHolder> {
    private List<JenisTugasKhususResponse.DetailJenisTugasKhusus> list;

    public DaftarTugasKhususAdapter(List<JenisTugasKhususResponse.DetailJenisTugasKhusus> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_tugas_khusus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewJenisKegiatan.setText(list.get(position).jenis);
        holder.textViewJumlahKegiatan.setText(list.get(position).total + " Kegiatan");
        if (list.get(position).jumlah != null) {
            holder.textViewJumlahPoin.setText(list.get(position).jumlah + " Poin");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewJenisKegiatan, textViewJumlahPoin, textViewJumlahKegiatan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewJenisKegiatan = itemView.findViewById(R.id.textViewJenisKegiatan);
            textViewJumlahPoin = itemView.findViewById(R.id.textViewJumlahPoin);
            textViewJumlahKegiatan = itemView.findViewById(R.id.textViewJumlahKegiatan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DaftarJenisTugasKhususActivity.class);
                    intent.putExtra("ID_JENIS", list.get(getAdapterPosition()).idJenis);
                    intent.putExtra("JENIS", list.get(getAdapterPosition()).jenis);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
