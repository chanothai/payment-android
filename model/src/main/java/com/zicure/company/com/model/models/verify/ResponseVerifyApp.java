package com.zicure.company.com.model.models.verify;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 8/8/2017 AD.
 */

public class ResponseVerifyApp {
    @SerializedName("result") private ResultVerifyApp result;

    public ResultVerifyApp getResult() {
        return result;
    }

    public void setResult(ResultVerifyApp result) {
        this.result = result;
    }

    public static class ResultVerifyApp {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("Data") private DataVerify data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public DataVerify getData() {
            return data;
        }

        public void setData(DataVerify data) {
            this.data = data;
        }

        public static class DataVerify {
            @SerializedName("secure_code") private String secureCode;

            public String getSecureCode() {
                return secureCode;
            }

            public void setSecureCode(String secureCode) {
                this.secureCode = secureCode;
            }
        }
    }
}
