package com.android.poinku.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.poinku.R;
import com.android.poinku.adapter.DaftarTugasKhususAdapter;
import com.android.poinku.adapter.NilaiAdapter;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.databinding.FragmentNilaiBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.InformasiViewModel;

public class NilaiFragment extends Fragment {
    private FragmentNilaiBinding binding;
    private InformasiViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNilaiBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this).get(InformasiViewModel.class);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);

        getNilai();

        binding.swiperRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shimmerFrameLayout.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.linearLayoutDataKosong.setVisibility(View.GONE);
                getNilai();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swiperRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        binding.shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.shimmerFrameLayout.startShimmer();
    }

    public void getNilai() {
        binding.shimmerFrameLayout.startShimmer();
        viewModel.getNilai(
                AppPreference.getUser(getContext()).idAturan
        ).observe(getActivity(), new Observer<NilaiResponse>() {
            @Override
            public void onChanged(NilaiResponse nilaiResponse) {
                if (nilaiResponse != null) {
                    if (nilaiResponse.status) {
                        binding.recyclerView.setAdapter(new NilaiAdapter(nilaiResponse.data));

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