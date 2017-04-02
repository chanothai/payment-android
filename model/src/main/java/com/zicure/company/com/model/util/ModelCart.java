package com.zicure.company.com.model.util;

import com.zicure.company.com.model.models.ResponseProfile;
import com.zicure.company.com.model.models.ResponseQRCode;
import com.zicure.company.com.model.models.ResponseScanQR;
import com.zicure.company.com.model.models.ResponseTokenModel;
import com.zicure.company.com.model.models.ResponseUserCode;
import com.zicure.company.com.model.models.AccountUser;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ModelCart {
    private static ModelCart me = null;
    private String mode;
    private ResponseUserCode deviceToken = null;
    private ResponseProfile responseProfile = null;
    private ResponseTokenModel responseTokenModel = null;
    private AccountUser accountUser = null;
    private ResponseQRCode responseQRCode = null;
    private ResponseScanQR responseScanQR = null;

    public ModelCart() {
        responseTokenModel = new ResponseTokenModel();
        responseProfile = new ResponseProfile();
        responseQRCode = new ResponseQRCode();
        responseScanQR = new ResponseScanQR();
        accountUser = new AccountUser();
        mode = "";

        deviceToken = new ResponseUserCode();
    }

    public static ModelCart getInstance(){
        if (me == null){
            me = new ModelCart();
        }

        return me;
    }

    public ResponseScanQR getResponseScanQR(){
        return responseScanQR;
    }
    public ResponseProfile getUserInfo(){
        return responseProfile;
    }

    public ResponseTokenModel getToken(){
        return responseTokenModel;
    }

    public ResponseQRCode getQRcode(){
        return responseQRCode;
    }

    public AccountUser getAccountUser(){
        return accountUser;
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
