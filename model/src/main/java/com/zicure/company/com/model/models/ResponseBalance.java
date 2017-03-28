package com.zicure.company.com.model.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ResponseBalance {
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

        @SerializedName("balance")
        private String balance;

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
