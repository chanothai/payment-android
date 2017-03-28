package com.zicure.company.com.model.util;

import com.zicure.company.com.model.models.AccountUserModel;
import com.zicure.company.com.model.models.ResponseStatement;
import com.zicure.company.com.model.models.ResponseUserCode;
import com.zicure.company.com.model.store.StoreAccount;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ModelCart {
    private static ModelCart me = null;
    private StoreAccount storeAccount = null;
    private String mode;
    private ResponseUserCode deviceToken = null;

    private ModelCart() {
        storeAccount = new StoreAccount();
        storeAccount.accountUserModel = new AccountUserModel();
        storeAccount.option = new ArrayList<ResponseStatement.Result>();
        mode = "";

        deviceToken = new ResponseUserCode();
    }

    public static ModelCart getInstance(){
        if (me == null){
            me = new ModelCart();
        }

        return me;
    }

    public StoreAccount getModel(){
        return storeAccount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ResponseUserCode getDeviceToken(){
        return deviceToken;
    }
}
