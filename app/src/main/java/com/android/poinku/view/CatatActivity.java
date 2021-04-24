package com.android.poinku.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.android.poinku.R;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.databinding.ActivityCatatBinding;
import com.android.poinku.viewmodel.CatatViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.ImagePickerActivity;
import com.github.dhaval2404.imagepicker.ImagePickerFileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CatatActivity extends AppCompatActivity {
    private ActivityCatatBinding binding;
    private CatatViewModel viewModel;
    private String tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(CatatViewModel.class);

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
                                } else {
                                    binding.linearLayoutBukanKonten.setVisibility(View.VISIBLE);
                                    binding.linearLayoutKonten.setVisibility(View.GONE);
                                }
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
    }
}