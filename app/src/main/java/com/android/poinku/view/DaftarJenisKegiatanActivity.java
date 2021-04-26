package com.android.poinku.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.poinku.databinding.ActivityDaftarJenisKegiatanBinding;

public class DaftarJenisKegiatanActivity extends AppCompatActivity {
    private ActivityDaftarJenisKegiatanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarJenisKegiatanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}