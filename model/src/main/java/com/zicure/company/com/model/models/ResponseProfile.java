package com.zicure.company.com.model.models;

import java.util.List;

/**
 * Created by 4GRYZ52 on 3/29/2017.
 */

public class ResponseProfile {
    /**
     * Result : {"account_key":"f859d3449cfac683622cc2cab036fdfe","account_no":"201799990001030013","balance":"500.0000","Customer":{"first_name":"ball","last_name":"omo","image_path":"http://psp.pakgon.com/img/default_male.png"},"Transaction":[{"transaction_id":"728","transaction_date":"2017-03-29","transaction_time":"16:43:21","transaction_type":"transfer","account_debit":"201799990001030013","account_credit":"201799990001030012","credit":null,"debit":"500.0000","balance":"500.0000","symbol":"-","date_ago":"9 นาทีที่ผ่านมา"},{"transaction_id":"727","transaction_date":"2017-03-29","transaction_time":"16:42:36","transaction_type":"deposit","account_debit":null,"account_credit":"201799990001030013","credit":"1000.0000","debit":null,"balance":"1000.0000","symbol":"+","date_ago":"10 นาทีที่ผ่านมา"}]}
     */

    private ResultBean Result;

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * account_key : f859d3449cfac683622cc2cab036fdfe
         * account_no : 201799990001030013
         * balance : 500.0000
         * Customer : {"first_name":"ball","last_name":"omo","image_path":"http://psp.pakgon.com/img/default_male.png"}
         * Transaction : [{"transaction_id":"728","transaction_date":"2017-03-29","transaction_time":"16:43:21","transaction_type":"transfer","account_debit":"201799990001030013","account_credit":"201799990001030012","credit":null,"debit":"500.0000","balance":"500.0000","symbol":"-","date_ago":"9 นาทีที่ผ่านมา"},{"transaction_id":"727","transaction_date":"2017-03-29","transaction_time":"16:42:36","transaction_type":"deposit","account_debit":null,"account_credit":"201799990001030013","credit":"1000.0000","debit":null,"balance":"1000.0000","symbol":"+","date_ago":"10 นาทีที่ผ่านมา"}]
         */

        private String account_key;
        private String account_no;
        private String balance;
        private CustomerBean Customer;
        private List<TransactionBean> Transaction;

        public String getAccount_key() {
            return account_key;
        }

        public void setAccount_key(String account_key) {
            this.account_key = account_key;
        }

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public CustomerBean getCustomer() {
            return Customer;
        }

        public void setCustomer(CustomerBean Customer) {
            this.Customer = Customer;
        }

        public List<TransactionBean> getTransaction() {
            return Transaction;
        }

        public void setTransaction(List<TransactionBean> Transaction) {
            this.Transaction = Transaction;
        }

        public static class CustomerBean {
            /**
             * first_name : ball
             * last_name : omo
             * image_path : http://psp.pakgon.com/img/default_male.png
             */

            private String first_name;
            private String last_name;
            private String image_path;

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getImage_path() {
                return image_path;
            }

            public void setImage_path(String image_path) {
                this.image_path = image_path;
            }
        }

        public static class TransactionBean {
            /**
             * transaction_id : 728
             * transaction_date : 2017-03-29
             * transaction_time : 16:43:21
             * transaction_type : transfer
             * account_debit : 201799990001030013
             * account_credit : 201799990001030012
             * credit : null
             * debit : 500.0000
             * balance : 500.0000
             * symbol : -
             * date_ago : 9 นาทีที่ผ่านมา
             */

            private String transaction_id;
            private String transaction_date;
            private String transaction_time;
            private String transaction_type;
            private String account_debit;
            private String account_credit;
            private String credit;
            private String debit;
            private String balance;
            private String symbol;
            private String date_ago;
            private String first_name;
            private String last_name;
            private String telephone;
            private String image_path;

            public String getTransaction_id() {
                return transaction_id;
            }

            public void setTransaction_id(String transaction_id) {
                this.transaction_id = transaction_id;
            }

            public String getTransaction_date() {
                return transaction_date;
            }

            public void setTransaction_date(String transaction_date) {
                this.transaction_date = transaction_date;
            }

            public String getTransaction_time() {
                return transaction_time;
            }

            public void setTransaction_time(String transaction_time) {
                this.transaction_time = transaction_time;
            }

            public String getTransaction_type() {
                return transaction_type;
            }

            public void setTransaction_type(String transaction_type) {
                this.transaction_type = transaction_type;
            }

            public String getAccount_debit() {
                return account_debit;
            }

            public void setAccount_debit(String account_debit) {
                this.account_debit = account_debit;
            }

            public String getAccount_credit() {
                return account_credit;
            }

            public void setAccount_credit(String account_credit) {
                this.account_credit = account_credit;
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

            public String getDate_ago() {
                return date_ago;
            }

            public void setDate_ago(String date_ago) {
                this.date_ago = date_ago;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getImage_path() {
                return image_path;
            }

            public void setImage_path(String image_path) {
                this.image_path = image_path;
            }
        }
    }
}
