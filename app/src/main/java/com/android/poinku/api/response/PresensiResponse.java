package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

public class PresensiResponse extends BaseResponse {
    @SerializedName("data")
    public PresensiDetail data;

    public static class PresensiDetail {
        @SerializedName("ID_PRESENSI")
        public String idPresensi;

        @SerializedName("EMAIL")
        public String email;

        @SerializedName("ID_EVENT")
        public String idEvent;

        @SerializedName("STATUS")
        public String status;

        @SerializedName("WAKTU_PRESENSI")
        public String waktuPresensi;
    }
}
