package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.repository.OnlineRepository;

public class DaftarTugasKhususViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public DaftarTugasKhususViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<JenisTugasKhususResponse> getJenisTugasKhusus(String nrp) {
        return onlineRepository.getJenisTugasKhusus(
                nrp
        );
    }
}
