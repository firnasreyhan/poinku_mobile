package com.android.poinku.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.api.response.PresensiResponse;
import com.android.poinku.api.response.TugasKhususResponse;
import com.android.poinku.databinding.ActivityDetailEventBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.DetailEventViewModel;
import com.blikoon.qrcodescanner.QrCodeActivity;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailEventActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QR_SCAN = 101;

    private ActivityDetailEventBinding binding;
    private DetailEventViewModel viewModel;
    private ProgressDialog progressDialog;

    private String idEvent, url;
    private boolean isKuotaFull;

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

        binding.materialButtonDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isKuotaFull) {
                    alert("Kuota pendaftar telah terpenuhi, silahkan mendaftar event lain");
                } else {
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

        binding.materialButtonAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailEventActivity.this, QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_QR_SCAN);
            }
        });

        binding.materialButtonLihatSertifikat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SertifikatActivity.class);
                intent.putExtra("URL", url);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetailEvent();
        getPresesni();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK) {
            Log.e("errorQR","COULD NOT GET A GOOD RESULT.");
            if(data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null) {
                AlertDialog alertDialog = new AlertDialog.Builder(DetailEventActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;
        }

        if(requestCode == REQUEST_CODE_QR_SCAN) {
            if(data == null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d("successQR","Have scan result in your app activity :"+ result);
            putAbsensi(result);
//            AlertDialog alertDialog = new AlertDialog.Builder(DetailEventActivity.this).create();
//            alertDialog.setTitle("Scan result");
//            alertDialog.setMessage(result);
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();

        }
    }

    public void postDaftarEvent() {
        progressDialog.show();
        viewModel.postDaftarEvent(
                AppPreference.getUser(this).email,
                AppPreference.getUser(this).nama,
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

                        if (Integer.parseInt(event.pendaftar) < Integer.parseInt(event.kuota)) {
                            isKuotaFull = false;
                        } else {
                            isKuotaFull = true;
                        }
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
                        if (presensiResponse.data.status.equalsIgnoreCase("0")) {
                            binding.materialButtonDaftar.setVisibility(View.GONE);
                            binding.linearLayoutDaftar.setVisibility(View.VISIBLE);
                            binding.materialButtonLihatSertifikat.setVisibility(View.GONE);
                        } else {
                            if (presensiResponse.data.sertifikat == null) {
                                binding.materialButtonDaftar.setVisibility(View.GONE);
                                binding.linearLayoutDaftar.setVisibility(View.GONE);
                                binding.materialButtonLihatSertifikat.setVisibility(View.GONE);
                            } else {
                                binding.materialButtonDaftar.setVisibility(View.GONE);
                                binding.linearLayoutDaftar.setVisibility(View.GONE);
                                binding.materialButtonLihatSertifikat.setVisibility(View.VISIBLE);

                                url = presensiResponse.data.sertifikat;
                            }
                        }
                    } else {
                        binding.materialButtonDaftar.setVisibility(View.VISIBLE);
                        binding.linearLayoutDaftar.setVisibility(View.GONE);
                        binding.materialButtonLihatSertifikat.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    public void putAbsensi(String link) {
        String pattern = "https://poinku.my.id/kuesionerKegiatan/";
        String idEvent = link.substring(pattern.length());

        if (this.idEvent.equalsIgnoreCase(idEvent)) {
            Intent intent = new Intent(this, KuesionerActivity.class);
            intent.putExtra("ID_EVENT", idEvent);
            startActivity(intent);
        } else {
            alert("QR Code tidak sesuai, silahkan coba lagi");
        }
//        viewModel.putAbsensi(
//                AppPreference.getUser(this).nrp,
//                AppPreference.getUser(this).email,
//                idEvent
//        ).observe(this, new Observer<BaseResponse>() {
//            @Override
//            public void onChanged(BaseResponse baseResponse) {
//                if (baseResponse != null) {
//                    Intent intent = new Intent(DetailEventActivity.this, StatusAbsenActivity.class);
//                    intent.putExtra("STATUS", baseResponse.status);
//                    startActivity(intent);
//                }
//            }
//        });
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