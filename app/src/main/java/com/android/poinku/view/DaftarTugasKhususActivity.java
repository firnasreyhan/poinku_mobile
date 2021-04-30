package com.android.poinku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.poinku.adapter.DaftarTugasKhususAdapter;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.databinding.ActivityDaftarTugasKhususBinding;
import com.android.poinku.viewmodel.DaftarTugasKhususViewModel;

public class DaftarTugasKhususActivity extends AppCompatActivity {
    private ActivityDaftarTugasKhususBinding binding;
    private DaftarTugasKhususViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarTugasKhususBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(DaftarTugasKhususViewModel.class);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        getJenisTugasKhusus();

        binding.swiperRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shimmerFrameLayout.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.linearLayoutDataKosong.setVisibility(View.GONE);
                getJenisTugasKhusus();
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
    }

    public void getJenisTugasKhusus() {
        binding.shimmerFrameLayout.startShimmer();
        viewModel.getJenisTugasKhusus(
                "171111079"
        ).observe(this, new Observer<JenisTugasKhususResponse>() {
            @Override
            public void onChanged(JenisTugasKhususResponse jenisTugasKhususResponse) {
                if (jenisTugasKhususResponse != null) {
                    if (jenisTugasKhususResponse.status) {
                        binding.recyclerView.setAdapter(new DaftarTugasKhususAdapter(jenisTugasKhususResponse.data));

                        binding.shimmerFrameLayout.stopShimmer();
                        binding.shimmerFrameLayout.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                        binding.linearLayoutDataKosong.setVisibility(View.GONE);
                    } else {
                        binding.shimmerFrameLayout.stopShimmer();
                        binding.shimmerFrameLayout.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.linearLayoutDataKosong.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}