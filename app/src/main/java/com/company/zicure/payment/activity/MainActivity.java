package com.company.zicure.payment.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.payment.R;
import com.company.zicure.payment.common.BaseActivity;
import com.company.zicure.payment.fragment.GenerateQRCodeFragment;
import com.company.zicure.payment.fragment.MainPayFragment;
import com.company.zicure.payment.fragment.PayResultFragment;
import com.company.zicure.payment.fragment.PayCashFragment;
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
import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.amount_cash)
    TextView amountCash;

    //Toolbar
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
            accountUser = getString(R.string.account2);
            setToolbar();
            getFirstToken();


        }
    }

    private void setToolbar(){
        toolbarMenu.setTitle("");
        titleToolbar.setText("MOF PAY");
        setSupportActionBar(toolbarMenu);

        setLogoAnimation();
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

    public void setFragmentLayout(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, MainPayFragment.newInstance(accountUser, getModel().accountUserModel.token),getString(R.string.tagMainPayFragment));
        transaction.commit();
    }

    public void setLayoutPayResult(){
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
    public void onEvent(ResponseTokenModel tokenModel){
        ResponseTokenModel.Result result = tokenModel.getResult();

        Log.d("Token", new Gson().toJson(tokenModel));
        if (!result.getToken().isEmpty()){
            //Store Data
            ModelCart.getInstance().getModel().accountUserModel.token = result.getToken();
            ModelCart.getInstance().getModel().accountUserModel.accountNo = result.getAccountNo();

            //get balance
            setBalance();
            //set first page
            setFragmentLayout();
        }
    }

    @Subscribe
    public void onEvent(ResponseQRCode qrCode){
        ResponseQRCode.Result result = qrCode.getResult();
        //Store Data
        ModelCart.getInstance().getModel().qrCodeModel.qrcode = result.getUrlQRCode();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, GenerateQRCodeFragment.newInstance(String.valueOf(ModelCart.getInstance().getModel().accountUserModel.amount),result.getUrlQRCode()));
        transaction.addToBackStack(null);
        transaction.commit();

        dismissDialog();
    }

    @Subscribe
    public void onEvent(ResponseScanQR scanQR){
        ResponseScanQR.Result result = scanQR.getResult();

        if (result.getCode().equalsIgnoreCase("SUCCESS")){
            setLayoutPayResult();
            Toast.makeText(getApplicationContext(), result.getCode(), Toast.LENGTH_SHORT).show();
            dismissDialog();
        }else{
            Toast.makeText(getApplicationContext(), result.getCode(), Toast.LENGTH_SHORT).show();
            dismissDialog();

            callCamera();
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
        rotationLogo.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rotationLogo.end();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0){
            count--;
            String tag = getSupportFragmentManager().getBackStackEntryAt(count).getName();
            if (tag != null){
                if (tag.equalsIgnoreCase(getString(R.string.tag_pay))){
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    super.onBackPressed();
                }
                else if (tag.equalsIgnoreCase(getString(R.string.tag_receive))){
                    Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    super.onBackPressed();
                }
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
