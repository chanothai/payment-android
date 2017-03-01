package com.company.zicure.survey.interfaces;

import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.models.QuestionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 4GRYZ52 on 1/12/2017.
 */

public interface LogApi {
    @POST("e-money-client/Assessments/get")
    Call<QuestionResponse> getQuestion(@Body QuestionRequest questionRequest);

    @POST("e-money-client/Assessments/post")
    Call<AnswerResponse> resultAnswer(@Body AnswerRequest answerRequest);
}
