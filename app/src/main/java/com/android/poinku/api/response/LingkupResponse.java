package com.android.poinku.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LingkupResponse extends BaseResponse {
    @SerializedName("data")
    public List<LingkupModel> data;

    public static class LingkupModel {
        @SerializedName("ID_LINGKUP")
        public String idLingkup;

        @SerializedName("LINGKUP")
        public String lingkup;

        @Override
        public String toString() {
            return lingkup;
        }
    }
}
