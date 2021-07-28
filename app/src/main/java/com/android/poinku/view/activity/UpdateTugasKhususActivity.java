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
import com.android.poinku.api.response.DetailTugasKhususResponse;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.KegiatanResponse;
import com.android.poinku.api.response.KontenResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.api.response.TugasKhususResponse;
import com.android.poinku.databinding.ActivityUpdateTugasKhususBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.CatatViewModel;
import com.android.poinku.viewmodel.UpdateTugasKhususViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateTugasKhususActivity extends AppCompatActivity {
    private ActivityUpdateTugasKhususBinding binding;
    private UpdateTugasKhususViewModel viewModel;
    private ProgressDialog progressDialog;

    private String tanggal, jenis, namaJenis, peran, lingkup, mediaK, jenisK, idTugasKusus, idLingkup, idPeran;
    private boolean isKonten;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTugasKhususBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idTugasKusus = getIntent().getStringExtra("ID_TUGAS_KHUSUS");
        idLingkup = getIntent().getStringExtra("ID_LINGKUP");
        idPeran = getIntent().getStringExtra("ID_PERAN");
        viewModel = ViewModelProviders.of(this).get(UpdateTugasKhususViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.setCancelable(false);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        getJenis();
//        getLingkup();
//        getPeran();

//        getDetailTugasKhusus();
        getMahasiwa();
        setKonten("Blog", "Artikel");

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
                ImagePicker.Companion.with(UpdateTugasKhususActivity.this)
                        .crop()
                        .compress(1024)
                        .start();
            }
        });

        binding.materialButtonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData()) {
                    postUpdateTugasKhusus();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getDetailTugasKhusus();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//            binding.imageViewBuktiKegiatan.setImageURI(data.getData());
            Picasso.get()
                    .load(data.getData())
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(binding.imageViewBuktiKegiatan);
            uri = data.getData();
        }
    }

    public void getMahasiwa() {
        viewModel.getMahasiswa(
                AppPreference.getUser(this).nrp
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
//                        getJenis(mahasiswaResponse.data.idAturan, mahasiswaResponse.data.idAturan);
                        getDetailTugasKhusus(mahasiswaResponse.data.idAturan);
                    }
                }
            }
        });
    }

    public void getDetailTugasKhusus(String idAturan) {
        viewModel.getDetailTugasKhusus(
                idTugasKusus
        ).observe(this, new Observer<DetailTugasKhususResponse>() {
            @Override
            public void onChanged(DetailTugasKhususResponse detailTugasKhususResponse) {
                if (detailTugasKhususResponse != null) {
                    if (detailTugasKhususResponse.status) {
                        tanggal = detailTugasKhususResponse.data.tanggalKegiatan;
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

                        getJenis(idAturan, detailTugasKhususResponse.data.idJenis);
//                        binding.editTextJenis.setText(detailTugasKhususResponse.data.jenis);

                        if (!detailTugasKhususResponse.data.idJenis.equalsIgnoreCase("14")) {
                            binding.linearLayoutBukanKonten.setVisibility(View.VISIBLE);
                            binding.linearLayoutKonten.setVisibility(View.GONE);

                            getLingkup(idAturan, detailTugasKhususResponse.data.idJenis, detailTugasKhususResponse.data.idLingkup);
                            getPeran(idAturan, detailTugasKhususResponse.data.idJenis, detailTugasKhususResponse.data.idLingkup, detailTugasKhususResponse.data.idPeran);
//                            binding.editTextLingkup.setText(detailTugasKhususResponse.data.lingkup);
//                            binding.editTextPeran.setText(detailTugasKhususResponse.data.peran);

                            Picasso.get()
                                    .load(detailTugasKhususResponse.data.bukti)
                                    .placeholder(R.drawable.no_image)
                                    .error(R.drawable.no_image)
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
//                        binding.editTextJenisKonten.setText(kontenResponse.data.jenisKonten);
//                        binding.editTextMediaKonten.setText(kontenResponse.data.mediaKonten);
                        setKonten(kontenResponse.data.mediaKonten, kontenResponse.data.jenisKonten);
                    }
                }
            }
        });
    }

    public void getJenis(String idAturan, String idJenis) {
        viewModel.getJenis(
                idAturan
        ).observe(this, new Observer<JenisResponse>() {
            @Override
            public void onChanged(JenisResponse jenisResponse) {
                if (jenisResponse != null) {
                    if (jenisResponse.status) {
                        ArrayAdapter<JenisResponse.JenisModel> adapter = new ArrayAdapter<>(UpdateTugasKhususActivity.this, R.layout.item_spinner, jenisResponse.data);
                        binding.spinnerJenis.setAdapter(adapter);

                        binding.spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (jenisResponse.data.get(binding.spinnerJenis.getSelectedItemPosition()).idJenis.equalsIgnoreCase("14")) {
                                    binding.linearLayoutBukanKonten.setVisibility(View.GONE);
                                    binding.linearLayoutKonten.setVisibility(View.VISIBLE);

                                    isKonten = true;
                                } else {
                                    binding.linearLayoutBukanKonten.setVisibility(View.VISIBLE);
                                    binding.linearLayoutKonten.setVisibility(View.GONE);

                                    isKonten = false;

                                    getLingkup(idAturan, jenisResponse.data.get(position).idJenis, idLingkup);
                                }

                                jenis = jenisResponse.data.get(position).idJenis;
                                namaJenis = jenisResponse.data.get(position).jenis;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        Log.e("sIdjenis", String.valueOf(idJenis));
                        for (int i = 0; i < jenisResponse.data.size(); i++) {
                            Log.e("posisiIdJenis", jenisResponse.data.get(i).idJenis);
                            if (jenisResponse.data.get(i).idJenis.equalsIgnoreCase(idJenis)) {
                                binding.spinnerJenis.setSelection(i);
                                Log.e("posisiIdJenis", String.valueOf(i));
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    public void getLingkup(String idAturan, String idJenis, String idLingkup) {
        viewModel.getLingkup(
                idAturan,
                idJenis
        ).observe(this, new Observer<LingkupResponse>() {
            @Override
            public void onChanged(LingkupResponse lingkupResponse) {
                if (lingkupResponse != null) {
                    if (lingkupResponse.status) {
                        ArrayAdapter<LingkupResponse.LingkupModel> adapter = new ArrayAdapter<>(UpdateTugasKhususActivity.this, R.layout.item_spinner, lingkupResponse.data);
                        binding.spinnerLingkup.setAdapter(adapter);

                        for (int i = 0; i < lingkupResponse.data.size(); i++) {
                            if (lingkupResponse.data.get(i).idLingkup.equalsIgnoreCase(idLingkup)) {
                                binding.spinnerLingkup.setSelection(i);
                                break;
                            }
                        }

                        binding.spinnerLingkup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                lingkup = lingkupResponse.data.get(position).idLingkup;

                                getPeran(idAturan, idJenis, idLingkup, idPeran);
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

    public void getPeran(String idAturan, String idJenis, String idLingkup, String idPeran) {
        viewModel.getPeran(
                idAturan,
                idJenis,
                idLingkup
        ).observe(this, new Observer<PeranResponse>() {
            @Override
            public void onChanged(PeranResponse peranResponse) {
                if (peranResponse != null) {
                    if (peranResponse.status) {
                        ArrayAdapter<PeranResponse.PeranModel> adapter = new ArrayAdapter<>(UpdateTugasKhususActivity.this, R.layout.item_spinner, peranResponse.data);
                        binding.spinnerPeran.setAdapter(adapter);

                        for (int i = 0; i < peranResponse.data.size(); i++) {
                            if (peranResponse.data.get(i).idPeran.equalsIgnoreCase(idPeran)) {
                                binding.spinnerPeran.setSelection(i);
                                break;
                            }
                        }

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

    public void setKonten(String sMediaKonten, String sJenisKonten) {
        String[] mediaKonten = {"Blog", "Youtube", "Instagram", "Web Prodi / STIKI"};
        String[] jenisKonten = {"Artikel", "Video"};

        ArrayAdapter<String> adapterMedia = new ArrayAdapter<>(this, R.layout.item_spinner, mediaKonten);
        ArrayAdapter<String> adapterJenis = new ArrayAdapter<>(this, R.layout.item_spinner, jenisKonten);

        binding.spinnerMediaKonten.setAdapter(adapterMedia);
        binding.spinnerJenisKonten.setAdapter(adapterJenis);

        for (int i = 0; i < mediaKonten.length; i++) {
            if (mediaKonten[i].equalsIgnoreCase(sMediaKonten)) {
                binding.spinnerMediaKonten.setSelection(i);
            }
        }

        for (int i = 0; i < jenisKonten.length; i++) {
            if (jenisKonten[i].equalsIgnoreCase(sJenisKonten)) {
                binding.spinnerJenisKonten.setSelection(i);
            }
        }

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

    public void postUpdateTugasKhusus() {
        progressDialog.show();
        viewModel.postUpdateTugasKhusus(
                idTugasKusus,
                jenis,
                isKonten ? "1" : lingkup,
                isKonten ? "1" : peran,
                binding.editTextJudul.getText().toString(),
                tanggal
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        if (isKonten) {
                            postBuktiKonten();
                            postUpdateKonten();
                        } else {
                            if (uri != null) {
                                postBuktiKegiatan();
                            }
                            postUpdateKegiatan();
                        }
                    }
                }
            }
        });
    }

    public void postUpdateKonten() {
        viewModel.postUpdateKonten(
                idTugasKusus,
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

    public void postUpdateKegiatan() {
        viewModel.postUpdateKegiatan(
                idTugasKusus,
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

    public void postBuktiKonten() {
        viewModel.postBuktiKonten(
                idTugasKusus,
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

    public void postBuktiKegiatan() {
        viewModel.postBuktiKegiatan(
                idTugasKusus,
                AppPreference.getUser(this).nrp,
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
                })
                .create()
                .show();
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

//            if (uri == null) {
//                Toast.makeText(this, "Mohon upload bukti kegiatan", Toast.LENGTH_SHORT).show();
//                check5 = false;
//            }

            return check1 && check2 && check4 && check5;
        }
    }
}