package com.company.zicure.payment.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class AccountUserModel {
    @SerializedName("account_no")
    public String accountNo;
    @SerializedName("amount")
    public double amount;
    @SerializedName("token")
    public String token;
    @SerializedName("transaction_ref")
    public String code;
    @SerializedName("type")
    public String type;
    @SerializedName("balance")
    public String balance = null;
    @SerializedName("card_code")
    public String cardCode;
}
