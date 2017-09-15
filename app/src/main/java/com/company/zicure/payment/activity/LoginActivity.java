package com.company.zicure.payment.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.payment.R;
import com.company.zicure.payment.network.ClientHttp;
import com.zicure.company.com.model.common.BaseActivity;
import com.zicure.company.com.model.models.RequestRegister;
import com.zicure.company.com.model.models.ResponseRegister;
import com.zicure.company.com.model.models.ResponseUserCode;
import com.zicure.company.com.model.util.EventBusCart;
import com.squareup.otto.Subscribe;
import com.zicure.company.com.model.util.ModelCart;
import com.zicure.company.com.model.util.ResizeScreen;
import com.zicure.company.com.model.util.ToolbarManager;
import com.zicure.company.com.model.util.VarialableConnect;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private String authToken = null;
    private Dialog dialog = null;

    private Button btnConfirm = null;
    private TextView txtCode = null;
    private ImageView img = null;
    @Bind(R.id.form_message)
    LinearLayout messageLayout;
    @Bind(R.id.form_btn)
    LinearLayout btnLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title_toolbar)
    TextView titleToolbar;
    @Bind(R.id.appbarLayout)
    AppBarLayout appBarLayout;

    private SharedPreferences pref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBusCart.getInstance().getEventBus().register(this);
        ButterKnife.bind(this);
        bindView();
        setToolbar();

        if (savedInstanceState == null){
            pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
        }
    }

    private void bindView(){
        btnConfirm = (Button)findViewById(R.id.btn_active);
        btnConfirm.setOnClickListener(this);

        txtCode = (TextView)findViewById(R.id.show_auth_code);

        img = (ImageView)findViewById(R.id.img_mof_pay);
        img.setImageResource(R.drawable.logo_mof);
        resizeImage();
    }

    private void setToolbar(){
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setToolbar(toolbar, titleToolbar, getString(R.string.login_th));
        toolbarManager.setAppbarLayout(appBarLayout);
        setMarginLayout();
    }

    private void setMarginLayout(){
        messageLayout.setPadding(0,0,0,btnLayout.getLayoutParams().height);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initial();
    }

    private void initial(){
        Intent intent = getIntent();
        String authToken = intent.getStringExtra(Intent.EXTRA_TEXT);
        storeAuthToken(authToken);
    }

    private void storeAuthToken(String authToken){
        if (authToken != null && !authToken.isEmpty()){
            pref = getSharedPreferences(VarialableConnect.fileKey , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(VarialableConnect.authTokenKey, authToken);
            editor.apply();
        }

        checkAuthToken();
    }

    private void resizeImage(){
        ResizeScreen screen = new ResizeScreen(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
        params.height = screen.widthScreen(5);
        params.width = screen.widthScreen(5);
        img.setLayoutParams(params);
    }

    private void checkAuthToken(){
        pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
        authToken = pref.getString(VarialableConnect.authTokenKey, null);

        if (authToken == null){
            showLoadingDialog();
            ClientHttp.getInstance(this).requestUserCode(VarialableConnect.clientID);
        }else{
            restoreAccount(authToken);
        }
    }

    private void restoreAccount(String authToken){
        pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
        String account = pref.getString(VarialableConnect.accountKey, null);

        if (account != null){
            Bundle bundle = new Bundle();
            bundle.putString(VarialableConnect.clearStack,"clear");
            bundle.putString(VarialableConnect.accountKey, account);
            openActivity(MainActivity.class, bundle, true);
        }else{
            RequestRegister.DeviceTokenBean deviceToken = new RequestRegister.DeviceTokenBean();
            deviceToken.setAuth_token(authToken);

            RequestRegister request = new RequestRegister();
            request.setDeviceToken(deviceToken);

            showLoadingDialog();
            ClientHttp.getInstance(this).requestAccount(request);
        }
    }

    @Subscribe
    public void onEventGenUserCode(ResponseUserCode response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            ModelCart.getInstance().getDeviceToken().setResult(response.getResult());
            String code = getString(R.string.domain_code_th)+ " " + ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCode();
            txtCode.setText(code);
            Toast.makeText(this, ModelCart.getInstance().getDeviceToken().getResult().getSuccess() , Toast.LENGTH_SHORT).show();

            //store user_code, expire_code
            storeDeviceToken(ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCode(), ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCodeExpire());
        }
        dismissDialog();
    }

    private void storeDeviceToken(String authCode, String expireCode){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(VarialableConnect.authCodeKey, authCode);
        editor.putString(VarialableConnect.expireCodeKey, expireCode);
        editor.apply();
    }

    @Subscribe
    public void onEventRegister(ResponseRegister response){
        try {
            if (response.getResult().getCode().equalsIgnoreCase("SUCCESS")){
                Toast.makeText(this, response.getResult().getAccount_no(), Toast.LENGTH_SHORT).show();
                storeAccount(response.getResult().getAccount_no());
                dismissDialog();
            }else{
                Toast.makeText(this, response.getResult().getDescription(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();

                showLoadingDialog();
                ClientHttp.getInstance(this).requestUserCode(VarialableConnect.clientID);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void storeAccount(String account){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(VarialableConnect.accountKey, account);
        editor.apply();

        Bundle bundle = new Bundle();
        bundle.putString(VarialableConnect.accountKey, account);
        openActivity(MainActivity.class, bundle, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:{
                finish();
                break;
            }

            case R.id.btn_active:{
                finish();
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusCart.getInstance().getEventBus().unregister(this);
    }
}
