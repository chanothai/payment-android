package com.company.zicure.payment.store;

import com.company.zicure.payment.model.AccountUserModel;
import com.company.zicure.payment.model.QRCodeModel;
import com.company.zicure.payment.model.ResponseStatement;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class StoreAccount {
    @SerializedName("AccountUser")
    public AccountUserModel accountUserModel;

    @SerializedName("URL_QRCode")
    public QRCodeModel qrCodeModel;

    @SerializedName("option")
    public ArrayList<ResponseStatement.Result> option;
}
