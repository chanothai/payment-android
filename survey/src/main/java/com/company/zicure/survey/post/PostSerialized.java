package com.company.zicure.survey.post;

import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.models.RestaurantModel;

/**
 * Created by 4GRYZ52 on 1/16/2017.
 */


public class PostSerialized {
    public QuestionRequest questionRequest;
    public QuestionResponse.Result questionResponse;
    public RestaurantModel restaurantModel;
    public AnswerRequest answerRequest;
    public AnswerResponse answerResponse;
}
