package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.DataPoinResponse;
import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.api.response.KriteriaTugasKhususResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.api.response.TotalPoinResponse;
import com.android.poinku.repository.OnlineRepository;

public class MainViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<MahasiswaResponse> getMahasiswa(String nrp) {
        return onlineRepository.getMahasiswa(
                nrp
        );
    }

    public MutableLiveData<DataPoinResponse> getDataPoin(String nrp, String jenis) {
        return onlineRepository.getDataPoin(
                nrp,
                jenis
        );
    }

    public MutableLiveData<TotalPoinResponse> getTotalPoin(String nrp) {
        return onlineRepository.getTotalPoin(
                nrp
        );
    }

    public MutableLiveData<NilaiResponse> getNilai(String id) {
        return onlineRepository.getNilai(
                id
        );
    }

    public MutableLiveData<KriteriaResponse> getKriteria(String id) {
        return onlineRepository.getKriteria(
                id
        );
    }

    public MutableLiveData<KriteriaTugasKhususResponse> getKriteriaTugasKhusus(String nrp) {
        return onlineRepository.getKriteriaTugasKhusus(
                nrp
        );
    }
}
