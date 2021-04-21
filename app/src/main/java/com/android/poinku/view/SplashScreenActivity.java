package com.android.poinku.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.poinku.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toMainActivity();
    }

    private void toMainActivity() {
        int loadingTime = 3000;
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
//            if (AppPreference.getUser(this) != null) {
//                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//                finish();
//            } else {
//                startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
//                finish();
//            }
        }, loadingTime);
    }
}