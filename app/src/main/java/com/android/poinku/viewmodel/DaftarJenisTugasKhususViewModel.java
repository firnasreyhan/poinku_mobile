package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.DataPoinResponse;
import com.android.poinku.repository.OnlineRepository;

public class DaftarJenisTugasKhususViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public DaftarJenisTugasKhususViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<DataPoinResponse> getDataPoin(String nrp, String jenis) {
        return onlineRepository.getDataPoin(
                nrp,
                jenis
        );
    }
}
