package com.android.poinku.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.adapter.DaftarTugasKhususAdapter;
import com.android.poinku.adapter.EventAdapter;
import com.android.poinku.adapter.TabAdapter;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.databinding.ActivityDaftarEventBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.view.fragment.EventServerFragment;
import com.android.poinku.view.fragment.EventUserFragment;
import com.android.poinku.view.fragment.NilaiFragment;
import com.android.poinku.view.fragment.PoinFragment;
import com.android.poinku.viewmodel.DaftarEventViewModel;
import com.google.android.material.tabs.TabLayout;

public class DaftarEventActivity extends AppCompatActivity {
    private ActivityDaftarEventBinding binding;
    private DaftarEventViewModel viewModel;

    private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabAdapter = new TabAdapter(getSupportFragmentManager(), this);
        tabAdapter.addFragment(new EventServerFragment(), "Semua", R.drawable.ic_circle);
        tabAdapter.addFragment(new EventUserFragment(), "Terdaftar", R.drawable.ic_circle);

        binding.viewPager.setAdapter(tabAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        highLightCurrentTab(0);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        viewModel = ViewModelProviders.of(this).get(DaftarEventViewModel.class);
//
//        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        binding.recyclerView.setHasFixedSize(true);
//
//        getEvent();
//
//        binding.swiperRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                binding.shimmerFrameLayout.setVisibility(View.VISIBLE);
//                binding.recyclerView.setVisibility(View.GONE);
//                binding.linearLayoutDataKosong.setVisibility(View.GONE);
//                getEvent();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        binding.swiperRefreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(tabAdapter.getTabView(i));
        }
        TabLayout.Tab tab = binding.tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(tabAdapter.getSelectedWhiteTabView(position));
    }

//    @Override
//    protected void onPause() {
//        binding.shimmerFrameLayout.stopShimmer();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        binding.shimmerFrameLayout.startShimmer();
//    }
//
//    public void getEvent() {
//        binding.shimmerFrameLayout.startShimmer();
//        viewModel.getEvent().observe(this, new Observer<EventResponse>() {
//            @Override
//            public void onChanged(EventResponse eventResponse) {
//                if (eventResponse != null) {
//                    if (eventResponse.status) {
//                        binding.recyclerView.setAdapter(new EventAdapter(eventResponse.data));
//
//                        binding.shimmerFrameLayout.stopShimmer();
//                        binding.shimmerFrameLayout.setVisibility(View.GONE);
//                        binding.recyclerView.setVisibility(View.VISIBLE);
//                        binding.linearLayoutDataKosong.setVisibility(View.GONE);
//                    } else {
//                        binding.shimmerFrameLayout.stopShimmer();
//                        binding.shimmerFrameLayout.setVisibility(View.GONE);
//                        binding.recyclerView.setVisibility(View.GONE);
//                        binding.linearLayoutDataKosong.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });
//    }
}