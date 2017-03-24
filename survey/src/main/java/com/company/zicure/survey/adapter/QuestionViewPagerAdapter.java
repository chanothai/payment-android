package com.company.zicure.survey.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.zicure.survey.R;
import com.company.zicure.survey.fragment.EmoQuestionFragment;
import com.company.zicure.survey.fragment.SubmitQuestFragment;
import com.company.zicure.survey.utilize.ModelCartSurvey;
import com.google.gson.Gson;


/**
 * Created by 4GRYZ52 on 1/13/2017.
 */

public class QuestionViewPagerAdapter extends FragmentPagerAdapter {
    private final static int ROWCHOICE = 4;

    public QuestionViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        int valueAllPage = ModelCartSurvey.newInstance().getSerialized().questionResponse.getAssessmentTopic().get(0).getAssessmentQuestion().size();
        if (position == valueAllPage){
            return new SubmitQuestFragment();
        }

        int pageCurrent = position + 1;
        int resultPage = valueAllPage - (ROWCHOICE * pageCurrent);

        //initial value quest of page
        if (valueAllPage >= 4 && position == 0){
            ModelCartSurvey.newInstance().getAllPage().add(position, ROWCHOICE);
        }

        if (resultPage > 0){
            if (resultPage <= 4){
                ModelCartSurvey.newInstance().getAllPage().add(pageCurrent, resultPage);
            }else{
                ModelCartSurvey.newInstance().getAllPage().add(pageCurrent, ROWCHOICE);
            }
            return EmoQuestionFragment.newInstance(position, "");
        }else if (resultPage <= 0){
            int rs = resultPage + 4;
            if (rs <= 0) {
                return new SubmitQuestFragment();
            }
            ModelCartSurvey.newInstance().getAllPage().add(pageCurrent, rs);
            return EmoQuestionFragment.newInstance(position, "");
        }

        return null;
    }

    @Override
    public int getCount() {
        int math = 0;
        if (ModelCartSurvey.newInstance().getSerialized().questionResponse != null){
            double valueQuest = ModelCartSurvey.newInstance().getSerialized().questionResponse.getAssessmentTopic().get(0).getAssessmentQuestion().size();
            double dividePage = (valueQuest / 4) + 1;
            if (valueQuest > 0){
                math =(int) Math.ceil(dividePage);
                ModelCartSurvey.newInstance().setResultPage((int)valueQuest);
            }
        }
        return math;
    }
}
