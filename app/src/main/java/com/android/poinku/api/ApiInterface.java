package com.android.poinku.api;

import com.android.poinku.api.response.JenisResponse;
import com.android.poinku.api.response.LingkupResponse;
import com.android.poinku.api.response.PeranResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("jenis")
    Call<JenisResponse> getJenis();

    @GET("lingkup")
    Call<LingkupResponse> getLingkup();

    @GET("peran")
    Call<PeranResponse> getPeran();

//    @POST("user/login")
//    @FormUrlEncoded
//    Call<SignInResponse> postSignIn(
//            @Field("username") String username,
//            @Field("password") String password,
//            @Field("token") String token
//    );
//
//    @POST("user/register")
//    @Multipart
//    Call<BaseResponse> postSignUp(
//            @Part("username") RequestBody username,
//            @Part("namaLengkap") RequestBody namaLengkap,
//            @Part("role") RequestBody role,
//            @Part("departement") RequestBody departemen,
//            @Part("division") RequestBody divisi,
//            @Part("password") RequestBody password,
//            @Part MultipartBody.Part signature,
//            @Part("token") RequestBody token
//    );
//
//    @POST("user/logout")
//    @FormUrlEncoded
//    Call<BaseResponse> postSigngOut(
//            @Field("idUser") String idUser
//    );
//
//    @GET("transaction/{username}")
//    Call<TransactionResponse> getTransaction(
//            @Path("username") String username,
//            @Query("limit") int limit,
//            @Query("isApproval") boolean isApproval
//    );
//
//    @GET("debitNoteApi/{username}")
//    Call<DebitNoteResponse> getDebitNote(
//            @Path("username") String username,
//            @Query("limit") int limit
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formSnack")
//    Call<BaseResponse> postPembelianSnack(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formMobdin")
//    Call<IdTransResponse> postPermintaanMobilDinas(
//            @Body String body
//    );
//
//    @Multipart
//    @POST("formMobdin/upload")
//    Call<BaseResponse> postSimMobilDinas(
//            @Part("idUser") RequestBody idUser,
//            @Part("idTrans") RequestBody idTrans,
//            @Part MultipartBody.Part file
//    );
//
//    @PUT("formMobdin/nopol")
//    @FormUrlEncoded
//    Call<BaseResponse> putNopolMobilDinas(
//            @Field("idUser") String username,
//            @Field("idTrans") String idTrans,
//            @Field("nopol") String isApprove
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formMobpri")
//    Call<IdTransResponse> postPermintaanMobilPribadi(
//            @Body String body
//    );
//
//    @Multipart
//    @POST("formMobpri/upload")
//    Call<BaseResponse> postSimMobilPribadi(
//            @Part("idUser") RequestBody idUser,
//            @Part("idTrans") RequestBody idTrans,
//            @Part MultipartBody.Part file
//    );
//
//    @PUT("formMobpri/nopol")
//    @FormUrlEncoded
//    Call<BaseResponse> putNopolMobilPribadi(
//            @Field("idUser") String username,
//            @Field("idTrans") String idTrans,
//            @Field("nopol") String isApprove
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formControlHarian")
//    Call<BaseResponse> postControlHarian(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formIdentifikasi")
//    Call<BaseResponse> postIdentifikasi(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formExternalWorkOrder")
//    Call<BaseResponse> postExternalWorkOrder(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formSidakCatering")
//    Call<BaseResponse> postSidakCatering(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formLegalitas")
//    Call<BaseResponse> postLegalitas(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formLaporanPerbaikan")
//    Call<BaseResponse> postLaporanPerbaikan(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formICGS")
//    Call<BaseResponse> postICGS(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formPerbaikan")
//    Call<BaseResponse> postPerbaikan(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formICP")
//    Call<BaseResponse> postICP(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formICH")
//    Call<BaseResponse> postICH(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formICPAB")
//    Call<BaseResponse> postICPAB(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formKepuasan")
//    Call<BaseResponse> postKepuasan(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formCateringReguler")
//    Call<BaseResponse> postCateringReguler(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formCRM")
//    Call<BaseResponse> postRuangMeeting(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formICAK")
//    Call<BaseResponse> postAlatKomunikasi(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formIWO")
//    Call<BaseResponse> postInternalWorkOrder(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formMaterial")
//    Call<BaseResponse> postMaterialUsedSlip(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formOrderCatering")
//    Call<BaseResponse> postOrderCatering(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formEvaluasi")
//    Call<BaseResponse> postEvaluasiVendor(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formKomplainUsulan")
//    Call<BaseResponse> postKomplainUsulan(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formTestfood")
//    Call<BaseResponse> postTestFood(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formSuratJalan")
//    Call<BaseResponse> postSuratJalan(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formDeklarasi")
//    Call<BaseResponse> postDeklarasi(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formNonAsset")
//    Call<BaseResponse> postNonAsset(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formExtension")
//    Call<BaseResponse> postExtensionDanAkses(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formMonitoring")
//    Call<BaseResponse> postMonitoringLapangan(
//            @Body String body
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formLayout")
//    Call<IdTransResponse> postLayoutAcara(
//            @Body String body
//    );
//
//    @Multipart
//    @POST("formLayout/upload")
//    Call<BaseResponse> postGambarLayoutAcara(
//            @Part("idUser") RequestBody idUser,
//            @Part("idTrans") RequestBody idTrans,
//            @Part MultipartBody.Part file
//    );
//
//    @Headers("Content-Type: application/json")
//    @POST("formPVRV")
//    Call<IdTransResponse> postPVRV(
//            @Body String body
//    );
//
//    @Multipart
//    @POST("formPVRV/upload")
//    Call<BaseResponse> postDokumenPVRV(
//            @Part("idUser") RequestBody idUser,
//            @Part("idTrans") RequestBody idTrans,
//            @Part("statUpload") RequestBody statUpload,
//            @Part("totalUpload") RequestBody totalUpload,
//            @Part MultipartBody.Part file
//    );
//
//
//    @GET("formPVRV/dokPend")
//    Call<DokumenPendukungResponse> getDokumenPendukung(
//            @Query("idUser") String idUser,
//            @Query("idTrans") String idTrans
//    );
//
//    @GET("form")
//    Call<FormResponse> getListForm(
//            @Query("role") String role
//    );
//
//    @PUT("transaction/confirm")
//    @FormUrlEncoded
//    Call<BaseResponse> putConfirm(
//            @Field("username") String username,
//            @Field("idTrans") String idTrans,
//            @Field("isApprove") int isApprove,
//            @Field("keterangan") String keterangan
//    );
//
//    @PUT("debitNoteApi/confirm")
//    @FormUrlEncoded
//    Call<BaseResponse> putConfirmDebitNote(
//            @Field("username") String username,
//            @Field("idDebitnote") String idDebitnote,
//            @Field("isApprove") int isApprove
//    );
//
//    @GET("formSnack")
//    Call<PembelianSnackResponse> getPembelianSnack(
//            @Query("idTrans") String idTrans
//    );
//
//    @GET("transaction/detail")
//    Call<TransactionDetailResponse> getTransactionDetail(
//            @Query("username") String username,
//            @Query("idTrans") String idTrans,
//            @Query("isApproval") boolean isApproval
//    );
//
//    @GET("debitNoteApi/detail")
//    Call<DetailDebitNoteResponse> getDebitNoteDetail(
//            @Query("username") String username,
//            @Query("idDebitnote") String idDebitnote
//    );
}
