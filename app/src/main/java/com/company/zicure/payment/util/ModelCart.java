package com.company.zicure.payment.util;

import com.company.zicure.payment.model.AccountUserModel;
import com.company.zicure.payment.model.ResponseStatement;
import com.company.zicure.payment.store.StoreAccount;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ModelCart {
    private static ModelCart me = null;
    private StoreAccount storeAccount = null;
    private String mode;

    private ModelCart() {
        storeAccount = new StoreAccount();
        storeAccount.accountUserModel = new AccountUserModel();
        storeAccount.option = new ArrayList<ResponseStatement.Result>();
        mode = "";
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
}
