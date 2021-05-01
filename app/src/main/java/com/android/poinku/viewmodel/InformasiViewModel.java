package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.api.response.PoinResponse;
import com.android.poinku.repository.OnlineRepository;

public class InformasiViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public InformasiViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<NilaiResponse> getNilai(String id) {
        return onlineRepository.getNilai(
                id
        );
    }

    public MutableLiveData<PoinResponse> getPoin(String id) {
        return onlineRepository.getPoin(
                id
        );
    }
}
