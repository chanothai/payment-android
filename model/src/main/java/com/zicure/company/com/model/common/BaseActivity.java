package com.zicure.company.com.model.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.zicure.company.com.model.dialog.LoadingDialogFragment;


/**
 * Created by 4GRYZ52 on 10/21/2016.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG_DIALOG_FRAGMENT = "dialog_fragment";
    private LoadingDialogFragment loadingDialogFragment = null;


    protected void openActivity(Class<?> cls) {
        openActivity(cls,null, false);
    }

    protected void openActivity(Class<?> cls, boolean finishActivity) {
        openActivity(cls,null, finishActivity);
    }

    protected void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, false);
    }

    protected void openActivity(Class<?> cls,Bundle bundle, boolean finishActivity) {
        if (cls != null){
            Intent intent = new Intent(this, cls);
            if (bundle != null){
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
        if (finishActivity){
            finish();
        }
    }

    public void showLoadingDialog(){
        dismissDialog();
        loadingDialogFragment = new LoadingDialogFragment.Builder().build();
        createFragmentDialog(loadingDialogFragment);
    }

    private void createFragmentDialog(DialogFragment dialogFragment){
        try{
            dialogFragment.show(getSupportFragmentManager(), TAG_DIALOG_FRAGMENT);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void dismissDialog(){
        try{
            if (loadingDialogFragment != null){
                loadingDialogFragment.dismiss();
            }
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

//    protected void showTitle(Toolbar toolbar, TextView titleView, String title, ImageView imgToggle){
//        if (title == null){
//            title = "PAKGON";
//        }
//
//        if (imgToggle != null){
//            imgToggle.setImageResource(R.drawable.ic_action_toc);
//            imgToggle.setColorFilter(ContextCompat.getColor(this, R.color.color_text));
//            imgToggle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MainMenuActivity.root.toggleMenu();
//                }
//            });
//        }
//        if (toolbar != null && titleView != null && imgToggle != null){
//            toolbar.setTitle("");
//            titleView.setText(title);
//
//            setSupportActionBar(toolbar);
//
//        }
//    }

}
