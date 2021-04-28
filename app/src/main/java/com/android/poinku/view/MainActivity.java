package com.android.poinku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.api.response.KriteriaTugasKhususResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.api.response.TotalPoinResponse;
import com.android.poinku.databinding.ActivityMainBinding;
import com.android.poinku.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private int poin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        getMahasiswa();
        getTotalPoin();

        Log.e("poin", String.valueOf(poin));

        binding.cardViewCatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CatatActivity.class));
            }
        });
    }

    public void getMahasiswa() {
        viewModel.getMahasiswa(
                "171111079"
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
                        binding.textViewAturan.setText(mahasiswaResponse.data.tahun + " / " + mahasiswaResponse.data.keterangan);
                        binding.textViewStatusValidasi.setText(mahasiswaResponse.data.status.equalsIgnoreCase("0") ? "Belum Divalidasi" : "Divalidasi");
                        binding.textViewTanggalValidasi.setText(mahasiswaResponse.data.tanggalValidasi != null ? mahasiswaResponse.data.tanggalValidasi : "-");

                        getNilai(mahasiswaResponse.data.idAturan);
                    }
                }
            }
        });
    }

    public void getTotalPoin() {
        viewModel.getTotalPoin(
                "171111079"
        ).observe(this, new Observer<TotalPoinResponse>() {
            @Override
            public void onChanged(TotalPoinResponse totalPoinResponse) {
                if (totalPoinResponse != null) {
                    if (totalPoinResponse.status) {
                        binding.textViewTotalPoin.setText(totalPoinResponse.poin + " Poin");
                        binding.linearProgressIndicatorPoin.setProgress(Integer.parseInt(totalPoinResponse.poin));

                        poin = Integer.parseInt(totalPoinResponse.poin);
                    }
                }
            }
        });
    }

    public void getNilai(String idAturan) {
        viewModel.getNilai(
                idAturan
        ).observe(this, new Observer<NilaiResponse>() {
            @Override
            public void onChanged(NilaiResponse nilaiResponse) {
                if (nilaiResponse != null) {
                    if (nilaiResponse.status) {
                        binding.linearProgressIndicatorPoin.setMax(Integer.parseInt(nilaiResponse.data.get(0).poinMinimal));
                        for (NilaiResponse.DetailNilai nilai : nilaiResponse.data) {
                            if (poin >= Integer.parseInt(nilai.poinMinimal)) {
                                getKriteria(nilai.idNilai);
                            }
                        }
                    }
                }
            }
        });
    }

    public void getKriteria(String idNilai) {
        viewModel.getKriteria(
                idNilai
        ).observe(this, new Observer<KriteriaResponse>() {
            @Override
            public void onChanged(KriteriaResponse kriteriaResponse) {
                if (kriteriaResponse != null) {
                    if (kriteriaResponse.status) {
                        getKriteriaTugasKhusus("171111079", kriteriaResponse);
                    }
                }
            }
        });
    }

    public void getKriteriaTugasKhusus(String nrp, KriteriaResponse kriteriaResponse) {
        viewModel.getKriteriaTugasKhusus(
                nrp
        ).observe(this, new Observer<KriteriaTugasKhususResponse>() {
            @Override
            public void onChanged(KriteriaTugasKhususResponse kriteriaTugasKhususResponse) {
                if (kriteriaTugasKhususResponse != null) {
                    if (kriteriaTugasKhususResponse.status) {
                        boolean nilai = true;
                        for (KriteriaResponse.DetailKriteria kriteria : kriteriaResponse.data) {
                            int jumlah = 0;
                            for (KriteriaTugasKhususResponse.DetailKriteriaTugasKhusus kriteriaTugasKhusus : kriteriaTugasKhususResponse.data) {
                                if (kriteria.idJenis.equalsIgnoreCase(kriteriaTugasKhusus.idJenis)) {
                                    if (kriteria.idLingkup.equalsIgnoreCase("1")) {
                                        jumlah = jumlah + Integer.parseInt(kriteriaTugasKhusus.jumlah);
                                    } else {
                                        if (kriteria.idLingkup.equalsIgnoreCase(kriteriaTugasKhusus.idLingkup)) {
                                            jumlah = Integer.parseInt(kriteriaTugasKhusus.jumlah);
                                        }
                                    }
                                }
                            }

                            if (jumlah < Integer.parseInt(kriteria.jumlah)) {
                                nilai = false;
                                break;
                            }
                        }

                        if (nilai) {
                            Log.e("Nilai", kriteriaResponse.data.get(0).nilai);
                            binding.textViewNilai.setText(kriteriaResponse.data.get(0).nilai);
                        }
                    }
                }
            }
        });
    }
}