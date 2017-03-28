package com.company.zicure.payment.interfaces;

import com.zicure.company.com.model.models.AccountUserModel;
import com.zicure.company.com.model.models.RequestRegister;
import com.zicure.company.com.model.models.RequestTokenModel;
import com.zicure.company.com.model.models.ResponseBalance;
import com.zicure.company.com.model.models.ResponseQRCode;
import com.zicure.company.com.model.models.ResponseRegister;
import com.zicure.company.com.model.models.ResponseScanQR;
import com.zicure.company.com.model.models.ResponseStatement;
import com.zicure.company.com.model.models.ResponseTokenModel;
import com.zicure.company.com.model.models.ResponseUserCode;

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

    @POST("e-money-client/Registers")
    Call<ResponseRegister> requestAccount(@Body RequestRegister requestRegister);
}
