package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.repository.OnlineRepository;

public class SplashScreenViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<MahasiswaResponse> postMahasiswa(String nrp, String email, String aturan, String prodi, String angkatan) {
        return onlineRepository.postMahasiswa(
                nrp,
                email,
                aturan,
                prodi,
                angkatan
        );
    }

    public MutableLiveData<MahasiswaResponse> getMahasiswa(String nrp) {
        return onlineRepository.getMahasiswa(
                nrp
        );
    }
}
