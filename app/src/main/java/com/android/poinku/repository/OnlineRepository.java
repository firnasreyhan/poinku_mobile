package com.android.poinku.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.ApiClient;
import com.android.poinku.api.ApiInterface;
import com.android.poinku.api.response.AturanResponse;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.DataPoinResponse;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.api.response.PoinResponse;
import com.android.poinku.api.response.TotalPoinResponse;
import com.android.poinku.api.response.TugasKhususResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public MutableLiveData<TugasKhususResponse> postTugasKhusus(String nrp, String jenis, String lingkup, String peran, String judul, String tanggal) {
        MutableLiveData<TugasKhususResponse> data = new MutableLiveData<>();
        apiInterface.postTugasKhusus(
                nrp,
                jenis,
                lingkup,
                peran,
                judul,
                tanggal
        ).enqueue(new Callback<TugasKhususResponse>() {
            @Override
            public void onResponse(Call<TugasKhususResponse> call, Response<TugasKhususResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TugasKhususResponse> call, Throwable t) {
                Log.e("postTugasKhusus", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postKonten(String idTugasKhusus, String media, String jenis) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postKonten(
                idTugasKhusus,
                media,
                jenis
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postKonten", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postKegiatan(String idTugasKhusus, String keterangan) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postKegiatan(
                idTugasKhusus,
                keterangan
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postKegiatan", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postBuktiKonten(String idTugasKhusus, String bukti) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postBuktiKonten(
                idTugasKhusus,
                bukti
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postBuktiKonten", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postBuktiKegiatan(RequestBody idTugasKhusus, RequestBody nrp, RequestBody namaJenis, MultipartBody.Part file) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postBuktiKegiatan(
                idTugasKhusus,
                nrp,
                namaJenis,
                file
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postBuktiKonten", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<DataPoinResponse> getDataPoin(String nrp, String jenis) {
        MutableLiveData<DataPoinResponse> data = new MutableLiveData<>();
        apiInterface.getDataPoin(
                nrp,
                jenis
        ).enqueue(new Callback<DataPoinResponse>() {
            @Override
            public void onResponse(Call<DataPoinResponse> call, Response<DataPoinResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DataPoinResponse> call, Throwable t) {
                Log.e("getDataPoin", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<TotalPoinResponse> getTotalPoin(String nrp) {
        MutableLiveData<TotalPoinResponse> data = new MutableLiveData<>();
        apiInterface.getTotalPoin(
                nrp
        ).enqueue(new Callback<TotalPoinResponse>() {
            @Override
            public void onResponse(Call<TotalPoinResponse> call, Response<TotalPoinResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TotalPoinResponse> call, Throwable t) {
                Log.e("getTotalPoin", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<AturanResponse> getAturan(String id) {
        MutableLiveData<AturanResponse> data = new MutableLiveData<>();
        apiInterface.getAturan(
                id
        ).enqueue(new Callback<AturanResponse>() {
            @Override
            public void onResponse(Call<AturanResponse> call, Response<AturanResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AturanResponse> call, Throwable t) {
                Log.e("getAturan", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<NilaiResponse> getNilai(String id) {
        MutableLiveData<NilaiResponse> data = new MutableLiveData<>();
        apiInterface.getNilai(
                id
        ).enqueue(new Callback<NilaiResponse>() {
            @Override
            public void onResponse(Call<NilaiResponse> call, Response<NilaiResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NilaiResponse> call, Throwable t) {
                Log.e("getNilai", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<PoinResponse> getPoin(String id) {
        MutableLiveData<PoinResponse> data = new MutableLiveData<>();
        apiInterface.getPoin(
                id
        ).enqueue(new Callback<PoinResponse>() {
            @Override
            public void onResponse(Call<PoinResponse> call, Response<PoinResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PoinResponse> call, Throwable t) {
                Log.e("getAturan", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<KriteriaResponse> getKriteria(String id) {
        MutableLiveData<KriteriaResponse> data = new MutableLiveData<>();
        apiInterface.getKriteria(
                id
        ).enqueue(new Callback<KriteriaResponse>() {
            @Override
            public void onResponse(Call<KriteriaResponse> call, Response<KriteriaResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<KriteriaResponse> call, Throwable t) {
                Log.e("getAturan", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<MahasiswaResponse> getMahasiswa(String nrp) {
        MutableLiveData<MahasiswaResponse> data = new MutableLiveData<>();
        apiInterface.getMahasiswa(
                nrp
        ).enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                Log.e("getMahasiswa", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<JenisTugasKhususResponse> getJenisTugasKhusus(String nrp) {
        MutableLiveData<JenisTugasKhususResponse> data = new MutableLiveData<>();
        apiInterface.getJenisTugasKhusus(
                nrp
        ).enqueue(new Callback<JenisTugasKhususResponse>() {
            @Override
            public void onResponse(Call<JenisTugasKhususResponse> call, Response<JenisTugasKhususResponse> response) {
                if (response.code() == 200) {
                    data.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JenisTugasKhususResponse> call, Throwable t) {
                Log.e("getJenisTugasKhusus", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }
}
