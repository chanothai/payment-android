package com.company.zicure.payment.network;

import android.content.Context;
import android.util.Log;

import com.company.zicure.payment.R;
import com.company.zicure.payment.interfaces.LogApi;
import com.company.zicure.payment.model.AccountUserModel;
import com.company.zicure.payment.model.RequestTokenModel;
import com.company.zicure.payment.model.ResponseBalance;
import com.company.zicure.payment.model.ResponseQRCode;
import com.company.zicure.payment.model.ResponseScanQR;
import com.company.zicure.payment.model.ResponseStatement;
import com.company.zicure.payment.model.ResponseTokenModel;
import com.company.zicure.payment.store.StoreAccount;
import com.company.zicure.payment.util.EventBusCart;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ClientHttp {
    private Context context = null;
    private static ClientHttp me;

    private final String LOGAPI = "API_RESPONSE";
    private String jsonStr;

    private Retrofit retrofit = null;
    private LogApi service = null;

    public ClientHttp(Context context){
        this.context = context;
        retrofit = RetrofitAPI.newInstance(context.getString(R.string.url_server)).getRetrofit();
        service = retrofit.create(LogApi.class);
    }

    public static ClientHttp getInstance(Context context){
        if (me == null){
            me = new ClientHttp(context);
        }
        return me;
    }

    public void userCallToken(RequestTokenModel tokenModel){
        Log.d("Model", new Gson().toJson(tokenModel));
        Call<ResponseTokenModel> callToken = service.cmRequestToken(tokenModel);
        callToken.enqueue(new Callback<ResponseTokenModel>() {
            @Override
            public void onResponse(Call<ResponseTokenModel> call, Response<ResponseTokenModel> response) {
                Log.d("Token", new Gson().toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseTokenModel> call, Throwable t) {
                Log.d("Error", new Gson().toJson(call));
                t.printStackTrace();
            }
        });
    }

    public void requestPay(AccountUserModel accountUserModel){
        Log.d("RequestPay", new Gson().toJson(accountUserModel));
        Call<ResponseQRCode> callQRcode = service.requestPay(accountUserModel);
        callQRcode.enqueue(new Callback<ResponseQRCode>() {
            @Override
            public void onResponse(Call<ResponseQRCode> call, Response<ResponseQRCode> response) {
                Log.d("Code", new Gson().toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseQRCode> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestScanQR(AccountUserModel accountUserModel){
        Log.d("Code", new Gson().toJson(accountUserModel));
        Call<ResponseScanQR> callScanQR = service.requestScanQR(accountUserModel);
        callScanQR.enqueue(new Callback<ResponseScanQR>() {
            @Override
            public void onResponse(Call<ResponseScanQR> call, Response<ResponseScanQR> response) {
                Log.d("Code", new Gson().toJson(response.body()));
                if (response.body() != null){
                    try {
                        EventBusCart.getInstance().getEventBus().post(response.body());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseScanQR> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestBalance(RequestTokenModel tokenModel){
        Log.d("token", new Gson().toJson(tokenModel));
        Call<ResponseBalance> callBalance = service.requestBalance(tokenModel);
        callBalance.enqueue(new Callback<ResponseBalance>() {
            @Override
            public void onResponse(Call<ResponseBalance> call, Response<ResponseBalance> response) {
                Log.d("Balance", new Gson().toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBalance> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestStatement(final RequestTokenModel tokenModel){
        Call<ResponseStatement> callStatement = service.requestStatement(tokenModel);
        callStatement.enqueue(new Callback<ResponseStatement>() {
            @Override
            public void onResponse(Call<ResponseStatement> call, Response<ResponseStatement> response) {
                Log.d("Statement" , new Gson().toJson(response.body()));
                try {
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatement> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
