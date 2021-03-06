package com.android.poinku.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.poinku.api.ApiClient;
import com.android.poinku.api.ApiInterface;
import com.android.poinku.api.response.AturanResponse;
import com.android.poinku.api.response.BaseResponse;
import com.android.poinku.api.response.DataPoinResponse;
import com.android.poinku.api.response.DetailTugasKhususResponse;
import com.android.poinku.api.response.EventResponse;
import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.JenisTugasKhususResponse;
import com.android.poinku.api.response.KegiatanResponse;
import com.android.poinku.api.response.KontenResponse;
import com.android.poinku.api.response.KriteriaResponse;
import com.android.poinku.api.response.KriteriaTugasKhususResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.MahasiswaResponse;
import com.android.poinku.api.response.NilaiResponse;
import com.android.poinku.api.response.PeranResponse;
import com.android.poinku.api.response.PoinResponse;
import com.android.poinku.api.response.PresensiResponse;
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

    public MutableLiveData<JenisResponse> getJenis(String idAturan) {
        MutableLiveData<JenisResponse> data = new MutableLiveData<>();
        apiInterface.getJenis(
                idAturan
        ).enqueue(new Callback<JenisResponse>() {
            @Override
            public void onResponse(Call<JenisResponse> call, Response<JenisResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<LingkupResponse> getLingkup(String idAturan, String idJenis) {
        MutableLiveData<LingkupResponse> data = new MutableLiveData<>();
        apiInterface.getLingkup(
                idAturan,
                idJenis
        ).enqueue(new Callback<LingkupResponse>() {
            @Override
            public void onResponse(Call<LingkupResponse> call, Response<LingkupResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<PeranResponse> getPeran(String idAturan, String idJenis, String idLingkup) {
        MutableLiveData<PeranResponse> data = new MutableLiveData<>();
        apiInterface.getPeran(
                idAturan,
                idJenis,
                idLingkup
        ).enqueue(new Callback<PeranResponse>() {
            @Override
            public void onResponse(Call<PeranResponse> call, Response<PeranResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<BaseResponse> postUpdateTugasKhusus(String idTugasKhusus, String jenis, String lingkup, String peran, String judul, String tanggal) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postUpdateTugasKhusus(
                idTugasKhusus,
                jenis,
                lingkup,
                peran,
                judul,
                tanggal
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postUpdateTugasKhusus", t.getMessage());
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<BaseResponse> postUpdateKonten(String idTugasKhusus, String media, String jenis) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postUpdateKonten(
                idTugasKhusus,
                media,
                jenis
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postUpdateKonten", t.getMessage());
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<BaseResponse> postUpdateKegiatan(String idTugasKhusus, String keterangan) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postUpdateKegiatan(
                idTugasKhusus,
                keterangan
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postUpdateKegiatan", t.getMessage());
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<BaseResponse> getIsValidasi(String nrp) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.getIsValidasi(
                nrp
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("getIsValidasi", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postDeleteTugasKhusus(String id) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postDeleteTugasKhusus(
                id
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("deleteTugasKhusus", t.getMessage());
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<AturanResponse> getAturanAktif(String kategori) {
        MutableLiveData<AturanResponse> data = new MutableLiveData<>();
        apiInterface.getAturanAktif(
                kategori
        ).enqueue(new Callback<AturanResponse>() {
            @Override
            public void onResponse(Call<AturanResponse> call, Response<AturanResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<AturanResponse> call, Throwable t) {
                Log.e("getAturanAktif", t.getMessage());
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<MahasiswaResponse> postMahasiswa(String nrp, String email, String aturan, String nama, String prodi, String angkatan, String token) {
        MutableLiveData<MahasiswaResponse> data = new MutableLiveData<>();
        apiInterface.postMahasiswa(
                nrp,
                email,
                aturan,
                nama,
                prodi,
                angkatan,
                token
        ).enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                Log.e("postMahasiswa", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postTokenMahasiswa(String nrp, String token) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postTokenMahasiswa(
                nrp,
                token
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postTokenMahasiswa", t.getMessage());
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
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
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

    public MutableLiveData<KriteriaTugasKhususResponse> getKriteriaTugasKhusus(String nrp) {
        MutableLiveData<KriteriaTugasKhususResponse> data = new MutableLiveData<>();
        apiInterface.getKriteriaTugasKhusus(
                nrp
        ).enqueue(new Callback<KriteriaTugasKhususResponse>() {
            @Override
            public void onResponse(Call<KriteriaTugasKhususResponse> call, Response<KriteriaTugasKhususResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<KriteriaTugasKhususResponse> call, Throwable t) {
                Log.e("getKriteriaTugasKhusus", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<DetailTugasKhususResponse> getDetailTugasKhusus(String id) {
        MutableLiveData<DetailTugasKhususResponse> data = new MutableLiveData<>();
        apiInterface.getDetailTugasKhusus(
                id
        ).enqueue(new Callback<DetailTugasKhususResponse>() {
            @Override
            public void onResponse(Call<DetailTugasKhususResponse> call, Response<DetailTugasKhususResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<DetailTugasKhususResponse> call, Throwable t) {
                Log.e("getDetailTugasKhusus", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<KegiatanResponse> getKegiatan(String id) {
        MutableLiveData<KegiatanResponse> data = new MutableLiveData<>();
        apiInterface.getKegiatan(
                id
        ).enqueue(new Callback<KegiatanResponse>() {
            @Override
            public void onResponse(Call<KegiatanResponse> call, Response<KegiatanResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<KegiatanResponse> call, Throwable t) {
                Log.e("getKegiatan", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<KontenResponse> getKonten(String id) {
        MutableLiveData<KontenResponse> data = new MutableLiveData<>();
        apiInterface.getKonten(
                id
        ).enqueue(new Callback<KontenResponse>() {
            @Override
            public void onResponse(Call<KontenResponse> call, Response<KontenResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<KontenResponse> call, Throwable t) {
                Log.e("getKonten", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<EventResponse> getEvent() {
        MutableLiveData<EventResponse> data = new MutableLiveData<>();
        apiInterface.getEvent().enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("getEvent", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<EventResponse> getDetailEvent(String id) {
        MutableLiveData<EventResponse> data = new MutableLiveData<>();
        apiInterface.getDetailEvent(
                id
        ).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("getDetailEvent", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<EventResponse> getEventUser(String email) {
        MutableLiveData<EventResponse> data = new MutableLiveData<>();
        apiInterface.getEventUser(
                email
        ).enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("getEventUser", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postDaftarEvent(String email, String nama, String id) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postDaftarEvent(
                email,
                nama,
                id
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postDaftarEvent", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postBatalDaftarEvent(String email, String id) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postBatalDaftarEvent(
                email,
                id
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postBatalDaftarEvent", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> putPengajuanTugasKhusus(String nrp, String nilai) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.putPengajuanTugasKhusus(
                nrp,
                nilai
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("putPengajuanTugasKhusus", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> putAbsensi(String nrp, String email, String id) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.putAbsensi(
                nrp,
                email,
                id
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("putAbsensi", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> postKuesioner(String email, String id, int jwb1, int jwb2, int jwb3, int jwb4, int jwb5, String saran) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.postKuesioner(
                email,
                id,
                jwb1,
                jwb2,
                jwb3,
                jwb4,
                jwb5,
                saran
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("postKuesioner", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<BaseResponse> putRemoveToken(String nrp) {
        MutableLiveData<BaseResponse> data = new MutableLiveData<>();
        apiInterface.putRemoveToken(
                nrp
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("putRemoveToken", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }

    public MutableLiveData<PresensiResponse> getPresensi(String email, String id) {
        MutableLiveData<PresensiResponse> data = new MutableLiveData<>();
        apiInterface.getPresensi(
                email,
                id
        ).enqueue(new Callback<PresensiResponse>() {
            @Override
            public void onResponse(Call<PresensiResponse> call, Response<PresensiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        data.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PresensiResponse> call, Throwable t) {
                Log.e("getPresensi", t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }
}
