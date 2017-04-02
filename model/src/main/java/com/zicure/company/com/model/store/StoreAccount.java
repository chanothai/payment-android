package com.zicure.company.com.model.store;

import com.google.gson.annotations.SerializedName;
import com.zicure.company.com.model.models.AccountUser;
import com.zicure.company.com.model.models.ResponseStatement;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class StoreAccount {
    @SerializedName("AccountUser")
    public AccountUser accountUser;

    @SerializedName("option")
    public ArrayList<ResponseStatement.Result> option;
}
