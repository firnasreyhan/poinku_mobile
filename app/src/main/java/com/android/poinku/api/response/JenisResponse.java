package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JenisResponse extends BaseResponse {
    @SerializedName("data")
    public List<JenisModel> data;

    public static class JenisModel {
        @SerializedName("ID_JENIS")
        public String idJenis;

        @SerializedName("JENIS")
        public String jenis;

        @Override
        public String toString() {
            return jenis;
        }
    }
}
