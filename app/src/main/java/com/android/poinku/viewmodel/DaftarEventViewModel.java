package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.EventResponse;
import com.android.poinku.repository.OnlineRepository;

import org.jetbrains.annotations.NotNull;

public class DaftarEventViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public DaftarEventViewModel(@NonNull @NotNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<EventResponse> getEvent() {
        return onlineRepository.getEvent();
    }
}
