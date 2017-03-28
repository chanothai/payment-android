package com.zicure.company.com.model.models;

/**
 * Created by 4GRYZ52 on 3/28/2017.
 */

public class ResponseRegister {

    /**
     * Result : {"code":"SUCCESS","description":"This is a book","account_no":"XXXXXXXXXXXXXX"}
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
         * code : SUCCESS
         * description : This is a book
         * account_no : XXXXXXXXXXXXXX
         */

        private String code;
        private String description;
        private String account_no;

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

        public String getAccount_no() {
            return account_no;
        }

        public void setAccount_no(String account_no) {
            this.account_no = account_no;
        }
    }
}
