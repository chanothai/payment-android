package com.company.zicure.survey.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.zicure.survey.R;
import com.company.zicure.survey.adapter.EmoChoiceAdapter;
import com.company.zicure.survey.interfaces.ItemClickListener;
import com.company.zicure.survey.interfaces.LaunchCallback;
import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.utilize.ModelCartSurvey;
import com.company.zicure.survey.utilize.NextzyUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmoQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmoQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int page;
    private String mParam2;


    private RecyclerView listOptions = null;
    private EmoChoiceAdapter emoChoiceAdapter = null;
    private TextView txtRestaurantName = null;

    public EmoQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmoQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmoQuestionFragment newInstance(int page, String param2) {
        EmoQuestionFragment fragment = new EmoQuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_emo_question, container, false);
        listOptions = (RecyclerView) root.findViewById(R.id.list_option);
        txtRestaurantName = (TextView) root.findViewById(R.id.assessment_restaurant_name);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listOptions.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listOptions.setItemAnimator(new DefaultItemAnimator());
        if (savedInstanceState == null){
            setRecyclerView();
            txtRestaurantName.setText(ModelCartSurvey.newInstance().getSerialized().restaurantModel.getName());
        }
    }

    private QuestionResponse.Result.AssessmentTopic getQuiz(){
        return ModelCartSurvey.newInstance().getSerialized().questionResponse.getAssessmentTopic().get(0);
    }

    private void initialAnswer(){
        if (ModelCartSurvey.newInstance().getArrAssessment().size() == 0){
            setAnswer();
        }
    }

    private void setAnswer(){
        for (int i = 0; i < ModelCartSurvey.newInstance().getResultPage(); i++) {
            AnswerRequest.Assessment assessment = new AnswerRequest().new Assessment();
            assessment.setAmQuestionID("");
            assessment.setAmTopicID("");
            assessment.setAmID("");
            assessment.setAmAnswerID("");
            assessment.setValue("");
            ModelCartSurvey.newInstance().getArrAssessment().add(i, assessment);
        }
    }

    private void setRecyclerView(){
        initialAnswer();
        int currentPage = ModelCartSurvey.newInstance().getAllPage().get(page);
        emoChoiceAdapter = new EmoChoiceAdapter(currentPage) {
            @Override
            public void onBindViewHolder(final EmoChoiceHolder holder, final int position) {
                final int positionStore = (((page + 1) * 4) - 4) + position;
                initial(holder,positionStore);
                holder.imgEmoBad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        answerPosition = 0;
                        setAnswer( //parameter 1 = choice, 2 = score ,3 = position
                                getQuiz().getAssessmentQuestion().get(positionStore).getAmAnswer().get(answerPosition).getId(),
                                getQuiz().getAssessmentQuestion().get(positionStore).getAmAnswer().get(answerPosition).getScore(),
                                positionStore);

                        animationEmoBad(holder);
                    }
                });

                holder.imgEmoNormal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        answerPosition = 1;
                        setAnswer( //parameter 1 = choice, 2 = score ,3 = position
                                getQuiz().getAssessmentQuestion().get(positionStore).getAmAnswer().get(answerPosition).getId(),
                                getQuiz().getAssessmentQuestion().get(positionStore).getAmAnswer().get(answerPosition).getScore(),
                                positionStore);

                        animationEmoNormal(holder);
                    }
                });

                holder.imgEmoGreat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        answerPosition = 2;
                        setAnswer( //parameter 1 = choice, 2 = score ,3 = position
                                getQuiz().getAssessmentQuestion().get(positionStore).getAmAnswer().get(answerPosition).getId(),
                                getQuiz().getAssessmentQuestion().get(positionStore).getAmAnswer().get(answerPosition).getScore(),
                                positionStore);

                        animationEmoGreat(holder);
                    }
                });
            }

            private void initial(EmoChoiceHolder holder, int position){
                holder.question.setText(getQuiz().getAssessmentQuestion().get(position).getName());
                holder.imgEmoBad.setImageResource(R.drawable.red);
                holder.imgEmoNormal.setImageResource(R.drawable.yellow);
                holder.imgEmoGreat.setImageResource(R.drawable.green);

                setAnimationScale(holder.imgEmoBad,0.7f,false);
                setAnimationScale(holder.imgEmoNormal,0.7f, false);
                setAnimationScale(holder.imgEmoGreat,0.7f, false);

                setChoice(holder, position);
            }

            private void animationEmoBad(EmoChoiceHolder holder){
                setAnimationScale(holder.imgEmoBad, 1f, true);
                setAnimationScale(holder.imgEmoNormal, 0.7f,false);
                setAnimationScale(holder.imgEmoGreat, 0.7f,false);
            }

            private void animationEmoNormal(EmoChoiceHolder holder){
                setAnimationScale(holder.imgEmoNormal, 1f,true);
                setAnimationScale(holder.imgEmoGreat, 0.7f,false);
                setAnimationScale(holder.imgEmoBad, 0.7f,false);
            }

            private void animationEmoGreat(EmoChoiceHolder holder){
                setAnimationScale(holder.imgEmoGreat, 1f,true);
                setAnimationScale(holder.imgEmoNormal, 0.7f,false);
                setAnimationScale(holder.imgEmoBad, 0.7f,false);
            }

            private void setAnimationScale(ImageView imgChoice, float scale, boolean status){
                AnimatorSet animSet = new AnimatorSet();
                ObjectAnimator animScaleX = ObjectAnimator.ofFloat(imgChoice, View.SCALE_X, scale);
                ObjectAnimator animScaleY = ObjectAnimator.ofFloat(imgChoice, View.SCALE_Y, scale);

                ObjectAnimator animAlpha = null;
                if (status){
                    animAlpha = ObjectAnimator.ofFloat(imgChoice, View.ALPHA, 1f);
                }else {
                    animAlpha = ObjectAnimator.ofFloat(imgChoice, View.ALPHA, 0.5f);
                }
                animSet.playTogether(animScaleX, animScaleY,animAlpha);
                animSet.start();
            }

            private void setAnswer(String choice, String score, int position){
                AnswerRequest.Assessment assessment = new AnswerRequest().new Assessment();
                assessment.setAmID(getQuiz().getAssessmentId());
                assessment.setAmTopicID(getQuiz().getAssessmentQuestion().get(position).getAmTopicID());
                assessment.setAmQuestionID(getQuiz().getAssessmentQuestion().get(position).getId());
                assessment.setAmAnswerID(choice); //answer id 1 = bad, 2 = normal, 3 = great;
                assessment.setValue(score); // bad = score 1, normal = score 2, great = score 3

                ModelCartSurvey.newInstance().getArrAssessment().set(position, assessment);
                Log.d("Answer", new Gson().toJson(ModelCartSurvey.newInstance().getArrAssessment()));

                //set array of assessment
                ModelCartSurvey.newInstance().getSerialized().answerRequest.setAssessments(ModelCartSurvey.newInstance().getArrAssessment());
            }

            private void setChoice(EmoChoiceHolder holder, int position){
                try{
                    if (ModelCartSurvey.newInstance().getSerialized().questionResponse.getAssessmentTopic().get(0).getAssessmentQuestion().get(position).getAmResult() != null){
                        checkAnswerHasAlready(position, holder);
                    }else{
                        checkAnswerOfChoice(position, holder);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }

            private void checkAnswerOfChoice(int position, final EmoChoiceHolder holder){
                if (ModelCartSurvey.newInstance().getSerialized().answerRequest.getAssessments().size() > 0){
                    String value = ModelCartSurvey.newInstance().getSerialized().answerRequest.getAssessments().get(position).getValue();
                    if (!value.isEmpty()){
                        switch (Integer.parseInt(value)){
                            case 1:
                                NextzyUtil.launch(new LaunchCallback() {
                                    @Override
                                    public void onLaunchCallback() {
                                        setAnimationScale(holder.imgEmoBad, 1f, true);
                                    }
                                });
                                break;
                            case 2:
                                NextzyUtil.launch(new LaunchCallback() {
                                    @Override
                                    public void onLaunchCallback() {
                                        setAnimationScale(holder.imgEmoNormal, 1f,true);
                                    }
                                });
                                break;
                            case 3:
                                NextzyUtil.launch(new LaunchCallback() {
                                    @Override
                                    public void onLaunchCallback() {
                                        setAnimationScale(holder.imgEmoGreat, 1f,true);
                                    }
                                });
                                break;
                        }
                    }
                }
            }

            private void checkAnswerHasAlready(int position, EmoChoiceHolder holder){
                for (int i = 0; i < getQuiz().getAssessmentQuestion().get(position).getAmAnswer().size(); i++){
                    switch (i){
                        case 0:
                            holder.choiceBad.setText(getQuiz().getAssessmentQuestion().get(position).getAmAnswer().get(i).getName());
                            if (getQuiz().getAssessmentQuestion().get(position).getAmResult() != null) {
                                if (getQuiz().getAssessmentQuestion().get(position).getAmResult().getValue().equalsIgnoreCase(getQuiz().getAssessmentQuestion().get(position).getAmAnswer().get(i).getScore())) {
                                    setAnimationScale(holder.imgEmoBad, 1f, true);
                                }else{
                                    setAnimationScale(holder.imgEmoBad, 0.7f, false);
                                }
                                break;
                            }
                            setAnimationScale(holder.imgEmoBad, 0.7f, false);
                            break;
                        case 1:
                            holder.choiceNormal.setText(getQuiz().getAssessmentQuestion().get(position).getAmAnswer().get(i).getName());
                            if (getQuiz().getAssessmentQuestion().get(position).getAmResult() != null) {
                                if (getQuiz().getAssessmentQuestion().get(position).getAmResult().getValue().equalsIgnoreCase(getQuiz().getAssessmentQuestion().get(position).getAmAnswer().get(i).getScore())) {
                                    setAnimationScale(holder.imgEmoNormal, 1f,true);
                                }else{
                                    setAnimationScale(holder.imgEmoNormal, 0.7f,false);
                                }
                                break;
                            }
                            setAnimationScale(holder.imgEmoBad, 0.7f,false);
                            break;
                        case 2:
                            holder.choiceGreat.setText(getQuiz().getAssessmentQuestion().get(position).getAmAnswer().get(i).getName());
                            if (getQuiz().getAssessmentQuestion().get(position).getAmResult() != null) {
                                if (getQuiz().getAssessmentQuestion().get(position).getAmResult().getValue().equalsIgnoreCase(getQuiz().getAssessmentQuestion().get(position).getAmAnswer().get(i).getScore())) {
                                    setAnimationScale(holder.imgEmoGreat, 1f,true);
                                }else{
                                    setAnimationScale(holder.imgEmoGreat, 0.7f,false);
                                }
                                break;
                            }
                            setAnimationScale(holder.imgEmoGreat, 0.7f,false);
                            break;
                        default:
                            break;
                    }
                }
            }
        };

        listOptions.setAdapter(emoChoiceAdapter);
        listOptions.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
