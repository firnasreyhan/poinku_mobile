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

    public MutableLiveData<BaseResponse> postDaftarEvent(String email, String nama, String id) {
        return onlineRepository.postDaftarEvent(
                email,
                nama,
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

    public MutableLiveData<BaseResponse> putAbsensi(String nrp, String email, String id) {
        return onlineRepository.putAbsensi(
                nrp,
                email,
                id
        );
    }

    public MutableLiveData<BaseResponse> postKuesioner(String email, String id, int jwb1, int jwb2, int jwb3, int jwb4, int jwb5, String saran) {
        return onlineRepository.postKuesioner(
                email,
                id,
                jwb1,
                jwb2,
                jwb3,
                jwb4,
                jwb5,
                saran
        );
    }
}
