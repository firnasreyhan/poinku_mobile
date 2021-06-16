package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.android.poinku.R;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.databinding.ActivityKuesionerBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.DetailEventViewModel;

public class KuesionerActivity extends AppCompatActivity {
    private ActivityKuesionerBinding binding;
    private DetailEventViewModel viewModel;
    private ProgressDialog progressDialog;

    private String idEvent;
    private int soal1 = 3, soal2 = 3, soal3 = 2, soal4 = 2, soal5= 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKuesionerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idEvent = getIntent().getStringExtra("ID_EVENT");
        viewModel = ViewModelProviders.of(this).get(DetailEventViewModel.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.setCancelable(false);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton11:
                        soal1 = 5;
                        break;
                    case R.id.radioButton12:
                        soal1 = 4;
                        break;
                    case R.id.radioButton13:
                        soal1 = 3;
                        break;
                    case R.id.radioButton14:
                        soal1 = 2;
                        break;
                    case R.id.radioButton15:
                        soal1 = 1;
                        break;
                }
            }
        });

        binding.radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton21:
                        soal2 = 5;
                        break;
                    case R.id.radioButton22:
                        soal2 = 4;
                        break;
                    case R.id.radioButton23:
                        soal2 = 3;
                        break;
                    case R.id.radioButton24:
                        soal2 = 2;
                        break;
                    case R.id.radioButton25:
                        soal2 = 1;
                        break;
                }
            }
        });

        binding.radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton31:
                        soal3 = 3;
                        break;
                    case R.id.radioButton32:
                        soal3 = 2;
                        break;
                    case R.id.radioButton33:
                        soal3 = 1;
                        break;
                }
            }
        });

        binding.radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton41:
                        soal4 = 3;
                        break;
                    case R.id.radioButton42:
                        soal4 = 2;
                        break;
                    case R.id.radioButton43:
                        soal4 = 1;
                        break;
                }
            }
        });

        binding.radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton51:
                        soal5 = 5;
                        break;
                    case R.id.radioButton52:
                        soal5 = 4;
                        break;
                    case R.id.radioButton53:
                        soal5 = 3;
                        break;
                    case R.id.radioButton54:
                        soal5 = 2;
                        break;
                    case R.id.radioButton55:
                        soal5 = 1;
                        break;
                }
            }
        });

        binding.materialButtonKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                postKuesioner();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void postKuesioner() {
        viewModel.postKuesioner(
                AppPreference.getUser(this).email,
                idEvent,
                soal1,
                soal2,
                soal3,
                soal4,
                soal5,
                binding.editTextSaran.getText().toString().isEmpty() ? "-" : binding.editTextSaran.getText().toString()
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (baseResponse.status) {
                        putAbsensi();
                    } else {
                        alert("Gagal melakukan kuesioner, silahkan coba lagi");
                    }
                }
            }
        });
    }

    public void putAbsensi() {
        viewModel.putAbsensi(
                AppPreference.getUser(this).nrp,
                AppPreference.getUser(this).email,
                idEvent
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Intent intent = new Intent(KuesionerActivity.this, StatusAbsenActivity.class);
                    intent.putExtra("STATUS", baseResponse.status);
                    startActivity(intent);
                    finish();
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