package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.api.response.KriteriaTugasKhususResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.api.response.TotalPoinResponse;
import com.android.poinku.databinding.ActivityMainBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    private int poin;
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.setCancelable(false);

        Picasso.get()
                .load(firebaseUser.getPhotoUrl())
                .into(binding.shapeableImageViewProfil);
        for (int i = 0, x = 0; i < firebaseUser.getDisplayName().length(); i++) {
            if (firebaseUser.getDisplayName().charAt(i) == ' ') {
                x++;
            }

            if (x == 3) {
                binding.textViewNama.setText(firebaseUser.getDisplayName().substring(10, i));
                break;
            } else if ((i + 1) == firebaseUser.getDisplayName().length() && (x == 0 || x == 1)) {
                binding.textViewNama.setText(firebaseUser.getDisplayName());
            }
        }

//        getMahasiswa();
//        getTotalPoin();

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.textViewAturan.setText("-");
                binding.textViewStatusValidasi.setText("-");
                binding.textViewTanggalValidasi.setText("-");
                binding.textViewTotalPoin.setText("0 Poin");
                binding.linearProgressIndicatorPoin.setProgress(0);
                binding.textViewNilai.setText("E");
                binding.materialButtonAjukanValidasi.setVisibility(View.GONE);

                getMahasiswa();
                getTotalPoin();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        binding.materialCardViewCatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValid) {
                    startActivity(new Intent(v.getContext(), CatatActivity.class));
                } else {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Pesan")
                            .setMessage("Anda tidak dapat mencatat kegiatan tugas khusus karena telah divalidasi atau sedang dalam proses validasi")
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
        });

        binding.materialCardViewKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), DaftarEventActivity.class));
            }
        });

        binding.materialCardViewInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), InformasiActivity.class));
            }
        });

        binding.materialCardViewRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), DaftarTugasKhususActivity.class));
            }
        });

        binding.materialButtonAjukanValidasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.textViewNilai.getText().toString().equalsIgnoreCase("E")) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Pesan")
                            .setMessage("Anda tidak dapat mengajukan validasi tugas khusus dikarenakan nilai yang anda dapatkan tidak mencukupi")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                } else {
                    progressDialog.show();
                    getIsValidasi();
                    isValid = true;
                }
            }
        });

        binding.shapeableImageViewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ProfileActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMahasiswa();
        getTotalPoin();
    }

    public void getMahasiswa() {
        viewModel.getMahasiswa(
                AppPreference.getUser(this).nrp
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
                        binding.textViewAturan.setText(mahasiswaResponse.data.tahun + " / " + mahasiswaResponse.data.keterangan);
                        binding.textViewTanggalValidasi.setText(mahasiswaResponse.data.tanggalValidasi != null ? mahasiswaResponse.data.tanggalValidasi : "-");

                        if (mahasiswaResponse.data.status != null) {
                            if (mahasiswaResponse.data.status.equalsIgnoreCase("1")) {
                                binding.textViewNilai.setText(mahasiswaResponse.data.nilai);
                            } else {
                                getNilai(mahasiswaResponse.data.idAturan);
                            }
                        } else {
                            getNilai(mahasiswaResponse.data.idAturan);
                        }

                        if (mahasiswaResponse.data.status != null) {
                            if (mahasiswaResponse.data.status.equalsIgnoreCase("1")) {
                                binding.textViewStatusValidasi.setText("Divalidasi");
                                binding.materialButtonAjukanValidasi.setVisibility(View.GONE);
                                isValid = true;
                            } else if (mahasiswaResponse.data.status.equalsIgnoreCase("2")) {
                                binding.textViewStatusValidasi.setText("Ditolak");
                                binding.materialButtonAjukanValidasi.setVisibility(View.VISIBLE);
                                isValid = false;
                            } else if (mahasiswaResponse.data.status.equalsIgnoreCase("0")) {
                                binding.textViewStatusValidasi.setText("Diproses");
                                binding.materialButtonAjukanValidasi.setVisibility(View.GONE);
                                isValid = true;
                            }
                        } else {
                            binding.materialButtonAjukanValidasi.setVisibility(View.VISIBLE);
                            binding.textViewStatusValidasi.setText("Belum Diajukan");
                            isValid = false;
                        }
                    }
                }
            }
        });
    }

    public void putPengajuanTugasKhusus() {
        viewModel.putPengajuanTugasKhusus(
                AppPreference.getUser(this).nrp,
                binding.textViewNilai.getText().toString()
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        alert(baseResponse.message);
//                        new AlertDialog.Builder(MainActivity.this)
//                                .setTitle("Pesan")
//                                .setMessage(baseResponse.message)
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .create()
//                                .show();
                    }
                }
            }
        });
    }

    public void getTotalPoin() {
        viewModel.getTotalPoin(
                AppPreference.getUser(this).nrp
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
                        List<NilaiResponse.DetailNilai> list = new ArrayList<>();
                        for (NilaiResponse.DetailNilai nilai : nilaiResponse.data) {
                            if (poin >= Integer.parseInt(nilai.poinMinimal)) {
                                list.add(nilai);
                            }
                        }
                        setNilai(list, 0);
                    }
                }
            }
        });
    }

    public void setNilai(List<NilaiResponse.DetailNilai> list, int index) {
        if (index < list.size()) {
            getKriteria(list, index);
        }
    }

    public void getKriteria(List<NilaiResponse.DetailNilai> list, int index) {
        viewModel.getKriteria(
                list.get(index).idNilai
        ).observe(this, new Observer<KriteriaResponse>() {
            @Override
            public void onChanged(KriteriaResponse kriteriaResponse) {
                if (kriteriaResponse != null) {
                    if (kriteriaResponse.status) {
                        getKriteriaTugasKhusus(list, index, AppPreference.getUser(MainActivity.this).nrp, kriteriaResponse);
                    }
                }
            }
        });
    }

    public void getKriteriaTugasKhusus(List<NilaiResponse.DetailNilai> list, int index, String nrp, KriteriaResponse kriteriaResponse) {
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
                        } else {
                            setNilai(list, (index + 1));
                        }
                    }
                }
            }
        });
    }

    public void getIsValidasi() {
        viewModel.getIsValidasi(
                AppPreference.getUser(this).nrp
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        putPengajuanTugasKhusus();
                        binding.materialButtonAjukanValidasi.setVisibility(View.GONE);
                        binding.textViewStatusValidasi.setText("Diproses");
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        alert(baseResponse.message);
                    }
                } else {
                    alert("Terjadi kesalahan pada server, silahkan coba lagi");
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