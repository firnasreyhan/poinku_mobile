package com.android.poinku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.api.response.DetailTugasKhususResponse;
import com.android.poinku.api.response.KegiatanResponse;
import com.android.poinku.api.response.KontenResponse;
import com.android.poinku.databinding.ActivityDetailTugasKhususBinding;
import com.android.poinku.viewmodel.DetailTugasKhususViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailTugasKhususActivity extends AppCompatActivity {
    private ActivityDetailTugasKhususBinding binding;
    private DetailTugasKhususViewModel viewModel;

    private String idTugasKusus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTugasKhususBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idTugasKusus = getIntent().getStringExtra("ID_TUGAS_KHUSUS");
        Log.e("idTugasKhusus", idTugasKusus);

        viewModel = ViewModelProviders.of(this).get(DetailTugasKhususViewModel.class);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getDetailTugasKhusus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getDetailTugasKhusus() {
        viewModel.getDetailTugasKhusus(
                idTugasKusus
        ).observe(this, new Observer<DetailTugasKhususResponse>() {
            @Override
            public void onChanged(DetailTugasKhususResponse detailTugasKhususResponse) {
                if (detailTugasKhususResponse != null) {
                    if (detailTugasKhususResponse.status) {
                        binding.editTextJudul.setText(detailTugasKhususResponse.data.judul);

                        String nmyFormat = "dd MMMM yyyy"; //In which you need put here
                        SimpleDateFormat nsdf = new SimpleDateFormat(nmyFormat, new Locale("id", "ID"));
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                        try {
                            Date date = format.parse(detailTugasKhususResponse.data.tanggalKegiatan);
                            binding.editTextTanggalKegiatan.setText(nsdf.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        binding.editTextJenis.setText(detailTugasKhususResponse.data.jenis);

                        if (!detailTugasKhususResponse.data.idJenis.equalsIgnoreCase("15")) {
                            binding.linearLayoutBukanKonten.setVisibility(View.VISIBLE);
                            binding.linearLayoutKonten.setVisibility(View.GONE);

                            binding.editTextLingkup.setText(detailTugasKhususResponse.data.lingkup);
                            binding.editTextPeran.setText(detailTugasKhususResponse.data.peran);

                            Picasso.get()
                                    .load(detailTugasKhususResponse.data.bukti)
                                    .placeholder(R.drawable.ic_logi_stiki)
                                    .error(R.drawable.ic_logi_stiki)
                                    .into(binding.imageViewBuktiKegiatan);

                            getKegiatan();
                        } else {
                            binding.linearLayoutBukanKonten.setVisibility(View.GONE);
                            binding.linearLayoutKonten.setVisibility(View.VISIBLE);

                            binding.editTextLinkKonten.setText(detailTugasKhususResponse.data.bukti);

                            getKonten();
                        }
                    }
                }
            }
        });
    }

    public void getKegiatan() {
        viewModel.getKegiatan(
                idTugasKusus
        ).observe(this, new Observer<KegiatanResponse>() {
            @Override
            public void onChanged(KegiatanResponse kegiatanResponse) {
                if (kegiatanResponse != null) {
                    if (kegiatanResponse.status) {
                        binding.editTextPenyelenggaraPembicara.setText(kegiatanResponse.data.keterangan);
                    }
                }
            }
        });
    }

    public void getKonten() {
        viewModel.getKonten(
                idTugasKusus
        ).observe(this, new Observer<KontenResponse>() {
            @Override
            public void onChanged(KontenResponse kontenResponse) {
                if (kontenResponse != null) {
                    if (kontenResponse.status) {
                        binding.editTextJenisKonten.setText(kontenResponse.data.jenisKonten);
                        binding.editTextMediaKonten.setText(kontenResponse.data.mediaKonten);
                    }
                }
            }
        });
    }
}