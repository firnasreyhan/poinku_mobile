package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeranResponse extends BaseResponse {
    @SerializedName("data")
    public List<PeranModel> data;

    public static class PeranModel {
        @SerializedName("ID_PERAN")
        public String idPeran;

        @SerializedName("PERAN")
        public String peran;

        @Override
        public String toString() {
            return peran;
        }
    }
}
