package com.company.zicure.payment.interfaces;

import com.company.zicure.payment.model.AccountUserModel;
import com.company.zicure.payment.model.RequestTokenModel;
import com.company.zicure.payment.model.ResponseBalance;
import com.company.zicure.payment.model.ResponseQRCode;
import com.company.zicure.payment.model.ResponseScanQR;
import com.company.zicure.payment.model.ResponseStatement;
import com.company.zicure.payment.model.ResponseTokenModel;
import com.company.zicure.payment.model.ResponseUserCode;
import com.company.zicure.payment.store.StoreAccount;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

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

    @GET("Api/getDeviceToken.json")
    Call<ResponseUserCode> genUserCode(@QueryMap Map<String, String> ClientID);
}
