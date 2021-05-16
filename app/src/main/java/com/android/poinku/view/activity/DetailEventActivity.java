package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.api.response.PresensiResponse;
import com.android.poinku.api.response.TugasKhususResponse;
import com.android.poinku.databinding.ActivityDetailEventBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.DetailEventViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailEventActivity extends AppCompatActivity {
    private ActivityDetailEventBinding binding;
    private DetailEventViewModel viewModel;
    private ProgressDialog progressDialog;

    private String idEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idEvent = getIntent().getStringExtra("ID_EVENT");

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModel = ViewModelProviders.of(this).get(DetailEventViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.setCancelable(false);

        getDetailEvent();
        getPresesni();

        binding.materialButtonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Pesan")
                        .setMessage("Apakah anda yakin ingin mengikuti kegiatan ini?")
                        .setCancelable(false)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postDaftarEvent();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        binding.materialButtonBatalDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Pesan")
                        .setMessage("Apakah anda yakin ingin membatalkan mengikuti kegiatan ini?")
                        .setCancelable(false)
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postBatalDaftarEvent();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void postDaftarEvent() {
        progressDialog.show();
        viewModel.postDaftarEvent(
                AppPreference.getUser(this).email,
                idEvent
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (baseResponse != null) {
                    if (baseResponse.status) {
                        alert("Anda berhasil mendaftar kegiatan ini");
                        binding.materialButtonDaftar.setVisibility(View.GONE);
                        binding.linearLayoutDaftar.setVisibility(View.VISIBLE);

                        getDetailEvent();
                    } else {
                        alert("Terjadi kesalahan pada server, silahkan coba lagi");
                    }
                } else {
                    alert("Terjadi kesalahan pada server, silahkan coba lagi");
                }
            }
        });
    }

    public void postBatalDaftarEvent() {
        progressDialog.show();
        viewModel.postBatalDaftarEvent(
                AppPreference.getUser(this).email,
                idEvent
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (baseResponse != null) {
                    if (baseResponse.status) {
                        alert("Anda berhasil membatalkan pendaftaran pada kegiatan ini");
                        binding.materialButtonDaftar.setVisibility(View.VISIBLE);
                        binding.linearLayoutDaftar.setVisibility(View.GONE);

                        getDetailEvent();
                    } else {
                        alert("Terjadi kesalahan pada server, silahkan coba lagi");
                    }
                } else {
                    alert("Terjadi kesalahan pada server, silahkan coba lagi");
                }
            }
        });
    }

    public void getDetailEvent() {
        viewModel.getDetailEvent(
                idEvent
        ).observe(this, new Observer<EventResponse>() {
            @Override
            public void onChanged(EventResponse eventResponse) {
                if (eventResponse != null) {
                    if (eventResponse.status) {
                        EventResponse.DetailEvent event = eventResponse.data.get(0);

                        Picasso.get()
                                .load(event.poster)
                                .placeholder(R.drawable.ic_logi_stiki)
                                .into(binding.imageViewPoster);
                        binding.textViewJudul.setText(event.judul);

                        String nmyFormat = "dd MMMM yyyy"; //In which you need put here
                        SimpleDateFormat nsdf = new SimpleDateFormat(nmyFormat, new Locale("id", "ID"));
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        try {
                            Date date = format.parse(event.tanggalAcara);
                            binding.textViewTanggal.setText(nsdf.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        binding.textViewJam.setText(event.jamMulai.substring(0, 5) + " - " + event.jamSelesai.substring(0, 5) + " WIB");
                        binding.textViewJenis.setText(event.jenis);
                        binding.textViewLingkup.setText(event.lingkup);
                        binding.textViewPendaftar.setText("Pendaftar " + event.pendaftar + " Orang");
                        binding.textViewKuota.setText("Kuota " + event.kuota + " Orang");
                        binding.textViewDeskripsi.setText(event.deskripsi);
                    }
                }
            }
        });
    }

    public void getPresesni() {
        viewModel.getPresensi(
                AppPreference.getUser(this).email,
                idEvent
        ).observe(this, new Observer<PresensiResponse>() {
            @Override
            public void onChanged(PresensiResponse presensiResponse) {
                if (presensiResponse != null) {
                    if (presensiResponse.status) {
                        binding.materialButtonDaftar.setVisibility(View.GONE);
                        binding.linearLayoutDaftar.setVisibility(View.VISIBLE);
                    } else {
                        binding.materialButtonDaftar.setVisibility(View.VISIBLE);
                        binding.linearLayoutDaftar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public void alert(String pesan) {
        new AlertDialog.Builder(this)
                .setTitle("Pesan")
                .setMessage(pesan)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}