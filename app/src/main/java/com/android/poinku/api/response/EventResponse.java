package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailEvent> data;

    public static class DetailEvent {
        @SerializedName("ID_EVENT")
        public String idEvent;

        @SerializedName("EMAIL")
        public String email;

        @SerializedName("ID_JENIS")
        public String idJenis;

        @SerializedName("JENIS")
        public String jenis;

        @SerializedName("ID_LINGKUP")
        public String idLingkup;

        @SerializedName("LINGKUP")
        public String lingkup;

        @SerializedName("JUDUL")
        public String judul;

        @SerializedName("DESKRIPSI")
        public String deskripsi;

        @SerializedName("TANGGAL_ACARA")
        public String tanggalAcara;

        @SerializedName("JAM_MULAI")
        public String jamMulai;

        @SerializedName("JAM_SELESAI")
        public String jamSelesai;

        @SerializedName("POSTER")
        public String poster;

        @SerializedName("KUOTA")
        public String kuota;

        @SerializedName("PENDAFTAR")
        public String pendaftar;
    }
}
