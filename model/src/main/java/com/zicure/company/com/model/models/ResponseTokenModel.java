package com.zicure.company.com.model.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ResponseTokenModel {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("account_no")
        private String accountNo;
        @SerializedName("ip")
        private String ip;
        @SerializedName("token")
        private String token;

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


}
