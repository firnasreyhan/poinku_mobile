package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.repository.OnlineRepository;

public class KriteriaViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public KriteriaViewModel(@NonNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<KriteriaResponse> getKriteria(String idAturan) {
        return onlineRepository.getKriteria(
                idAturan
        );
    }
}
