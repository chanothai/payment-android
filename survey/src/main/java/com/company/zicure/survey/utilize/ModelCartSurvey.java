package com.company.zicure.survey.utilize;

import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.models.RestaurantModel;
import com.company.zicure.survey.post.PostSerialized;

import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 1/16/2017.
 */

public class ModelCartSurvey {
    private static ModelCartSurvey me = null;
    private PostSerialized postSerialized = null;
    private ArrayList<Integer> calPage = null;
    private int resultPage;
    private ArrayList<AnswerRequest.Assessment> arrAssessment = null;

    private ModelCartSurvey(){
        postSerialized = new PostSerialized();
        postSerialized.questionRequest = new QuestionRequest();
        postSerialized.questionResponse = new QuestionResponse().new Result();
        postSerialized.restaurantModel = new RestaurantModel();
        postSerialized.answerRequest = new AnswerRequest();
        postSerialized.answerResponse = new AnswerResponse();
        calPage = new ArrayList<Integer>();
        arrAssessment = new ArrayList<AnswerRequest.Assessment>();

        resultPage = 0;
    }

    public static ModelCartSurvey newInstance(){
        if (me == null){
            me = new ModelCartSurvey();
        }
        return me;
    }


    public PostSerialized getSerialized(){
        return postSerialized;
    }

    public ArrayList<Integer> getAllPage(){
        return calPage;
    }

    public void setResultPage(int resultPage){
        this.resultPage = resultPage;
    }

    public int getResultPage(){
        return resultPage;
    }

    public ArrayList<AnswerRequest.Assessment> getArrAssessment(){
        return arrAssessment;
    }
}
