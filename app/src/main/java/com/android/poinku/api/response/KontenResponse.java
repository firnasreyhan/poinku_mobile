package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

public class KontenResponse extends BaseResponse {
    @SerializedName("data")
    public Konten data;

    public static class Konten {
        @SerializedName("ID_KONTEN")
        public String idKegiatan;

        @SerializedName("ID_TUGAS_KHUSUS")
        public String idTugasKhusus;

        @SerializedName("MEDIA_KONTEN")
        public String mediaKonten;

        @SerializedName("JENIS_KONTEN")
        public String jenisKonten;
    }
}
