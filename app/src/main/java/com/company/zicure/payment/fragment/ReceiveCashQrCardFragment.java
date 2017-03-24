package com.company.zicure.payment.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.zicure.payment.R;
import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.network.ClientHttp;
import com.company.zicure.payment.util.ModelCart;
import com.joooonho.SelectableRoundedImageView;

import gallery.zicure.company.com.gallery.util.ResizeScreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiveCashQrCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiveCashQrCardFragment extends Fragment implements EditText.OnEditorActionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imgPayer = null;

    //View
    private EditText editCash = null;
    private InputMethodManager keySoft = null;

    public ReceiveCashQrCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceiveCashQrCard.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiveCashQrCardFragment newInstance(String param1, String param2) {
        ReceiveCashQrCardFragment fragment = new ReceiveCashQrCardFragment();
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
        View root = inflater.inflate(R.layout.fragment_receive_cash_qr_card, container, false);
        imgPayer = (ImageView) root.findViewById(R.id.img_payer);
        editCash = (EditText) root.findViewById(R.id.edit_amount_pay);
        keySoft = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editCash.setOnEditorActionListener(this);
        if (savedInstanceState == null){
            setImgProfile();
        }
    }

    private void setImgProfile(){
        ResizeScreen resizeScreen = new ResizeScreen(getActivity());
        int width = resizeScreen.widthScreen(5);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) imgPayer.getLayoutParams();
        params.height = width;
        params.width = width;
        imgPayer.setLayoutParams(params);
        imgPayer.setColorFilter(ContextCompat.getColor(getActivity(), android.R.color.darker_gray));
        imgPayer.setImageResource(R.drawable.bank_card);
    }

    private void openKeyBoard(){
        keySoft.showSoftInput(editCash, InputMethodManager.SHOW_IMPLICIT);
    }

    private void closeKeyBoard(){
        keySoft.hideSoftInputFromWindow(editCash.getWindowToken(), 0);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
        switch (actionID){
            case EditorInfo.IME_ACTION_NEXT:
                if (!editCash.getText().toString().trim().isEmpty()){
                    ((MainActivity)getActivity()).showLoadingDialog();
                    ModelCart.getInstance().setMode(getString(R.string.txt_qrcard));
                    ModelCart.getInstance().getModel().accountUserModel.amount = Double.parseDouble(editCash.getText().toString().trim());
                    ClientHttp.getInstance(getActivity()).requestPay(ModelCart.getInstance().getModel().accountUserModel);
                    closeKeyBoard();
                }
                break;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeKeyBoard();
    }
}
