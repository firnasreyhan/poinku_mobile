package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JenisTugasKhususResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailJenisTugasKhusus> data;

    public static class DetailJenisTugasKhusus {
        @SerializedName("NRP")
        public String nrp;

        @SerializedName("ID_JENIS")
        public String idJenis;

        @SerializedName("JENIS")
        public String jenis;

        @SerializedName("TOTAL")
        public String total;

        @SerializedName("JUMLAH")
        public String jumlah;
    }
}
