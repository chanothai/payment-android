package com.company.zicure.payment.activity;

import android.app.Dialog;
import android.content.ComponentName;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.payment.R;
import com.company.zicure.payment.common.BaseActivity;
import com.company.zicure.payment.model.ResponseUserCode;
import com.company.zicure.payment.network.ClientHttp;
import com.company.zicure.payment.util.EventBusCart;
import com.company.zicure.payment.util.ModelCart;
import com.company.zicure.payment.util.ToolbarManager;
import com.company.zicure.payment.util.VarialableConnect;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import gallery.zicure.company.com.gallery.util.ResizeScreen;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private String authToken = null;
    private Dialog dialog = null;

    private Button btnCancel = null;
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
    @Bind(R.id.title_logo)
    ImageView titleLogo;

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

    private void storeAuthToken(String authToken){
        if (authToken != null){
            pref = getSharedPreferences(VarialableConnect.fileKey , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString( VarialableConnect.authTokenKey, authToken);
            editor.commit();
        }

        checkAuthToken();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String authToken = intent.getStringExtra(Intent.EXTRA_TEXT);
        storeAuthToken(authToken);
        Toast.makeText(this, authToken, Toast.LENGTH_SHORT).show();
    }

    private void bindView(){
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnConfirm = (Button)findViewById(R.id.btn_active);
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        txtCode = (TextView)findViewById(R.id.show_auth_code);

        img = (ImageView)findViewById(R.id.img_mof_pay);
        resizeImage();
    }

    private void resizeImage(){
        ResizeScreen screen = new ResizeScreen(this);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
        params.height = screen.widthScreen(5);
        params.width = screen.widthScreen(5);
        img.setLayoutParams(params);
    }

    private void setToolbar(){
        ToolbarManager toolbarManager = new ToolbarManager(this);
        toolbarManager.setToolbar(toolbar, titleToolbar, getString(R.string.login_th));
        toolbarManager.setAppbarLayout(appBarLayout);
        titleLogo.setVisibility(View.INVISIBLE);
        setMarginLayout();
    }

    private void setMarginLayout(){
        messageLayout.setPadding(0,0,0,btnLayout.getLayoutParams().height);
    }

    private void checkAuthToken(){
        pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
        authToken = pref.getString(VarialableConnect.authTokenKey, null);

        if (authToken == null){
            img.setImageResource(R.drawable.logo_mof);
            showLoadingDialog();
            ClientHttp.getInstance(this).requestUserCode(VarialableConnect.clientID);
        }else{
            validateCurrentDate();
        }
    }

    private void validateCurrentDate(){
        pref = getSharedPreferences(VarialableConnect.fileKey, Context.MODE_PRIVATE);
        String expireCode = pref.getString(VarialableConnect.expireCodeKey, null);

        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            Date strDate = dateFormat.parse(expireCode);

            if (date.after(strDate)){
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
            }else {
                openActivity(MainActivity.class, true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEventGenUserCode(ResponseUserCode response){
        if (response.getResult().getSuccess().equalsIgnoreCase("OK")){
            ModelCart.getInstance().getDeviceToken().setResult(response.getResult());
            String code = getString(R.string.domain_code_th)+ " " + ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCode();
            txtCode.setText(code);
            Toast.makeText(this, ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCode() , Toast.LENGTH_SHORT).show();

            //store user_code, expire_code
            storeDeviceToken(ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCode(), ModelCart.getInstance().getDeviceToken().getResult().getDeviceToken().getUserCodeExpire());
        }

        dismissDialog();
    }

    private void storeDeviceToken(String authCode, String expireCode){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(VarialableConnect.authCodeKey, authCode);
        editor.putString(VarialableConnect.expireCodeKey, expireCode);
        editor.commit();
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
