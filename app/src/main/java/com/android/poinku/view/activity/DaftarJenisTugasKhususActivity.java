package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.poinku.adapter.DaftarJenisTugasKhususAdapter;
import com.android.poinku.adapter.DaftarTugasKhususAdapter;
import com.android.poinku.api.response.DataPoinResponse;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.databinding.ActivityDaftarJenisTugasKhususBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.DaftarJenisTugasKhususViewModel;

public class DaftarJenisTugasKhususActivity extends AppCompatActivity {
    private ActivityDaftarJenisTugasKhususBinding binding;
    private DaftarJenisTugasKhususViewModel viewModel;

    private String idJenis, jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarJenisTugasKhususBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idJenis = getIntent().getStringExtra("ID_JENIS");
        jenis = getIntent().getStringExtra("JENIS");

        for (int i = 0, x = 0; i < jenis.length(); i++) {
            if (jenis.charAt(i) == ' ') {
                x++;
            }

            if (x == 2) {
                binding.textViewToolbar.setText(jenis.substring(0, i));
                break;
            } else if ((i + 1) == jenis.length() && (x == 0 || x == 1)) {
                binding.textViewToolbar.setText(jenis);
            }
        }

        viewModel = ViewModelProviders.of(this).get(DaftarJenisTugasKhususViewModel.class);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        binding.swiperRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shimmerFrameLayout.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                getDataPoin();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swiperRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onPause() {
        binding.shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.shimmerFrameLayout.startShimmer();
        getDataPoin();
    }

    public void getDataPoin() {
        binding.shimmerFrameLayout.startShimmer();
        viewModel.getDataPoin(
                AppPreference.getUser(this).nrp,
                idJenis
        ).observe(this, new Observer<DataPoinResponse>() {
            @Override
            public void onChanged(DataPoinResponse dataPoinResponse) {
                if (dataPoinResponse != null) {
                    if (dataPoinResponse.status) {
                        binding.recyclerView.setAdapter(new DaftarJenisTugasKhususAdapter(dataPoinResponse.data));

                        binding.shimmerFrameLayout.stopShimmer();
                        binding.shimmerFrameLayout.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}