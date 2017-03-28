package com.zicure.company.com.model.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ResponseQRCode {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @SerializedName("token")
        private String token;
        @SerializedName("code")
        private String urlQRCode;
        @SerializedName("amount")
        private String amount;

        public String getUrlQRCode() {
            return urlQRCode;
        }

        public void setUrlQRCode(String urlQRCode) {
            this.urlQRCode = urlQRCode;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
