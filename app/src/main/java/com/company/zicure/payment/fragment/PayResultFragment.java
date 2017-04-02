package com.company.zicure.payment.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.R;
import com.zicure.company.com.model.util.FormatCash;
import com.zicure.company.com.model.util.ModelCart;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayResultFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String status;
    private String cash;

    //View
    private TextView txtResult = null;
    private TextView payAmount = null;
    private TextView dateTime = null;
    private TextView amountCard = null;
    private Button btnComplete = null;

    public PayResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayResultFragment newInstance(String param1, String param2) {
        PayResultFragment fragment = new PayResultFragment();
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
            status = getArguments().getString(ARG_PARAM1);
            cash = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pay_result, container, false);
        dateTime = (TextView) root.findViewById(R.id.date_time);
        payAmount = (TextView) root.findViewById(R.id.amount_pay);
        btnComplete = (Button) root.findViewById(R.id.btn_complete);
        txtResult = (TextView) root.findViewById(R.id.txt_result);
        amountCard = (TextView) root.findViewById(R.id.txt_amount_qrcard);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnComplete.setOnClickListener(this);
        if (savedInstanceState == null){
            setTextResult();
            setDateTime();
            if (!status.isEmpty()){
                try{
                    if (ModelCart.getInstance().getResponseScanQR().getResult().getBalanceDebit() != null){
                        if (ModelCart.getInstance().getMode().equalsIgnoreCase(getString(R.string.txt_qrcard))){
                            String currentCash = getString(R.string.card_amount_th) + " " + ModelCart.getInstance().getResponseScanQR().getResult().getBalanceDebit()
                                    + " " + getString(R.string.cash_thai);
                            amountCard.setText(currentCash);
                            amountCard.setVisibility(View.VISIBLE);
                        }
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    amountCard.setVisibility(View.GONE);
                }

                String resultAmount = cash + getString(R.string.cash_thai);
                payAmount.setText(resultAmount);
            }
        }
    }

    private void setDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        dateTime.setText(currentDate);
    }

    private void setTextResult(){
        if (ModelCart.getInstance().getAccountUser().type.equalsIgnoreCase("receive")){
            txtResult.setText(getString(R.string.receive_complete));
        }
        else if (ModelCart.getInstance().getAccountUser().type.equalsIgnoreCase("pay")){
            txtResult.setText(getString(R.string.pay_complete));
        }
    }

    @Override
    public void onClick(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ((MainActivity)getActivity()).callUserInfo();
    }
}
