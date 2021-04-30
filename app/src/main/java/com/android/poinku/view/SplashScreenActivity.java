package com.android.poinku.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.poinku.R;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.databinding.ActivitySplashScreenBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.viewmodel.SplashScreenViewModel;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private SplashScreenViewModel viewModel;

    private FirebaseUser firebaseUser;
    private AuthMethodPickerLayout authMethodPickerLayout;
    private List<AuthUI.IdpConfig> providers;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel.class);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        authMethodPickerLayout = new AuthMethodPickerLayout
                .Builder(R.layout.firebase_login)
                .setGoogleButtonId(R.id.signInButton)
                .setPhoneButtonId(R.id.signInButton1)
                .build();

        toMainActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    if (firebaseUser.getEmail() != null) {
                        if (firebaseUser.getEmail().contains("@mhs.stiki.ac.id")) {
                            String nrp = firebaseUser.getEmail().substring(0,9);
                            String nama = firebaseUser.getDisplayName().substring(10);
                            String email = firebaseUser.getEmail();
                            String prodi = "";
                            if (nrp.substring(2,5).equals("111")) {
                                prodi = "TI";
                            } else if (nrp.substring(2,5).equals("122")) {
                                prodi = "MI";
                            } else if (nrp.substring(2,5).equals("113")) {
                                prodi = "SI";
                            } else if (nrp.substring(2,5).equals("211")) {
                                prodi = "DKV";
                            }
                            String angkatan = "20" + nrp.substring(0,2);

                            getMahasiswa(nrp, email, "1", prodi, angkatan);
                        } else {
                            deleteAccount();
                        }
                    }
                }
            } else {
                if (response == null) {
                    finish();
                }
            }
        }
    }

    private void toMainActivity() {
        int loadingTime = 3000;
        new Handler().postDelayed(() -> {
            if (AppPreference.getUser(this) != null) {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            } else {
                doSignIn();
            }
        }, loadingTime);
    }

    public void doSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.FirebaseLoginAppTheme)
                        .setAuthMethodPickerLayout(authMethodPickerLayout)
                        .build(),
                RC_SIGN_IN);
    }

    public void deleteAccount() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SplashScreenActivity.this, "Mohon gunakan email mahasiswa anda.", Toast.LENGTH_SHORT).show();
                        doSignIn();
                    }
                });
    }

    public void getMahasiswa(String nrp, String email, String aturan, String prodi, String angkatan) {
        viewModel.getMahasiswa(
                nrp
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
                        AppPreference.saveUser(SplashScreenActivity.this, mahasiswaResponse.data);

                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    } else {
                        postMahasiswa(nrp, email, "1", prodi, angkatan);
                    }
                }
            }
        });
    }

    public void postMahasiswa(String nrp, String email, String aturan, String prodi, String angkatan) {
        viewModel.postMahasiswa(
                nrp,
                email,
                aturan,
                prodi,
                angkatan
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
                        AppPreference.saveUser(SplashScreenActivity.this, mahasiswaResponse.data);

                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
        });
    }
}