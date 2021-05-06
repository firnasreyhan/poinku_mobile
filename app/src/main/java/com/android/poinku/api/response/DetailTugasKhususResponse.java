package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

public class DetailTugasKhususResponse extends BaseResponse {
    @SerializedName("data")
    public DetailTugasKhusus data;

    public static class DetailTugasKhusus {
        @SerializedName("ID_TUGAS_KHUSUS")
        public String idTugasKhusus;

        @SerializedName("NRP")
        public String nrp;

        @SerializedName("ID_JENIS")
        public String idJenis;

        @SerializedName("JENIS")
        public String jenis;

        @SerializedName("ID_LINGKUP")
        public String idLingkup;

        @SerializedName("LINGKUP")
        public String lingkup;

        @SerializedName("ID_PERAN")
        public String idPeran;

        @SerializedName("PERAN")
        public String peran;

        @SerializedName("JUDUL")
        public String judul;

        @SerializedName("TANGGAL_KEGIATAN")
        public String tanggalKegiatan;

        @SerializedName("BUKTI")
        public String bukti;

        @SerializedName("TANGGAL_DATA")
        public String tanggalData;

        @SerializedName("STATUS_VALIDASI")
        public String statusValidasi;

        @SerializedName("TANGGAL_VALIDASI")
        public String tanggalValidasi;

        @SerializedName("POIN")
        public String poin;
    }
}
