package com.android.poinku.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.repository.OnlineRepository;

import org.jetbrains.annotations.NotNull;

public class ProfileViewModel extends AndroidViewModel {
    private OnlineRepository onlineRepository;

    public ProfileViewModel(@NonNull @NotNull Application application) {
        super(application);
        onlineRepository = new OnlineRepository();
    }

    public MutableLiveData<BaseResponse> putRemoveToken(String nrp) {
        return onlineRepository.putRemoveToken(
                nrp
        );
    }
}
