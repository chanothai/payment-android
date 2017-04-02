package com.company.zicure.payment.fragment;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.R;
import com.company.zicure.payment.adapters.StatementAdapter;
import com.company.zicure.payment.contents.ContentAdapterCart;
import com.company.zicure.payment.interfaces.ItemClickListener;
import com.company.zicure.payment.network.ClientHttp;
import com.company.zicure.survey.activity.SurveyActivity;
import com.company.zicure.survey.models.QuestionRequest;
import com.company.zicure.survey.utilize.ModelCartSurvey;

import com.google.gson.Gson;
import com.joooonho.SelectableRoundedImageView;
import com.zicure.company.com.model.models.RequestTokenModel;
import com.zicure.company.com.model.util.FormatCash;
import com.zicure.company.com.model.util.ModelCart;
import com.zicure.company.com.model.util.ResizeScreen;

import profilemof.zicure.company.com.profilemof.activity.ProfileActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPayFragment extends Fragment implements View.OnClickListener , ViewTreeObserver.OnGlobalLayoutListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String account;
    private String token;

    private ViewTreeObserver viewTreeObserver = null;
    //View
    private Button btnPay = null;
    private Button btnReceive = null;
    private RecyclerView listStatement = null;
    private LinearLayout layoutBtn = null;

    private SelectableRoundedImageView circleImageView = null;
    private StatementAdapter statementAdapter = null;

    //Layout
    private RelativeLayout layoutProfile = null;
    private LinearLayout layoutStatement = null;

    private final int DIVIDE_FONT = 8;

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
        layoutBtn = (LinearLayout) root.findViewById(R.id.layout_btn_control);

        btnPay.setOnClickListener(this);
        btnReceive.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resizeImgProfile();
        setDrawableScale();

        ModelCart.getInstance().setMode("");
        if (savedInstanceState == null){
            if (account != null && token != null){
                //make image to owner account;
                Glide.with(this)
                        .load(ModelCart.getInstance().getUserInfo().getResult().getCustomer().getImage_path())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(circleImageView);

                setAdapterStatement();
            }
        }
    }

    private void setDrawableScale(){
        viewTreeObserver = layoutBtn.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(this);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layoutBtn.getLayoutParams();
        params.bottomMargin = circleImageView.getLayoutParams().height / 2;
        layoutBtn.setLayoutParams(params);
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

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (view.getId()){
            case R.id.btn_pay_cash:
                ModelCart.getInstance().getAccountUser().type = getString(R.string.tag_pay);
                fragment = new PayCashFragment();
                transaction.replace(R.id.container, fragment,getString(R.string.tagScanQRFragment));
                transaction.addToBackStack(getString(R.string.tag_pay));
                transaction.commit();

                //set mode
                ModelCart.getInstance().setMode(getString(R.string.txt_wallet));
                break;
            case R.id.btn_receive_cash:
                ModelCart.getInstance().getAccountUser().type = getString(R.string.tag_receive);
                fragment = new ReceiveCashFragment();
                transaction.replace(R.id.container, fragment);
                transaction.addToBackStack(getString(R.string.tag_receive));
                transaction.commit();

                //set mode
                ModelCart.getInstance().setMode(getString(R.string.txt_wallet));
                break;
            case R.id.img_profile:
                ((MainActivity)getActivity()).setIntent(ProfileActivity.class, R.anim.anim_slide_in_top, R.anim.anim_slide_out_top);
                break;
        }
    }

    public void setAdapterStatement(){
        ContentAdapterCart adapterCart = new ContentAdapterCart(getActivity());

        listStatement.setLayoutManager(new LinearLayoutManager(getActivity()));
        listStatement.setAdapter(adapterCart.setAdapterStatement(statementAdapter));
        listStatement.setItemAnimator(new DefaultItemAnimator());
        listStatement.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onGlobalLayout() {
        try {
            layoutBtn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            int width = btnPay.getWidth() / 2;
            int height = width + 25;

            Drawable imgReceive = getActivity().getDrawable(R.drawable.ic_scan_qrcode);
            imgReceive.setBounds(0,0,width,height);
            btnPay.setCompoundDrawables(null, imgReceive, null, null);

            Drawable imgPay = getActivity().getDrawable(R.drawable.ic_receive_qrcode);
            imgPay.setBounds(0,0,width,height);
            btnReceive.setCompoundDrawables(null, imgPay, null,null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
