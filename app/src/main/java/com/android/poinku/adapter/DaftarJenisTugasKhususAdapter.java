package com.android.poinku.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.poinku.R;
import com.android.poinku.api.response.DataPoinResponse;
import com.android.poinku.view.activity.DetailTugasKhususActivity;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class DaftarJenisTugasKhususAdapter extends RecyclerView.Adapter<DaftarJenisTugasKhususAdapter.ViewHolder> {
    private List<DataPoinResponse.DetailDataPoin> list;

    public DaftarJenisTugasKhususAdapter(List<DataPoinResponse.DetailDataPoin> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daftar_jenis_tugas_khusus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewJudul.setText(list.get(position).judul);
        holder.textViewLingkup.setText(list.get(position).lingkup);
        holder.textViewPeran.setText(list.get(position).peran);
        holder.textViewPoin.setText(list.get(position).poin == null ? "0" : list.get(position).poin);

        if (list.get(position).statusValidasi.equalsIgnoreCase("0")) {
            holder.imageViewStatus.setImageResource(R.drawable.ic_process);
            holder.textViewStatus.setTextColor(Color.parseColor("#F8B500"));
            holder.textViewStatus.setText("Sedang Diproses");
        } else if (list.get(position).statusValidasi.equalsIgnoreCase("1")) {
            holder.imageViewStatus.setImageResource(R.drawable.ic_approve);
            holder.textViewStatus.setTextColor(Color.parseColor("#149654"));
            holder.textViewStatus.setText("Disetujui");
        } else if (list.get(position).statusValidasi.equalsIgnoreCase("2")) {
            holder.imageViewStatus.setImageResource(R.drawable.ic_reject);
            holder.textViewStatus.setTextColor(Color.parseColor("#F06641"));
            holder.textViewStatus.setText("Ditolak");
        }
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
        private final TextView textViewJudul, textViewLingkup, textViewPeran, textViewPoin, textViewStatus;
        private final ImageView imageViewStatus;
        private final MaterialButton materialButtonDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewLingkup = itemView.findViewById(R.id.textViewLingkup);
            textViewPeran = itemView.findViewById(R.id.textViewPeran);
            textViewPoin = itemView.findViewById(R.id.textViewPoin);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
            materialButtonDetail = itemView.findViewById(R.id.materialButtonDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailTugasKhususActivity.class);
                    intent.putExtra("ID_TUGAS_KHUSUS", list.get(getAdapterPosition()).idTugasKhusus);
                    v.getContext().startActivity(intent);
                }
            });

            materialButtonDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailTugasKhususActivity.class);
                    intent.putExtra("ID_TUGAS_KHUSUS", list.get(getAdapterPosition()).idTugasKhusus);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
