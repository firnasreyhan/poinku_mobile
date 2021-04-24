package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.repository.OnlineRepository;

public class CatatViewModel extends AndroidViewModel {
    private final OnlineRepository onlineRepository;

    public CatatViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
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
