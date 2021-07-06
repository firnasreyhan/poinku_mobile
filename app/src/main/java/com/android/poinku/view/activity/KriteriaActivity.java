package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.poinku.adapter.KriteriaAdapter;
import com.android.poinku.adapter.PoinAdapter;
import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.databinding.ActivityKriteriaBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.KriteriaViewModel;

public class KriteriaActivity extends AppCompatActivity {
    private ActivityKriteriaBinding binding;
    private KriteriaViewModel viewModel;

    private String idNilai, nilai, poinMinimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKriteriaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        idNilai = getIntent().getStringExtra("ID_NILAI");
        nilai = getIntent().getStringExtra("NILAI");
        poinMinimal = getIntent().getStringExtra("POIN_MINIMAL");

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        viewModel = ViewModelProviders.of(this).get(KriteriaViewModel.class);

        binding.textViewNilai.setText(nilai);
        binding.textViewPoinMinimal.setText(poinMinimal + " Poin");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        getKriteria();

        binding.swiperRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shimmerFrameLayout.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.linearLayoutDataKosong.setVisibility(View.GONE);
                getKriteria();
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

    public void getKriteria() {
        binding.shimmerFrameLayout.startShimmer();
        viewModel.getKriteria(
                idNilai
        ).observe(this, new Observer<KriteriaResponse>() {
            @Override
            public void onChanged(KriteriaResponse kriteriaResponse) {
                if (kriteriaResponse != null) {
                    if (kriteriaResponse.status) {
                        binding.recyclerView.setAdapter(new KriteriaAdapter(kriteriaResponse.data));

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