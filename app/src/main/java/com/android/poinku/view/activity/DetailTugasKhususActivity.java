package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.poinku.R;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.DetailTugasKhususResponse;
import com.android.poinku.api.response.KegiatanResponse;
import com.android.poinku.api.response.KontenResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.databinding.ActivityDetailTugasKhususBinding;
import com.android.poinku.preference.AppPreference;
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

    private String idTugasKusus, idLingkup, idPeran;

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

        binding.materialButtonUbahKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UpdateTugasKhususActivity.class);
                intent.putExtra("ID_TUGAS_KHUSUS", idTugasKusus);
                intent.putExtra("ID_LINGKUP", idLingkup);
                intent.putExtra("ID_PERAN", idPeran);
                startActivity(intent);
            }
        });

        binding.materialButtonHapusKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Pesan")
                        .setMessage("Apakah anda ingin menghapus kegiatan ini?")
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                postDeleteTugasKhusus();
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

    @Override
    protected void onResume() {
        super.onResume();
        getDetailTugasKhusus();
    }

    public void getMahasiswa() {
        viewModel.getMahasiswa(
                AppPreference.getUser(this).nrp
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
                        if (mahasiswaResponse.data.status != null) {
                            if (mahasiswaResponse.data.status.equalsIgnoreCase("1")) {
                                binding.materialButtonUbahKegiatan.setVisibility(View.GONE);
                                binding.materialButtonHapusKegiatan.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }
        });
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

                        if (!detailTugasKhususResponse.data.idJenis.equalsIgnoreCase("14")) {
                            binding.linearLayoutBukanKonten.setVisibility(View.VISIBLE);
                            binding.linearLayoutKonten.setVisibility(View.GONE);

                            binding.editTextLingkup.setText(detailTugasKhususResponse.data.lingkup);
                            binding.editTextPeran.setText(detailTugasKhususResponse.data.peran);

                            if (detailTugasKhususResponse.data.bukti != null) {
                                if (detailTugasKhususResponse.data.bukti.contains(".pdf")) {
                                    binding.imageViewBuktiKegiatan.setVisibility(View.GONE);
                                    binding.webView.setVisibility(View.VISIBLE);
                                    binding.materialButtonUbahKegiatan.setVisibility(View.GONE);
                                    String newUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + detailTugasKhususResponse.data.bukti;
                                    pdfView(newUrl);
                                } else {
                                    binding.imageViewBuktiKegiatan.setVisibility(View.VISIBLE);
                                    binding.webView.setVisibility(View.GONE);
                                    binding.materialButtonUbahKegiatan.setVisibility(View.VISIBLE);
                                    Picasso.get()
                                            .load(detailTugasKhususResponse.data.bukti)
                                            .placeholder(R.drawable.no_image)
                                            .error(R.drawable.no_image)
                                            .into(binding.imageViewBuktiKegiatan);
                                }
                            }

                            getKegiatan();
                        } else {
                            binding.linearLayoutBukanKonten.setVisibility(View.GONE);
                            binding.linearLayoutKonten.setVisibility(View.VISIBLE);

                            binding.editTextLinkKonten.setText(detailTugasKhususResponse.data.bukti);

                            getKonten();
                        }

                        idLingkup = detailTugasKhususResponse.data.idLingkup;
                        idPeran = detailTugasKhususResponse.data.idPeran;
                        
                        getMahasiswa();
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

    public void postDeleteTugasKhusus() {
        viewModel.postDeleteTugasKhusus(
                idTugasKusus
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        new AlertDialog.Builder(DetailTugasKhususActivity.this)
                                .setTitle("Pesan")
                                .setMessage(baseResponse.message)
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                })
                                .create()
                                .show();
                    }
                }
            }
        });
    }

    private void pdfView(String responseURL) {
        binding.webView.invalidate();
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        binding.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
//        binding.webView.getSettings().setUseWideViewPort(true);
//        String newUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + responseURL;
        binding.webView.loadUrl(responseURL);
        binding.webView.setWebChromeClient(new WebChromeClient());

        binding.webView.setWebViewClient(new WebViewClient() {
            boolean checkHasOnPageStarted = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkHasOnPageStarted = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (checkHasOnPageStarted) {
//                    binding.webView.loadUrl(url);
                } else {
                    pdfView(responseURL);
                }
            }
        });
    }
}