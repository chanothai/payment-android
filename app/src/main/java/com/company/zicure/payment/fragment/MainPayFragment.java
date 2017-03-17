package com.company.zicure.payment.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.R;
import com.company.zicure.payment.adapters.StatementAdapter;
import com.company.zicure.payment.interfaces.ItemClickListener;
import com.company.zicure.payment.model.AccountUserModel;
import com.company.zicure.payment.model.RequestTokenModel;
import com.company.zicure.payment.model.ResponseStatement;
import com.company.zicure.payment.network.ClientHttp;
import com.company.zicure.payment.util.FormatCash;
import com.company.zicure.payment.util.ModelCart;
import com.company.zicure.survey.activity.SurveyActivity;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.utilize.ModelCartSurvey;

import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import gallery.zicure.company.com.gallery.util.ResizeScreen;
import profilemof.zicure.company.com.profilemof.activity.ProfileActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPayFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String account;
    private String token;

    //View
    private Button btnPay = null;
    private Button btnReceive = null;
    private RecyclerView listStatement = null;

    private SelectableRoundedImageView circleImageView = null;
    private StatementAdapter statementAdapter = null;

    //Layout
    private RelativeLayout layoutProfile = null;
    private LinearLayout layoutStatement = null;

    public MainPayFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainPayFragment newInstance(String account, String token) {
        MainPayFragment fragment = new MainPayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, account);
        args.putString(ARG_PARAM2, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString(ARG_PARAM1);
            token = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_pay, container, false);
        btnPay = (Button) root.findViewById(R.id.btn_pay_cash);
        btnReceive = (Button) root.findViewById(R.id.btn_receive_cash);
        circleImageView = (SelectableRoundedImageView) root.findViewById(R.id.img_profile);
        listStatement = (RecyclerView) root.findViewById(R.id.recycler_list);

        layoutProfile = (RelativeLayout) root.findViewById(R.id.layout_profile);
        layoutStatement = (LinearLayout) root.findViewById(R.id.layout_statement);

        btnPay.setOnClickListener(this);
        btnReceive.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resizeImgProfile();
        initialAnimation();
        if (savedInstanceState == null){
            if (account != null && token != null){
                setToken();
                if (account.equalsIgnoreCase(getString(R.string.account2))){
                    circleImageView.setImageResource(R.drawable.yajai);
                }
                else if (account.equalsIgnoreCase(getString(R.string.account1))){
                    circleImageView.setImageResource(R.drawable.base);
                }
            }
        }
    }

    private void initialAnimation() {
        AnimatorSet animTogether = new AnimatorSet();
        ObjectAnimator animProfileY = ObjectAnimator.ofFloat(layoutProfile, View.TRANSLATION_Y, -1000f, 0);
        ObjectAnimator animStatementY = ObjectAnimator.ofFloat(layoutStatement, View.TRANSLATION_Y, 1000, 0);

        animTogether.playTogether(animProfileY,animStatementY);
        animTogether.start();
    }

    private void resizeImgProfile(){
        ResizeScreen resizeScreen = new ResizeScreen(getActivity());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) circleImageView.getLayoutParams();
        params.height = resizeScreen.widthScreen(4);
        params.width = resizeScreen.widthScreen(4);
        circleImageView.setLayoutParams(params);
    }

    private void setToken(){
        RequestTokenModel tokenModel = new RequestTokenModel();
        tokenModel.setAccountNo(account);
        tokenModel.setToken(token);
        ClientHttp.getInstance(getActivity()).requestStatement(tokenModel);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (view.getId()){
            case R.id.btn_pay_cash:
                ModelCart.getInstance().getModel().accountUserModel.type = getString(R.string.tag_pay);
                fragment = new PayCashFragment();
                transaction.replace(R.id.container, fragment,getString(R.string.tagScanQRFragment));
                transaction.addToBackStack(getString(R.string.tag_pay));
                transaction.commit();

                break;
            case R.id.btn_receive_cash:
                ModelCart.getInstance().getModel().accountUserModel.type = getString(R.string.tag_receive);
                fragment = new ReceiveCashFragment();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(getString(R.string.tag_receive));
                transaction.commit();

                break;
            case R.id.img_profile:
                ((MainActivity)getActivity()).setIntent(ProfileActivity.class, R.anim.anim_slide_in_top, R.anim.anim_slide_out_top);
                break;
        }
    }

    private ArrayList<ResponseStatement.Result> getStatement(){
        return ModelCart.getInstance().getModel().option;
    }

    private AccountUserModel getAccount(){
        return ModelCart.getInstance().getModel().accountUserModel;
    }

    private QuestionRequest getSurveyRequest(){
        return ModelCartSurvey.newInstance().getSerialized().questionRequest;
    }

    public void setAdapterStatement(){
        statementAdapter = new StatementAdapter(getActivity()) {
            @Override
            public void onBindViewHolder(StatementHolder holder, final int position) {
                holder.date.setText(getStatement().get(position).getDateAgo());


                holder.setItemOnClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int rowPosition) {
                        if (getStatement().get(rowPosition).getSymbol().equalsIgnoreCase("-")){
                            //initial to request survey
                            ModelCartSurvey.newInstance().getSerialized().restaurantModel.setName(getString(R.string.head_restaurant) + getStatement().get(rowPosition).getAccountCredit());
                            getSurveyRequest().setAccount_no(getAccount().accountNo);
                            getSurveyRequest().setToken(getAccount().token);
                            getSurveyRequest().setCode("canteen");
                            getSurveyRequest().setType("transaction");
                            getSurveyRequest().setRefID(getStatement().get(rowPosition).getTransactionID());
                            Log.d("SurveyRequest", new Gson().toJson(getSurveyRequest()));

                            ((MainActivity)getActivity()).setIntent(SurveyActivity.class,R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                        }
                    }
                });

                if (getStatement().get(position).getSymbol().equalsIgnoreCase("+")){
                    holder.rating.setVisibility(View.GONE);
                    holder.cash.setText("+ " + FormatCash.newInstance().setFormatCash(Double.parseDouble(getStatement().get(position).getCredit())) + "฿");
                    holder.cash.setTextColor(getResources().getColor(R.color.colorBlueStrongNormal));
                    if (ModelCart.getInstance().getModel().accountUserModel.accountNo.equalsIgnoreCase(getString(R.string.account1))){
                        holder.imgProfile.setImageResource(R.drawable.yajai);
                    }
                    else if (ModelCart.getInstance().getModel().accountUserModel.accountNo.equalsIgnoreCase(getString(R.string.account2))){
                        holder.imgProfile.setImageResource(R.drawable.base);
                    }
                }
                else if (getStatement().get(position).getSymbol().equalsIgnoreCase("-")){
                    holder.rating.setVisibility(View.VISIBLE);
                    holder.rating.setText(R.string.txt_survey);
                    holder.cash.setText("- " + FormatCash.newInstance().setFormatCash(Double.parseDouble(getStatement().get(position).getDebit())) + "฿");
                    holder.cash.setTextColor(getResources().getColor(R.color.colorRedNormal));
                    if (ModelCart.getInstance().getModel().accountUserModel.accountNo.equalsIgnoreCase(getString(R.string.account1))){
                        holder.imgProfile.setImageResource(R.drawable.yajai);
                    }
                    else if (ModelCart.getInstance().getModel().accountUserModel.accountNo.equalsIgnoreCase(getString(R.string.account2))){
                        holder.imgProfile.setImageResource(R.drawable.base);
                    }
                }

            }
        };

        listStatement.setLayoutManager(new LinearLayoutManager(getActivity()));
        listStatement.setAdapter(statementAdapter);
        listStatement.setItemAnimator(new DefaultItemAnimator());
        listStatement.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
