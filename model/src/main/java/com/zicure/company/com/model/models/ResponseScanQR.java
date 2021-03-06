package com.zicure.company.com.model.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ResponseScanQR {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        @SerializedName("transaction_ref")
        private String transactionRef;
        @SerializedName("code")
        private String code;
        @SerializedName("description")
        private String description;
        @SerializedName("balance_debit")
        private String balanceDebit;

        public String getTransactionRef() {
            return transactionRef;
        }

        public void setTransactionRef(String transactionRef) {
            this.transactionRef = transactionRef;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBalanceDebit() {
            return balanceDebit;
        }

        public void setBalanceDebit(String balanceDebit) {
            this.balanceDebit = balanceDebit;
        }
    }

}
