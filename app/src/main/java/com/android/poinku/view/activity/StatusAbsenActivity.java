package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.databinding.ActivityStatusAbsenBinding;

public class StatusAbsenActivity extends AppCompatActivity {
    private ActivityStatusAbsenBinding binding;

    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusAbsenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        status = getIntent().getBooleanExtra("STATUS", false);

        if (status) {
            binding.imageViewStatusAbsen.setImageResource(R.drawable.ic_success);
            binding.textViewStatusAbsen.setText("Sukses Absen");
        } else {
            binding.imageViewStatusAbsen.setImageResource(R.drawable.ic_failure);
            binding.textViewStatusAbsen.setText("Gagal Absen");
        }

        binding.materialButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}