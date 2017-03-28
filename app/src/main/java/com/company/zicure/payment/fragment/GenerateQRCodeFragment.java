package com.company.zicure.payment.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.zicure.company.com.model.util.FormatCash;
import com.zicure.company.com.model.util.NextzyUtil;

import gallery.zicure.company.com.gallery.util.ResizeScreen;

import static android.R.attr.name;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenerateQRCodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerateQRCodeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "amount_cash";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String cash;
    private String code;

    private CountDownTimer countDownTimer = null;
    private MultiFormatWriter multiFormatWriter = null;

    //View
    private TextView timeRefresh = null;
    private TextView amountCash = null;
    private ImageView imgQrCode = null;
    public GenerateQRCodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenerateQRCodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GenerateQRCodeFragment newInstance(String param1, String param2) {
        GenerateQRCodeFragment fragment = new GenerateQRCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cash = getArguments().getString(ARG_PARAM1);
            code = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_generate_qrcode, container, false);
        timeRefresh = (TextView) root.findViewById(R.id.time_refresh);
        amountCash = (TextView) root.findViewById(R.id.amount_cash);
        imgQrCode = (ImageView) root.findViewById(R.id.img_qrcode);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imgQrCode.setOnClickListener(this);
        if (savedInstanceState == null){
            calLayoutQrcode();
            double number = Double.parseDouble(cash);
            amountCash.setText(FormatCash.newInstance().setFormatCash(number));
            runTimeRefresh();
        }
    }

    private void calLayoutQrcode(){
        ResizeScreen resizeScreen = new ResizeScreen(getActivity());
        int width = resizeScreen.widthScreen(1);
        generateQRCode(width, width);
    }

    private void runTimeRefresh(){
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRefresh.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                NextzyUtil.launch(new NextzyUtil.LaunchCallback() {
                    @Override
                    public void onLaunchCallback() {
                        getFragmentManager().popBackStack(getString(R.string.tag_show_qrcode), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                });
            }
        }.start();
    }

    private void generateQRCode(int width, int height){
        if (multiFormatWriter == null){
            multiFormatWriter = new MultiFormatWriter();
            String strQR = code + "," + cash;
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode(strQR, BarcodeFormat.QR_CODE, width, height);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imgQrCode.setImageBitmap(bitmap);
            }catch (WriterException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).callPayResultFragment();
    }
}
