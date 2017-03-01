package com.company.zicure.survey.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/22/2017.
 */

public class QuestionRequest {
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
}
