package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

public class MahasiswaResponse extends BaseResponse {
    @SerializedName("data")
    public DetailMahasiswa data;

    public static class DetailMahasiswa {
        @SerializedName("NRP")
        public String nrp;

        @SerializedName("EMAIL")
        public String email;

        @SerializedName("ID_ATURAN")
        public String idAturan;

        @SerializedName("TAHUN")
        public String tahun;

        @SerializedName("KETERANGAN")
        public String keterangan;

        @SerializedName("NAMA")
        public String nama;

        @SerializedName("PRODI")
        public String prodi;

        @SerializedName("ANGKATAN")
        public String angkatan;

        @SerializedName("NILAI")
        public String nilai;

        @SerializedName("TANGGAL_VALIDASI")
        public String tanggalValidasi;

        @SerializedName("STATUS")
        public String status;

        @SerializedName("TOKEN")
        public String token;
    }
}
