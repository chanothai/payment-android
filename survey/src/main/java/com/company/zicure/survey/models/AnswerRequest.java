package com.company.zicure.survey.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 2/23/2017.
 */

public class AnswerRequest {
    @SerializedName("account_no")
    private String account_no;
    @SerializedName("token")
    private String token;
    @SerializedName("code")
    private String code;
    @SerializedName("type")
    private String type;
    @SerializedName("ref_id")
    private String refID;
    @SerializedName("assessment")

    private ArrayList<Assessment> assessments = null;

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefID() {
        return refID;
    }

    public void setRefID(String refID) {
        this.refID = refID;
    }


    public class Assessment {
        @SerializedName("assessment_id")
        private String amID;
        @SerializedName("assessment_topic_id")
        private String amTopicID;
        @SerializedName("assessment_question_id")
        private String amQuestionID;
        @SerializedName("assessment_answer_id")
        private String amAnswerID;
        @SerializedName("value")
        private String value;

        public String getAmID() {
            return amID;
        }

        public void setAmID(String amID) {
            this.amID = amID;
        }

        public String getAmTopicID() {
            return amTopicID;
        }

        public void setAmTopicID(String amTopicID) {
            this.amTopicID = amTopicID;
        }

        public String getAmQuestionID() {
            return amQuestionID;
        }

        public void setAmQuestionID(String amQuestionID) {
            this.amQuestionID = amQuestionID;
        }

        public String getAmAnswerID() {
            return amAnswerID;
        }

        public void setAmAnswerID(String amAnswerID) {
            this.amAnswerID = amAnswerID;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
