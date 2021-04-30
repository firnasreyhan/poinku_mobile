package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

public class KegiatanResponse extends BaseResponse {
    @SerializedName("data")
    public Kegiatan data;

    public static class Kegiatan {
        @SerializedName("ID_KEGIATAN")
        public String idKegiatan;

        @SerializedName("ID_TUGAS_KHUSUS")
        public String idTugasKhusus;

        @SerializedName("KETERANGAN")
        public String keterangan;
    }
}
