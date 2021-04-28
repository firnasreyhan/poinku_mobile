package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KriteriaResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailKriteria> data;

    public static class DetailKriteria {
        @SerializedName("ID_KRITERIA")
        public String idKriteria;

        @SerializedName("ID_ATURAN")
        public String idAturan;

        @SerializedName("ID_NILAI")
        public String idNilai;

        @SerializedName("NILAI")
        public String nilai;

        @SerializedName("ID_JENIS")
        public String idJenis;

        @SerializedName("JENIS")
        public String jenis;

        @SerializedName("ID_LINGKUP")
        public String idLingkup;

        @SerializedName("LINGKUP")
        public String lingkup;

        @SerializedName("JUMLAH")
        public String jumlah;
    }
}
