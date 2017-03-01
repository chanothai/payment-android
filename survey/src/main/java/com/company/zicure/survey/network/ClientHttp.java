package com.company.zicure.survey.network;

import android.content.Context;
import android.util.Log;

import com.company.zicure.survey.R;
import com.company.zicure.survey.interfaces.LogApi;
import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.utilize.EventBusCart;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 4GRYZ52 on 1/12/2017.
 */

public class ClientHttp {
    private Context context = null;
    private static ClientHttp clientHttp;
    private Retrofit retrofit = null;
    private LogApi service = null;
    private HttpLoggingInterceptor logging = null;
    private OkHttpClient.Builder httpClient = null;

    public ClientHttp(Context context){
        this.context = context;
        retrofit = RetrofitAPI.newInstance(context.getString(R.string.url_question)).getRetrofit();
        service = retrofit.create(LogApi.class);
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder();
    }

    public static ClientHttp newInstance(Context context){
        if (clientHttp == null){
            clientHttp = new ClientHttp(context);
        }
        return clientHttp;
    }

    public void requestQuestion(QuestionRequest questionRequest){
        httpClient.addInterceptor(logging); //<-- This is important line!
        Call<QuestionResponse> callQuestion = service.getQuestion(questionRequest);
        callQuestion.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                if (response.body() != null){
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(response.body());
                    Log.d("QuestionResponse" , jsonStr);
                    EventBusCart.getInstance().getEventBus().post(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<QuestionResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void requestResultAnswer(AnswerRequest answerRequest){
        httpClient.addInterceptor(logging); //<-- This is important line!
        Log.d("AnswerRequest", new Gson().toJson(answerRequest.getAssessments()));
        Call<AnswerResponse> callResultAnswer = service.resultAnswer(answerRequest);
        callResultAnswer.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                if (response.body() != null){
                    EventBusCart.getInstance().getEventBus().post(response.body());
                }
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
