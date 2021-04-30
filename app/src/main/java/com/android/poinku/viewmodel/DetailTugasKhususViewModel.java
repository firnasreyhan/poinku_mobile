package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.DetailTugasKhususResponse;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.KegiatanResponse;
import com.android.poinku.api.response.KontenResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.repository.OnlineRepository;

public class DetailTugasKhususViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public DetailTugasKhususViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<DetailTugasKhususResponse> getDetailTugasKhusus(String idTugasKhusus) {
        return onlineRepository.getDetailTugasKhusus(
                idTugasKhusus
        );
    }

    public MutableLiveData<KegiatanResponse> getKegiatan(String idTugasKhusus) {
        return onlineRepository.getKegiatan(
                idTugasKhusus
        );
    }

    public MutableLiveData<KontenResponse> getKonten(String idTugasKhusus) {
        return onlineRepository.getKonten(
                idTugasKhusus
        );
    }

    public MutableLiveData<JenisResponse> getJenis() {
        return onlineRepository.getJenis();
    }

    public MutableLiveData<LingkupResponse> getLingkup() {
        return onlineRepository.getLingkup();
    }

    public MutableLiveData<PeranResponse> getPeran() {
        return onlineRepository.getPeran();
    }
}
