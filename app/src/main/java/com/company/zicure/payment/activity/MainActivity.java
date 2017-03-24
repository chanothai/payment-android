package com.company.zicure.payment.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.payment.R;
import com.company.zicure.payment.common.BaseActivity;
import com.company.zicure.payment.fragment.GenerateQRCodeFragment;
import com.company.zicure.payment.fragment.MainPayFragment;
import com.company.zicure.payment.fragment.PayResultFragment;
import com.company.zicure.payment.fragment.PayCashFragment;
import com.company.zicure.payment.fragment.ReceiveCashFragment;
import com.company.zicure.payment.model.RequestTokenModel;
import com.company.zicure.payment.model.ResponseBalance;
import com.company.zicure.payment.model.ResponseQRCode;
import com.company.zicure.payment.model.ResponseScanQR;
import com.company.zicure.payment.model.ResponseStatement;
import com.company.zicure.payment.model.ResponseTokenModel;
import com.company.zicure.payment.network.ClientHttp;
import com.company.zicure.payment.store.StoreAccount;
import com.company.zicure.payment.util.EventBusCart;
import com.company.zicure.payment.util.FormatCash;
import com.company.zicure.payment.util.ModelCart;
import com.company.zicure.payment.util.VarialableConnect;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import profilemof.zicure.company.com.profilemof.utilize.ModelCartProfile;

public class MainActivity extends BaseActivity {

    @Bind(R.id.amount_cash)
    TextView amountCash;

    //Toolbar
    @Bind(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbarMenu;
    @Bind(R.id.title_toolbar)
    TextView titleToolbar;
    @Bind(R.id.title_logo)
    ImageView titleLogo;

    //Fragment
    private ObjectAnimator rotationLogo = null;

    private double amount;
    private String accountUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            accountUser = VarialableConnect.account;
            ModelCartProfile.getInstance().getUser().setAccount(accountUser);
            setToolbar();

            Bundle bundle = getIntent().getExtras();
            try{
                if (bundle != null){
                    String authCode = bundle.getString("auth_code");
                    if (authCode != null){
                        Toast.makeText(this, authCode, Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    private void setToolbar(){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        params.height = getActionBarHeight() + getStatusBarHeight() + 10;
        appBarLayout.setLayoutParams(params);
        toolbarMenu.setTitle("");
        toolbarMenu.setPadding(0, getStatusBarHeight() + 10 , 0,0);
        titleToolbar.setText("MOF PAY");
        setSupportActionBar(toolbarMenu);
    }

    private int getActionBarHeight(){
        int actionBarHeight = 0;
        final TypedArray styleAttributes = getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styleAttributes.getDimension(0,0);
        styleAttributes.recycle();

        return actionBarHeight;
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setLogoAnimation(){
        if (rotationLogo == null){
            rotationLogo = ObjectAnimator.ofFloat(titleLogo, View.ROTATION_Y, 0,360);
            rotationLogo.setRepeatCount(ValueAnimator.INFINITE);
            rotationLogo.setDuration(10000);
            rotationLogo.setInterpolator(new AccelerateDecelerateInterpolator());
        }
    }

    private void getFirstToken(){
        showLoadingDialog();
        RequestTokenModel tokenModelBuyer = new RequestTokenModel();
        tokenModelBuyer.setAccountNo(accountUser);
        ClientHttp.getInstance(this).userCallToken(tokenModelBuyer);
    }

    public void callMainPayFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, MainPayFragment.newInstance(accountUser, getModel().accountUserModel.token),getString(R.string.tagMainPayFragment));
        transaction.commit();
    }

    public void callPayCashFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, PayCashFragment.newInstance("",""));
        transaction.addToBackStack(getString(R.string.tag_receive_qr_card));
        transaction.commit();
    }

    public void callGenerateQRCodeFragment(String qrcode){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, GenerateQRCodeFragment.newInstance(String.valueOf(ModelCart.getInstance().getModel().accountUserModel.amount),qrcode));
        transaction.addToBackStack(getString(R.string.tag_show_qrcode));
        transaction.commit();
    }

    public void callPayResultFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, PayResultFragment.newInstance(getModel().accountUserModel.type, String.valueOf(ModelCart.getInstance().getModel().accountUserModel.amount)));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String setFormatCash(String balance){
        amount = Double.parseDouble(balance);
        return FormatCash.newInstance().setFormatCash(amount);
    }

    private StoreAccount getModel(){
        return ModelCart.getInstance().getModel();
    }

    public void setIntent(Class<?> nameClass, int overrideTransitionIn, int overrideTransitionOut){
        openActivity(nameClass);
        overridePendingTransition(overrideTransitionIn, overrideTransitionOut);
    }

