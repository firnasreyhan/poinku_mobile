package com.android.poinku.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.ApiClient;
import com.android.poinku.api.ApiInterface;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineRepository {
    private final ApiInterface apiInterface;

    public OnlineRepository() {
        this.apiInterface = ApiClient.getClient();
    }

    public MutableLiveData<JenisResponse> getJenis() {
        MutableLiveData<JenisResponse> data = new MutableLiveData<>();
        apiInterface.getJenis().enqueue(new Callback<JenisResponse>() {
            @Override
            public void onResponse(Call<JenisResponse> call, Response<JenisResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JenisResponse> call, Throwable t) {
                Log.e("getJenis", t.getMessage());
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<LingkupResponse> getLingkup() {
        MutableLiveData<LingkupResponse> data = new MutableLiveData<>();
        apiInterface.getLingkup().enqueue(new Callback<LingkupResponse>() {
            @Override
            public void onResponse(Call<LingkupResponse> call, Response<LingkupResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<LingkupResponse> call, Throwable t) {
                Log.e("getLingkup", t.getMessage());
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<PeranResponse> getPeran() {
        MutableLiveData<PeranResponse> data = new MutableLiveData<>();
        apiInterface.getPeran().enqueue(new Callback<PeranResponse>() {
            @Override
            public void onResponse(Call<PeranResponse> call, Response<PeranResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PeranResponse> call, Throwable t) {
                Log.e("getPeran", t.getMessage());
                data.postValue(null);
            }
        });
        return data;
    }
}
