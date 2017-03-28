package com.zicure.company.com.model.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 2/7/2017.
 */

public class ResponseStatement {
    @SerializedName("Result")
    private ArrayList<Result> result;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }


    public class Result {
        @SerializedName("transaction_date")
        private String transactionDate;
        @SerializedName("transaction_time")
        private String transactionTime;
        @SerializedName("transaction_type")
        private String transactionType;
        @SerializedName("account_debit")
        private String accountDebit;
        @SerializedName("account_credit")
        private String accountCredit;
        @SerializedName("credit")
        private String credit;
        @SerializedName("debit")
        private String debit;
        @SerializedName("balance")
        private String balance;
        @SerializedName("symbol")
        private String symbol;
        @SerializedName("date_ago")
        private String dateAgo;
        @SerializedName("transaction_id")
        private String transactionID;

        public String getTransactionDate() {
            return transactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
        }

        public String getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(String transactionTime) {
            this.transactionTime = transactionTime;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getDebit() {
            return debit;
        }

        public void setDebit(String debit) {
            this.debit = debit;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getAccountDebit() {
            return accountDebit;
        }

        public void setAccountDebit(String accountDebit) {
            this.accountDebit = accountDebit;
        }

        public String getAccountCredit() {
            return accountCredit;
        }

        public void setAccountCredit(String accountCredit) {
            this.accountCredit = accountCredit;
        }

        public String getDateAgo() {
            return dateAgo;
        }

        public void setDateAgo(String dateAgo) {
            this.dateAgo = dateAgo;
        }

        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }
    }
}