    public void setBalance(){
        RequestTokenModel tokenModel = new RequestTokenModel();
        tokenModel.setAccountNo(accountUser);
        tokenModel.setToken(getModel().accountUserModel.token);

        if (getModel().accountUserModel.balance != null){
            String str1 = setFormatCash(getModel().accountUserModel.balance);
            String str2 =   amountCash.getText().toString();

            ClientHttp.getInstance(this).requestBalance(tokenModel);
            if (!str1.equalsIgnoreCase(str2)){

            }
        }else{//Load balance First time
            ClientHttp.getInstance(this).requestBalance(tokenModel);
        }
    }

    @Subscribe
    public void onEventStatement(ResponseStatement statement){
        getModel().option = statement.getResult();
        Log.d("Statement", new Gson().toJson(getModel().option));

        ModelCart.getInstance().getModel().accountUserModel.accountNo = accountUser;
        //Call statement for show
        FragmentManager fm = getSupportFragmentManager();
        MainPayFragment fragment = (MainPayFragment)fm.findFragmentByTag(getString(R.string.tagMainPayFragment));
        fragment.setAdapterStatement();
    }

    @Subscribe
    public void onEventBalance(ResponseBalance balance){
        ResponseBalance.Result result = balance.getResult();
        Log.d("Balance", new Gson().toJson(balance));
        if (balance != null){
            if (!result.getBalance().isEmpty()){
                String currentBalance = setFormatCash(result.getBalance());
                amountCash.setText(currentBalance);
                ModelCart.getInstance().getModel().accountUserModel.balance = result.getBalance();
            }
            dismissDialog();
        }
    }

    @Subscribe
    public void onEventReponseToken(ResponseTokenModel tokenModel){
        ResponseTokenModel.Result result = tokenModel.getResult();

        Log.d("Token", new Gson().toJson(tokenModel));
        if (!result.getToken().isEmpty()){
            //Store Data
            ModelCart.getInstance().getModel().accountUserModel.token = result.getToken();
            ModelCart.getInstance().getModel().accountUserModel.accountNo = result.getAccountNo();

            //get balance
            setBalance();
            //set first page
            callMainPayFragment();
        }
    }

    @Subscribe
    public void onEventReceiveCash(ResponseQRCode qrCode){
        try{
            ResponseQRCode.Result result = qrCode.getResult();

            if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_wallet))){
                callGenerateQRCodeFragment(result.getUrlQRCode());
            }

            else if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_qrcard))){
                ModelCart.getInstance().getModel().accountUserModel.code = result.getUrlQRCode();
                callPayCashFragment();
            }

        }catch (NullPointerException e){
            e.printStackTrace();
            Toast.makeText(this, R.string.message_error_check_money_th, Toast.LENGTH_SHORT).show();
        }

        dismissDialog();
    }

    @Subscribe
    public void onEventPayCash(ResponseScanQR scanQR){
        ResponseScanQR.Result result = scanQR.getResult();
        Log.d("Paycash", new Gson().toJson(scanQR));
        try {
            if (result.getCode().equalsIgnoreCase("SUCCESS")){
                callPayResultFragment();
                Toast.makeText(getApplicationContext(), result.getCode(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), result.getDescription(), Toast.LENGTH_SHORT).show();
                callCamera();
            }
            //reset user because it changed user to receive cash qr card before
            ModelCart.getInstance().getModel().accountUserModel.accountNo = accountUser;
            dismissDialog();
        }catch (NullPointerException e){
            e.printStackTrace();
            dismissDialog();
        }
    }

    private void callCamera(){
        FragmentManager fm = getSupportFragmentManager();
        PayCashFragment fragment = (PayCashFragment)fm.findFragmentByTag(getString(R.string.tagScanQRFragment));
        fragment.qrScanner();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 123:
                callCamera();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFirstToken();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0){
            count--;
            try{
                String tag = getSupportFragmentManager().getBackStackEntryAt(count).getName();
                if (!tag.isEmpty()){
                    if (tag.equalsIgnoreCase(getString(R.string.tag_pay))){
                        super.onBackPressed();
                    }
                    else if (tag.equalsIgnoreCase(getString(R.string.tag_receive))){
                        super.onBackPressed();
                    }
                    else if (tag.equalsIgnoreCase(getString(R.string.tag_receive_qr_card))){
                        getSupportFragmentManager().popBackStack(getString(R.string.tag_receive_qr_card), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }

                    else if (tag.equalsIgnoreCase(getString(R.string.tag_show_qrcode))){
                        getSupportFragmentManager().popBackStack(getString(R.string.tag_show_qrcode), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }
}
