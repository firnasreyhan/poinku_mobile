package com.android.poinku.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.poinku.R;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.api.response.TugasKhususResponse;
import com.android.poinku.databinding.ActivityCatatBinding;
import com.android.poinku.viewmodel.CatatViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CatatActivity extends AppCompatActivity {
    private ActivityCatatBinding binding;
    private CatatViewModel viewModel;
    private ProgressDialog progressDialog;

    private String tanggal, jenis, namaJenis, peran, lingkup, mediaK, jenisK;
    private boolean isKonten;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(CatatViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.setCancelable(false);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getJenis();
        getLingkup();
        getPeran();
        setKonten();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat simpleDateFormatServer = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                binding.editTextTanggalKegiatan.setText(simpleDateFormatView.format(calendar.getTime()));
                tanggal = simpleDateFormatServer.format(calendar.getTime());
            }
        };

        binding.editTextTanggalKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.imageViewBuktiKegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(CatatActivity.this)
                        .crop()
                        .compress(1024)
                        .start();
            }
        });

        binding.materialButtonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    postTugasKhusus();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            binding.imageViewBuktiKegiatan.setImageURI(data.getData());
            uri = data.getData();
        }
    }

    public void getJenis() {
        viewModel.getJenis().observe(this, new Observer<JenisResponse>() {
            @Override
            public void onChanged(JenisResponse jenisResponse) {
                if (jenisResponse != null) {
                    if (jenisResponse.status) {
                        ArrayAdapter<JenisResponse.JenisModel> adapter = new ArrayAdapter<>(CatatActivity.this, R.layout.item_spinner, jenisResponse.data);
                        binding.spinnerJenis.setAdapter(adapter);

                        binding.spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (jenisResponse.data.get(binding.spinnerJenis.getSelectedItemPosition()).idJenis.equalsIgnoreCase("15")) {
                                    binding.linearLayoutBukanKonten.setVisibility(View.GONE);
                                    binding.linearLayoutKonten.setVisibility(View.VISIBLE);

                                    isKonten = true;
                                } else {
                                    binding.linearLayoutBukanKonten.setVisibility(View.VISIBLE);
                                    binding.linearLayoutKonten.setVisibility(View.GONE);

                                    isKonten = false;
                                }

                                jenis = jenisResponse.data.get(position).idJenis;
                                namaJenis = jenisResponse.data.get(position).jenis;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }
        });
    }

    public void getLingkup() {
        viewModel.getLingkup().observe(this, new Observer<LingkupResponse>() {
            @Override
            public void onChanged(LingkupResponse lingkupResponse) {
                if (lingkupResponse != null) {
                    if (lingkupResponse.status) {
                        ArrayAdapter<LingkupResponse.LingkupModel> adapter = new ArrayAdapter<>(CatatActivity.this, R.layout.item_spinner, lingkupResponse.data);
                        binding.spinnerLingkup.setAdapter(adapter);

                        binding.spinnerLingkup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                lingkup = lingkupResponse.data.get(position).idLingkup;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }
        });
    }

    public void getPeran() {
        viewModel.getPeran().observe(this, new Observer<PeranResponse>() {
            @Override
            public void onChanged(PeranResponse peranResponse) {
                if (peranResponse != null) {
                    if (peranResponse.status) {
                        ArrayAdapter<PeranResponse.PeranModel> adapter = new ArrayAdapter<>(CatatActivity.this, R.layout.item_spinner, peranResponse.data);
                        binding.spinnerPeran.setAdapter(adapter);

                        binding.spinnerPeran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                peran = peranResponse.data.get(position).idPeran;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }
        });
    }

    public void setKonten() {
        String[] mediaKonten = {"Blog", "Youtube", "Instagram", "Web Prodi / STIKI"};
        String[] jenisKonten = {"Artikel", "Video"};

        ArrayAdapter<String> adapterMedia = new ArrayAdapter<>(this, R.layout.item_spinner, mediaKonten);
        ArrayAdapter<String> adapterJenis = new ArrayAdapter<>(this, R.layout.item_spinner, jenisKonten);

        binding.spinnerMediaKonten.setAdapter(adapterMedia);
        binding.spinnerJenisKonten.setAdapter(adapterJenis);

        binding.spinnerMediaKonten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mediaK = mediaKonten[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerJenisKonten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisK = jenisKonten[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void postTugasKhusus() {
        progressDialog.show();
        viewModel.postTugasKhusus(
                "171111079",
                jenis,
                isKonten ? "1" : lingkup,
                isKonten ? "1" : peran,
                binding.editTextJudul.getText().toString(),
                tanggal
        ).observe(this, new Observer<TugasKhususResponse>() {
            @Override
            public void onChanged(TugasKhususResponse tugasKhususResponse) {
                if (tugasKhususResponse != null) {
                    if (tugasKhususResponse.status) {
                        if (isKonten) {
                            postBuktiKonten(tugasKhususResponse.idTugasKhusus);
                            postKonten(tugasKhususResponse.idTugasKhusus);
                        } else {
                            postBuktiKegiatan(tugasKhususResponse.idTugasKhusus);
                            postKegiatan(tugasKhususResponse.idTugasKhusus);
                        }
                    }
                }
            }
        });
    }

    public void postKonten(String idTugasKhusus) {
        viewModel.postKonten(
                idTugasKhusus,
                mediaK,
                jenisK
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        alert();
                    }
                }
            }
        });
    }

    public void postKegiatan(String idTugasKhusus) {
        viewModel.postKegiatan(
                idTugasKhusus,
                binding.editTextPenyelenggaraPembicara.getText().toString()
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        alert();
                    }
                }
            }
        });
    }

    public void postBuktiKonten(String idTugasKhusus) {
        viewModel.postBuktiKonten(
                idTugasKhusus,
                binding.editTextLinkKonten.getText().toString()
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        Log.e("postBuktiKonten", String.valueOf(baseResponse.message));
                    }
                }
            }
        });
    }

    public void postBuktiKegiatan(String idTugasKhusus) {
        viewModel.postBuktiKegiatan(
                idTugasKhusus,
                "171111079",
                jenis,
                uri
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        Log.e("postBuktiKegiatan", String.valueOf(baseResponse.message));
                    }
                }
            }
        });
    }

    public void alert() {
        new AlertDialog.Builder(this)
                .setTitle("Pesan")
                .setMessage("Data berhasil disimpan")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

    public boolean checkData() {
        boolean check1 = true;
        boolean check2 = true;
        boolean check3 = true;
        boolean check4 = true;
        boolean check5 = true;

        if (binding.editTextJudul.getText().toString().isEmpty()) {
            binding.editTextJudul.setError("Mohon isi data berikut");
            check1 = false;
        }

        if (binding.editTextTanggalKegiatan.getText().toString().isEmpty()) {
            binding.editTextTanggalKegiatan.setError("Mohon isi data berikut");
            check2 = false;
        }

        if (isKonten) {
            if (binding.editTextLinkKonten.getText().toString().isEmpty()) {
                binding.editTextLinkKonten.setError("Mohon isi data berikut");
                check3 = false;
            }

            return check1 && check2 && check3;
        } else {
            if (binding.editTextPenyelenggaraPembicara.getText().toString().isEmpty()) {
                binding.editTextPenyelenggaraPembicara.setError("Mohon isi data berikut");
                check4 = false;
            }

            if (uri == null) {
                Toast.makeText(this, "Mohon upload bukti kegiatan", Toast.LENGTH_SHORT).show();
                check5 = false;
            }

            return check1 && check2 && check4 && check5;
        }
    }
}