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

import com.android.poinku.adapter.EventAdapter;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.databinding.FragmentEventServerBinding;
import com.android.poinku.viewmodel.DaftarEventViewModel;

public class EventServerFragment extends Fragment {
    private FragmentEventServerBinding binding;
    private DaftarEventViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventServerBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this).get(DaftarEventViewModel.class);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);

        getEvent();

        binding.swiperRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.shimmerFrameLayout.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                binding.linearLayoutDataKosong.setVisibility(View.GONE);
                getEvent();
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

    public void getEvent() {
        binding.shimmerFrameLayout.startShimmer();
        viewModel.getEvent().observe(getActivity(), new Observer<EventResponse>() {
            @Override
            public void onChanged(EventResponse eventResponse) {
                if (eventResponse != null) {
                    if (eventResponse.status) {
                        binding.recyclerView.setAdapter(new EventAdapter(eventResponse.data));

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