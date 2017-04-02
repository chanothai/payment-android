package com.company.zicure.payment.contents;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.payment.R;
import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.adapters.StatementAdapter;
import com.company.zicure.payment.interfaces.ItemClickListener;
import com.company.zicure.survey.activity.SurveyActivity;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.utilize.ModelCartSurvey;
import com.google.gson.Gson;
import com.zicure.company.com.model.util.FormatCash;
import com.zicure.company.com.model.util.ModelCart;


/**
 * Created by 4GRYZ52 on 4/1/2017.
 */

public class ContentAdapterCart {
    private Activity activity = null;

    public ContentAdapterCart(Activity activity){
        this.activity = activity;
    }

    private QuestionRequest getSurveyRequest(){
        return ModelCartSurvey.newInstance().getSerialized().questionRequest;
    }

    public StatementAdapter setAdapterStatement(StatementAdapter statementAdapter){
        try{
            statementAdapter = new StatementAdapter(activity) {
                @Override
                public void onBindViewHolder(StatementHolder holder, int position) {
                    holder.date.setText(ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getDate_ago());


                    holder.setItemOnClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int rowPosition) {
                            if (ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(rowPosition).getSymbol().equalsIgnoreCase("-")){
                                //initial to request survey
                                ModelCartSurvey.newInstance().getSerialized().restaurantModel.setName(activity.getString(R.string.head_restaurant) + ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(rowPosition).getAccount_credit());
                                getSurveyRequest().setAccount_no(ModelCart.getInstance().getToken().getResult().getAccountNo());
                                getSurveyRequest().setToken(ModelCart.getInstance().getToken().getResult().getToken());
                                getSurveyRequest().setCode("canteen");
                                getSurveyRequest().setType("transaction");
                                getSurveyRequest().setRefID(ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(rowPosition).getTransaction_id());
                                Log.d("SurveyRequest", new Gson().toJson(getSurveyRequest()));

                                ((MainActivity) activity).setIntent(SurveyActivity.class,R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                            }
                        }
                    });

                    if (ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getSymbol().equalsIgnoreCase("+")){
                        holder.rating.setVisibility(View.GONE);
                        holder.cash.setText("+ " + ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getCredit() + "฿");
                        holder.cash.setTextColor(activity.getResources().getColor(R.color.colorBlueStrongNormal));

                    }
                    else if (ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getSymbol().equalsIgnoreCase("-")){
                        holder.rating.setVisibility(View.VISIBLE);
                        holder.rating.setText(R.string.txt_survey);
                        holder.cash.setText("- " + ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getDebit() + "฿");
                        holder.cash.setTextColor(activity.getResources().getColor(R.color.colorRedNormal));

                    }

                    Glide.with(activity)
                            .load(ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getImage_path())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .centerCrop()
                            .into(holder.imgProfile);

                    holder.name.setText(ModelCart.getInstance().getUserInfo().getResult().getTransaction().get(position).getFirst_name());
                }
            };

            return statementAdapter;
        }catch (NullPointerException e){
            return null;
        }
    }
}
