package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.api.response.PresensiResponse;
import com.android.poinku.repository.OnlineRepository;

import org.jetbrains.annotations.NotNull;

public class DetailEventViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public DetailEventViewModel(@NonNull @NotNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<BaseResponse> postDaftarEvent(String email, String id) {
        return onlineRepository.postDaftarEvent(
                email,
                id
        );
    }

    public MutableLiveData<BaseResponse> postBatalDaftarEvent(String email, String id) {
        return onlineRepository.postBatalDaftarEvent(
                email,
                id
        );
    }

    public MutableLiveData<PresensiResponse> getPresensi(String email, String id) {
        return onlineRepository.getPresensi(
                email,
                id
        );
    }

    public MutableLiveData<EventResponse> getDetailEvent(String id) {
        return onlineRepository.getDetailEvent(
                id
        );
    }
}
