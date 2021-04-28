package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KriteriaTugasKhususResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailKriteriaTugasKhusus> data;

    public static class DetailKriteriaTugasKhusus {
        @SerializedName("NRP")
        public String nrp;

        @SerializedName("ID_JENIS")
        public String idJenis;

        @SerializedName("ID_LINGKUP")
        public String idLingkup;

        @SerializedName("JUMLAH")
        public String jumlah;
    }
}
