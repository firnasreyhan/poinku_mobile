package com.android.poinku.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.poinku.R;
import com.android.poinku.api.response.AturanResponse;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.databinding.ActivitySplashScreenBinding;
import com.android.poinku.preference.AppPreference;
import com.android.poinku.service.notif.Token;
import com.android.poinku.viewmodel.SplashScreenViewModel;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
        };

        if(!hasPermissions(PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, 0);
        }else {
            toMainActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: {
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED
                        || grantResults[1] != PackageManager.PERMISSION_GRANTED
                        || grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Izin diperlukan untuk menggunakan aplikasi", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    toMainActivity();
                }
            }
        }
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
                            String kategori = nrp.substring(5,6).equalsIgnoreCase("1") ? "0" : "1";

//                            getAturanAktif(nrp, email, kategori, prodi, angkatan, updateToken(nrp));
                            getMahasiswa(nrp, email, kategori, nama, prodi, angkatan, updateToken(nrp));
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

    private boolean hasPermissions(String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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

    public void doSignOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
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

    public void getMahasiswa(String nrp, String email, String kategori, String nama, String prodi, String angkatan, String token) {
        viewModel.getMahasiswa(
                nrp
        ).observe(this, new Observer<MahasiswaResponse>() {
            @Override
            public void onChanged(MahasiswaResponse mahasiswaResponse) {
                if (mahasiswaResponse != null) {
                    if (mahasiswaResponse.status) {
                        postTokenMahasiswa(nrp, token);

                        AppPreference.saveUser(SplashScreenActivity.this, mahasiswaResponse.data);

                        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                        finish();
                    } else {
                        getAturanAktif(nrp, email, kategori, nama, prodi, angkatan, token);
                    }
                }
            }
        });
    }

    public void getAturanAktif(String nrp, String email, String kategori, String nama, String prodi, String angkatan, String token) {
        viewModel.getAturanAktif(
                kategori
        ).observe(this, new Observer<AturanResponse>() {
            @Override
            public void onChanged(AturanResponse aturanResponse) {
                if (aturanResponse != null) {
                    if (aturanResponse.status) {
                        postMahasiswa(nrp, email, aturanResponse.data.idAturan, nama, prodi, angkatan, token);
                    } else {
                        doSignOut();
                        Toast.makeText(SplashScreenActivity.this, "Tidak ada aturan tugas khusus yang aktif, segera hubungi admin", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    doSignOut();
                    Toast.makeText(SplashScreenActivity.this, "Tidak ada aturan tugas khusus yang aktif, segera hubungi admin", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void postMahasiswa(String nrp, String email, String aturan, String nama, String prodi, String angkatan, String token) {
        Log.e("nama", nama);
        viewModel.postMahasiswa(
                nrp,
                email,
                aturan,
                nama,
                prodi,
                angkatan,
                token
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

    public void postTokenMahasiswa(String nrp, String token) {
        viewModel.postTokenMahasiswa(
                nrp,
                token
        ).observe(this, new Observer<BaseResponse>() {
            @Override
            public void onChanged(BaseResponse baseResponse) {
                if (baseResponse != null) {
                    Log.e("postTokenMahasiswa", baseResponse.message);
                }
            }
        });
    }

    private String updateToken(String nrp) {
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        String userKey = nrp.replaceAll("[-+.^:,]","");
        Log.e("userKey", userKey);
        Log.e("refreshToken", refreshToken);
        FirebaseDatabase.getInstance().getReference().child("Token").child(userKey).setValue(refreshToken);
        return refreshToken;
    }
}