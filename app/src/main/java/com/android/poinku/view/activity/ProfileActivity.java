package com.android.poinku.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.databinding.ActivityProfileBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.ProfileViewModel;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private FirebaseUser firebaseUser;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Picasso.get()
                .load(firebaseUser.getPhotoUrl())
                .into(binding.shapeableImageViewProfil);
        binding.textViewNama.setText(AppPreference.getUser(this).nama);
        binding.textViewEmail.setText(AppPreference.getUser(this).email);
        binding.textViewAngkatan.setText(AppPreference.getUser(this).angkatan);
        binding.textViewProdi.setText(AppPreference.getUser(this).prodi);

        binding.materialButtonKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignOut();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void doSignOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        putRemoveToken();

                        AppPreference.removeUser(ProfileActivity.this);
                        Intent intent = new Intent(ProfileActivity.this, SplashScreenActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }

    public void putRemoveToken() {
        viewModel.putRemoveToken(
                AppPreference.getUser(this).nrp
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    Log.e("putRemoveToken", baseResponse.message);
                }
            }
        });
    }
}