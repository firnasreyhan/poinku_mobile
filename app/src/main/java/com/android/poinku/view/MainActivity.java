package com.android.poinku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.poinku.R;
import com.android.poinku.api.response.TotalPoinResponse;
import com.android.poinku.databinding.ActivityMainBinding;
import com.android.poinku.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        binding.cardViewCatat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), CatatActivity.class));
            }
        });
    }

    public void getTotalPoin() {
        viewModel.getTotalPoin(
                "171111079"
        ).observe(this, new Observer<TotalPoinResponse>() {
            @Override
            public void onChanged(TotalPoinResponse totalPoinResponse) {
                if (totalPoinResponse != null) {
                    if (totalPoinResponse.status) {
                        binding.textViewTotalPoin.setText(totalPoinResponse.poin + " Poin");
                    }
                }
            }
        });
    }
}