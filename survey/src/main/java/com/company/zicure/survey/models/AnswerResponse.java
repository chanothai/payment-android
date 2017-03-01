package com.company.zicure.survey.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/23/2017.
 */

public class AnswerResponse {
    @SerializedName("Result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {
        @SerializedName("code")
        private String code;
        @SerializedName("description")
        private String description;

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
    }
}
