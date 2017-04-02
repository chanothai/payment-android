package com.company.zicure.payment.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.R;
import com.company.zicure.payment.network.ClientHttp;
import com.google.zxing.Result;
import com.zicure.company.com.model.util.FormatCash;
import com.zicure.company.com.model.util.ModelCart;
import com.zicure.company.com.model.util.PermissionRequest;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayCashFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayCashFragment extends Fragment implements ZXingScannerView.ResultHandler , View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //View
    private ZXingScannerView scannerView = null;

    //Dialog
    private Dialog dialog = null;
    private TextView txtReceiver = null;
    private TextView txtAmountCash = null;
    private TextView txtQuestion = null;
    private Button btnCancel = null;
    private Button btnConfirm = null;

    private String currentCash = null;
    private String currentReceiver = null;

    public PayCashFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanQRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayCashFragment newInstance(String param1, String param2) {
        PayCashFragment fragment = new PayCashFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pay_cash, container, false);
        scannerView = (ZXingScannerView) root.findViewById(R.id.view_scan_qr);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null){
            PermissionRequest.newInstance(getActivity()).requestCamera();
            qrScanner();
        }
    }

    private void createDialog(String code, String cash){
        dialog = new Dialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_check_pay);

        txtQuestion = (TextView) dialog.findViewById(R.id.title_receiver);
        txtReceiver = (TextView) dialog.findViewById(R.id.title_detail_receive);
        txtAmountCash = (TextView) dialog.findViewById(R.id.amount_cash);
        btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        currentCash = FormatCash.newInstance().setFormatCash(Double.parseDouble(cash));
        currentReceiver = code;

        txtReceiver.setText("ชำระเงินไปยัง " + code + " เป็นจำนวนเงิน: ");
        txtQuestion.setText(R.string.content_dialog_th);
        txtAmountCash.setText(currentCash+" " + getString(R.string.cash_thai));
    }

    public void qrScanner(){
//        scannerView = new ZXingScannerView(getActivity());
//        getActivity().setContentView(scannerView);

        scannerView.setResultHandler(this);
    }

    @Override
    public void handleResult(Result result) {
        if (!result.getText().trim().isEmpty() && result.getText() != null){
            String[] value = result.getText().split(",");
            Log.d("Scan", result.getText());
            if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_wallet))){
                if (value.length == 2){
                    Toast.makeText(getActivity(), value[0] + ", " + value[1], Toast.LENGTH_SHORT).show();
                    createDialog(value[0], value[1]);
                    dialog.show();
                }else{
                    Toast.makeText(getActivity(), "QR CODE incorrect", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).callCamera();
                }
            }
            else if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_qrcard))){
                ModelCart.getInstance().getAccountUser().accountNo = value[0];
                ((MainActivity)getActivity()).showLoadingDialog();
                ClientHttp.getInstance(getActivity()).requestScanQR(ModelCart.getInstance().getAccountUser());
            }
        }
    }

    public void resumeCamera(){
        scannerView.resumeCameraPreview(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_cancel){
            dialog.cancel();
            resumeCamera();
        }
        if (view.getId() == R.id.btn_confirm){
            dialog.cancel();
            ModelCart.getInstance().getAccountUser().code = currentReceiver;
            ModelCart.getInstance().getAccountUser().amount = currentCash;
            ((MainActivity)getActivity()).showLoadingDialog();
            ClientHttp.getInstance(getActivity()).requestScanQR(ModelCart.getInstance().getAccountUser());
        }
    }
}
