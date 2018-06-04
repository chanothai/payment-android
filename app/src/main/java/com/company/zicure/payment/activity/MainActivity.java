package com.company.zicure.payment.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.payment.R;
import com.company.zicure.payment.fragment.GenerateQRCodeFragment;
import com.company.zicure.payment.fragment.MainPayFragment;
import com.company.zicure.payment.fragment.PayResultFragment;
import com.company.zicure.payment.fragment.PayCashFragment;
import com.company.zicure.payment.network.ClientHttp;
import com.zicure.company.com.model.common.BaseActivity;
import com.zicure.company.com.model.models.AccountUser;
import com.zicure.company.com.model.models.RequestTokenModel;
import com.zicure.company.com.model.models.ResponseBalance;
import com.zicure.company.com.model.models.ResponseProfile;
import com.zicure.company.com.model.models.ResponseQRCode;
import com.zicure.company.com.model.models.ResponseScanQR;
import com.zicure.company.com.model.models.ResponseTokenModel;
import com.zicure.company.com.model.util.EventBusCart;
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.zicure.company.com.model.util.FormatCash;
import com.zicure.company.com.model.util.ModelCart;
import com.zicure.company.com.model.util.ToolbarManager;
import com.zicure.company.com.model.util.VarialableConnect;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.amount_cash)
    TextView amountCash;

    //Toolbar
    @Bind(R.id.appbarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbarMenu;
    @Bind(R.id.title_toolbar)
    TextView titleToolbar;

    //Refresh
    @Bind(R.id.refresh_main)
    SwipeRefreshLayout refreshMain;

    //Fragment
    private ObjectAnimator rotationLogo = null;

    private double amount;
    private String accountUser;
    private String clearStack = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);
        setToolbar();

        appBarLayout.setKeepScreenOn(true);
        if (savedInstanceState == null){
            //initial stack
            Bundle bundle = getIntent().getExtras();
            String accounted = bundle.getString(VarialableConnect.accountKey);
            clearStack = bundle.getString(VarialableConnect.clearStack);
            if (accounted != null){
                accountUser = accounted;
            }else{
                SharedPreferences pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
                accountUser = pref.getString(VarialableConnect.accountKey, null);
            }
        }
    }

    public void callUserInfo(){
        RequestTokenModel tokenModel = new RequestTokenModel();
        tokenModel.setAccountNo(ModelCart.getInstance().getToken().getResult().getAccountNo());
        tokenModel.setToken(ModelCart.getInstance().getToken().getResult().getToken());
        tokenModel.setCurrency("THB");

        ClientHttp.getInstance(this).requestUserInfo(tokenModel);
    }


    private void setToolbar(){
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setToolbar(toolbarMenu, titleToolbar, getString(R.string.app_name));
        toolbarManager.setAppbarLayout(appBarLayout);

        //set swipe refresh
        refreshMain.setOnRefreshListener(this);
        refreshMain.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
    }

    private void getFirstToken(){
        if (accountUser != null){
            ModelCart.getInstance().getAccountUser().accountNo = accountUser;
        }else{
            SharedPreferences pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
            accountUser = pref.getString(VarialableConnect.accountKey, null);
            ModelCart.getInstance().getAccountUser().accountNo = accountUser;
        }

        showLoadingDialog();
        RequestTokenModel tokenModelBuyer = new RequestTokenModel();
        tokenModelBuyer.setAccountNo(accountUser);
        ClientHttp.getInstance(this).userCallToken(tokenModelBuyer);
    }

    public void callMainPayFragment(){
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, MainPayFragment.newInstance(accountUser, ModelCart.getInstance().getToken().getResult().getToken()), getString(R.string.tagMainPayFragment));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callPayCashFragment(){
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, PayCashFragment.newInstance("",""),getString(R.string.tagScanQRFragment));
            transaction.addToBackStack(getString(R.string.tag_receive_qr_card));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callGenerateQRCodeFragment(String qrcode){
        try{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, GenerateQRCodeFragment.newInstance(String.valueOf(ModelCart.getInstance().getAccountUser().amount),qrcode));
            transaction.addToBackStack(getString(R.string.tag_show_qrcode));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void callPayResultFragment(){
        try{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, PayResultFragment.newInstance(ModelCart.getInstance().getAccountUser().type, String.valueOf(ModelCart.getInstance().getAccountUser().amount)));
            transaction.addToBackStack(null);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String setFormatCash(String balance){
        try{
            amount = Double.parseDouble(balance);
            return FormatCash.newInstance().setFormatCash(amount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setIntent(Class<?> nameClass, int overrideTransitionIn, int overrideTransitionOut){
        openActivity(nameClass);
        overridePendingTransition(overrideTransitionIn, overrideTransitionOut);
    }

    public void setBalance(){
        RequestTokenModel tokenModel = new RequestTokenModel();
        tokenModel.setAccountNo(accountUser);
        tokenModel.setCurrency("THB");
        tokenModel.setToken(ModelCart.getInstance().getAccountUser().token);

        if (ModelCart.getInstance().getAccountUser().balance != null){
            ClientHttp.getInstance(this).requestBalance(tokenModel);
        }else{//Load balance First time
            ClientHttp.getInstance(this).requestBalance(tokenModel);
        }
    }

    @Subscribe
    public void onEventBalance(ResponseBalance balance){
        try{
            if (!balance.getResult().getBalance().isEmpty()){
                if (ModelCart.getInstance().getAccountUser().balance == null){
                    ModelCart.getInstance().getAccountUser().balance = balance.getResult().getBalance();
                }

                final String resultBalance = balance.getResult().getBalance()+ " " + getString(R.string.cash_thai);
                if (ModelCart.getInstance().getAccountUser().balance.equalsIgnoreCase(balance.getResult().getBalance())){
                    getSupportFragmentManager().popBackStack(getString(R.string.tag_show_qrcode), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else{
                    callPayResultFragment();
                    amountCash.setText(resultBalance);
                    ModelCart.getInstance().getAccountUser().balance = balance.getResult().getBalance();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            String resultBanalnce = setFormatCash("0 " + getString(R.string.cash_thai));
            amountCash.setText(resultBanalnce);
        }
        dismissDialog();
    }

    @Subscribe
    public void onEventUserInfo(ResponseProfile response){
        try {
            if (response.getResult() != null){
                ModelCart.getInstance().getUserInfo().setResult(response.getResult());
                ModelCart.getInstance().getAccountUser().balance = response.getResult().getBalance();
            }

            if (!response.getResult().getBalance().isEmpty()){
                amountCash.setText(ModelCart.getInstance().getUserInfo().getResult().getBalance() + " " + getString(R.string.cash_thai));
            }

            //set first page
            checkPage();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        refreshMain.setRefreshing(false);
        dismissDialog();
    }

    private void checkPage(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            callMainPayFragment();
        }
    }

    @Subscribe
    public void onEventReponseToken(ResponseTokenModel tokenModel){
        try{
            ResponseTokenModel.Result result = tokenModel.getResult();
            Log.d("Token", new Gson().toJson(tokenModel));
            if (!result.getToken().isEmpty()){
                //Store Data
                ModelCart.getInstance().getToken().setResult(tokenModel.getResult());
                ModelCart.getInstance().getAccountUser().token = tokenModel.getResult().getToken();
                callUserInfo();
            }
        }catch (Exception e){
            e.printStackTrace();
            getFirstToken();
        }
    }

    @Subscribe
    public void onEventReceiveCash(ResponseQRCode qrCode){
        try{
            ResponseQRCode.Result result = qrCode.getResult();
            ModelCart.getInstance().getQRcode().setResult(result);

            if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_wallet))){
                callGenerateQRCodeFragment(result.getUrlQRCode());
            }

            else if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_qrcard))){
                ModelCart.getInstance().getAccountUser().code = qrCode.getResult().getUrlQRCode();
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
                ModelCart.getInstance().getResponseScanQR().setResult(scanQR.getResult());
                callPayResultFragment();
                Toast.makeText(getApplicationContext(), result.getCode(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), result.getDescription(), Toast.LENGTH_SHORT).show();
                callCamera();
            }
            //reset user because it changed user to receive cash qr card before
            ModelCart.getInstance().getAccountUser().accountNo = accountUser;
        }catch (NullPointerException e){
            e.printStackTrace();
            callCamera();
        }

        dismissDialog();
    }

    public void callCamera(){
        FragmentManager fm = getSupportFragmentManager();
        PayCashFragment fragment = (PayCashFragment)fm.findFragmentByTag(getString(R.string.tagScanQRFragment));
        fragment.qrScanner();
        fragment.resumeCamera();
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

    @Override
    public void onRefresh() {
        if (ModelCart.getInstance().getAccountUser().token != null){
            showLoadingDialog();
            callUserInfo();
        }else{
            getFragmentManager();
        }
    }
}
