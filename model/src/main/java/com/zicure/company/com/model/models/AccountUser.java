package com.zicure.company.com.model.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class AccountUser {
    @SerializedName("account_no")
    public String accountNo;
    @SerializedName("amount")
    public String amount;
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
    @SerializedName("currency")
    public String currency;
}
