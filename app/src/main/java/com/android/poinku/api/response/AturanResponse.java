package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

public class AturanResponse extends BaseResponse{
    @SerializedName("data")
    public DetailAturan data;

    public static class DetailAturan {
        @SerializedName("ID_ATURAN")
        public String idAturan;

        @SerializedName("TAHUN")
        public String tahun;

        @SerializedName("KETERANGAN")
        public String keterangan;
    }
}
