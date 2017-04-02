package com.company.zicure.payment.network;

import android.content.Context;
import android.util.Log;

import com.company.zicure.payment.R;
import com.company.zicure.payment.interfaces.LogApi;
import com.google.gson.GsonBuilder;
import com.zicure.company.com.model.models.AccountUser;
import com.zicure.company.com.model.models.RequestRegister;
import com.zicure.company.com.model.models.RequestTokenModel;
import com.zicure.company.com.model.models.ResponseBalance;
import com.zicure.company.com.model.models.ResponseProfile;
import com.zicure.company.com.model.models.ResponseQRCode;
import com.zicure.company.com.model.models.ResponseRegister;
import com.zicure.company.com.model.models.ResponseScanQR;
import com.zicure.company.com.model.models.ResponseTokenModel;
import com.zicure.company.com.model.models.ResponseUserCode;
import com.zicure.company.com.model.util.EventBusCart;
import com.google.gson.Gson;
import com.zicure.company.com.model.util.VarialableConnect;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class ClientHttp {
    private Context context = null;
    private static ClientHttp me;

    private final String LOGAPI = "API_RESPONSE";
    private String jsonStr;
    private Gson gson = null;

    private Retrofit retrofit = null;
    private LogApi service = null;

    public ClientHttp(Context context){
        this.context = context;
        retrofit = RetrofitAPI.newInstance(VarialableConnect.urlServerPayment).getRetrofit();
        service = retrofit.create(LogApi.class);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    public static ClientHttp getInstance(Context context){
        if (me == null){
            me = new ClientHttp(context);
        }
        return me;
    }

    public void userCallToken(final RequestTokenModel tokenModel){
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
            }
        });
    }

    public void requestPay(AccountUser accountUser){
        Log.d("Requestpay", new Gson().toJson(accountUser));
        Call<ResponseQRCode> callQRcode = service.requestPay(accountUser);
        callQRcode.enqueue(new Callback<ResponseQRCode>() {
            @Override
            public void onResponse(Call<ResponseQRCode> call, Response<ResponseQRCode> response) {
                try {
                    Log.d("Code", new Gson().toJson(response.body()));
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    ResponseQRCode responseQRCode = new ResponseQRCode();
                    ResponseQRCode.Result result = new ResponseQRCode.Result();
                    result.setUrlQRCode(context.getString(R.string.message_error_check_qrcode_th));
                    responseQRCode.setResult(result);
                    EventBusCart.getInstance().getEventBus().post(responseQRCode);
                }
            }

            @Override
            public void onFailure(Call<ResponseQRCode> call, Throwable t) {
            }
        });
    }

    public void requestScanQR(AccountUser accountUser){
        Log.d("Code", new Gson().toJson(accountUser));
        Call<ResponseScanQR> callScanQR = service.requestScanQR(accountUser);
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

    public void requestUserCode(String clientId){
        Retrofit rtf = new Retrofit.Builder()
                .baseUrl(VarialableConnect.urlServerConnect)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LogApi sv = rtf.create(LogApi.class);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("clientId", clientId);
        Call<ResponseUserCode> userCode = sv.genUserCode(map);
        userCode.enqueue(new Callback<ResponseUserCode>() {
            @Override
            public void onResponse(Call<ResponseUserCode> call, Response<ResponseUserCode> response) {
                try{
                    Log.d("UserCode", new Gson().toJson(response.body()));
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseUserCode> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestAccount(RequestRegister requestRegister){
        Log.d("Account", gson.toJson(requestRegister));
        Call<ResponseRegister> register = service.requestAccount(requestRegister);
        register.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                try{
                    if (response.body() != null){
                        EventBusCart.getInstance().getEventBus().post(response.body());
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestUserInfo(RequestTokenModel requestTokenModel){
        Call<ResponseProfile> callUserInfo = service.requestUserInfo(requestTokenModel);
        callUserInfo.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                try{
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
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
                try {
                    Log.d("Balance", new Gson().toJson(response.body()));
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
}
