package com.company.zicure.payment.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.zicure.payment.R;
import com.company.zicure.payment.util.ModelCart;
import com.company.zicure.survey.common.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends BaseActivity {

    @Bind(R.id.img_profile)
    CircleImageView imgProfile;
    @Bind(R.id.qr_profile)
    ImageView qrProfile;
    @Bind(R.id.layout_profile)
    LinearLayout layoutProfile;

    //Form profileInfo
    @Bind(R.id.form_id_card)
    TextView txtIdCard;
    @Bind(R.id.form_firstname)
    TextView txtFirstName;
    @Bind(R.id.form_lastname)
    TextView txtLastName;
    @Bind(R.id.form_birth)
    TextView txtBirth;
    @Bind(R.id.form_religion)
    TextView txtReligion;
    @Bind(R.id.form_address)
    TextView txtAddress;
    @Bind(R.id.form_issued)
    TextView txtIssue;
    @Bind(R.id.form_expired)
    TextView txtExpire;

    private MultiFormatWriter multiFormatWriter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        if (savedInstanceState == null){
            setProfileInfo();
            calPayoutParams();
        }
    }

    private void fixedImgProfile(){
        if (ModelCart.getInstance().getModel().accountUserModel.accountNo.equalsIgnoreCase(getString(R.string.account1))) {
            imgProfile.setImageResource(R.drawable.base);
        }else if (ModelCart.getInstance().getModel().accountUserModel.accountNo.equalsIgnoreCase(getString(R.string.account2))){
            imgProfile.setImageResource(R.drawable.yajai);
        }
    }
    private void setProfileInfo(){
        fixedImgProfile();
        txtIdCard.setText("1 7199 00291 478");
        txtFirstName.setText("ชื่อ");
        txtLastName.setText("นามสกุล");
        txtBirth.setText("วันเกิด");
        txtReligion.setText("ศาสนา");
        txtAddress.setText("ที่อยุ่");
        txtIssue.setText("00/2017");
        txtExpire.setText("00/2020");
    }

    private void calPayoutParams(){
        ViewTreeObserver observer = qrProfile.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                generateQRCode(qrProfile.getHeight(), qrProfile.getHeight());
                Log.d("LayoutParam", String.valueOf(qrProfile.getWidth())+ " , "+ String.valueOf(qrProfile.getHeight()));
            }
        });
    }

    private void generateQRCode(int width, int height){
        if (multiFormatWriter == null){
            multiFormatWriter = new MultiFormatWriter();
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode("1719900291478", BarcodeFormat.QR_CODE, width, height);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrProfile.setImageBitmap(bitmap);
            }catch (WriterException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.anim_slide_out_bottom);
    }
}
