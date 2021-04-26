package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PoinResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailPoin> data;

    public static class DetailPoin {
        @SerializedName("ID_POIN")
        public String idNilai;

        @SerializedName("ID_ATURAN")
        public String idAturan;

        @SerializedName("JENIS")
        public String jenis;

        @SerializedName("LINGKUP")
        public String lingkup;

        @SerializedName("PERAN")
        public String peran;

        @SerializedName("POIN")
        public String poin;
    }
}
