package com.company.zicure.survey.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.survey.R;
import com.company.zicure.survey.adapter.QuestionViewPagerAdapter;
import com.company.zicure.survey.common.BaseActivity;
import com.company.zicure.survey.models.AnswerRequest;
import com.company.zicure.survey.models.AnswerResponse;
import com.company.zicure.survey.models.QuestionResponse;
import com.company.zicure.survey.network.ClientHttp;
import com.company.zicure.survey.post.PostSerialized;
import com.company.zicure.survey.utilize.EventBusCart;
import com.company.zicure.survey.utilize.ModelCartSurvey;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

public class SurveyActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String KEY_QUEST = "key_quest";

    private QuestionViewPagerAdapter questViewPagerAdapter;
    private TextView restaurantName = null;
    //View

    //viewPager
    private ViewPager questPager = null;

    //Create dot for position's viewpager.
    private TextView[] dots;
    private LinearLayout dotLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        EventBusCart.getInstance().getEventBus().register(this);
        hideStatusBar();
        bindView();
        questPager.setOnPageChangeListener(this);

        if (savedInstanceState == null) {
            if (ModelCartSurvey.newInstance().getSerialized().questionRequest != null){
                showLoadingDialog();
                ClientHttp.newInstance(this).requestQuestion(ModelCartSurvey.newInstance().getSerialized().questionRequest);
            }
        }

    }

    public void showLoadDialog(){
        showLoadingDialog();
    }

    private void hideStatusBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void bindView() {
        questPager = (ViewPager) findViewById(R.id.quest_view_pager);
        dotLayout = (LinearLayout) findViewById(R.id.layoutDot);
    }

    private PostSerialized setModelQuest() {
        return ModelCartSurvey.newInstance().getSerialized();
    }

    private void setViewPager() {
        questViewPagerAdapter = new QuestionViewPagerAdapter(getSupportFragmentManager());
        questPager.setAdapter(questViewPagerAdapter);
        questPager.addOnPageChangeListener(this);

        addDotLayout(0);
    }

    private void addDotLayout(int currentPage){
        try{
            double numPage = setModelQuest().questionResponse.getAssessmentTopic().get(0).getAssessmentQuestion().size();
            double dividePage = (numPage / 4) + 1;
            int allPage = (int) Math.ceil(dividePage);
            dots = new TextView[allPage];
            dotLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++){
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.color_dot_inactive_survey));
                dotLayout.addView(dots[i]);
            }

            if (dots.length > 0){
                dots[currentPage].setTextColor(getResources().getColor(R.color.color_dot_active_survey));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEvent(QuestionResponse.Result result) {
        ModelCartSurvey.newInstance().getSerialized().questionResponse.setAssessment(result.getAssessment());
        ModelCartSurvey.newInstance().getSerialized().questionResponse.setAssessmentTopic(result.getAssessmentTopic());
        Log.d("SurveyResponse", new Gson().toJson(ModelCartSurvey.newInstance().getSerialized().questionResponse));

        setViewPager();
        dismissDialog();
    }

    @Subscribe
    public void onEvent(AnswerResponse answerResponse){
        AnswerResponse.Result result = answerResponse.getResult();
        Log.d("AnswerResponse", new Gson().toJson(answerResponse));
        if (result.getCode().equalsIgnoreCase("SUCCESS")){
            ModelCartSurvey.newInstance().getSerialized().answerRequest = new AnswerRequest();
            ModelCartSurvey.newInstance().getSerialized().answerResponse.setResult(result);
            finish();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 Toast.makeText(this, result.getCode() , Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, result.getDescription(), Toast.LENGTH_LONG).show();
        }
        dismissDialog();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Clear answer assessment
        ModelCartSurvey.newInstance().getSerialized().answerRequest = new AnswerRequest();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }


    //Pager change
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addDotLayout(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
    }
}
