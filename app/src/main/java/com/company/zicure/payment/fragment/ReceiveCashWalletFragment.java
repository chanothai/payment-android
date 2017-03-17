package com.company.zicure.payment.fragment;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.zicure.payment.R;
import com.company.zicure.payment.activity.MainActivity;
import com.company.zicure.payment.network.ClientHttp;
import com.company.zicure.payment.util.ModelCart;
import com.joooonho.SelectableRoundedImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import gallery.zicure.company.com.gallery.util.ResizeScreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReceiveCashWalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReceiveCashWalletFragment extends Fragment implements View.OnFocusChangeListener, TextWatcher, EditText.OnEditorActionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SelectableRoundedImageView imgPayer = null;

    //View
    private EditText inputCash = null;
    private InputMethodManager keySoft = null;

    public ReceiveCashWalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReceiveCashWalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiveCashWalletFragment newInstance(String param1, String param2) {
        ReceiveCashWalletFragment fragment = new ReceiveCashWalletFragment();
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
        View root = inflater.inflate(R.layout.fragment_receive_cash_wallet, container, false);
        inputCash = (EditText) root.findViewById(R.id.edit_amount_pay);
        imgPayer = (SelectableRoundedImageView) root.findViewById(R.id.img_payer);
        keySoft = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        inputCash.addTextChangedListener(this);
        inputCash.setOnFocusChangeListener(this);
        inputCash.requestFocus();
        inputCash.setOnEditorActionListener(this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
            openKeyBoard();
            setImgProfile();
        }
    }

    private void setImgProfile(){
        ResizeScreen resizeScreen = new ResizeScreen(getActivity());
        int width = resizeScreen.widthScreen(5);

        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) imgPayer.getLayoutParams();
        params2.height = width;
        params2.width = width;
        imgPayer.setLayoutParams(params2);
        imgPayer.setImageResource(R.drawable.ic_person_black_100dp);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_NEXT){
            if (!inputCash.getText().toString().trim().isEmpty()){
                ((MainActivity)getActivity()).showLoadingDialog();
                ModelCart.getInstance().getModel().accountUserModel.amount = Double.parseDouble(inputCash.getText().toString().trim());
                ClientHttp.getInstance(getActivity()).requestPay(ModelCart.getInstance().getModel().accountUserModel);
                closeKeyBoard();
            }
        }
        return false;
    }

    private void openKeyBoard(){
        keySoft.showSoftInput(inputCash, InputMethodManager.SHOW_IMPLICIT);
    }

    private void closeKeyBoard(){
        keySoft.hideSoftInputFromWindow(inputCash.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
