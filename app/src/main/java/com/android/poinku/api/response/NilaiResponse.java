package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NilaiResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailNilai> data;

    public static class DetailNilai {
        @SerializedName("ID_NILAI")
        public String idNilai;

        @SerializedName("ID_ATURAN")
        public String idAturan;

        @SerializedName("NILAI")
        public String nilai;

        @SerializedName("POIN_MINIMAL")
        public String poinMinimal;
    }
}
