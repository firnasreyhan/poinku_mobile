package com.android.poinku.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.poinku.R;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.view.activity.DetailEventActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EventUserAdapter extends RecyclerView.Adapter<EventUserAdapter.ViewHolder> {
    private final List<EventResponse.DetailEvent> list;

    public EventUserAdapter(List<EventResponse.DetailEvent> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Picasso.get()
                .load(list.get(position).poster)
                .placeholder(R.drawable.no_image)
                .into(holder.imageViewPoster);
        holder.textViewJudul.setText(list.get(position).judul);
        holder.textViewJenis.setText(list.get(position).jenis);
        holder.textViewLingkup.setText(list.get(position).lingkup);
        holder.textViewPendaftarKuota.setText(list.get(position).pendaftar + " / " + list.get(position).kuota);
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
        private final TextView textViewJudul, textViewJenis, textViewLingkup, textViewPendaftarKuota;
        private final ImageView imageViewPoster;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewJenis = itemView.findViewById(R.id.textViewJenis);
            textViewLingkup = itemView.findViewById(R.id.textViewLingkup);
            textViewPendaftarKuota = itemView.findViewById(R.id.textViewPendaftarKuota);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailEventActivity.class);
                    intent.putExtra("ID_EVENT", list.get(getAdapterPosition()).idEvent);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
