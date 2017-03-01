package com.company.zicure.survey.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.company.zicure.survey.R;
import com.company.zicure.survey.activity.SurveyActivity;
import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.network.ClientHttp;
import com.company.zicure.survey.utilize.ModelCartSurvey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubmitQuestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubmitQuestFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //View
    private Button btnSubmit;


    public SubmitQuestFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubmitQuestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubmitQuestFragment newInstance(String param1, String param2) {
        SubmitQuestFragment fragment = new SubmitQuestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_submit_quest, container, false);
        btnSubmit = (Button) root.findViewById(R.id.btn_submit);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {

        }

        btnSubmit.setOnClickListener(this);
    }

    private QuestionRequest getQuestRequest(){
        return ModelCartSurvey.newInstance().getSerialized().questionRequest;
    }

    private AnswerRequest setAnswerRequest(){
        return ModelCartSurvey.newInstance().getSerialized().answerRequest;
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_submit) {
            setAnswerRequest().setAccount_no(getQuestRequest().getAccount_no());
            setAnswerRequest().setToken(getQuestRequest().getToken());
            setAnswerRequest().setCode(getQuestRequest().getCode());
            setAnswerRequest().setType(getQuestRequest().getType());
            setAnswerRequest().setRefID(getQuestRequest().getRefID());

            ((SurveyActivity)getActivity()).showLoadingDialog();
            ClientHttp.newInstance(getActivity()).requestResultAnswer(ModelCartSurvey.newInstance().getSerialized().answerRequest);
        }
    }
}
