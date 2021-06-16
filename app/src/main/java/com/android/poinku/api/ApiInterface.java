package com.android.poinku.api;

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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("jenis")
    Call<JenisResponse> getJenis();

    @GET("lingkup")
    Call<LingkupResponse> getLingkup();

    @GET("peran")
    Call<PeranResponse> getPeran();

    @FormUrlEncoded
    @POST("TugasKhusus")
    Call<TugasKhususResponse> postTugasKhusus(
            @Field("nrp") String nrp,
            @Field("jenis") String jenis,
            @Field("lingkup") String lingkup,
            @Field("peran") String peran,
            @Field("judul") String judul,
            @Field("tanggal") String tanggal
    );

    @FormUrlEncoded
    @POST("TugasKhusus/konten")
    Call<BaseResponse> postKonten(
            @Field("id_tugas_khusus") String idTugasKhusus,
            @Field("media") String media,
            @Field("jenis") String jenis
    );

    @FormUrlEncoded
    @POST("TugasKhusus/kegiatan")
    Call<BaseResponse> postKegiatan(
            @Field("id_tugas_khusus") String idTugasKhusus,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("TugasKhusus/buktiKonten")
    Call<BaseResponse> postBuktiKonten(
            @Field("id_tugas_khusus") String idTugasKhusus,
            @Field("bukti") String bukti
    );

    @Multipart
    @POST("TugasKhusus/buktiKegiatan")
    Call<BaseResponse> postBuktiKegiatan(
            @Part("id_tugas_khusus") RequestBody idTugasKhusus,
            @Part("nrp") RequestBody nrp,
            @Part("nama_jenis") RequestBody namaJenis,
            @Part MultipartBody.Part file
    );

    @GET("TugasKhusus/poin")
    Call<DataPoinResponse> getDataPoin(
            @Query("nrp") String nrp,
            @Query("jenis") String jenis
    );

    @GET("TugasKhusus/totalPoin")
    Call<TotalPoinResponse> getTotalPoin(
            @Query("nrp") String nrp
    );

    @GET("TugasKhusus/jenisTugasKhusus")
    Call<JenisTugasKhususResponse> getJenisTugasKhusus(
            @Query("nrp") String nrp
    );

    @GET("TugasKhusus/kriteriaTugasKhusus")
    Call<KriteriaTugasKhususResponse> getKriteriaTugasKhusus(
            @Query("nrp") String nrp
    );

    @GET("Aturan/detail")
    Call<AturanResponse> getAturan(
            @Query("id") String id
    );

    @GET("Aturan/aturanAktif")
    Call<AturanResponse> getAturanAktif(
            @Query("kategori") String kategori
    );

    @GET("Aturan/nilai")
    Call<NilaiResponse> getNilai(
            @Query("id") String id
    );

    @GET("Aturan/poin")
    Call<PoinResponse> getPoin(
            @Query("id") String id
    );

    @GET("Aturan/kriteria")
    Call<KriteriaResponse> getKriteria(
            @Query("id") String id
    );

    @GET("Mahasiswa")
    Call<MahasiswaResponse> getMahasiswa(
            @Query("nrp") String nrp
    );

    @GET("TugasKhusus/detail")
    Call<DetailTugasKhususResponse> getDetailTugasKhusus(
            @Query("id") String id
    );

    @GET("TugasKhusus/kegiatan")
    Call<KegiatanResponse> getKegiatan(
            @Query("id") String id
    );

    @GET("TugasKhusus/konten")
    Call<KontenResponse> getKonten(
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("Mahasiswa/insert")
    Call<MahasiswaResponse> postMahasiswa(
            @Field("nrp") String nrp,
            @Field("email") String email,
            @Field("aturan") String aturan,
            @Field("nama") String nama,
            @Field("prodi") String prodi,
            @Field("angkatan") String angkatan,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("Mahasiswa/updateToken")
    Call<BaseResponse> postTokenMahasiswa(
            @Field("nrp") String nrp,
            @Field("token") String token
    );

    @GET("Event")
    Call<EventResponse> getEvent();

    @GET("Event/detailEvent")
    Call<EventResponse> getDetailEvent(
            @Query("id") String id
    );

    @GET("Event/eventUser")
    Call<EventResponse> getEventUser(
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("Event/daftar")
    Call<BaseResponse> postDaftarEvent(
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("Event/batal")
    Call<BaseResponse> postBatalDaftarEvent(
            @Field("email") String email,
            @Field("id") String id
    );

    @GET("Event/presensi")
    Call<PresensiResponse> getPresensi(
            @Query("email") String email,
            @Query("id") String id
    );

    @FormUrlEncoded
    @PUT("Mahasiswa/pengajuan")
    Call<BaseResponse> putPengajuanTugasKhusus(
            @Field("nrp") String nrp,
            @Field("nilai") String nilai
    );

    @FormUrlEncoded
    @POST("Event/kuesioner")
    Call<BaseResponse> postKuesioner(
            @Field("email") String email,
            @Field("id") String id,
            @Field("jwb1") int jwb1,
            @Field("jwb2") int jwb2,
            @Field("jwb3") int jwb3,
            @Field("jwb4") int jwb4,
            @Field("jwb5") int jwb5,
            @Field("saran") String saran
    );

    @FormUrlEncoded
    @PUT("Event/absen")
    Call<BaseResponse> putAbsensi(
            @Field("nrp") String nrp,
            @Field("email") String email,
            @Field("id") String id
    );

    @FormUrlEncoded
    @PUT("Mahasiswa/removeToken")
    Call<BaseResponse> putRemoveToken(
            @Field("nrp") String nrp
    );
}
