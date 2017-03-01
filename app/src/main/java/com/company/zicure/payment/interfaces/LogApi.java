package com.company.zicure.payment.interfaces;

import com.company.zicure.payment.model.AccountUserModel;
import com.company.zicure.payment.model.RequestTokenModel;
import com.company.zicure.payment.model.ResponseBalance;
import com.company.zicure.payment.model.ResponseQRCode;
import com.company.zicure.payment.model.ResponseScanQR;
import com.company.zicure.payment.model.ResponseStatement;
import com.company.zicure.payment.model.ResponseTokenModel;
import com.company.zicure.payment.store.StoreAccount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public interface LogApi {
    @POST("e-money-client/Accounts/statement")
    Call<ResponseStatement> requestStatement(@Body RequestTokenModel requestTokenModel);

    @POST("e-money-client/Accounts/token")
    Call<ResponseTokenModel> cmRequestToken(@Body RequestTokenModel requestTokenModel);

    @POST("e-money-client/Sellers/QRcode")
    Call<ResponseQRCode> requestPay(@Body AccountUserModel accountUserModel);

    @POST("e-money-client/Buyers/QRcode")
    Call<ResponseScanQR> requestScanQR(@Body AccountUserModel accountUserModel);

    @POST("e-money-client/Accounts/balance")
    Call<ResponseBalance> requestBalance(@Body RequestTokenModel requestTokenModel);

}
